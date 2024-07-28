package org.sld.htmle;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
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

public final class CodeEditor extends EditText {

    private ArrayList<Token> tokens;
    private Context context;

    public void setContext(Context context) {
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

    public void setKeywords(Token[] keywords) {
        tokens = new ArrayList<>();
        for (Token token : keywords) {
            tokens.add(token);
        }
    }

    private final void init() {
        tokens = new ArrayList<>();
        setHorizontallyScrolling(true);

        setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getUnicodeChar() == '(') {
                            StringBuilder output = new StringBuilder();
                            int selectionStart = getSelectionStart();
                            String text = getText().toString();
                            output.append(
                                    text.substring(0, selectionStart)
                                            + ')'
                                            + text.substring(selectionStart + 1, text.length()));
                            setText(output.toString());

                            
                            return true;
                        }
                        return false;
                    }
                });

        addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence arg0, int arg1, int arg2, int arg3) {}

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        syntaxHighlight();
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {}
                });
    }

    public void coincidencesHighlight(String regex) {

        Matcher m = Pattern.compile(regex).matcher(getText().toString());

        while (m.find()) {
            getText()
                    .setSpan(
                            new BackgroundColorSpan(context.getColor(R.color.selections)),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void syntaxHighlight() {
        Pattern p;
        Matcher m;
        getText()
                .setSpan(
                        new BackgroundColorSpan(Color.parseColor("#00000000")),
                        0,
                        getText().toString().length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String text = getText().toString();
        for (Token token : tokens) {
            try {
                p = Pattern.compile(token.getWords());
                m = p.matcher(text);
                while (m.find()) {
                    getText()
                            .setSpan(
                                    new ForegroundColorSpan(token.getColor()),
                                    m.start(),
                                    m.end(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception e) {

            }
        }
    }
}
