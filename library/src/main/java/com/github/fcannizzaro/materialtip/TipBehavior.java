package com.github.fcannizzaro.materialtip;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Francesco Cannizzaro (fcannizzaro)
 */
public class TipBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private MaterialTip tip;

    public TipBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        if (dependency instanceof MaterialTip)
            tip = (MaterialTip) dependency;

        boolean tipVisible = tip != null && !tip.isVisible() || tip == null;

        return dependency instanceof MaterialTip || tipVisible && dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        child.setTranslationY(Math.min(0, dependency.getTranslationY() - dependency.getHeight()));
        return true;
    }

}
