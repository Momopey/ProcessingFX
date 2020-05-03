package com.symbol.primative;


import com.symbol.ChildrenContainer;
import com.symbol.Symbol;
import com.symbol.SymbolChild;
import processing.core.PVector;

public class SymbolPrim extends Symbol implements SymbolChild {
    private ChildrenContainer parentContainer;
    public SymbolPrim(ChildrenContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha){
        super(dPos,dCenter,dAngle,dScale,dAlpha);
        setParentContainer(dParent);
//        alphaMult=alpha;
//        alphaMult*=parentContainer.getActiveParent().getAlphaMult();
        fixTint();
    }

    @Override
    public ChildrenContainer getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setParentContainer(ChildrenContainer parent) {
        parentContainer=parent;
        parentContainer.addChild(this);
    }

    @Override
    public void fixTint() {
        absoluteTint=tint(tint,parentContainer.getActiveParent().getAbsoluteTint());
        super.fixTint();
    }
}