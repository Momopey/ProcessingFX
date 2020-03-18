package com.symbol;

public interface SymbolChild extends SymbolFunctionality {
    ChildrenContainer getParentContainer();
    void setParentContainer(ChildrenContainer parent);
    void setAlpha();
}
