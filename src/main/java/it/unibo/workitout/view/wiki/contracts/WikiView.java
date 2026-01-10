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

    /**
     * Switch to the list view.
     */
    void showList();

    /**
     * Switch to the detail view.
     * 
     * @param title the content title.
     * @param text the content text.
     */
    void showDetail(String title, String text);

    /**
     * Listener for the item in the list.
     * 
     * @param listener ...
     */
    void addSelectionListener(java.util.function.Consumer<WikiContent> listener);

    /**
     * Listener for back button.
     * 
     * @param listener ...
     */
    void addBackListener(Runnable listener);

    /**
     * Get the query.
     * 
     * @return the text of the query.
     */
    String getSearchQuery();

    /**
     * Listener for search.
     * 
     * @param action ...
     */
    void addSearchListener(Runnable action);
}
