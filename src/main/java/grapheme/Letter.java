package grapheme;

import java.util.*;

public class Letter implements CharSequence {
    private char content;
    private String representation;
    public String previous, next;
    private boolean hasShamsi = false;

    public Letter(char content, String previous, String next) {
        this.content = content;
        this.previous = previous;
        this.next = next;
    }

    public char getLastPrev(){
        return previous.charAt(previous.length() - 1);
    }

    public boolean firstOfNextIs(char c) {
        return next != null && next.charAt(0) == c;
    }

    public boolean lastOfPrevIs(char c) {
        return previous != null && previous.charAt(previous.length() - 1) == c;
    }

    public boolean firstOfNextIsShamsi(){
        return shamsi.contains(next.charAt(0));
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public char getFirstOfNext(){
        return next.charAt(0);
    }

    public void setRepresentation(String representation){
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public boolean hasShamsi() {
        return hasShamsi;
    }

    public void setHasShamsi(boolean hasShamsi) {
        this.hasShamsi = hasShamsi;
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
    private static Set<Character> shamsi = new HashSet<>();
    private static Set<Character> qamari = new HashSet<>();
    static {
        shamsi = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('ث','ت','د','ذ','ر','ز','س','ش','ص','ض','ط','ظ','ن','م')));
        qamari = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('أ','ب','ج','ح','خ','ع','غ','ف','ق','ك','م','ه','و','ي')));
    }
}