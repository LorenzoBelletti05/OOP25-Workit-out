package it.unibo.workitout.model.wiki.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.contracts.WikiRepository;

/**
 * Implementation of WikiRepository.
 */
public class WikiRepositoryImpl implements WikiRepository {
    private static final String ARTICLES_PATH = "/data/wiki/articles.json";
    private static final String VIDEOS_PATH = "/data/wiki/videos.json";
    private static final System.Logger LOGGER = System.getLogger(WikiRepositoryImpl.class.getName());

    private final Gson gson = new Gson();

    /**
     * Load the contents.
     * 
     * @param model of the wiki.
     */
    @Override
    public void loadAll(final Wiki model) {
        loadArticles(model);
        loadVideos(model);
    }

    /**
     * Load all the articles from the json.
     * 
     * @param model of the wiki.
     */
    private void loadArticles(final Wiki model) {
        try (Reader reader = new InputStreamReader(
            WikiRepositoryImpl.class.getResourceAsStream(ARTICLES_PATH), StandardCharsets.UTF_8)) {
            final ArticleImpl[] articles = gson.fromJson(reader, ArticleImpl[].class);
            if (articles != null) {
                for (final ArticleImpl art : articles) {
                    model.addContent(art);
                }
            }
        } catch (final IOException e) {
            LOGGER.log(
                System.Logger.Level.ERROR, 
                "Error loading articles.json: " + e.getMessage(), 
                e
            );
        }
    }

    /**
     * Load all the videos from the json.
     * 
     * @param model of the wiki.
     */
    private void loadVideos(final Wiki model) {
        try (Reader reader = new InputStreamReader(
            WikiRepositoryImpl.class.getResourceAsStream(VIDEOS_PATH), StandardCharsets.UTF_8)) {
            final VideoImpl[] videos = gson.fromJson(reader, VideoImpl[].class);
            if (videos != null) {
                for (final VideoImpl vid : videos) {
                    model.addContent(vid);
                }
            }
        } catch (final IOException e) {
            LOGGER.log(
                System.Logger.Level.ERROR, 
                "Error loading videos.json: " + e.getMessage(), 
                e
            );
        }
    }
}
