package com.symbol.primative;


import com.symbol.SymbolContainer;
import com.symbol.Symbol;
import com.symbol.ChildSymbol;
import processing.core.PVector;

public class PrimSymbol extends Symbol implements ChildSymbol {
    private SymbolContainer parentContainer;
    public PrimSymbol(SymbolContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha){
        super(dPos,dCenter,dAngle,dScale,dAlpha);
        setParentContainer(dParent);
//        alphaMult=alpha;
//        alphaMult*=parentContainer.getActiveParent().getAlphaMult();
        fixTint();
    }

    @Override
    public SymbolContainer getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setParentContainer(SymbolContainer parent) {
        parentContainer=parent;
        parentContainer.addChild(this);
    }

    @Override
    public void fixTint() {
        absoluteTint=tint(tint,parentContainer.getActiveParent().getAbsoluteTint());
        super.fixTint();
    }
}