package it.unibo.workitout.model.wiki.api;

import java.util.Set;

/**
 * Wiki interface.
 */
public interface Wiki {
    /**
     * Return articles and videos saved.
     * 
     * @return a set of WikiContent
     */
    Set<WikiContent> getContents();

    /**
     * Add a new Content.
     * 
     * @param content the new content to add
     */
    void addContent(WikiContent content);
}
