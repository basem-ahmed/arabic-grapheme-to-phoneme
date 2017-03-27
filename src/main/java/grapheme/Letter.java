package grapheme;

import java.util.*;

public class Letter implements CharSequence {
    private String content;
    private String representation;
    private Letter previous, next;
    private Type type;
    public enum Type{V, C}
    public Letter(String content, Letter previous) {
        this.content = content;
        this.previous = previous;
        this.type = vowels.contains(content)? Type.V : Type.C;
    }

    public boolean nextIs(String c) {
        return next != null && next.getContent().equals(c);
    }

    public boolean prevIs(String c) {
        return previous != null && previous.getContent().equals(c);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRepresentation(String representation){
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public Letter getPrevious() {
        return previous;
    }

    public void setPrevious(Letter previous) {
        this.previous = previous;
    }

    public Letter getNext() {
        return next;
    }

    public void setNext(Letter next) {
        this.next = next;
    }

    public Type getType(){
        return type;
    }

    public boolean isShamsi(){
        return shamsi.contains(content);
    }

    @Override
    public int length() {
        return representation.length();
    }

    @Override
    public char charAt(int i) {
        return representation.charAt(i);
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        return representation.subSequence(i, i1);
    }

    @Override
    public String toString() {
        return representation;
    }

    public static final String NULL_CHAR = "-";
    private static Set<String> shamsi = new HashSet<>();
    private static Set<String> vowels = new HashSet<>();
    static {
        shamsi = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("ت", "ث", "د", "ذ", "ر", "ز", "س", "ش", "ص", "ض", "ط", "ظ", "ن", "ل")));
        vowels = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("َ","ُ"","ِ","ً","ٍ","ٌ","ا","و","ي")));
    }
}
