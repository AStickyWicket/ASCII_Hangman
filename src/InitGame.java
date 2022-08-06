import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InitGame {
    private String level, phrase, userGuesses;
    private int lives;
    private final List<String> levelOne = new ArrayList<>();
    private final List<String> levelTwo = new ArrayList<>();
    private final List<String> levelThree = new ArrayList<>();

    private final String[] HANGMANSTAGES = new String[7];
    private final ArrayList<Character> guessedLetters = new ArrayList<>();
    private boolean winFlag = false;

    public String getLevel() {
        return level;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase() {
        Random random = new Random();
        if (level.equals("easy")){
            phrase = levelOne.get(random.nextInt(levelOne.size()));
            userGuesses = phrase.replaceAll("[a-zA-Z]", "_");
            levelOne.remove(phrase);
        } else if (level.equals("medium")) {
            phrase = levelTwo.get(random.nextInt(levelTwo.size()));
            userGuesses = phrase.replaceAll("[a-zA-Z]", "_");
            levelTwo.remove(phrase);
        } else {
            phrase = levelThree.get(random.nextInt(levelThree.size()));
            userGuesses = phrase.replaceAll("[a-zA-Z]", "_");
            levelThree.remove(phrase);
        }

    }

    public InitGame() {
        loadAllArrays();
        do{
            setLevel();
            setPhrase();
            lives = 6;
            printHangman();
            do {
                guessLetter();
            } while (lives !=0 && !winFlag);}
        while (playAgain());
    }

    private void loadAllArrays() {
        try {
            File phraseList = new File("phraseList.json");
            File hangmanStages = new File("hangmanStages.txt");
            BufferedReader br = new BufferedReader(new FileReader(hangmanStages));

            JsonElement fileElement = JsonParser.parseReader(new FileReader(phraseList));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray levelOneJson = fileObject.get("LevelOne").getAsJsonArray();
            JsonArray levelTwoJson = fileObject.get("LevelTwo").getAsJsonArray();
            JsonArray levelThreeJson = fileObject.get("LevelThree").getAsJsonArray();

            levelOneJson.forEach( phrase -> levelOne.add(phrase.getAsString()));
            levelTwoJson.forEach( phrase -> levelTwo.add(phrase.getAsString()));
            levelThreeJson.forEach( phrase -> levelThree.add(phrase.getAsString()));

            int counter = 6;
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null) {
                if (line.contains(",")){
                    line = line.replaceAll(",", "");
                    sb.append(line);
                    sb.append("\n");
                    HANGMANSTAGES[counter] = sb.toString();
                    counter--;
                    sb.setLength(0);
                }else{
                    sb.append(line);
                    sb.append("\n");
                }
                line = br.readLine();
            }
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLevel(){
        Scanner input = new Scanner(System.in);
        String chosenLevel;

        label:
        while(true) {
            System.out.println("Enter a difficulty from 1-3 (1 is easy, 3 is hard)");
            String difficulty = input.next();
            switch (difficulty) {
                case "1":
                    System.out.println("You have chosen Easy");
                    chosenLevel = "easy";
                    break label;
                case "2":
                    System.out.println("You have chosen Medium");
                    chosenLevel = "medium";
                    break label;
                case "3":
                    System.out.println("You have chosen Hard");
                    chosenLevel = "hard";
                    break label;
                default:
                    System.out.println("You have entered an invalid difficulty");
                    break;
            }
        }
        this.level = chosenLevel;
    }

    public void guessLetter(){
        Scanner input = new Scanner(System.in);


        while (true) {
            String guess = input.next();
            guess = guess.toLowerCase();

            if (guessedLetters.contains(guess.toUpperCase().charAt(0))) {
                System.out.println("You have already guessed that letter");
            }
            else if (guess.length() == 1 && guess.matches("[a-z]")) {
                if (phrase.toLowerCase().contains(guess)) {
                    for (int i = 0; i < phrase.length(); i++) {
                        if (phrase.toLowerCase().charAt(i) == guess.charAt(0)) {
                            userGuesses = userGuesses.substring(0, i) + guess + userGuesses.substring(i + 1);
                        }
                    }
                } else {
                    lives--;
                }
                guessedLetters.add(guess.toUpperCase().charAt(0));
                printHangman();
                return;
            }
            else {
                System.out.println("You have entered an invalid letter");
            }
        }
    }

    private void printHangman(){
        if (lives >= 0) {
            System.out.println(HANGMANSTAGES[lives] + userGuesses);
            System.out.println("Guessed letters: " + guessedLetters);
        }
        if (lives == 0) {
            System.out.println("You have lost");
            System.out.println("The phrase was: " + phrase);
        }
        if (userGuesses.equals(phrase.toLowerCase())) {
            System.out.println("You have won");
            winFlag = true;
        }
    }

    public boolean playAgain(){
        Scanner input = new Scanner(System.in);
        String playAgain;
        boolean playAgainFlag;
        while(true) {
            System.out.println("Would you like to play again? (yes/no)");
            playAgain = input.next();
            if (playAgain.startsWith("y")) {
                playAgainFlag = true;
                break;
            } else if (playAgain.startsWith("n")) {
                System.out.println("Thank you for playing");
                playAgainFlag = false;
                break;
            } else {
                System.out.println("Not a valid answer, try again!");
            }
        }
    return playAgainFlag;
    }
}
