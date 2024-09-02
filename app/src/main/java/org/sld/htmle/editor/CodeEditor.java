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
    }
}
