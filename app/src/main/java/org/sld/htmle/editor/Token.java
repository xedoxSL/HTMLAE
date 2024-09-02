package org.sld.htmle.editor;

public final class Token {

    private final String keyword;
    private final int color;

    public Token(String keyword, int color) {
        this.keyword = keyword;
        this.color = color;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public int getColor() {
        return this.color;
    }
}
