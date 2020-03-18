package com.symbol;

import processing.core.PVector;

//Composite Symbols
public class SymbolComp extends SymbolParent implements SymbolChild {
    private ChildrenContainer parentContainer;
    public SymbolComp(ChildrenContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, ChildrenContainer dChildContainer){
        super(dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
        setParentContainer(dParent);
        setAlpha();
    }

    @Override
    public ChildrenContainer getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setParentContainer(ChildrenContainer dParent) {
        parentContainer=dParent;
        parentContainer.addChild(this);
    }
    public ChildrenContainer GetChildrenContainer() {
        return childContainer;
    }
    @Override
    public void setAlpha(){
        alphaMult=alpha;
        alphaMult*=parentContainer.getActiveParent().alphaMult;
    }

}
