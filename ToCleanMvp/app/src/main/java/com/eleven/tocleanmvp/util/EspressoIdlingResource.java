package com.eleven.tocleanmvp.util;

import android.support.test.espresso.IdlingResource;

/**
 * @author vic Zhou
 * @time 2017-12-16 0:12
 * @des Contains a static reference to {@link IdlingResource}, only available in the 'mock' build type.
 */

public class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";
    private static final SimpleCountingIdlingResource DEFAULT_INSTANCE =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        DEFAULT_INSTANCE.increment();
    }

    public static void decrement() {
        DEFAULT_INSTANCE.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return DEFAULT_INSTANCE;
    }
}
