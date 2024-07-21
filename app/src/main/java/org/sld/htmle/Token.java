package org.sld.htmle;

import android.graphics.Color;

public class Token {

    private String words;
    private int color;

    public Token(String words, int color) {
        this.words = words;
        this.color = color;
    }

    public String getWords() {
        return this.words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
