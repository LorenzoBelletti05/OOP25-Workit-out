package it.unibo.workitout.model.wiki.impl;

import java.net.URL;
import java.util.Set;

import it.unibo.workitout.model.wiki.contracts.Video;

/**
 * Implementation of Video.
 */
public final class VideoImpl implements Video {
    private final String title;
    private final String description;
    private final URL url;
    private final Set<String> tags;

    /**
     * Constructor.
     * 
     * @param title the title of the video.
     * @param description the description of the video.
     * @param url the url of the video.
     * @param tags set of strings for filtering infos.
     */
    public VideoImpl(final String title, final String description, final URL url, final Set<String> tags) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.tags = Set.copyOf(tags);
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

    @Override
    public Set<String> getTags() {
        return this.tags;
    }
}
