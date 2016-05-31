package com.github.fcannizzaro.materialtip;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.fcannizzaro.materialtip.util.AnimationAdapter;
import com.github.fcannizzaro.materialtip.util.ButtonListener;
import com.github.fcannizzaro.materialtip.util.InternalBehavior;

/**
 * Francesco Cannizzaro (fcannizzaro)
 */
@SuppressWarnings("unused")
public class MaterialTip extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {

    private Context context;
    private ButtonListener listener;
    private Button positive, negative;
    private TextView title, text;
    private ImageView icon;
    private View tip;
    private int color, background, titleColor, textColor;
    private float eventY, MIN_DISTANCE;
    private boolean visible;

    public MaterialTip(Context context) {
        super(context);
        init();
    }

    public MaterialTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTip, 0, 0);

        try {

            String positiveStr = array.getString(R.styleable.MaterialTip_tip_positive);
            String negativeStr = array.getString(R.styleable.MaterialTip_tip_negative);

            color = array.getColor(R.styleable.MaterialTip_tip_color, ContextCompat.getColor(context, R.color.colorPrimary));
            background = array.getColor(R.styleable.MaterialTip_tip_background, ContextCompat.getColor(context, R.color.tip_background));
            titleColor = array.getColor(R.styleable.MaterialTip_tip_title_color, ContextCompat.getColor(context, R.color.tip_title));
            textColor = array.getColor(R.styleable.MaterialTip_tip_text_color, ContextCompat.getColor(context, R.color.tip_text));
            title.setText(array.getString(R.styleable.MaterialTip_tip_title));
            text.setText(array.getString(R.styleable.MaterialTip_tip_text));
            positive.setText(positiveStr);
            negative.setText(negativeStr);
            icon.setImageDrawable(array.getDrawable(R.styleable.MaterialTip_tip_icon));


            checkColor();
            checkButtonsVisibility();
            checkIconVisibility();

        } finally {
            array.recycle();
        }

    }

    public MaterialTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        context = getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tip_layout, this, true);

        tip = findViewById(R.id.tip);
        positive = (Button) findViewById(R.id.tip_positive);
        negative = (Button) findViewById(R.id.tip_negative);
        title = (TextView) findViewById(R.id.tip_title);
        text = (TextView) findViewById(R.id.tip_text);
        icon = (ImageView) findViewById(R.id.tip_icon);

        tip.setOnTouchListener(this);
        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

        waitHeight();

    }

    private void applyBehaviour() {
        CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) getLayoutParams();
        paramsFab.setBehavior(new InternalBehavior(context, null));
        requestLayout();
    }

    // Builder Methods

    public void withButtonListener(ButtonListener listener) {
        this.listener = listener;
    }

    // direct

    @Deprecated
    public MaterialTip withTitleVisible(boolean titleVisible) {
        this.title.setVisibility(titleVisible ? VISIBLE : GONE);
        return this;
    }

    public MaterialTip withTitle(String title) {
        this.title.setText(title);
        checkButtonsVisibility();
        return this;
    }

    public MaterialTip withIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
        checkIconVisibility();
        return this;
    }

    public MaterialTip withText(String text) {
        this.text.setText(text);
        return this;
    }

    public MaterialTip withPositive(String positive) {
        this.positive.setText(positive);
        checkButtonsVisibility();
        return this;
    }

    public MaterialTip withNegative(String negative) {
        this.negative.setText(negative);
        checkButtonsVisibility();
        return this;
    }

    public MaterialTip withIconRes(@DrawableRes int icon) {
        this.icon.setImageResource(icon);
        checkIconVisibility();
        return this;
    }

    public MaterialTip withColor(@ColorInt int color) {
        this.color = color;
        checkColor();
        return this;
    }

    public MaterialTip withBackground(@ColorInt int background) {
        this.background = background;
        checkColor();
        return this;
    }

    public MaterialTip withTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        checkColor();
        return this;
    }

    public MaterialTip withTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        checkColor();
        return this;
    }

    // From Resources

    public MaterialTip withColorRes(@ColorRes int color) {
        this.color = ContextCompat.getColor(context, color);
        checkColor();
        return this;
    }

    public MaterialTip withTextColorRes(@ColorRes int textColor) {
        this.textColor = ContextCompat.getColor(context, textColor);
        checkColor();
        return this;
    }

    public MaterialTip withTitleColorRes(@ColorRes int titleColor) {
        this.titleColor = ContextCompat.getColor(context, titleColor);
        checkColor();
        return this;
    }

    public MaterialTip withBackgroundRes(@ColorRes int background) {
        this.background = ContextCompat.getColor(context, background);
        checkColor();
        return this;
    }

    public MaterialTip withTitleRes(@StringRes int title) {
        this.title.setText(context.getText(title));
        checkButtonsVisibility();
        return this;
    }

    public MaterialTip withTextRes(@StringRes int text) {
        this.text.setText(context.getText(text));
        return this;
    }

    public MaterialTip withPositiveRes(@StringRes int positive) {
        this.positive.setText(context.getText(positive));
        checkButtonsVisibility();
        return this;
    }

    public MaterialTip withNegativeRes(@StringRes int negative) {
        this.negative.setText(context.getText(negative));
        checkButtonsVisibility();
        return this;
    }

    public void show() {

        animate()
                .translationY(0)
                .setDuration(300)
                .setListener(new AnimationAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visible = true;
                    }
                })
                .start();

    }

    public void hide() {

        animate()
                .translationY(getHeight())
                .setDuration(300)
                .setListener(new AnimationAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visible = false;
                    }
                })
                .start();
    }

    public void toggle() {
        if (visible)
            hide();
        else
            show();
    }

    private void checkColor() {

        // text color
        title.setTextColor(titleColor);
        text.setTextColor(textColor);
        negative.setTextColor(color);

        // background
        tip.setBackgroundColor(background);
        negative.setBackgroundColor(background);
        positive.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void checkButtonsVisibility() {
        positive.setVisibility(positive.getText().toString().isEmpty() ? GONE : VISIBLE);
        negative.setVisibility(negative.getText().toString().isEmpty() ? GONE : VISIBLE);
        title.setVisibility(title.getText().toString().isEmpty() ? GONE : VISIBLE);
    }

    private void checkIconVisibility() {
        icon.setVisibility(icon.getDrawable() == null ? GONE : VISIBLE);
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (listener != null) {

            if (id == R.id.tip_positive)
                listener.onPositive(this);

            else if (id == R.id.tip_negative)
                listener.onNegative(this);
        }

        hide();

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                eventY = event.getY();
                break;

            case MotionEvent.ACTION_UP:

                if (event.getY() - eventY >= MIN_DISTANCE)
                    hide();

                break;
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void waitHeight() {

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);

                MIN_DISTANCE = getHeight() / 4;
                setTranslationY(getHeight());
                applyBehaviour();

            }
        });
    }

}
