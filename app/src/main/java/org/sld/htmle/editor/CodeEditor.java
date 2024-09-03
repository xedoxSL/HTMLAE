package org.sld.htmle;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.sld.htmle.editor.Token;

public final class CodeEditor extends EditText {

    private ArrayList<Token> tokens;
    private Context context;

    public final void setContext(Context context) {
        this.context = context;
    }

    public CodeEditor(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CodeEditor(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        init();
    }

    public final void setKeywords(Token[] keywords) {
        tokens = new ArrayList<>();
        for (Token token : keywords) {
            tokens.add(token);
        }
    }

    private final void init() {
        tokens = new ArrayList<>();
        setHorizontallyScrolling(true);
        defaultKeywords();
        hightlightKeywords();
        addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence arg0, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        getText()
                                .setSpan(
                                        new BackgroundColorSpan(Color.TRANSPARENT),
                                        0,
                                        getText().length(),
                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        hightlightKeywords();
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });
    }

    private void hightlightKeywords() {
        Matcher m;
        for (Token key : tokens) {
            m = Pattern.compile(key.getKeyword()).matcher(getText().toString());
            while (m.find()) {
                getText()
                        .setSpan(
                                new ForegroundColorSpan(key.getColor()),
                                m.start(),
                                m.end(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private final void defaultKeywords() {
        Token[] keywords = {
            new Token(".*?", context.getColor(R.color.textColor)),
            new Token("(\\d+)", context.getColor(R.color.number)),
            new Token("(\\b[a-zA-Z\\d_]+\\()", context.getColor(R.color.func)),
            new Token("(<|>|/|\\(|\\))", context.getColor(R.color.tag)),
            new Token("<!--.*?-->", context.getColor(R.color.string)),
            new Token("\"([^\"]*)\"", context.getColor(R.color.string))
        };
        setKeywords(keywords);
    }

    public final void spanBackground(String regex, int color) {
        Matcher m = Pattern.compile(regex).matcher(getText().toString());
        while (m.find()) {
            getText()
                    .setSpan(
                            new BackgroundColorSpan(color),
                            m.start(),
                            m.end(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public final void replace(String regex, String two) {
        Matcher m = Pattern.compile(regex).matcher(getText().toString());
        while (m.find()) {
            getText().toString().replaceAll(m.group(), two);
        }
    }
}
