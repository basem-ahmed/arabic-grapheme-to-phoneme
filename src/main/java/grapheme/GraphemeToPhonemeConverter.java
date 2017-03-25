package grapheme;

import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GraphemeToPhonemeConverter {
    private StatefulKnowledgeSession session;
    public GraphemeToPhonemeConverter(StatefulKnowledgeSession session){
        this.session = session;
    }

    public String convert(String text){
        List<Sentence> sentences = Arrays.stream(text.split("[,.]")).map(x -> new Sentence(x.split(" "))).collect(Collectors.toList());
        return String.join("\n", sentences);
    }

    private class Sentence implements CharSequence{
        private List<Word> words = new ArrayList<>();
        private String rep;

        public Sentence(String[] wrs) {
            Arrays.stream(wrs).forEach(word -> this.words.add(new Word(word, session)));
            int size = words.size();
            StringBuilder builder = new StringBuilder(words.get(0).representation());
            for (int i = 1; i < size; i++) {
                Word current = words.get(i);
                boolean shamsi = current.getFirst().getContent().equals("ا")
                              && current.getFirst().getNext().getContent().equals("ل")
                              && current.getFirst().getNext().getNext().isShamsi();
                builder.append(shamsi ? "" : " ").append(current.representation());
            }
            rep = builder.toString();
        }

        public List<Word> getWords(){
            return words;
        }

        public String representation(){
            return rep;
        }

        public List<List<Syllable>> syllables(){
            return words.stream().map(Word::getSyllables).collect(Collectors.toList());
        }

        @Override
        public int length() {
            return representation().length();
        }

        @Override
        public char charAt(int i) {
            return representation().charAt(i);
        }

        @Override
        public CharSequence subSequence(int i, int i1) {
            return representation().subSequence(i, i1);
        }
    }
}
