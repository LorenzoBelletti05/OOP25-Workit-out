package it.unibo.workitout.controller.wiki.impl;

import it.unibo.workitout.controller.wiki.contracts.WikiController; 
import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.food.api.Meal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.impl.SmartSuggestionImpl;
import it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl;
import it.unibo.workitout.model.workout.impl.Exercise;

import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of WikiController.
 */
public class WikiControllerImpl implements WikiController {
    private final Wiki model;
    private final WikiView view;
    private final SmartSuggestionImpl smartSuggestion = new SmartSuggestionImpl();

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

        final WikiRepositoryImpl repository = new WikiRepositoryImpl();
        repository.loadAll(this.model);
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

    /**
     * New view with smart suggestions.
     * 
     * @param user the current user.
     */
    @Override
    public void showSmartSuggestions(final UserProfile user, final List<Exercise> exercises, final Meal meal) {
        this.view.updateContents(this.smartSuggestion.suggest(this.model, user, exercises, meal));
    }
}
