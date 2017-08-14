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
package com.tuarua.frekotlin;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.air.TRActivityResultCallback;
import com.adobe.air.AndroidActivityWrapper.ActivityState;
import com.adobe.air.TRStateChangeCallback;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
//This file must remain as Java
// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib/1.1.3-2
public class FreKotlinContext extends FREContext implements TRActivityResultCallback, TRStateChangeCallback {
    private String TAG = null;
    protected FreKotlinController controller;
    private String[] functions;
    public FreKotlinContext(String name, FreKotlinController controller, String[] functions) {
        TAG = name;
        this.controller = controller;
        this.functions = functions;
        controller.setFREContext(this);
    }

    @Override
    public Map<String, FREFunction> getFunctions() {
        Map<String, FREFunction> functionsToSet = new HashMap<>();
        for (String function : functions) {
            functionsToSet.put(function, new CallKotlinFunction(function));
        }
        return functionsToSet;
    }

    @Override
    public void dispose() {
        controller.dispose();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }

    @Override
    public void onActivityStateChanged(ActivityState activityState) {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    // https://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
    private class CallKotlinFunction implements FREFunction {
        private String _name;

        CallKotlinFunction(String name) {
            _name = name;
        }

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            //Log.d(TAG, "calling function: "+_name);
            Class[] parameterTypes = new Class[2];
            parameterTypes[0] = FREContext.class;
            parameterTypes[1] = ArrayList.class;
            ArrayList<FREObject> al = new ArrayList<>();
            Collections.addAll(al, freObjects);
            try {
                Method func = controller.getClass().getMethod(_name, parameterTypes);
                //Log.d(TAG, String.valueOf(method1));
                return (FREObject) func.invoke(controller, freContext, al);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "can't find function " + _name + " " + e.getMessage());
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e(TAG, "can't call function " + _name + " " + e.getStackTrace().toString());
            }
            return null;
        }
    }

}
