package it.unibo.workitout.view.wiki.contracts;

import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.Set;
import it.unibo.workitout.model.wiki.contracts.WikiContent;

/**
 * Interface for the Wikiview.
 */
public interface WikiView {
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
     * Back to the main view.
     * 
     * @param listener the action listener for back navigation.
     */
    void addMainBackListener(ActionListener listener);

    /**
     * Listener for the item in the list.
     * 
     * @param listener the consumer for selected content.
     */
    void addSelectionListener(java.util.function.Consumer<WikiContent> listener);

    /**
     * Listener for back button.
     * 
     * @param listener the action to run on back.
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
     * @param action the action to run on search.
     */
    void addSearchListener(Runnable action);

    /**
     * Listener for Show all filters.
     * 
     * @param action the action to run for showing all.
     */
    void addAllFilterListener(Runnable action);

    /**
     * Listener for articles.
     * 
     * @param action the action to run for filtering articles.
     */
    void addArticlesFilterListener(Runnable action);

    /**
     * Listener for videos.
     * 
     * @param action the action to run for filtering videos.
     */
    void addVideosFilterListener(Runnable action);

    /**
     * Listener for food priority.
     * 
     * @param action the action to run for food priority.
     */
    void addPrioFoodListener(Runnable action);

    /**
     * Listener for exercise priority.
     * 
     * @param action the action to run for exercise priority.
     */
    void addPrioExerciseListener(Runnable action);

    /**
     * Set the videoplayer.
     * 
     * @param url of the video.
     */
    void showVideoPlayer(String url) throws URISyntaxException;

    /**
     * Update the label.
     * 
     * @param message the message.
     */
    void updateLabel(String message);
}
