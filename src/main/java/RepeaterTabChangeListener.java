package main.java;

import burp.IRequestInfo;
import com.staticflow.BurpGuiControl;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.net.URL;

public class RepeaterTabChangeListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        new SwingWorker<Boolean, Void>() {
            @Override
            public Boolean doInBackground() {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                /*
                    If this new tab is a number we need to change it. We do
                    that by add a hierarchy listener to the tab so that when
                    it eventually becomes visible we change its name and
                    finally remove the listener

                 */
                if (NumberUtils.isCreatable(sourceTabbedPane.getTitleAt(sourceTabbedPane.getSelectedIndex()))) {
                    java.awt.EventQueue.invokeLater(() -> {

                        try {
                            JTextArea textArea =
                                    BurpGuiControl.getRepeaterTabRequestTextArea((Container) sourceTabbedPane.getComponentAt(sourceTabbedPane.getSelectedIndex()));
                            IRequestInfo requestInfo =
                                    ExtensionState.getInstance().getCallbacks().getHelpers().analyzeRequest(textArea.getText().getBytes());
                            String firstline = requestInfo.getHeaders().get(0).split(
                                    " ")[1];
                            String hostHeader = requestInfo.getHeaders().get(1).split(
                                    ":")[1].substring(1);

                            URL parsedUrl =
                                    new URL("http://" + hostHeader + firstline);
                            String path = parsedUrl.getPath();

                            if (path.length() == 1) {
                                path =
                                        parsedUrl.getHost() + parsedUrl.getPath();
                            } else if (path.length() > 50) {
                                path =
                                        path.substring(0, 10) + "..." + path.substring(path.length() - 10);
                            }
                            sourceTabbedPane.setTitleAt(sourceTabbedPane.getSelectedIndex(), path);
                            sourceTabbedPane.revalidate();
                        } catch (Exception exception) {
                            ExtensionState.getInstance().getCallbacks().printError(exception.getMessage());
                        }
                    });
                }
                return true;
            }
        }.execute();
    }
}
