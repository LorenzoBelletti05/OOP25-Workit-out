package it.unibo.workitout.model.wiki.impl;

import java.util.Set;

import it.unibo.workitout.model.wiki.contracts.Article;

/**
 * Implementation of Article.
 */
public final class ArticleImpl implements Article {
    private final String title;
    private final String text;
    private final Set<String> tags;

    /**
     * Constructor.
     * 
     * @param title the title of the article.
     * @param text the actual text of the article.
     * @param tags set of strings for filtering infos.
     */
    public ArticleImpl(final String title, final String text, final Set<String> tags) {
        this.title = title;
        this.text = text;
        this.tags = Set.copyOf(tags);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getText() { 
        return this.text; 
    }

    @Override
    public Set<String> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    @Override
    public boolean isVideo() {
        return false;
    }
}
