public class WordActions {
    public static boolean checkWord(String word, String guess) {
        return word.equals(guess);
    }

    public static boolean checkLetter(String word, String guess) {
        return word.contains(guess);
    }
}
