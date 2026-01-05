package it.unibo.workitout.view.wiki.contracts;

import java.util.Set;
import it.unibo.workitout.model.wiki.contracts.WikiContent;

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
