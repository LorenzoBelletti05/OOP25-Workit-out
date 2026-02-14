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
                    "Errore durante la lettura dell'icona: " + e.getMessage(), 
                    "Errore Icona", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                mainView, 
                "Icona non trovata nelle risorse!", 
                "Errore Icona", 
                JOptionPane.ERROR_MESSAGE
            );
        }
        final MainControllerImpl mainController = new MainControllerImpl(mainView);
        mainController.start();
    }
}
