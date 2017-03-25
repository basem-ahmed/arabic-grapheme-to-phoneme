package grapheme;

import java.util.List;

public class Syllable {
    private List<Letter> letters;
    public Syllable(List<Letter> letters){
        this.letters = letters;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public String representation(){
        return letters.stream().map(l->l.getType().toString()).reduce("", (f, s) -> f + s);
    }

    @Override
    public String toString() {
        return String.join("", letters);
    }
}
