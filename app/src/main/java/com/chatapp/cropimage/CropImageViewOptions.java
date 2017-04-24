package com.chatapp.cropimage;

import android.util.Pair;

import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by indianic on 24/04/17.
 */

public class CropImageViewOptions {
    public CropImageView.ScaleType scaleType = CropImageView.ScaleType.CENTER_INSIDE;

    public CropImageView.CropShape cropShape = CropImageView.CropShape.RECTANGLE;

    public CropImageView.Guidelines guidelines = CropImageView.Guidelines.ON_TOUCH;

    public Pair<Integer, Integer> aspectRatio = new Pair<>(1, 1);

    public boolean autoZoomEnabled;

    public int maxZoomLevel;

    public boolean fixAspectRatio;

    public boolean multitouch;

    public boolean showCropOverlay;

    public boolean showProgressBar;

    public boolean flipHorizontally;

    public boolean flipVertically;
}
