package com.sliding.navigator.callback;

public interface DragStateListener {

    void onDragStart();

    void onDragEnd(boolean isMenuOpened);
}
