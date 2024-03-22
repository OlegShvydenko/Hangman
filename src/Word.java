import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Word {
    private static final List<String> words = new ArrayList<>();
    private final Random randomGenerator = new Random();
    private ArrayList<Letter> letters;
    public Word() throws IOException {
        if(words.isEmpty()) fillDictionary();
        String tempWord = words.get(randomGenerator.nextInt(words.size()));
        this.letters = splitIntoLetters(tempWord);
    }

    public Word(int num){
        ArrayList<Letter> list = new ArrayList<>();
        for (int i = 0; i < num; i++){
            list.add(new Letter('▯'));
        }
        this.letters = list;
    }

    public ArrayList<Letter> getLetters() {
        return letters;
    }

    private void fillDictionary() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Words.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
    }

    private ArrayList<Letter> splitIntoLetters(String word) {
        ArrayList<Letter> list = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            list.add(new Letter(word.charAt(i)));
        }
        return list;
    }
}
