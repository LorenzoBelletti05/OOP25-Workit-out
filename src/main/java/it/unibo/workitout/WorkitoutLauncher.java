package it.unibo.workitout;

import it.unibo.workitout.view.main.impl.MainViewImpl;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import it.unibo.workitout.controller.main.impl.MainControllerImpl;

/**
 * Main entry point for the application.
 */
public final class WorkitoutLauncher {

    private WorkitoutLauncher() {
    }

    /**
     * Main method.
     * 
     * @param args ...
     * 
     * @throws IOException error save.
     * 
     */
    public static void main(final String[] args) throws IOException {
        final MainViewImpl mainView = new MainViewImpl();
        final URL iconUrl = WorkitoutLauncher.class.getResource("/icon.png");
        if (iconUrl != null) {
            try {
                mainView.setIconImage(ImageIO.read(iconUrl));
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(
                    mainView, 
                    "Error reading the icon: " + e.getMessage(), 
                    "Icon Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                mainView, 
                "Icon not found in the resources!", 
                "Icon error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
        final MainControllerImpl mainController = new MainControllerImpl(mainView);
        mainController.start();
    }
}
