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
    public void shouldComposeBoxes() {

        final Box b1 = Box.of('@');
        final Box b2 = Box.of('*', 30, 4);
        final Box b3 = Box.of("|we are happy today|");
        final Box b4 = Box.of("happy\nnew\nyear");
        final Box b5 = Box.of("Alexander\nis\na\ngood\nman", Box.HAlignment.LEFT);
        final Box b6 = Box.of("Javaslang\nis\na\nfunctional\nlibrary\nfor\nJava\n8+", Box.HAlignment.RIGHT);

        assertThat(b1.toString()).isEqualTo("@");

        assertThat(b2.toString()).isEqualTo("" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "******************************");

        assertThat(b3.toString()).isEqualTo("|we are happy today|");

        assertThat(b4.toString()).isEqualTo("" +
                "happy\n" +
                " new \n" +
                "year ");

        assertThat(b5.toString()).isEqualTo("" +
                "Alexander\n" +
                "is       \n" +
                "a        \n" +
                "good     \n" +
                "man      ");

        assertThat(b6.toString()).isEqualTo("" +
                " Javaslang\n" +
                "        is\n" +
                "         a\n" +
                "functional\n" +
                "   library\n" +
                "       for\n" +
                "      Java\n" +
                "        8+");

        final Box bv1 = Box.col(b1, b2, b3);
        final Box bv2 = Box.col(Box.HAlignment.LEFT, b1, b2, b3);
        final Box bv3 = Box.col(Box.HAlignment.RIGHT, b1, b2, b3);

        assertThat(bv1.toString()).isEqualTo("" +
                "              @               \n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "     |we are happy today|     ");

        assertThat(bv2.toString()).isEqualTo("" +
                "@                             \n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "|we are happy today|          ");

        assertThat(bv3.toString()).isEqualTo("" +
                "                             @\n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "******************************\n" +
                "          |we are happy today|");


        final Box b7 = Box.of(" --------- \n greetings \n --------- ");
        final Box b8 = Box.of(" ----- \n hello \n world \n ----- ");
        final Box b9 = Box.of(" ------ \n we \n are \n really \n happy \n today \n ------ ");
        final Box bh1 = Box.row(b7, b8, b9);
        final Box bh2 = Box.row(Box.VAlignment.TOP, b7, b8, b9);
        final Box bh3 = Box.row(Box.VAlignment.BOTTOM, b7, b8, b9);

        assertThat(bh1.toString()).isEqualTo("" +
                "                   ------ \n" +
                "            -----    we   \n" +
                " ---------  hello   are   \n" +
                " greetings  world  really \n" +
                " ---------  -----  happy  \n" +
                "                   today  \n" +
                "                   ------ ");

        assertThat(bh2.toString()).isEqualTo("" +
                " ---------  -----  ------ \n" +
                " greetings  hello    we   \n" +
                " ---------  world   are   \n" +
                "            -----  really \n" +
                "                   happy  \n" +
                "                   today  \n" +
                "                   ------ ");

        assertThat(bh3.toString()).isEqualTo("" +
                "                   ------ \n" +
                "                     we   \n" +
                "                    are   \n" +
                "            -----  really \n" +
                " ---------  hello  happy  \n" +
                " greetings  world  today  \n" +
                " ---------  -----  ------ ");
    }

    @Test
    public void shouldFrameBoxes() {

        final Box b = Box.of("happy\nnew\nyear");

        assertThat(b.frame().toString()).isEqualTo("" +
                "┌─────┐\n" +
                "│happy│\n" +
                "│ new │\n" +
                "│year │\n" +
                "└─────┘");

        assertThat(b.frame(false, false).toString()).isEqualTo("" +
                "┌──┴──┐\n" +
                "│happy│\n" +
                "│ new │\n" +
                "│year │\n" +
                "└──┬──┘");
    }

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
