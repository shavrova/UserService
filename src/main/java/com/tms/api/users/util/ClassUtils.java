package com.tms.api.users.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClassUtils {

    protected ClassUtils() { }

    public static <T> void setIfNotNull(final Supplier<T> getter, final Consumer<T> setter) {

        T t = getter.get();

        if (null != t ) {
            setter.accept(t);
        }
    }
}