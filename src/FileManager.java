import netscape.javascript.JSObject;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileManager {
    private File file;
    public String fileName;
    public Path repoPath = FileSystems.getDefault().getPath("src", "data");
    public String path;
    JSONArray jsonList;
    public FileManager(String fileName) {
        this.fileName = fileName;
        this.path = repoPath + "/" + this.fileName + ".json";
        System.out.println(this.fileName);
;    }

    // create file with fake data - pass an object
    public File createFile() {
        try {
            this.file = new File(this.path);

            if (file.createNewFile()) { // if it does not exist, create the file
                System.out.println("The file was created.");
            } else {
                System.out.println("The file already exists.");
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e.getMessage());
        }
        return file;
    }

    // Write fake data if file is empty
    public void writeFakeData() {
        try {
            this.file = new File(this.path);

            // fake data
            Voiture voiture1 = new Voiture("Peugeot", "206", 6, "diesel", 5);
            Voiture voiture2 = new Voiture("Peugeot", "207", 2, "diesel", 2);
            Voiture voiture3 = new Voiture("Peugeot", "208", 14, "diesel", 1);
            Voiture voiture4 = new Voiture("Peugeot", "209", 1, "diesel", 5);
            Voiture voiture5 = new Voiture("Peugeot", "Scenic", 7, "diesel", 3);
            Voiture voiture6 = new Voiture("Peugeot", "Laguna", 4, "diesel", 3);

            if (this.file.isFile()) {
                FileWriter writer = new FileWriter(this.file, true);
                this.jsonList = new JSONArray();

                this.jsonList.add(voiture1.voitureToJson());
                this.jsonList.add(voiture2.voitureToJson());
                this.jsonList.add(voiture3.voitureToJson());
                this.jsonList.add(voiture4.voitureToJson());
                this.jsonList.add(voiture5.voitureToJson());
                this.jsonList.add(voiture6.voitureToJson());

                System.out.println(this.jsonList.toJSONString());
                writer.write(this.jsonList.toJSONString());
                writer.flush();
            } else {
                System.out.println("File already filled");
            }
        } catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }

    public JSONArray readFile() {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();

         try {
             FileReader fr = new FileReader(this.path);

             Object ob = parser.parse(fr); // json parse
             jsonArray = (JSONArray) ob;
             System.out.println(jsonArray);
         } catch (FileNotFoundException e) {
             System.out.println(e.getMessage());
         } catch (IOException e) {
             throw new RuntimeException(e.getMessage());
         } catch (ParseException e) {
             throw new RuntimeException(e.getMessage());
         }
         return jsonArray;
    }

    public void updateFile() {

    }

    public void deleteFile() {

    }

    // Add new Json object to file
    public void writeInFile(Voiture voiture) {
        try {
            FileWriter fw = new FileWriter(this.path, true);
            JSONArray rootVoiture =  this.readFile(); // contruction du tableau json
            rootVoiture.add(voiture.voitureToJson());
//            fw.append(voiture.voitureToJson().toJSONString());

            fw.write(voiture.voitureToJson().toJSONString());
//            fw.write(rootVoiture.toJSONString());
            fw.flush();

            System.out.println("Added new voiture to the stock");
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
