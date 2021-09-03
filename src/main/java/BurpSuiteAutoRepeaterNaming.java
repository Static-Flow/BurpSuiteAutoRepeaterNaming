package main.java;

import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionStateListener;
import com.staticflow.BurpGuiControl;

import javax.swing.*;
import java.awt.*;

public class BurpSuiteAutoRepeaterNaming implements IBurpExtender, IExtensionStateListener {

    private static final String REPEATER = "Repeater";

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        callbacks.printOutput("Loaded extension");
        callbacks.registerExtensionStateListener(this);
        callbacks.setExtensionName("BurpSuiteAutoRepeaterNaming");
        ExtensionState.getInstance().setCallbacks(callbacks);
        Component repeaterComponent = BurpGuiControl.getBaseBurpComponent(
                REPEATER);
        if (repeaterComponent instanceof JTabbedPane ) {
            ExtensionState.getInstance().setRepeaterBaseComponent(repeaterComponent);
            ((JTabbedPane) ExtensionState.getInstance().getRepeaterBaseComponent()).addChangeListener(ExtensionState.getInstance().getRepeaterTabChangeListener());
        } else {
            Component jTabbedPane =
                    BurpGuiControl.FindJTabbedPane((Container) repeaterComponent);
            if( jTabbedPane != null ) {
                ExtensionState.getInstance().setRepeaterBaseComponent(jTabbedPane);
                ((JTabbedPane) ExtensionState.getInstance().getRepeaterBaseComponent()).addChangeListener(ExtensionState.getInstance().getRepeaterTabChangeListener());
            }
        }
    }

    @Override
    public void extensionUnloaded() {
        ((JTabbedPane) ExtensionState.getInstance().getRepeaterBaseComponent()).removeChangeListener(ExtensionState.getInstance().getRepeaterTabChangeListener());
    }


}
