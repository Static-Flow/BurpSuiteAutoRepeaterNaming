package main.java;

import com.staticflow.BurpGuiControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.net.MalformedURLException;
import java.net.URL;

public class RepeaterTabPaneHierarchyListener implements HierarchyListener {
    //this event fires twice so we skip every other one
    private boolean skipCheck;

    public RepeaterTabPaneHierarchyListener() {
        skipCheck = true;
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        new SwingWorker<Boolean, Void>() {
            @Override
            public Boolean doInBackground() {
                if (!skipCheck) {
                    JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                    Container selectedRepeaterTab = (Container) sourceTabbedPane.getSelectedComponent();
                    JTextArea requestTextArea  =
                            BurpGuiControl.getRepeaterTabRequestTextArea(selectedRepeaterTab);
                    String firstline = requestTextArea.getText().substring(0,
                            requestTextArea.getText().indexOf('\n')).split(" ")[1];
                    try {
                        URL parsedUrl = new URL("http://foo.com" + firstline);
                        java.awt.EventQueue.invokeLater(() -> {
                        sourceTabbedPane.setTitleAt(sourceTabbedPane.getSelectedIndex(),parsedUrl.getPath());
                        sourceTabbedPane.revalidate();
                        });
                    } catch (MalformedURLException malformedURLException) {
                        ExtensionState.getInstance().getCallbacks().printError(malformedURLException.getMessage());
                    }

                }
                return Boolean.TRUE;
            }

            @Override
            public void done() {
                //we don't need to do any cleanup so this is empty
            }
        }.execute();
        skipCheck = !skipCheck;
    }
}
