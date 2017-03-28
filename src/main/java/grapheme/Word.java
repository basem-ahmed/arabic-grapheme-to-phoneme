package grapheme;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        Letter begin = handleSpeicalCases();
        applyRules(begin, session);
        //syllablify();
    }

    private void applyRules(Letter begin, StatefulKnowledgeSession session) {
        class Consumer implements Runnable {
            StatefulKnowledgeSession knowledgeSession;
            BlockingQueue<Letter> queue;

            public Consumer(BlockingQueue<Letter> queue, StatefulKnowledgeSession session1) {
                knowledgeSession = session1;
                this.queue = queue;
            }

            @Override
            public void run() {
                Letter letter;
                List<FactHandle> handles = new ArrayList<>();
                try {
                    while ((letter = queue.take()) != null) {
                        handles.add(knowledgeSession.insert(letter));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                knowledgeSession.fireAllRules();
                handles.forEach(factHandle -> knowledgeSession.delete(factHandle));
            }

            public void add(Letter letter) {
                queue.add(letter);
            }
        }
//        BlockingQueue<Letter> letters = new ArrayBlockingQueue<>(10);
//        Thread thread = new Thread(new Consumer(letters, session));
//        thread.start();
        for (Letter letter = begin; letter != null; letter = letter.getNext()) {
            //letters.offer(letter);
            FactHandle handle = session.insert(letter);
            session.fireAllRules(1);
            session.delete(handle);
            if (letter.getRepresentation() == null) {
                letter.setRepresentation(Graphemes.getRepresentation(letter.getContent()));
            }
        }
//        thread.interrupt();
//        for (Letter letter = begin; letter != null; letter = letter.getNext()) {
//            if (letter.getRepresentation() == null)
//                letter.setRepresentation(Graphemes.getRepresentation(letter.getContent()));
//        }
    }

    private Letter handleSpeicalCases() {
        Letter begin = first;
        if (first.getContent().equals("ل") && first.getNext().getContent().equals("أ")) {
            first.setRepresentation("la?a");
            first.getNext().setRepresentation("la?a");
            begin = first.getNext().getNext();
        }
        if (first.getContent().equals("ا") && first.getNext().getContent().equals("ل") && first.getNext().getNext().getNext().equals("أ")) {
            first.setRepresentation("?al?a");
            first.getNext().setRepresentation("");
            first.getNext().getNext().setRepresentation("");
            begin = first.getNext().getNext().getNext();
        }
        return begin;
    }

    private void syllablify() {
        List<Letter> letters = new ArrayList<>();
        for (Letter temp = first; temp != null; temp = temp.getNext()) {
            letters.add(temp);
        }
        this.syllables = new MatchSyllabifier().syllablify(letters);
    }

    public String representation() {
        StringBuilder builder = new StringBuilder();
        for (Letter letter = first; letter != null; letter = letter.getNext()) {
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

    public boolean hasShamsi() {
        return first.getContent().equals("ا")
                && first.getNext().getContent().equals("ل")
                && first.getNext().getNext().isShamsi();
    }

    public List<Syllable> getSyllables() {
        return syllables;
    }

    public void setSyllables(List<Syllable> syllables) {
        this.syllables = syllables;
    }
}