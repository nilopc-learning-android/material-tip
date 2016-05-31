package com.github.fcannizzaro.materialtip.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.github.fcannizzaro.materialtip.MaterialTip;

/**
 * Francesco Cannizzaro (fcannizzaro)
 */
public class InternalBehavior extends CoordinatorLayout.Behavior<MaterialTip> {

    public InternalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, MaterialTip child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, MaterialTip child, View dependency) {
        if (child.isVisible())
            child.setTranslationY(Math.min(0, dependency.getTranslationY() - dependency.getHeight()));
        return true;
    }

}