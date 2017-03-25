package grapheme;

import java.util.List;

public interface Syllabifier {
    List<Syllable> syllablify(List<Letter> letters);
}
