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

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
//This file must remain as Java
public class FreKotlinContext extends FREContext {
    private final String TAG;
    protected FreKotlinMainController controller;
    private final String[] functions;
    public FreKotlinContext(String name, FreKotlinMainController controller, String[] functions) {
        TAG = name;
        this.controller = controller;
        this.functions = functions;
        controller.setContext(this);
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

    private class CallKotlinFunction implements FREFunction {
        private final String _name;

        CallKotlinFunction(String name) {
            _name = name;
        }

        @Override
        public FREObject call(FREContext freContext, FREObject[] freObjects) {
            //noinspection rawtypes
            Class[] parameterTypes = new Class[2];
            parameterTypes[0] = FREContext.class;
            parameterTypes[1] = ArrayList.class;
            ArrayList<FREObject> al = new ArrayList<>();
            Collections.addAll(al, freObjects);
            try {
                Method func = controller.getClass().getMethod(_name, parameterTypes);
                return (FREObject) func.invoke(controller, freContext, al);
            } catch (NoSuchMethodException e) {
                FREObject ret = new FreException(e, "can't call function " + _name).getError();
                Log.e(TAG, "can't find function " + _name + " ", e);
                return ret;
            } catch (IllegalAccessException | InvocationTargetException e) {
                FREObject ret = new FreException(e, "can't call function " + _name).getError();
                Log.e(TAG, "can't call function " + _name, e);
                return ret;
            }
        }
    }

}
