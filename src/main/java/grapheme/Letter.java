package grapheme;

import java.util.*;

public class Letter implements CharSequence {
    private String content;
    private String representation;
    private String previous, next;
    private boolean hasShamsi = false;

    public Letter(String content, String previous, String next) {
        this.content = content;
        this.previous = previous;
        this.next = next;
    }

    public char getLastPrev(){
        return previous.charAt(previous.length() - 1);
    }

    public boolean firstOfNextIs(String c) {
        return next != null && next.charAt(0) == c.charAt(0);
    }

    public boolean lastOfPrevIs(String c) {
        return previous != null && previous.charAt(previous.length() - 1) == c.charAt(0);
    }

    public boolean firstOfNextIsShamsi(){
        return shamsi.contains(next.charAt(0));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
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