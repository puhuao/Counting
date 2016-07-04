package com.wksc.counting.event;

/**
 * Created by Administrator on 2016/7/4.
 */
public class TurnToMoreFragmentEvent {
    public TurnToMoreFragmentEvent(Boolean isDestroyAll){
        this.isDestroyAll = isDestroyAll;
    }

    public Boolean isDestroyAll() {
        return isDestroyAll;
    }

    private  Boolean isDestroyAll;
}
