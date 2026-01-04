package it.unibo.workitout.model.wiki.api;

import java.util.Set;

/**
 * Interface for the Wikiview.
 */
public interface WikiView {
    /**
     * Start the wiki view. 
     */
    void start();

    /**
     * Update the view.
     * 
     * @param contents set of Wikicontent (Articles/Videos).
     */
    void updateContents(Set<WikiContent> contents);
}
