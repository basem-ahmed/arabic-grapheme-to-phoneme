package grapheme;

import java.util.HashSet;
import java.util.Set;

public class Letter implements CharSequence {
    private char content;
    private String representation;
    private String previous, next;
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

    public enum Vowel {
        SUKOON(''), FATHA(''), DAMA(''), KASRA(''), TAN_FATHA(''), TAN_KASRA(''), TAN_DAMA(''), SHEDAA('')
    }
    public enum Consonant{
        ALIF_MD(''), HAMZA_ALIF, HAMZA_WAW, HAMZA_YA, ALIF(''), LAM(''), WAW(''), YA_MAKSOORA(''), YA(''), HAMZA
    }
    public static final String NULL_CHAR = "-";
    private static final Set<Character> shamsi = new HashSet<>();
    private static final Set<Character> qamari = new HashSet<>();
}