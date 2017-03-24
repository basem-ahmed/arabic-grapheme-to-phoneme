import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.io.FileInputStream;
import java.io.IOException;

public class Converter {
    public static void main(String[] args) throws IOException{
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newInputStreamResource(new FileInputStream("/home/obada/IdeaProjects/GraphemeToPhoneme/src/main/java/rules/Grapheme_to_phoneme.drl")), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();
        //String text = "مرحبا كيف حالك يا صديقي. ذهبوا إلى الملعب برفقة أصدقائهم. طأطأ رأسه";
        //System.out.println(new GraphemeToPhonemeConverter(session).convert(text));
    }
}
