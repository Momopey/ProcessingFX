package com.symbol.editview;

import com.symbol.SymbolContainer;
import com.symbol.ParentSymbol;
import processing.core.PVector;

public class MainSymbol extends ParentSymbol {
    public MainSymbol(PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
        super(dPos,dCenter,dAngle,dScale,dAlpha,dChildContainer);
    }

}
