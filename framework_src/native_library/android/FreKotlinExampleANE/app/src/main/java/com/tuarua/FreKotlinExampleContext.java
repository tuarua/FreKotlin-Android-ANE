/*
 *  Copyright 2017 Tua Rua Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.tuarua;

import com.adobe.air.TRActivityResultCallback;
import com.adobe.air.AndroidActivityWrapper;
import com.adobe.air.TRStateChangeCallback;
import com.tuarua.frekotlin.FreKotlinContext;
import com.tuarua.frekotlin.FreKotlinMainController;

//This file must remain as Java
@SuppressWarnings({"WeakerAccess", "unused"})
public class FreKotlinExampleContext extends FreKotlinContext implements TRActivityResultCallback, TRStateChangeCallback {
    private AndroidActivityWrapper aaw;

    public FreKotlinExampleContext(String name, FreKotlinMainController controller, String[] functions) {
        super(name, controller, functions);
        this.controller = controller;
        aaw = AndroidActivityWrapper.GetAndroidActivityWrapper();
        aaw.addActivityResultListener(this);
        aaw.addActivityStateChangeListner(this);
    }

    @Override
    public void onActivityStateChanged(AndroidActivityWrapper.ActivityState activityState) {
        super.onActivityStateChanged(activityState);
        switch (activityState){
            case STARTED:
                this.controller.onStarted();
                break;
            case RESTARTED:
                this.controller.onRestarted();
                break;
            case RESUMED:
                this.controller.onResumed();
                break;
            case PAUSED:
                this.controller.onPaused();
                break;
            case STOPPED:
                this.controller.onStopped();
                break;
            case DESTROYED:
                this.controller.onDestroyed();
                break;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (aaw != null) {
            aaw.removeActivityResultListener(this);
            aaw.removeActivityStateChangeListner(this);
            aaw = null;
        }
    }
}
