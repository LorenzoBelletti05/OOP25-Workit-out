package it.unibo.workitout.model.wiki.contracts;

import java.util.Set;

/**
 * Wiki interface.
 */
public interface Wiki {
    /**
     * Return articles and videos saved.
     * 
     * @return a set of WikiContent.
     */
    Set<WikiContent> getContents();

    /**
     * Add a new Content.
     * 
     * @param content the new content to add.
     */
    void addContent(WikiContent content);

    /**
     * Filter content by tags or titles.
     * 
     * @param query the search string.
     * @return the contents that have that query
     */
    Set<WikiContent> search(String query);
}
