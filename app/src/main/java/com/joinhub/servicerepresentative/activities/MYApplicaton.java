package com.joinhub.servicerepresentative.activities;

import android.app.Application;

import com.huawei.hms.maps.MapsInitializer;

public class MYApplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapsInitializer.setApiKey("DAEDAGaEyT32GcbI3m2SmD6MIli6U0fFimXidQAWsdTCDMge7+XILbtgPmBZbQgqWljbYzGnvoiKycf4TeSwb2rbxlMLEDbUz+hlLQ==");
    }
}
