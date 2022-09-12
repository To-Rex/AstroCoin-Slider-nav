package com.sliding.navigator.transform;

import android.view.View;

import java.util.List;

public class CompositeTransformation implements RootTransformation {

    private final List<RootTransformation> transformations;

    public CompositeTransformation(List<RootTransformation> transformations) {
        this.transformations = transformations;
    }

    @Override
    public void transform(float dragProgress, View rootView) {
        for (RootTransformation t : transformations) {
            t.transform(dragProgress, rootView);
        }
    }
}
