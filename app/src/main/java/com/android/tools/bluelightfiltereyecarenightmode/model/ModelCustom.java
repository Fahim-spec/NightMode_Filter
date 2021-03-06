package com.android.tools.bluelightfiltereyecarenightmode.model;

import com.android.tools.bluelightfiltereyecarenightmode.interfaces.IChangeBackground;

public class ModelCustom {
    private static ModelCustom mInstance;
    private IChangeBackground iChangeBackground;
    private String background;
    private String color;
    private ModelCustom() {}

    public static ModelCustom getInstance() {
        if(mInstance == null) {
            mInstance = new ModelCustom();
        }
        return mInstance;
    }

    public void setSpeed(IChangeBackground iChangeBackground1) {
        iChangeBackground = iChangeBackground1;
    }

    public void changeSpeed(String sPosi,String s1) {
        if(iChangeBackground != null) {
            background = sPosi;
            color= s1;
            notifyStateChange();
        }
    }

    private void notifyStateChange() {
        iChangeBackground.intensity(background,color);
    }
}
