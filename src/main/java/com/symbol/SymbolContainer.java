package com.symbol;

import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// A class that just stores an array of Symbol children. A container for groups of children that could be shared by multiple parents, ie if a symbol is being used in multiple places in the global timeline.
// Likely some restrictions will need to be made to interactions of parents with children, but that is a TODO: Figure out how to manage childrencontainers that are used in multiple places, specifically dynamic calls and resolution
public class SymbolContainer {
    public Set<ParentSymbol> parentSymbols;
    public ParentSymbol activeParent;

    public ParentSymbol getActiveParent() {
        return activeParent;
    }
    private ArrayList<ChildSymbol> children;
    public SymbolContainer(){
        parentSymbols= new HashSet<ParentSymbol>();
        children= new ArrayList<ChildSymbol>();
    }
    public void render(PGraphics graphic) {
        for (ChildSymbol S : children) {
            Symbol child= (Symbol) S;
            child.render(graphic);
        }
    }

    public void setParentSymbol(ParentSymbol newParent){
        parentSymbols.add(newParent);
        activeParent= newParent;
        fixTint();
    }
    public ArrayList<ChildSymbol> getChildren() {
        return children;
    }
    public void addChild(ChildSymbol child){
//        child.SetContainer(this);
        children.add(child);
    }
    public void loadFrame(int frameNumber, ParentSymbol dActiveParent){
        setParentSymbol(dActiveParent);
        for(int childnum=0; childnum<children.size();childnum++){// for all children
           children.get(childnum).loadFrame(frameNumber);
        }
    }
    public void fixContainer(ParentSymbol dActiveParent){
        setParentSymbol(dActiveParent);
//        for(SymbolChild c: children){
//            c.fixSymbol();
//        }
    }

    public void fixTint() {
        for(ChildSymbol S:children){
            S.fixTint();
        }
    }
}
