package it.unibo.workitout.controller.wiki.impl;

import it.unibo.workitout.controller.wiki.contracts.WikiController; 
import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.impl.ArticleImpl;
import java.util.Set;
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
        new it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl().loadAll(model);
        this.setFakeData();
    }

    private void setFakeData() {
        model.addContent(new ArticleImpl(
            "Allenamento Petto", 
            "Questo è un testo mostruosamente lungo cosi da permettere di vedere come la view lo gestisce", 
            Set.of("Forza", "Petto")
        ));
        model.addContent(new ArticleImpl(
            "Dieta Massa", 
            "Testo dieta", 
            Set.of("Alimentazione", "Massa")
        ));
    }

    /**
     * Start the model/view.
     */
    @Override
    public void start() {
        this.view.updateContents(this.model.getContents());

        this.view.addSelectionListener(content -> 
            this.view.showDetail(content.getTitle(), content.getText())
        );

        this.view.addBackListener(this.view::showList);

        this.view.addSearchListener(() -> 
            this.view.updateContents(this.model.search(this.view.getSearchQuery()))
        );

        this.view.start();
    }
}
