package com.symbol;

// An interface for symbols who are children.
public interface SymbolChild extends SymbolFunctionality {
    ChildrenContainer getParentContainer();
    void setParentContainer(ChildrenContainer parent);
    void fixTint();
}
