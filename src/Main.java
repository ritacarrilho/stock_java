import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileManager newFile = new FileManager("StockVoiture"); // instance file
        newFile.createFile(); // create file
        newFile.writeFakeData(); // write fake data in file
        System.out.println(newFile.readFile());

        Gui gui = new Gui();
        gui.getDataFromFile(newFile.fileName);
    }
}