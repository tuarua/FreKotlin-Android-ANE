package com.mycompany;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;
import com.mycompany.helloworldane.KotlinController;

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class HelloWorldANE implements FREExtension {
    private String NAME = "com.mycompany.HelloWorldANE";
    private static final String[] FUNCTIONS = {
             "init"
            ,"sayHello"
    };

    public static HelloWorldANEContext extensionContext;

    @Override
    public void initialize() {

    }

    @Override
    public FREContext createContext(String s) {
        return extensionContext = new HelloWorldANEContext(NAME, new KotlinController(), FUNCTIONS);
    }

    @Override
    public void dispose() {
        extensionContext.dispose();
    }
}
