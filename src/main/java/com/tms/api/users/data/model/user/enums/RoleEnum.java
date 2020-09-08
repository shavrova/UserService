package com.tms.api.users.data.model.user.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    USER(1, "USER"),
    ADMIN(2, "ADMIN");

    @Setter
    @Getter
    int code;
    @Setter
    @Getter
    String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, RoleEnum> keyMap = new HashMap<>();

    static {
        for (RoleEnum item : RoleEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static RoleEnum fromCode(Integer code) {
        return keyMap.get(code);
    }


    public String getRoleName() {
        return "ROLE_" + name;
    }

}

