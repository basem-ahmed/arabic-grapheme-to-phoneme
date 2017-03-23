import grapheme.GraphemeToPhonemeConverter;

public class Converter {
    public static void main(String[] args) {
        String text = "مرحبا كيف حالك يا صديقي ذهبوا إلى الملعب برفقة أصدقائهم طأطأ رأسه";
        System.out.println(GraphemeToPhonemeConverter.convert(text));
    }
}
