package org.sld.htmle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CodeEditor extends EditText {

    private ArrayList<Token> tokens;
    private Context context;
    
    public void setContext(Context context){
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

    private void syntaxHighlight() {
        Pattern p;
        Matcher m;
        getText().setSpan(new ForegroundColorSpan(context.getColor(R.color.textColor)), 0, getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
