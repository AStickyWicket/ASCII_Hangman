import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class InitGame {
    private String level, phrase;
    List<String> levelOne = new ArrayList<>();
    List<String> levelTwo = new ArrayList<>();
    List<String> levelThree = new ArrayList<>();

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public InitGame() {
        genPhraseArray();
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

            System.out.println(levelOne);
            System.out.println(levelTwo);
            System.out.println(levelThree);




        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
