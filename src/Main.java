import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileManager newFile = new FileManager("StockVoiture"); // instance file
        newFile.createFile(); // create file
        newFile.writeFakeData(); // write fake data in file
        Voiture newv = new Voiture("tata", "miau", 6, "water", 5);
        newFile.writeInFile(newv);
    }
}