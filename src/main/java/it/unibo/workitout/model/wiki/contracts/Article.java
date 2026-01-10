package it.unibo.workitout.model.wiki.contracts;

/**
 * Article interface.
 */
public interface Article extends WikiContent {

    /**
     * @return Text.
     */
    @Override
    String getText();
}
