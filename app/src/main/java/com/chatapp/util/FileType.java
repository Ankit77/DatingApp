package com.chatapp.util;

public enum FileType {
    IMAGE(0), VIDEO(1), FILE(2);

    private int type;

    FileType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
