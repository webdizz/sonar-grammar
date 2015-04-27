package name.webdizz.sonar.grammar.sensor;

import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import name.webdizz.sonar.grammar.PluginParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Class-trigger to react to grammar spell violations
 *
 */
class GrammarViolationTrigger implements SpellCheckListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarViolationTrigger.class);

    private final GrammarIssuesWrapper lineWrapper;

    public GrammarViolationTrigger(final GrammarIssuesWrapper lineWrapper) {
        this.lineWrapper = lineWrapper;
    }

    /**
     * Detected spelling error
     *
     * @param event spell-check-event
     */
    @Override
    public void spellingError(final SpellCheckEvent event) {
        final StringBuilder spellMessageBuilder = new StringBuilder(100);
        spellMessageBuilder
                .append(PluginParameter.ERROR_DESCRIPTION)
                .append(event.getInvalidWord())
                .append("\'");
        if (LOGGER.isDebugEnabled()) {
            final Object[] arguments = new Object[]{ event.getInvalidWord(), event.getWordContextPosition(), lineWrapper.getLine()};
            LOGGER.debug("Detected invalid word \'{}\' at Col.{}\nin the line \"{}\"", arguments);
        }
        final List suggestions = event.getSuggestions();
        if (isNotEmpty(suggestions)) {
            spellMessageBuilder.append("\n  Suggestions: ");
            boolean first = true;
            for (final Object suggestion : suggestions) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Adding suggestion: {}", suggestion);
                }
                if (first) {
                    spellMessageBuilder.append(suggestion);
                    first = false;
                } else {
                    spellMessageBuilder.append(", ").append(suggestion);
                }
            }
            spellMessageBuilder.append(";");
        }
        final String spellMessage = spellMessageBuilder.toString();
        if (LOGGER.isInfoEnabled()) {
            Object[] args;
            args = new Object[]{spellMessage, event.getWordContextPosition(), lineWrapper.getKey()};
            LOGGER.info("{} at '{}' \n  in '{}'\n ", args);
        }
        lineWrapper.incident(spellMessage, event.getWordContextPosition());
    }

}
