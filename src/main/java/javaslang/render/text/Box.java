package javaslang.render.text;

import javaslang.collection.List;
import javaslang.collection.Stream;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public final class Box implements Serializable {

    private static final long serialVersionUID = 0L;

    private final String[] contents;

    private Box(String[] contents) {
        this.contents = contents;
    }

    public static Box of(String string) {
        Objects.requireNonNull(string, "string is null");
        List<String> list = List.of(string.split("\n"));
        int w = list.map(String::length).max().get();
        String[] contents = list.map(s -> {
            int n = s.length();
            return replicate(' ', (w - n) / 2) + s + replicate(' ', w - n - (w - n) / 2);
        }).toJavaArray(String.class);
        return new Box(contents);
    }

    public static Box of(String... s) {
        return new Box(s);
    }

    public static Box empty() {
        return new Box(new String[0]);
    }

    public static Box of(char c) {
        return new Box(new String[] { "" + c });
    }

    public static Box of(char c, int width, int height) {
        String s = replicate(c, width);
        String[] contents = Stream.gen(() -> s).take(height).toJavaArray(String.class);
        return new Box(contents);
    }

    @Override
    public String toString() {
        return List.of(contents).mkString("\n");
    }

    public int width() {
        return contents.length == 0 ? 0 : contents[0].length();
    }

    public int height() {
        return contents.length;
    }

    public Box widen(int w) {
        int wid = width();
        if (w <= wid) {
            return this;
        }
        int hei = height();
        Box left = Box.of(' ', (w - wid) / 2, hei);
        Box right = Box.of(' ', w - wid - left.width(), hei);
        return left.beside(this).beside(right);
    }

    public Box heighten(int h) {
        int hei = height();
        if (h <= hei) {
            return this;
        }
        int wid = width();
        Box top = Box.of(' ', wid, (h - hei) / 2);
        Box bottom = Box.of(' ', wid, h - hei - top.height());
        return top.above(this).above(bottom);
    }

    public Box heighten2(int h) {
        int hei = height();
        if (h <= hei) {
            return this;
        }
        int wid = width();
        Box bottom = Box.of(' ', wid, h - hei);
        return above(bottom);
    }

    public Box beside(Box b) {
        Box b1 = heighten(b.height());
        Box b2 = b.heighten(height());
        int h = b1.height();
        String[] result = Arrays.copyOf(b1.contents, b1.contents.length);
        for (int i = 0; i < h; i++) {
            result[i] += b2.contents[i];
        }
        return new Box(result);
    }

    public Box beside2(Box b) {
        Box b1 = heighten2(b.height());
        Box b2 = b.heighten2(height());
        int h = b1.height();
        String[] result = Arrays.copyOf(b1.contents, b1.contents.length);
        for (int i = 0; i < h; i++) {
            result[i] += b2.contents[i];
        }
        return new Box(result);
    }

    public Box above(Box b) {
        Box b1 = widen(b.width());
        Box b2 = b.widen(width());
        String[] result = Arrays.copyOf(b1.contents, b1.contents.length + b2.contents.length);
        System.arraycopy(b2.contents, 0, result, b1.contents.length, b2.contents.length);
        return new Box(result);
    }

    public Box frame() {
        Box horiz = Box.of('─', width(), 1);
        Box vert = Box.of('│', 1, height());
        return (Box.of("┌").beside(horiz).beside(Box.of("┐")))
                .above(vert.beside(this).beside(vert))
                .above(Box.of("└").beside(horiz).beside(Box.of("┘")));
    }

    public Box connect(Iterable<Box> iterable) {
        List<Box> list = List.ofAll(iterable);
        if (list.length() == 0) {
            return this;
        }
        if (list.length() == 1) {
            return this.above(Box.of('│')).above(list.get(0));
        }
        StringBuffer buffer = new StringBuffer();
        int p = list.get(0).width() / 2;
        buffer.append(replicate(' ', p)).append('┌').append(replicate('─', p));
        for (int i = 1; i < list.length() - 1; i++) {
            Box b = list.get(i);
            p = b.width() / 2;
            buffer.append(replicate('─', p + 1)).append('┬')
                    .append(replicate('─', p));
        }
        p = list.get(list.length() - 1).width() / 2;
        buffer.append(replicate('─', p + 1)).append('┐').append(replicate(' ', p));
        p = buffer.length() / 2;
        char c = buffer.charAt(p);
        buffer.setCharAt(p, c == '─' ? '┴' : '┼');

        Box bs2 = list.get(0);
        for (int i = 1; i < list.length(); i++) {
            bs2 = bs2.beside(Box.of(" ")).beside2(list.get(i));
        }
        return this.above(Box.of(buffer.toString())).above(bs2);
    }

    private static String replicate(char c, int n) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            buffer.append(c);
        }
        return buffer.toString();
    }
}
