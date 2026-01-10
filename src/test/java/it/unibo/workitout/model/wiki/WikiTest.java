package it.unibo.workitout.model.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.wiki.contracts.Article;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.impl.ArticleImpl;
import it.unibo.workitout.model.wiki.impl.WikiImpl;

class WikiTest {

    @Test
    void testWikiContent() {
        final Wiki wiki = new WikiImpl();
        final Set<String> tags = Set.of("Spalle", "Petto", "Forza");
        final Article art = new ArticleImpl("Allenmaneto Petto", "Test", tags);
        wiki.addContent(art);

        assertNotNull(wiki.getContents());
        assertEquals(1, wiki.getContents().size());
        assertEquals("Allenmaneto Petto", wiki.getContents().iterator().next().getTitle());
        assertEquals(1, wiki.search("Petto").size());
        assertEquals(1, wiki.search("forza").size());
        assertEquals(1, wiki.search("").size());
    }
}
