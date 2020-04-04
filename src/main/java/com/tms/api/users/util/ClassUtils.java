package com.tms.api.users.util;

import lombok.NoArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Supplier;

@NoArgsConstructor
public class ClassUtils {

    public static <T> void setIfNotNull(final Supplier<T> getter, final Consumer<T> setter) {

        T t = getter.get();

        if (null != t) {
            setter.accept(t);
        }
    }
}