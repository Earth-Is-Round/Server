package com.earth.server.core.service;

import com.earth.server.common.domain.DomainException;
import com.earth.server.core.dto.Item;
import com.earth.server.core.dto.ItemType;
import com.earth.server.core.dto.ItemsResponse;
import com.earth.server.core.dto.StepRequest;
import com.earth.server.core.repository.*;
import com.earth.server.user.domain.UserId;
import com.earth.server.user.domain.UserRepository;
import com.earth.server.user.infra.persistence.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        UserEntity user = userRepository.find(userId.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER));

        if (request.steps().size() > 30) {
            throw new DomainException(INVALID_REQUEST);
        }

        return new ItemsResponse(request.steps()
                .stream()
                .flatMap(step -> {
                    Optional<StepEntity> prevStep = stepRepository.findByDateAndUser(step.date(), user);
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

    private List<Item> makeRandomItems(UserEntity user, int count, LocalDate date) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String randomItemTypeName = ItemType.randomItemType().getValue();
            itemRepository.save(new ItemEntity(date, randomItemTypeName, user, null));
            items.add(new Item(randomItemTypeName, date));
        }
        return items;
    }
}
