package com.util;

import com.timeline.keyframe.Keyframe;

public interface Resolvable{
    void addDependentResolveRelationship(Resolvable dependentResolvable);
    void addRequiredResolveRelationship(Resolvable requiredResolvable);
    void removeDependentResolveRelationship(Resolvable prevDepResolvable);
    void removeRequiredResolveRelationship(Resolvable prevReqResolvable);

    void addDependentResolveKeyframe(Resolvable dependentResolvable);
    void removeDependentResolveKeyframe(Resolvable prevDependentResolvable);
    void addRequiredResolveKeyframe(Resolvable requiredResolvable);
    void removeRequiredResolveKeyframe(Resolvable prevRequiredResolvable);

    void requestResolveRelationship(Resolvable requester);
    void removeResolveRelationship(Resolvable requester);

    boolean isResolved();

    void resolve();
    void deResolve();

    void checkResolve();

}
