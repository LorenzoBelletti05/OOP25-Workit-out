package it.unibo.workitout.model.wiki.contracts;

import java.util.Set;

/**
 * Generic content of the wiki.
 */
public interface WikiContent {
    /**
     * @return the title of the cotent.
     */
    String getTitle();

    /**
     * @return the text of the content.
     */
    String getText();

    /**
     * @return tags for filtering.
     */
    Set<String> getTags();

    /**
     * @return true if it's a video.
     */
    boolean isVideo();
}
