package com.symbol;

import processing.core.PVector;

//Composite Symbols: symbols that are both parents and children. They are called composite symbols because they contain multiple children that get rendered( stored in a children container)
public class SymbolComp extends SymbolParent implements SymbolChild {
    private ChildrenContainer parentContainer;
    public SymbolComp(ChildrenContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, ChildrenContainer dChildContainer){
        super(dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
        setParentContainer(dParent);
        fixTint();
    }
//    public SymbolComp(ChildrenContainer dParent, RendererInfo dRendererInfo, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, ChildrenContainer dChildContainer){
//        super(dRendererInfo,dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
//        setParentContainer(dParent);
//        setTint();
//    }

    @Override
    public ChildrenContainer getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setParentContainer(ChildrenContainer dParent) {
        parentContainer=dParent;
        parentContainer.addChild(this);
    }

    @Override
    public void fixTint() {
        absoluteTint=tint(tint,parentContainer.getActiveParent().getAbsoluteTint());
        super.fixTint();
    }

    public ChildrenContainer GetChildrenContainer() {
        return childContainer;
    }
}
