package it.unibo.workitout.model.wiki.impl;

import it.unibo.workitout.model.wiki.api.Article;

/**
 * Implementation of Article.
 */
public final class ArticleImpl implements Article {
    private final String title;
    private final String description;
    private final String text;

    /**
     * Constructor.
     * 
     * @param title the title of the article.
     * @param description the description of the article.
     * @param text the actual text of the article
     */
    public ArticleImpl(final String title, final String description, final String text) {
        this.title = title;
        this.description = description;
        this.text = text;
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
    public String getDescription() { 
        return this.description; 
    }
}
