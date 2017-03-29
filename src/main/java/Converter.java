import grapheme.GraphemeToPhonemeConverter;
import grapheme.Letter;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.io.IOException;
import java.util.Collection;

public class Converter {
    public static void main(String[] args) throws IOException{
        final String ruleFilePath = "/home/obada/IdeaProjects/GraphemeToPhoneme/src/main/java/rules/Grapheme_to_phoneme.drl";
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newFileResource(ruleFilePath), ResourceType.DRL);
        final KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            errors.forEach(System.out::println);
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        final Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
        final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(pkgs);
        final StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();
        final String text = "طأطأ.";
        System.out.println(new GraphemeToPhonemeConverter(session).convert(text));
        session.dispose();
    }
}
