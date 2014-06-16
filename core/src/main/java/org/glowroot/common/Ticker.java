/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.glowroot.common;

import org.glowroot.markers.ThreadSafe;

/**
 * Basically the same as Guava's {@link com.google.common.base.Ticker} class, but doesn't get shaded
 * so it can be used from other modules (e.g. microbenchmarks).
 * 
 * @author Trask Stalnaker
 * @since 0.5
 */
@ThreadSafe
public abstract class Ticker {

    private static final Ticker SYSTEM_TICKER = new Ticker() {
        @Override
        public long read() {
            return System.nanoTime();
        }
    };

    public abstract long read();

    public static Ticker systemTicker() {
        return SYSTEM_TICKER;
    }

    // Nano times roll over every 292 years, so it is important to test differences between nano
    // times instead of direct comparison (e.g. nano2 - nano1 >= 0, not nano1 <= nano2)
    // (see http://java.sun.com/javase/7/docs/api/java/lang/System.html#nanoTime())
    public static boolean lessThanOrEqual(long tick1, long tick2) {
        return tick2 - tick1 >= 0;
    }
}