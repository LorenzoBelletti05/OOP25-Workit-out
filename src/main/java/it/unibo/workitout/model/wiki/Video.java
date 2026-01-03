package it.unibo.workitout.model.wiki;

import java.net.URL;

/**
 * Video interface.
 */
public interface Video extends WikiContent {
    /**
     * @return Url of the video.
     */
    URL getUrl();
}
