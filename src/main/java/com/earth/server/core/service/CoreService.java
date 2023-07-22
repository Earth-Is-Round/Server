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
                        return Stream.empty();
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
}
