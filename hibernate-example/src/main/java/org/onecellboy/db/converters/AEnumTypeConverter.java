package org.onecellboy.db.converters;

import org.onecellboy.db.type.IEnumType;


import javax.persistence.AttributeConverter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public  class AEnumTypeConverter<T extends IEnumType,S> implements AttributeConverter<T , S> {
    private Class<T> persistentClass;

    public AEnumTypeConverter(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public S convertToDatabaseColumn(IEnumType attribute) {
        return  (S)attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(S attribute) {
        try {
            Method values = persistentClass.getMethod("values");
            IEnumType[] invoke = (IEnumType [])values.invoke(null);
            for(IEnumType en:invoke)
            {
                if(en.getCode().equals(attribute)) {
                    return (T)en;
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
