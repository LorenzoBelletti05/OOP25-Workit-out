package it.unibo.workitout.model.wiki.impl;

import java.util.Set;

import it.unibo.workitout.model.wiki.contracts.WikiContent;

/**
 * Implementation of WikiContent.
 */
public final class WikiContentImpl implements WikiContent {
    private final String title;
    private final String description;
    private final Set<String> tags;

    /**
     * Constructor.
     * 
     * @param title content of the title.
     * @param description content of the description.
     * @param tags set of strings for filtering infos.
     */
    public WikiContentImpl(final String title, final String description, final Set<String> tags) {
        this.title = title;
        this.description = description;
        this.tags = Set.copyOf(tags);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Set<String> getTags() {
        return Set.copyOf(this.tags);
    }

}
