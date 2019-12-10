package org.onecellboy.db.converters.impl;

import org.onecellboy.db.converters.AEnumTypeConverter;
import org.onecellboy.db.type.impl.LeakType;

/**
 *     @Convert(converter = LeakTypeConverter.class)
 *     @Column(name = "IS_LEAK")
 *     LeakType is_leak;
 *     이렇게 사용하면 된다.
 */
public class LeakTypeConverter extends AEnumTypeConverter<LeakType,Integer> {
    public LeakTypeConverter() {
        super(LeakType.class);
    }

}
