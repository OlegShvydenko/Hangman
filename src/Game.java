import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
public class Game {
    private static int score = 0;
    private final Random randomGenerator = new Random();
    private static boolean indicator = false;
    private static final String[] baseHangman = {
            "      _______",
            "     |",
            "     |",
            "     |",
            "     |",
            "     |",
            "_____|_______"
    };
    private static String[] hangman = baseHangman;
    private void drawGallows(){
        switch (score){
            case (1):
                break;
            case (2):
                hangman[1] = "     |      (_)";
                break;
            case (3):
                hangman[2] = "     |       |";
                hangman[3] = "     |       |";
                break;
            case (4):
                hangman[2] = "     |      \\|/";
                break;
            case (5):
                hangman[4] = "     |      / \\";
                break;
        }
        if(score > 0)
            for (String line: hangman) {
                System.out.println(line);
            }
    }
    public void playGame() throws IOException {
        Word word = new Word();
        Word tempword = new Word(word.getLetters().size());
        System.out.println("Было загадано слово, попробуйте угадать:");

        startLetters(word, tempword);


        while (score < 5){
            System.out.println("Статус слова: " + getStringFromList(tempword.getLetters()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Letter letterFromKeyboard = new Letter((char)reader.read());
            openLetters(word, tempword, letterFromKeyboard);
            if (checkWin(tempword)) break;
        }

        System.out.println(checkWin(tempword) ? "Поздравляю, вы выиграли!!!" : "К сожалению вы проиграли.");
        System.out.println("Хотите начать новую игру?");
        System.out.println("Введите 1/2 чтобы подтвердить да/нет");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String answer = reader.readLine();
        if (Objects.equals(answer, "1")) {
            score = 0;
            hangman = baseHangman;
            playGame();
        }
    }

    private void startLetters(Word word, Word tempWord){
        int number1 = randomGenerator.nextInt(word.getLetters().size());
        int number2 = randomGenerator.nextInt(word.getLetters().size());

        while (word.getLetters().get(number1) == word.getLetters().get(number2)){
            number2 = randomGenerator.nextInt(word.getLetters().size());
        }
        openStartLetters(word, tempWord, word.getLetters().get(number1));
        openStartLetters(word, tempWord, word.getLetters().get(number2));
    }

    private void openStartLetters(Word word, Word tempWord, Letter letter){
        for (int i = 0; i < word.getLetters().size(); i++){
            if (word.getLetters().get(i).letter == letter.letter) {
                tempWord.getLetters().set(i, letter);
            }
        }
    }
    private void openLetters(Word word, Word tempWord, Letter letter){
        for (int i = 0; i < word.getLetters().size(); i++){
            if (word.getLetters().get(i).letter == letter.letter) {
                tempWord.getLetters().set(i, letter);
                indicator = true;
            }
        }
        if (!indicator) {
            score++;
            System.out.println("Ошибка, такой буквы нет!");
            System.out.println("Количество ошибок - " + score + " из 5.");
            drawGallows();
        }
        indicator = false;
    }
    private boolean checkWin(Word word){
        for (int i = 0; i < word.getLetters().size(); i++) {
            if (word.getLetters().get(i).letter == '▯') return false;
        }
        return true;
    }
    private String getStringFromList(ArrayList<Letter> list){
        StringBuilder tempString = new StringBuilder();
        for (Letter letter : list) {
            tempString.append(letter.getLetter());
        }
        return tempString.toString();
    }

}
