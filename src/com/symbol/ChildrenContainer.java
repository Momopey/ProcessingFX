package com.symbol;

import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
}
