import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gui {
    public Window frame;
    public JFrame f;
    public JLabel panelTitle;
    public JPanel topSection;
    public JPanel middleSection = new JPanel();
    public JPanel bottomSection;

    public JTable table;
    DefaultTableModel tableModel  = new DefaultTableModel();
    public Gui() {
        this.GuiFrame();
    }

    public void GuiFrame() {
        f = new JFrame("Fabulous Voitures Stock");
        panelTitle = new JLabel();
        panelTitle.setText("Fabulous Voitures Stock");

        topSection = new JPanel();
        topSection.add(panelTitle);

//        bottomSection = new JPanel();
//        bottomSection.add(new JScrollPane(this.createTable()));

        f.setSize(750, 750);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
//        f.add(new JScrollPane(this.createTable()));
        f.getContentPane().add(BorderLayout.NORTH,panelTitle);
        f.getContentPane().add(BorderLayout.SOUTH,new JScrollPane(this.createTable()));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JTable createTable() {
        table = new JTable(tableModel);
        tableModel.addColumn("Band");
        tableModel.addColumn("Model");
        tableModel.addColumn("Age");
        tableModel.addColumn("Carburation");
        tableModel.addColumn("Condition");

        return table;
    }

    // Print each voiture in a table row
    public void getDataFromFile(String fileName) {
        FileManager jsonFile = new FileManager(fileName);
        JSONArray jsonArray = jsonFile.readFile();
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject explrObject = (JSONObject) jsonArray.get(i);
            this.tableModel.insertRow(0, new Object[]{
                    explrObject.get("brand"),
                    explrObject.get("model"),
                    explrObject.get("age"),
                    explrObject.get("carburation"),
                    explrObject.get("condition")
            });
        }
    }
}
