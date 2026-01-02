package com.example.fate;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

public class PageTurnTransition extends Transition {

    private static final String PROPERTY_ROTATION = "page_turn:rotation";
    private static final String PROPERTY_ALPHA = "page_turn:alpha";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        transitionValues.values.put(PROPERTY_ROTATION, view.getRotationY());
        transitionValues.values.put(PROPERTY_ALPHA, view.getAlpha());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        transitionValues.values.put(PROPERTY_ROTATION, -90f);
        transitionValues.values.put(PROPERTY_ALPHA, 0f);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        View view = endValues.view;

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, View.ROTATION_Y,
                (Float) startValues.values.get(PROPERTY_ROTATION), (Float) endValues.values.get(PROPERTY_ROTATION));
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA,
                (Float) startValues.values.get(PROPERTY_ALPHA), (Float) endValues.values.get(PROPERTY_ALPHA));

        animatorSet.playTogether(rotationAnimator, alphaAnimator);

        return animatorSet;
    }
}
