/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.render.text;

import javaslang.collection.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxTest {

    @Test
    public void shouldConnectBoxes() {

        final Box b0 = Box.of("TOP");

        final List<Box> list = List.of(
                Box.of("1234567"),
                Box.of("123456789"),
                Box.of("12345678901"),
                Box.of("123456789"),
                Box.of("Bye"));

        final Box boxed = b0.connect(list);

        assertThat(boxed.toString()).isEqualTo("" +
                "                    TOP                    \n" +
                "   ┌────────┬────────┴─┬──────────┬──────┐ \n" +
                "1234567 123456789 12345678901 123456789 Bye"
        );
    }
}
