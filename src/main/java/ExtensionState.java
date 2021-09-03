package main.java;

import burp.IBurpExtenderCallbacks;

import java.awt.*;

public class ExtensionState {

    private static ExtensionState state = null;
    private IBurpExtenderCallbacks callbacks;
    private Component repeaterBaseComponent;
    private final RepeaterTabChangeListener repeaterTabChangeListener;

    private ExtensionState() {
        repeaterTabChangeListener = new RepeaterTabChangeListener();
    }

    public static ExtensionState getInstance() {
        if (state == null)
            state = new ExtensionState();
        return state;
    }

    public IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public Component getRepeaterBaseComponent() {
        return repeaterBaseComponent;
    }

    public void setRepeaterBaseComponent(Component repeaterBaseComponent) {
        this.repeaterBaseComponent = repeaterBaseComponent;
    }

    public RepeaterTabChangeListener getRepeaterTabChangeListener() {
        return repeaterTabChangeListener;
    }
}
