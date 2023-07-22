package com.earth.server.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

import static com.earth.server.core.dto.Position.*;

@RequiredArgsConstructor
@Getter
public enum ItemType {
    BUTTON("button", EYES),
    STONES("stones", MOUTH),
    SAW("saw", NOSE),
    CARROT("carrot", NOSE),
    RADISH("radish", NOSE),
    GLOVES("gloves", HAND),
    CARDIGAN("cardigan", BODY),
    BRANCH("branch", HAND),
    EAR_MUFF("earMuff", EAR),
    AIR_POD_MAX_1("airPodMax1", EAR),
    AIR_POD_MAX_2("airPodMax2", EAR),
    AIR_POD_MAX_3("airPodMax3", EAR),
    MUFFLER_1("muffler1", NECK),
    MUFFLER_2("muffler2", NECK),
    BOOTS("boots", FOOT),
    BIKINI("bikini", BODY),
    CAN("can", HEAD),
    SANTA_HAT("santaHat", HEAD),
    SUNGLASS("sunglass", HEAD),
    ;

    private static final Random RANDOM = new Random();

    public static ItemType randomItemType() {
        ItemType[] itemTypes = values();
        return itemTypes[RANDOM.nextInt(itemTypes.length)];
    }

    private final String value;
    private final Position position;

}
