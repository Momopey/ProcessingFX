package com.util;

import processing.core.PMatrix;
public interface Transform extends Cloneable{
    public PMatrix getMatrix();
//    public void apply(Transform transform);t
    public Transform clone();
}
