package com.earth.server.core.service;

import com.earth.server.common.domain.DomainException;
import com.earth.server.core.dto.*;
import com.earth.server.core.repository.*;
import com.earth.server.user.domain.UserId;
import com.earth.server.user.domain.UserRepository;
import com.earth.server.user.infra.persistence.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static com.earth.server.common.domain.ErrorCode.INVALID_REQUEST;
import static com.earth.server.common.domain.ErrorCode.NOT_EXIST_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class CoreService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final StepRepository stepRepository;
    private final SnowmanRepository snowmanRepository;

    public ItemsResponse recordSteps(UserId userId, StepRequest request) {
        UserEntity user = getUser(userId);

        if (request.steps().size() > 30) {
            throw new DomainException(INVALID_REQUEST);
        }

        return new ItemsResponse(request.steps()
                .stream()
                .flatMap(step -> {
                    Optional<StepEntity> prevStep = stepRepository.findByUserAndDate(user, step.date());
                    if (prevStep.isPresent()) {
                        // getPickedItems 은 테스트를 위한 로직
                        return getPickedItems(step, user);
                        // TODO: 아래가 정상 로직, 테스트가 끝나면 아래로 수정.
                        // return Stream.empty();
                    }
                    stepRepository.save(new StepEntity(user, step.date(), step.count()));

                    if (step.count() <= 3000) {
                        return makeRandomItems(user, 0, step.date()).stream();
                    }
                    if (step.count() <= 4000) {
                        return makeRandomItems(user, 1, step.date()).stream();
                    }
                    if (step.count() <= 5000) {
                        return makeRandomItems(user, 2, step.date()).stream();
                    }
                    if (step.count() <= 6000) {
                        return makeRandomItems(user, 3, step.date()).stream();
                    }
                    return makeRandomItems(user, 4, step.date()).stream();
                })
                .toList());
    }

    private Stream<Item> getPickedItems(Step step, UserEntity user) {
        List<ItemEntity> pickedItems = itemRepository.findAllByUserAndDate(user, step.date());
        return pickedItems.stream()
                .map(itemEntity -> new Item(itemEntity.getName(), itemEntity.getDate()))
                .toList()
                .stream();
    }

    public void calculateSnowmen(UserId userId, CalculateRequest request) {
        UserEntity user = getUser(userId);

        if (request.calculatingList().size() > 5) {
            throw new DomainException(INVALID_REQUEST);
        }

        request.calculatingList()
                .forEach(calculating -> {
                    if (calculating.calculateDate().getDayOfWeek() != DayOfWeek.SUNDAY) {
                        throw new DomainException(INVALID_REQUEST);
                    }

                    SnowmanEntity newSnowman = snowmanRepository.save(new SnowmanEntity(
                            calculating.calculateDate().minusDays(6),
                            calculating.calculateDate(),
                            user,
                            calculating.headSize(),
                            calculating.bodySize()
                    ));

                    List<ItemEntity> pickedUpItems = itemRepository.findAllByUserAndDateBetween(
                            user,
                            calculating.calculateDate().minusDays(6),
                            calculating.calculateDate());

                    Arrays.stream(Position.values()).toList()
                            .stream()
                            .filter(Position::wearable)
                            .map(position -> {
                                Collections.shuffle(pickedUpItems);
                                Optional<ItemEntity> wearableItem = pickedUpItems.stream()
                                        .filter(item -> item.getPosition().equals(position.name()))
                                        .findFirst();
                                return wearableItem.orElse(null);
                            })
                            .filter(Objects::nonNull)
                            .forEach(item -> item.setSnowman(newSnowman));
                });
    }

    private UserEntity getUser(UserId userId) {
        return userRepository.find(userId.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER));
    }

    private List<Item> makeRandomItems(UserEntity user, int count, LocalDate date) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ItemType randomItemType = ItemType.randomItemType();
            itemRepository.save(new ItemEntity(date, randomItemType.getValue(), user, null, randomItemType.getPosition().name()));
            items.add(new Item(randomItemType.getValue(), date));
        }
        return items;
    }

    public RecordResponse getRecord(UserId userId, LocalDate startDate) {
        UserEntity user = getUser(userId);
        SnowmanEntity snowman = snowmanRepository.findByUserAndStartDate(user, startDate).orElseThrow(() -> new DomainException(INVALID_REQUEST));
        List<ItemEntity> pickedUpItems = itemRepository.findAllByUserAndDateBetween(
                user,
                snowman.getStartDate(),
                snowman.getEndDate()
        );
        List<ItemEntity> usedItems = pickedUpItems.stream().filter(entity -> entity.getSnowman() != null).toList();
        List<StepEntity> steps = stepRepository.findAllByUserAndDateBetweenOrderByDateAsc(
                user,
                snowman.getStartDate(),
                snowman.getEndDate());

        return new RecordResponse(
                pickedUpItems
                        .stream()
                        .map(entity -> new Item(entity.getName(), entity.getDate()))
                        .toList(),
                steps.stream().map(StepEntity::getCount).toList(),
                new Snowman(snowman, usedItems)
        );
    }

    public SnowmanRecordListResponse getRecords(UserId userId, LocalDate maxDate) {
        UserEntity user = getUser(userId);
        // 최대 10개의 눈사람만 조회
        List<SnowmanEntity> snowmen = snowmanRepository.findFirst10ByUserAndStartDateLessThanEqual(user, maxDate);
        long snowmanCount = snowmanRepository.countByUserAndStartDateLessThanEqual(user, maxDate);

        return new SnowmanRecordListResponse(
                snowmanCount,
                snowmen.stream()
                        .map(snowmanEntity -> {
                            List<ItemEntity> pickedUpItems = itemRepository.findAllByUserAndDateBetween(
                                    user,
                                    snowmanEntity.getStartDate(),
                                    snowmanEntity.getEndDate()
                            );
                            List<ItemEntity> usedItems = pickedUpItems.stream().filter(entity -> entity.getSnowman() != null).toList();
                            return new Snowman(snowmanEntity, usedItems);
                        })
                        .toList()
        );
    }
}
