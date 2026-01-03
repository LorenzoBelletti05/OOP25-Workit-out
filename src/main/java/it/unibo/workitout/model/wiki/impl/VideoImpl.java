package it.unibo.workitout.model.wiki.impl;

import java.net.URL;
import it.unibo.workitout.model.wiki.Video;

/**
 * Implementation of Video.
 */
public final class VideoImpl implements Video {
    private final String title;
    private final String description;
    private final URL url;

    /**
     * Constructor.
     * 
     * @param title the title of the video.
     * @param description the description of the video.
     * @param url the url of the video
     */
    public VideoImpl(final String title, final String description, final URL url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
