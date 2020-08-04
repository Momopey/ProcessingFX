package com.util.debug;

public interface Debugable {
    String getName();
    String getDebugInfo();
    Debugable setName(String newName);
}
