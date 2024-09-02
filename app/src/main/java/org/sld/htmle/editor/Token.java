package org.sld.htmle.editor;

public final class Token {
    
    private final String keywords;
    private final int color;

    public Token(String keywords, int color) {
        this.keywords = keywords;
        this.color = color;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public int getColor() {
        return this.color;
    }
}
