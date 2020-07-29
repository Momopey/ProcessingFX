package com.symbol;

import com.processing.PAnim;
import processing.core.PVector;

//Composite Symbols: symbols that are both parents and children. They are called composite symbols because they contain multiple children that get rendered( stored in a children container)
public class CompSymbol extends ParentSymbol implements ChildSymbol {
    private SymbolContainer parentContainer;
    public CompSymbol(SymbolContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
        super(dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
        setParentContainer(dParent);
        fixTint();
    }
    public CompSymbol(SymbolContainer dParent, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha){
        super(dPos, dCenter, dAngle, dScale, dAlpha);
        setParentContainer(dParent);
        fixTint();
    }
    public CompSymbol( PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
        super(dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
//        setParentContainer(dParent);
        fixTint();
    }
    public CompSymbol( PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha){
        super(dPos, dCenter, dAngle, dScale, dAlpha);
//        setParentContainer(dParent);
        fixTint();
    }
//    public SymbolComp(SymbolContainer dParent, RendererInfo dRendererInfo, PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
//        super(dRendererInfo,dPos, dCenter, dAngle, dScale, dAlpha, dChildContainer);
//        setParentContainer(dParent);
//        setTint();
//    }

    @Override
    public SymbolContainer getParentContainer() {
        return parentContainer;
    }

    @Override
    public void setParentContainer(SymbolContainer dParent) {
        parentContainer=dParent;
        parentContainer.addChild(this);
    }

    @Override
    public void fixTint() {
        absoluteTint=tint(tint,parentContainer.getActiveParent().getAbsoluteTint());
        super.fixTint();
    }

    public SymbolContainer GetChildrenContainer() {
        return symbolContainer;
    }
}
