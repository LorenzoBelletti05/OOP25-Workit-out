package it.unibo.workitout.model.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.impl.WikiImpl;
import it.unibo.workitout.model.wiki.impl.WikiRepositoryImpl;

/**
 * New class for test the Wiki system.
 */
class WikiTest {
    private Wiki wiki;

    /**
     * Set up the test.
     */
    @BeforeEach
    void setUp() {
        this.wiki = new WikiImpl();
        final WikiRepositoryImpl repository = new WikiRepositoryImpl();
        //load all wiki contents
        repository.loadAll(this.wiki);
    }

    /**
     * Testing the loaded contents.
     */
    @Test
    void testDataLoading() {
        assertNotNull(
            wiki.getContents(), 
            "the wiki contents should not be null");
        assertFalse(
            wiki.getContents().isEmpty(), 
            "the wiki contents should contains articles and videos");
    }

    /**
     * Testing the search function.
     */
    @Test
    void testSearchWithRealData() {
        assertFalse(this.wiki.search("Dieta").isEmpty(), 
            "searching for 'Dieta' should return a content");
        assertFalse(this.wiki.search("squat").isEmpty(), 
            "Searching for 'squat' (lowercase) should return a content");
    }

    /**
     * Testing the empty search function.
     */
    @Test
    void testEmptySearch() {
        final int totalContents = this.wiki.getContents().size();
        assertEquals(
            totalContents, 
            this.wiki.search("").size(), 
            "An empty search should return all the contents");
    }
}
