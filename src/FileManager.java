import netscape.javascript.JSObject;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.json.simple.JSONArray;
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
;    }

    // create file with fake data - pass an object
    public File createFile() {
        try {
            this.file = new File(this.path);
            // create the file, if it does not exist
            if (file.createNewFile()) {
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

    public JSONArray readFile() {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();

        try {
            FileReader fr = new FileReader(this.path);
            Object ob = parser.parse(fr);
            jsonArray = (JSONArray) ob;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return jsonArray;
    }

    // delete data in file
    public void updateJsonData() {
        try{
            this.file = new File(this.path);
            FileWriter fw = new FileWriter(this.file, false);
            PrintWriter pw = new PrintWriter(fw, false);

            pw.flush();
            pw.close();
            fw.close();
        }catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }

    // Write fake data if file is empty
    public void writeFakeData() {
        try {
            this.file = new File(this.path);
            FileReader fr = new FileReader(this.path);
//            System.out.println(fr.read());

            if (this.file.isFile() && fr.read() < 0) {
                FileWriter writer = new FileWriter(this.file, true);
                this.jsonList = new JSONArray();

                // fake data
                Voiture voiture1 = new Voiture(1, "Peugeot", "206", 6, "diesel", "Very good");
                Voiture voiture2 = new Voiture(2,"Peugeot", "207", 2, "diesel", "Average");
                Voiture voiture3 = new Voiture(3, "Peugeot", "208", 14, "diesel", "Bad");
                Voiture voiture4 = new Voiture(4, "Peugeot", "209", 1, "diesel", "Good");
                Voiture voiture5 = new Voiture(5, "Peugeot", "Scenic", 7, "diesel", "Good");
                Voiture voiture6 = new Voiture(6, "Peugeot", "Laguna", 4, "diesel", "Average");

//                JSONArray rootVoiture =  this.readFile(); // contruction du tableau json
                    this.jsonList.add(voiture1.voitureToJson());
                    this.jsonList.add(voiture2.voitureToJson());
                    this.jsonList.add(voiture3.voitureToJson());
                    this.jsonList.add(voiture4.voitureToJson());
                    this.jsonList.add(voiture5.voitureToJson());
                    this.jsonList.add(voiture6.voitureToJson());

//                    System.out.println(this.jsonList.toJSONString());
                    writer.write(this.jsonList.toJSONString());
                    writer.flush();

            } else {
                System.out.println("File already filled");
            }
        } catch (Exception e){
            System.out.println( e.getMessage() );
        }
    }

    // Add new Json object to file
    public void writeInFile(Voiture voiture) {
        try {
            this.file = new File(this.path);
            FileReader fr = new FileReader(this.path);

            if (this.file.isFile() && fr.read() > 0) {
                FileWriter fw = new FileWriter(this.path, true);
                JSONArray voitureArray = this.readFile();
                voitureArray.add(voiture.voitureToJson());
                this.updateJsonData();

                fw.write(voitureArray.toJSONString());
                fw.flush();
                System.out.println("Added new voiture to the stock array");
            } else if (this.file.isFile() && fr.read() < 0) {
                FileWriter fw = new FileWriter(this.path, true);
                JSONArray voitureArray = new JSONArray();
                voitureArray.add(voiture.voitureToJson());

                fw.write(voitureArray.toJSONString());
                fw.flush();
                fw.close();
                System.out.println("Added new voiture to the empty stock");
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFileData() {
        try {
            this.file = new File(this.path);
            if (this.file.isFile()) {
                this.file.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Delete json file
    public void deleteFile() {
        try {
            this.file = new File(this.path);
            if (this.file.isFile()) {
                this.file.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer getLenght() {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();

        try {
            FileReader fr = new FileReader(this.path);
            Object ob = parser.parse(fr);
            jsonArray = (JSONArray) ob;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        return jsonArray.size();
    }
}
