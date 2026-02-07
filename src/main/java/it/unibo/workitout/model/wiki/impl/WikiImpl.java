package it.unibo.workitout.model.wiki.impl;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiContent;

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

    /**
     * New search system for titles and tags.
     */
    @Override
    public Set<WikiContent> search(final String query) {
        if (query == null || query.isBlank()) {
            return getContents();
        }
        final String lowerQuery = query.toLowerCase(Locale.ROOT);
        return this.contents.stream()
            .filter(c -> c.getTitle().toLowerCase(Locale.ROOT).contains(lowerQuery)
            || c.getTags().stream()
            .anyMatch(t -> t.toLowerCase(Locale.ROOT).contains(lowerQuery)))
            .collect(Collectors.toSet());
    }

}

