package grapheme;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Word {
    private Letter first;
    private List<Syllable> syllables = new ArrayList<>();

    public Word(String word, StatefulKnowledgeSession session) {
        Letter l = new Letter(String.valueOf(word.charAt(0)), null);
        first = l;
        int size = word.length();
        for (int i = 1; i < size; i++) {
            Letter c = new Letter(String.valueOf(word.charAt(i)), l);
            l.setNext(c);
            l = c;
        }
        init(session);
    }

    private void init(StatefulKnowledgeSession session) {
        Letter begin = first;
        if(first.getContent().equals("ل") && first.getNext().getContent().equals("أ")){
            first.setRepresentation("la?a");
            first.getNext().setRepresentation("la?a");
            begin = first.getNext().getNext();
        }
        if(first.getContent().equals("ا") && first.getNext().getContent().equals("ل") && first.getNext().getNext().getNext().equals("أ")){
            first.setRepresentation("?al?a");
            first.getNext().setRepresentation("");
            first.getNext().getNext().setRepresentation("");
            begin = first.getNext().getNext().getNext();
        }
        for (Letter letter = begin; letter != null; letter = letter.getNext()) {
            FactHandle handle = session.insert(letter);
            session.fireAllRules(1);
            session.delete(handle);
            if (letter.getRepresentation() == null) {
                letter.setRepresentation(Graphemes.getRepresentation(letter.getContent()));
            }
            System.out.println(letter.getContent());
            System.out.println(letter.getRepresentation());
        }
        //syllablify();
    }

    private void syllablify() {
        List<Letter> letters = new ArrayList<>();
        for(Letter temp = first; temp != null; temp = temp.getNext()){
            letters.add(temp);
        }
        List<String> sylabs = Arrays.asList("CVCC, CVC", "CV");
        Function<String, Integer> getIndex = (word) -> {
            for (String sylab : sylabs) {
                if (word.endsWith(sylab))
                    return sylab.length();
            }
            return 0;
        };
        List<Letter> temp = letters.stream()
                .filter(l -> !l.getRepresentation().equals(Letter.NULL_CHAR))
                .collect(Collectors.toList());
        while (true){
            String types = temp.stream()
                    .map(l -> l.getType().toString())
                    .reduce("", (f, s) -> f + s);
            int len = getIndex.apply(types);
            temp = temp.subList(types.length() - len, types.length() - 1);
            syllables.add(new Syllable(temp));
            if (temp.size() == 3 || temp.size() == 4) {
                Collections.reverse(syllables);
                break;
            }
        }
    }

    public String representation() {
        StringBuilder builder = new StringBuilder();
        for(Letter letter = first; letter != null; letter = letter.getNext()){
            builder.append(letter.getRepresentation());
        }
        return builder.toString();
    }

    public Letter getFirst() {
        return first;
    }

    public void setFirst(Letter first) {
        this.first = first;
    }

    public List<Syllable> getSyllables() {
        return syllables;
    }

    public void setSyllables(List<Syllable> syllables) {
        this.syllables = syllables;
    }
}