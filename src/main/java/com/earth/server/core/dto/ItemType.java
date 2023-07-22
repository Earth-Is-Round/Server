package com.earth.server.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
@Getter
public enum ItemType {
    BUTTON("button"),
    STONES("stones"),
    SAW("saw"),
    CARROT("carrot"),
    RADISH("radish"),
    GLOVES("gloves"),
    CARDIGAN("cardigan"),
    BRANCH("branch"),
    EAR_MUFF("earMuff"),
    AIR_POD_MAX_1("airPodMax1"),
    AIR_POD_MAX_2("airPodMax2"),
    AIR_POD_MAX_3("airPodMax3"),
    MUFFLER_1("muffler1"),
    MUFFLER_2("muffler2"),
    BOOTS("boots"),
    BIKINI("bikini"),
    CAN("can"),
    SANTA_HAT("santaHat"),
    SUNGLASS("sunglass"),
    ;

    private static final Random RANDOM = new Random();

    public static ItemType randomItemType()  {
        ItemType[] itemTypes = values();
        return itemTypes[RANDOM.nextInt(itemTypes.length)];
    }

    private final String value;
}
