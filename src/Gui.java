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
    public JPanel middleSection;
    public JPanel bottomSection;
    public JTable table;
    public JButton createVoitureBtn;
    public DefaultTableModel tableModel  = new DefaultTableModel();
    public JTextField t1,t2, t3, t4, t5;

    public Gui() {
        this.GuiFrame();
    }

/* TODO: add inputs to create new voiture
*   add delete button to delete each voiture
*   edit data of each voiture
*/

    public void GuiFrame() {
        this.f = new JFrame("Fabulous Voitures Stock");
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
        this.createInputFields();
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

    public void createInputFields() {
        this.middleSection = new JPanel();

        t1 = new JTextField("Brand");
//        t1.setBounds(50,100, 200,30);
        t2 = new JTextField("Model");
//        t2.setBounds(50,150, 200,30);
        t3 = new JTextField("Age");
//        t3.setBounds(50,150, 200,30);
        t4 = new JTextField("Carburation");
//        t4.setBounds(50,150, 200,30);
        t5 = new JTextField("Condition");
//        t5.setBounds(20,50,280,30);

        createVoitureBtn = new JButton("Add voiture to stock");
//        createVoitureBtn.setBounds(100,140,100,40);

        this.middleSection.add(t1);
        this.middleSection.add(t2);
        this.middleSection.add(t3);
        this.middleSection.add(t4);
        this.middleSection.add(t5);
        this.middleSection.add(createVoitureBtn);

        this.middleSection.setLayout(new BoxLayout(middleSection, BoxLayout.Y_AXIS));

        f.getContentPane().add(BorderLayout.CENTER,middleSection);
    }

    // Print each voiture in a table row
    public void getDataFromFile(String fileName) {
        try {
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
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
