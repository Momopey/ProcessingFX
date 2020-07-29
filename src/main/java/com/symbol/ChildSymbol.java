package com.symbol;

// An interface for symbols who are children.
public interface ChildSymbol extends SymbolFunctionality {
    SymbolContainer getParentContainer();

    void setParentContainer(SymbolContainer parent);
    void fixTint();

}
