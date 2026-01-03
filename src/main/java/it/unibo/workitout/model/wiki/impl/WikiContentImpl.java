package it.unibo.workitout.model.wiki.impl;

import it.unibo.workitout.model.wiki.WikiContent;

/**
 * Implementation of WikiContent.
 */
public final class WikiContentImpl implements WikiContent {
    private final String title;
    private final String description;

    /**
     * Constructor.
     * 
     * @param title content of the title.
     * @param description content of the description.
     */
    public WikiContentImpl(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
