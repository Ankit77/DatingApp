package com.chatapp.util;

public enum ActionType {

    COMPOSE(0), EDIT(1), TRANSLATE(2);

    private int type;

    ActionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
