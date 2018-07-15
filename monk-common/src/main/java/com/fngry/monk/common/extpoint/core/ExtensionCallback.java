package com.fngry.monk.common.extpoint.core;

import java.util.function.Function;

public interface ExtensionCallback<T, R> extends Function<T, R> {
    R apply(T t);
}
