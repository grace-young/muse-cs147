
package example.com.gracie.muse;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Gracie on 12/2/2016.
 */

public class CButton extends Button {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public CButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Montserrat-Regular.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Montserrat-Regular.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Montserrat-Regular.ttf");
        super.setTypeface(tf);
    }
}
