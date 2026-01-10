package it.unibo.workitout.model.wiki.contracts;

import java.util.Set;

/**
 * Generic content of the wiki.
 */
public interface WikiContent {
    /**
     * @return the title of the cotent
     */
    String getTitle();

    /**
     * @return the description of the content
     */
    String getDescription();

    /**
     * @return tags for filtering
     */
    Set<String> getTags();
}
