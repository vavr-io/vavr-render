package javaslang.render.text;

import javaslang.collection.List;
import javaslang.collection.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class Box {

    public enum HAlignment {LEFT, CENTER, RIGHT}

    public enum VAlignment {TOP, CENTER, BOTTOM}

    protected int w;
    protected int h;

    public static Box of(char c) {
        return new Basic(c, 1, 1);
    }

    public static Box of(char c, int width, int height) {
        return new Basic(c, width, height);
    }

    public static Box of(String text) {
        return new Basic(text, HAlignment.CENTER);
    }

    public static Box of(String text, HAlignment alignment) {
        return new Basic(text, alignment);
    }

    public static Box row(Box... boxes) {
        return row(VAlignment.CENTER, boxes);
    }

    public static Box row(VAlignment alignment, Box... boxes) {
        return new Row(alignment, boxes);
    }

    public static Box col(Box... boxes) {
        return new Col(HAlignment.CENTER, boxes);
    }

    public static Box col(HAlignment alignment, Box... boxes) {
        return new Col(alignment, boxes);
    }

    public int width() {
        return w;
    }

    public int height() {
        return h;
    }

    public Box frame() {
        return frame(true, true);
    }

    public Box frame(boolean noTopConnector, boolean noBottomConnector) {
        Basic b = toBasic();
        final int w = b.w;
        Box top = Box.of(noTopConnector ? replicate('─', w) : replicate('─', '┴', w));
        Box bottom = Box.of(noBottomConnector ? replicate('─', w) : replicate('─', '┬', w));
        Box vert = Box.of('│', 1, b.h);
        return Box.col(Box.row(Box.of("┌"), top, Box.of("┐")),
                Box.row(vert, b, vert),
                Box.row(Box.of("└"), bottom, Box.of("┘")));
    }

    public Box connect(Iterable<Box> iterable) {
        List<Box> list = List.ofAll(iterable);
        if (list.length() == 0) {
            return this;
        }
        if (list.length() == 1) {
            return Box.col(this, Box.of('│'), list.get(0));
        }
        StringBuffer buffer = new StringBuffer();
        int w0 = list.get(0).width();
        int w0a = w0 / 2;
        buffer.append(replicate(' ', w0a)).append('┌').append(replicate('─', w0 - w0a - 1));
        for (int i = 1; i < list.length() - 1; i++) {
            Box b = list.get(i);
            w0 = b.width();
            w0a = w0 / 2;
            buffer.append(replicate('─', w0a + 1)).append('┬')
                    .append(replicate('─', w0 - w0a - 1));
        }
        w0 = list.get(list.length() - 1).width();
        w0a = w0 / 2;
        buffer.append(replicate('─', w0a + 1)).append('┐').append(replicate(' ', w0 - w0a - 1));
        boolean box_has_even_width = width() % 2 == 0;
        int p = (buffer.length() - (box_has_even_width ? 0 : 1)) / 2;
        char c = buffer.charAt(p);
        buffer.setCharAt(p, c == '─' ? '┴' : '┼');

        Box bs2 = list.get(0);
        for (int i = 1; i < list.length(); i++) {
            bs2 = Box.row(VAlignment.TOP, bs2, Box.of(" "), list.get(i));
        }
        return Box.col(this, Box.of(buffer.toString()), bs2);
    }

    protected abstract Basic toBasic();

    protected static String replicate(char c, int n) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            buffer.append(c);
        }
        return buffer.toString();
    }

    protected static String replicate(char c, char connector, int n) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            buffer.append(c);
        }
        buffer.setCharAt((n-1) / 2, connector);
        return buffer.toString();
    }

    protected static String justify(String s, int width, HAlignment alignment) {
        final int leftWidth =
                alignment == HAlignment.LEFT ?
                        0 :
                        alignment == HAlignment.CENTER ?
                                (width - s.length()) / 2 :
                                width - s.length();
        return replicate(' ', leftWidth) + s + replicate(' ', width - leftWidth - s.length());
    }

    public static class Basic extends Box {

        protected String[] contents;

        private Basic(int w, int h, String[] contents) {
            this.w = w;
            this.h = h;
            this.contents = contents;
        }

        private Basic(String string, HAlignment alignment) {
            Objects.requireNonNull(string, "string is null");
            List<String> list = List.of(string.split("\n"));
            w = list.map(String::length).max().get();
            h = list.length();
            contents = list.map(s -> justify(s, w, alignment)).toJavaArray(String.class);
        }

        private Basic(char c, int width, int height) {
            w = width;
            h = height;
            String s = replicate(c, width);
            contents = Stream.continually(s).take(height).toJavaArray(String.class);
        }

        @Override
        protected Basic toBasic() {
            return this;
        }

        @Override
        public String toString() {
            if (contents.length > 0) {
                StringBuilder builder = new StringBuilder();
                for (String s : contents)
                    builder.append("\n").append(s);
                return builder.substring(1);
            }
            return "";
        }

        private Basic widen(int newWidth, HAlignment alignment) {
            if (newWidth <= w)
                return this;
            String[] result = new String[h];
            for (int i = 0; i < h; i++)
                result[i] = justify(contents[i], newWidth, alignment);
            return new Basic(newWidth, h, result);
        }

        private Basic heighten(int newHeight, VAlignment alignment) {
            if (newHeight <= h)
                return this;
            String[] result = new String[newHeight];
            Arrays.fill(result, Box.replicate(' ', w));
            int topHeight =
                    alignment == VAlignment.TOP ?
                            0 :
                            alignment == VAlignment.CENTER ?
                                    (newHeight - h) / 2 :
                                    newHeight - h;
            System.arraycopy(contents, 0, result, topHeight, h);
            return new Basic(w, newHeight, result);
        }
    }

    public static abstract class Compound extends Box {

        protected Basic[] boxes;

        @Override
        public String toString() {
            return toBasic().toString();
        }
    }

    public static class Row extends Compound {
        private VAlignment alignment;

        private Row(VAlignment alignment, Box... contents) {
            w = 0;
            h = 0;
            ArrayList<Basic> list = new ArrayList<>();
            for (Box b : contents) {
                h = Math.max(h, b.height());
                w += b.width();
                if (b instanceof Row)
                    list.addAll(Arrays.asList(((Row) b).boxes));
                else
                    list.add(b.toBasic());
            }
            boxes = list.toArray(new Basic[list.size()]);
            this.alignment = alignment;
        }

        @Override
        protected Basic toBasic() {
            String[] result = new String[h];
            Arrays.fill(result, "");
            for (Basic b : boxes) {
                String[] s = b.heighten(h, alignment).contents;
                for (int i = 0; i < h; i++)
                    result[i] += s[i];
            }
            return new Basic(w, h, result);
        }
    }

    public static class Col extends Compound {
        private HAlignment alignment;

        private Col(HAlignment alignment, Box... contents) {
            w = 0;
            h = 0;
            ArrayList<Basic> list = new ArrayList<>();
            for (Box b : contents) {
                h += b.height();
                w = Math.max(w, b.width());
                if (b instanceof Col)
                    list.addAll(Arrays.asList(((Col) b).boxes));
                else
                    list.add(b.toBasic());
            }
            boxes = list.toArray(new Basic[list.size()]);
            this.alignment = alignment;
        }

        @Override
        public Basic toBasic() {
            String[] result = new String[h];
            int pos = 0;
            for (Basic b : boxes) {
                String[] s = b.widen(w, alignment).contents;
                System.arraycopy(s, 0, result, pos, s.length);
                pos += s.length;
            }
            return new Basic(w, h, result);
        }
    }
}
