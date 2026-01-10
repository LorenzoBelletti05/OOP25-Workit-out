package it.unibo.workitout.model.wiki.contracts;

/**
 * Repository Interface.
 */
@FunctionalInterface
public interface WikiRepository {
    /**
     * Load articles and videos from Json files.
     * 
     * @param model of the wiki.
     */
    void loadAll(Wiki model);
}
