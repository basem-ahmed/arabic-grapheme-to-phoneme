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
    private List<Letter> letters = new ArrayList<>();
    private List<Syllable> syllables = new ArrayList<>();

    public Word(String word, StatefulKnowledgeSession session) {
        letters.add(new Letter(String.valueOf(word.charAt(0)), null, word.substring(1)));
        for (int i = 1; i < word.length() - 1; i++) {
            letters.add(new Letter(String.valueOf(word.charAt(i)), word.substring(0, i), word.substring(i + 1)));
        }
        letters.add(new Letter(String.valueOf(word.charAt(word.length() - 1)), word.substring(0, word.length() - 1), null));
        init(session);
    }

    private void init(StatefulKnowledgeSession session) {
        int size = letters.size(), startingIndex = 0;
        if (letters.get(0).getContent().equals("ل") && letters.get(0).getContent().equals("أ")) {
            letters.get(0).setRepresentation("la?a");
            letters.get(1).setRepresentation("");
            startingIndex = 2;
        }
        if (letters.get(0).getContent().equals("ا") && letters.get(1).getContent().equals("ل") && letters.get(2).getContent().equals("أ")) {
            letters.get(0).setRepresentation("?al?a");
            letters.get(1).setRepresentation("");
            letters.get(2).setRepresentation("");
            startingIndex = 3;
        }
        for (int i = startingIndex; i < size; ++i) {
            Letter letter = letters.get(i);
            FactHandle handle = session.insert(letter);
            session.fireAllRules(1);
            session.delete(handle);
            if (letter.getRepresentation() == null) {
                letter.setRepresentation(Graphemes.getRepresentation(letter.getContent()));
            }
        }
        syllablify();
    }

    private void syllablify() {
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
        return letters.stream().map(Letter::getRepresentation).reduce("", (f, s) -> f + s);
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public boolean hasShamsi() {
        return letters.stream().anyMatch(Letter::hasShamsi);
    }

    public Letter getLastLetter() {
        return letters.get(letters.size() - 1);
    }

    public List<Syllable> getSyllables() {
        return syllables;
    }

    public void setSyllables(List<Syllable> syllables) {
        this.syllables = syllables;
    }
}