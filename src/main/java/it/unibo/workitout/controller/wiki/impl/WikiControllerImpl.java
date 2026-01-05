package it.unibo.workitout.controller.wiki.impl;

import it.unibo.workitout.controller.wiki.contracts.WikiController; 
import it.unibo.workitout.view.wiki.contracts.WikiView; 
import it.unibo.workitout.model.wiki.contracts.Wiki;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of WikiController.
 */
public class WikiControllerImpl implements WikiController {
    private final Wiki model;
    private final WikiView view;

    /**
     * Constructor.
     * 
     * @param model wiki model.
     * @param view wiki view.
     */
    @SuppressFBWarnings(value = "EI2", justification = "The model must been shared between controller and view.")
    public WikiControllerImpl(final Wiki model, final WikiView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Start the model/view.
     */
    @Override
    public void start() {
        this.view.updateContents(this.model.getContents());
        this.view.start();
    }
}
