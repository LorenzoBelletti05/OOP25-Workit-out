package it.unibo.workitout;

import it.unibo.workitout.view.main.impl.MainViewImpl;
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
     */
    public static void main(final String[] args) {
        final MainViewImpl mainView = new MainViewImpl();
        final MainControllerImpl mainController = new MainControllerImpl(mainView);
        mainController.start();
    }
}

