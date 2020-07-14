package com.debug;

import javafx.beans.property.StringProperty;
public interface Debugable {
    String getName();
    String getDebugInfo();
    Debugable setName(String newName);
}
