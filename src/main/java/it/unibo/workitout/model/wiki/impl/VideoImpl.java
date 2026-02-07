package it.unibo.workitout.model.wiki.impl;

import java.net.URL;
import java.util.Set;
import it.unibo.workitout.model.wiki.contracts.Video;

/**
 * Implementation of Video.
 */
public final class VideoImpl implements Video {
    private final String title;
    private final String text;
    private final URL url;
    private final Set<String> tags;

    /**
     * Constructor.
     * 
     * @param title the title of the video.
     * @param url the url of the video.
     * @param text description of video
     * @param tags set of strings for filtering infos.
     */
    public VideoImpl(final String title, final String text, final URL url, final Set<String> tags) {
        this.title = title;
        this.text = text;
        this.url = url;
        this.tags = Set.copyOf(tags);
    }

    @Override
    public URL getUrl() {
        return this.url;
    }

    @Override
    public String getText() { 
        return this.text; 
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Set<String> getTags() {
        return Set.copyOf(this.tags);
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    @Override
    public boolean isVideo() {
        return true;
    }
}
