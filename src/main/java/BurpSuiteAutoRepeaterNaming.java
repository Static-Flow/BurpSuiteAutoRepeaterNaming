package main.java;

import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionStateListener;
import com.staticflow.BurpGuiControl;

public class BurpSuiteAutoRepeaterNaming implements IBurpExtender, IExtensionStateListener {

    private static final String REPEATER = "Repeater";

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        callbacks.printOutput("Loaded extension");
        callbacks.registerExtensionStateListener(this);
        callbacks.setExtensionName("BurpSuiteAutoRepeaterNaming");
        ExtensionState.getInstance().setCallbacks(callbacks);
        ExtensionState.getInstance().setRepeaterBaseComponent(BurpGuiControl.getBaseBurpComponent(
                REPEATER));
        ExtensionState.getInstance().getRepeaterBaseComponent()
         .addHierarchyListener(ExtensionState.getInstance().getTabVisibleListener());
    }

    @Override
    public void extensionUnloaded() {
        ExtensionState.getInstance().getRepeaterBaseComponent().removeHierarchyListener(ExtensionState.getInstance().getTabVisibleListener());
    }


}
