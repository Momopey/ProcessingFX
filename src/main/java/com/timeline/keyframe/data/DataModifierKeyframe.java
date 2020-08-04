package com.timeline.keyframe.data;

import com.mode.DataMode;

public interface DataModifierKeyframe<T extends DataKeyframe> {
    public DataMode getDataMode();
    public boolean causesHistoryDependance();
    public boolean checkResolveKeyframe(T data);
    public void modifyKeyframe(T data);
}
