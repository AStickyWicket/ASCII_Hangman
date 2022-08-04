import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InitGame {
    private String level, phrase;
    List<String> levelOne = new ArrayList<>();
    List<String> levelTwo = new ArrayList<>();
    List<String> levelThree = new ArrayList<>();

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
            levelOne.remove(phrase);
        } else if (level.equals("medium")) {
            phrase = levelTwo.get(random.nextInt(levelTwo.size()));
            levelTwo.remove(phrase);
        } else {
            phrase = levelThree.get(random.nextInt(levelThree.size()));
            levelThree.remove(phrase);
        }

    }

    public InitGame() {
        genPhraseArray();
        setLevel();
        setPhrase();
        System.out.println("Debugging: " + level + " " + phrase
                + "\n" + levelOne.size() + " " + levelTwo.size() + " " + levelThree.size()); // Verify item got removed
    }

    private void genPhraseArray() {
        try {
            File phraseList = new File("phraseList.json");
            JsonElement fileElement = JsonParser.parseReader(new FileReader(phraseList));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray levelOneJson = fileObject.get("LevelOne").getAsJsonArray();
            JsonArray levelTwoJson = fileObject.get("LevelTwo").getAsJsonArray();
            JsonArray levelThreeJson = fileObject.get("LevelThree").getAsJsonArray();

            levelOneJson.forEach( phrase -> levelOne.add(phrase.getAsString()));
            levelTwoJson.forEach( phrase -> levelTwo.add(phrase.getAsString()));
            levelThreeJson.forEach( phrase -> levelThree.add(phrase.getAsString()));

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

}
