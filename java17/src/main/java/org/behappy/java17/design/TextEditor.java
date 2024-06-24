package org.behappy.java17.design;

import java.lang.reflect.InvocationTargetException;

class TextEditor {
    StringBuilder sb = new StringBuilder();
    int cursor = 0;

    public TextEditor() {}

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TextEditor te = new TextEditor();
        te.addText("leetcode");
        System.out.println(te.deleteText(4));
        te.addText("practice");
        System.out.println(te.cursorRight(3));
        System.out.println(te.cursorLeft(8));
        te.deleteText(10);
        System.out.println(te.cursorLeft(2));
        System.out.println(te.cursorRight(6));

    }

    public void addText(String text) {
        sb.insert(cursor, text);
        cursor += text.length();
    }

    public int deleteText(int k) {
        int left = Math.max(0, cursor - k);
        int len = cursor - left;
        sb.delete(left, cursor);
        cursor = left;
        return len;
    }

    public String cursorLeft(int k) {
        cursor = Math.max(0, cursor - k);
        int left = Math.max(0, cursor - 10);
        return sb.substring(left, cursor);
    }

    public String cursorRight(int k) {
        cursor = Math.min(sb.length(), cursor + k);
        int left = Math.max(0, cursor - 10);
        return sb.substring(left, cursor);
    }
}
