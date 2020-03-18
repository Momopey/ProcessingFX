package com.symbol;

import com.mode.Mode;
import processing.core.PGraphics;

public interface SymbolFunctionality {
    void render(PGraphics graphic);
    void addMode(Mode mode);
    boolean usesMode(Mode useMode);
    void loadFrame(int dFrameNumber);
}
