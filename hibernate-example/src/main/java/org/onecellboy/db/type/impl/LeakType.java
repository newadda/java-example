package org.onecellboy.db.type.impl;

import org.onecellboy.db.type.IEnumType;


public enum LeakType implements IEnumType<Integer> {
    NOTLEAK(0),
    LEAK(0),
    UNKNOWN(0);

    private Integer code;

    LeakType(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
