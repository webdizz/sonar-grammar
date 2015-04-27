package name.webdizz.sonar.grammar;

import name.webdizz.sonar.grammar.issue.tracking.GrammarActionDefinition;
import name.webdizz.sonar.grammar.issue.tracking.LinkFunction;
import name.webdizz.sonar.grammar.profile.GrammarProfileDefinition;
import name.webdizz.sonar.grammar.rule.GrammarRulesDefinition;
import name.webdizz.sonar.grammar.sensor.GrammarIssuesSensor;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

@Properties({
    @Property(
            key = GrammarPlugin.EXCLUSION,
            name = "Exclusion Property",
            description = "Defines resources to be excluded from analysis",
            defaultValue = ""),
    @Property(
            key = GrammarPlugin.INCLUSION,
            name = "Inclusion Property",
            description = "Defines resources to be included for analysis",
            defaultValue = ""),
    @Property(
            key = GrammarPlugin.DICTIONARY,
            name = "Dictionary path",
            description = "Defines resources to be included for analysis",
            defaultValue = "dict/english.0"),
    @Property(
            key = GrammarPlugin.HOST_PORT,
            name = "Sonar host and port",
            description = "Host:port",
            defaultValue = "localhost:9000"),
    @Property(
            key = GrammarPlugin.CREDENTIAL,
            name = "Admin credential ",
            description = "login:password",
            defaultValue = "admin:admin")
})
public class GrammarPlugin extends SonarPlugin {

    public static final String EXCLUSION = "sonar.grammar.exclusion";
    public static final String INCLUSION = "sonar.grammar.inclusion";
    public static final String DICTIONARY = "sonar.grammar.dictionary";
    public static final String HOST_PORT = "sonar.host.port";
    public static final String CREDENTIAL = "sonar.admin.credential";

    @Override
    public List getExtensions() {
        return Arrays
                .asList(// Definitions
                        GrammarRulesDefinition.class,
                        GrammarProfileDefinition.class,
                        // Metrics
                        GrammarMetrics.class,
                        // Sensors
                        GrammarIssuesSensor.class,
                        //Issue review
                        LinkFunction.class,
                        GrammarActionDefinition.class

                );
    }
}
