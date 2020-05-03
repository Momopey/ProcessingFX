package com.symbol;

import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// A class that just stores an array of Symbol children. A container for groups of children that could be shared by multiple parents, ie if a symbol is being used in multiple places in the global timeline.
// Likely some restrictions will need to be made to interactions of parents with children, but that is a TODO: Figure out how to manage childrencontainers that are used in multiple places, specifically dynamic calls and resolution
public class ChildrenContainer {
    public Set<SymbolParent> parentSymbols;
    public SymbolParent activeParent;

    public SymbolParent getActiveParent() {
        return activeParent;
    }
    private ArrayList<SymbolChild> children;
    public ChildrenContainer(){
        parentSymbols= new HashSet<SymbolParent>();
        children= new ArrayList<SymbolChild>();
    }
    public void render(PGraphics graphic) {
        for (SymbolChild S : children) {
            Symbol child= (Symbol) S;
            child.render(graphic);
        }
    }

    public void setParentSymbol(SymbolParent newParent){
        parentSymbols.add(newParent);
        activeParent= newParent;
        fixTint();
    }
    public ArrayList<SymbolChild> getChildren() {
        return children;
    }
    public void addChild(SymbolChild child){
//        child.SetContainer(this);
        children.add(child);
    }
    public void loadFrame(int frameNumber,SymbolParent dActiveParent){
        setParentSymbol(dActiveParent);
        for(int childnum=0; childnum<children.size();childnum++){// for all children
           children.get(childnum).loadFrame(frameNumber);
        }
    }
    public void fixContainer(SymbolParent dActiveParent){
        setParentSymbol(dActiveParent);
//        for(SymbolChild c: children){
//            c.fixSymbol();
//        }
    }

    public void fixTint() {
        for(SymbolChild S:children){
            S.fixTint();
        }
    }
}
