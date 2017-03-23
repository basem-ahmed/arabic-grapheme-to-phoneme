package grapheme;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.List;

public class Word{
    private List<Letter> letters = new ArrayList<>();
    public Word(String word){
        letters.add(new Letter(word.charAt(0), null, word.substring(1)));
        for (int i = 1; i < word.length() - 1; i++) {
            letters.add(new Letter(word.charAt(i), word.substring(0, i), word.substring(i + 1)));
        }
        letters.add(new Letter(word.charAt(word.length() - 1), word.substring(0, word.length() - 1), null));
    }

    public String representation(StatefulKnowledgeSession session){
        int size = letters.size();
        for (int i = 0; i < size; i++) {
            Letter letter = letters.get(i);
            FactHandle handle = session.insert(letter);
            session.fireAllRules();
            session.delete(handle);
            if(letter.getRepresentation() == null){
                letter.setRepresentation(Graphemes.getRepresentation(letter.getContent()));
            }
        }
        return String.join("", letters);
    }

    public boolean hasShamsi(){
        return letters.stream().anyMatch(Letter::hasShamsi);
    }

    public Letter getLastLetter(){
        return letters.get(letters.size() - 1);
    }
}