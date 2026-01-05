package it.unibo.workitout.model.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.wiki.contracts.Article;
import it.unibo.workitout.model.wiki.contracts.Wiki;
import it.unibo.workitout.model.wiki.impl.ArticleImpl;
import it.unibo.workitout.model.wiki.impl.WikiImpl;

class WikiTest {

    @Test
    void testWikiContent() {
        final Wiki wiki = new WikiImpl();
        final Article art = new ArticleImpl("Titolo", "Descrizione", "Test");
        wiki.addContent(art);

        assertNotNull(wiki.getContents());
        assertEquals(1, wiki.getContents().size());
        assertEquals("Titolo", wiki.getContents().iterator().next().getTitle());
    }
}
