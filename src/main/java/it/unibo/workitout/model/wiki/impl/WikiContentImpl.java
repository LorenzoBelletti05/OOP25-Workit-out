package it.unibo.workitout.model.wiki.impl;

import java.util.Set;

import it.unibo.workitout.model.wiki.contracts.WikiContent;

/**
 * Implementation of WikiContent.
 */
public final class WikiContentImpl implements WikiContent {
    private final String title;
    private final String text;
    private final Set<String> tags;

    /**
     * Constructor.
     * 
     * @param title content of the title.
     * @param text text of the content.
     * @param tags set of strings for filtering infos.
     */
    public WikiContentImpl(final String title, final String text, final Set<String> tags) {
        this.title = title;
        this.text = text;
        this.tags = Set.copyOf(tags);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Set<String> getTags() {
        return Set.copyOf(this.tags);
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
