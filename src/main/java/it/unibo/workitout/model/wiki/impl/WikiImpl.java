package it.unibo.workitout.model.wiki.impl;

import java.util.HashSet;
import java.util.Set;
import it.unibo.workitout.model.wiki.api.Wiki;
import it.unibo.workitout.model.wiki.api.WikiContent;

/**
 * Implementation of Wiki.
 */
public final class WikiImpl implements Wiki {
    private final Set<WikiContent> contents = new HashSet<>();

    /**
     * Gets a copy of the set wiki contents.
     */
    @Override
    public Set<WikiContent> getContents() {
        return Set.copyOf(this.contents);
    }

    /**
     * Add a new content in the set.
     */
    @Override
    public void addContent(final WikiContent content) {
        this.contents.add(content);
    }

}
