package com.chatapp.tutorial.custom;

/**
 * Created on 16/03/17.
 */

public enum ViewPageTransformer {
    ALPHA_TRANSFORMER(0),
    CUBEIN_TRANSFORMER(1),
    CUBEOUT_TRANSFORMER(2),
    DEPTHPAGE_TRANSFORMER(3),
    FLIPHORIZONTAL_TRANSFORMER(4),
    FLIPVERTICAL_TRANSFORMER(5),
    ROTATIONPAGE_TRANSFORMER(6),
    ZOOMIN_TRANSFORMER(7),
    ZOOMOUT_TRANSFORMER(8),
    ZOOMOUTSLIDE_TRANSFORMER(9);

    private int type;

    ViewPageTransformer(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
