package com.mode;

public abstract class DataMode implements Mode {
    Class<?> dataClass;
    public Class<?> getDataClass() {
        return dataClass;
    }

    public DataMode(Class<?> dDataClass){
        dataClass=dDataClass;
    }
}
