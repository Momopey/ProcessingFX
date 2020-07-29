package com.symbol;

import com.mode.Mode;
import processing.core.PGraphics;
//Basic symbol functionality that needs to be present.
public interface SymbolFunctionality {
    void init();
    void render(PGraphics graphic);
    void addMode(Mode mode);
    boolean usesMode(Mode useMode);
    void loadFrame(int dFrameNumber);
}
