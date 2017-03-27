package grapheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MatchSyllabifier implements Syllabifier{
    @Override
    public List<Syllable> syllablify(List<Letter> letters) {
        List<Syllable> syllables = new ArrayList<>();
        List<String> sylabs = Arrays.asList("CVCC, CVC", "CV");
        Function<String, Integer> getIndex = (word) -> {
            return sylabs.stream().filter(word::endsWith).findFirst().map(String::length).orElse(0);
//            for (String sylab : sylabs) {
//                if (word.endsWith(sylab))
//                    return sylab.length();
//            }
//            return 0;
        };
        List<Letter> temp = letters.stream()
                .filter(l -> !l.getRepresentation().equals(Letter.NULL_CHAR))
                .collect(Collectors.toList());
        while (true) {
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
        return syllables;
    }
}
