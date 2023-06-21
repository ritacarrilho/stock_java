import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Gui {
    public Window frame;
    public JFrame f;
    public JLabel panelTitle;
    public JPanel topSection;
    public JPanel middleSection;
    public JPanel bottomSection;
    public JTable table;
    public JButton createVoitureBtn;
    public JButton deleteVoitureBtn;
    public DefaultTableModel tableModel  = new DefaultTableModel();
    public JTextField t1,t2, t3, t4, t5;

    public Gui() {
        this.GuiFrame();
    }

/* TODO:
    delete each voiture
*   edit data of each voiture
*/

    public void GuiFrame() {
        this.f = new JFrame("Fabulous Voitures Stock");
        panelTitle = new JLabel();
        panelTitle.setText("Fabulous Voitures Stock");

        topSection = new JPanel();
        topSection.add(panelTitle);

        bottomSection = new JPanel();
//        bottomSection.add(new JScrollPane(this.createTable()));
        f.setSize(750, 750);
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
//        f.add(new JScrollPane(this.createTable()));
        f.getContentPane().add(BorderLayout.NORTH,panelTitle);
        this.createInputFields();
        f.getContentPane().add(BorderLayout.SOUTH,new JScrollPane(this.createTable()));

        this.middleSection.setLayout(new BoxLayout(middleSection, BoxLayout.Y_AXIS));

        deleteVoitureBtn = new JButton("Delete");
        deleteVoitureBtn.addActionListener(new deleteVoiture());
        this.bottomSection.add(deleteVoitureBtn);
        f.getContentPane().add(BorderLayout.SOUTH, bottomSection);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JTable createTable() {
        JTable table = new JTable(tableModel);
//        table = new JTable(tableModel);
        tableModel.addColumn("Band");
        tableModel.addColumn("Model");
        tableModel.addColumn("Age");
        tableModel.addColumn("Carburation");
        tableModel.addColumn("Condition");

        return table;
    }

    public void createInputFields() {

        this.middleSection = new JPanel();
//        JLabel label1 = new JLabel("Brand:");
        t1 = new JTextField("Brand");
//        t1.setBounds(50,100, 200,30);
//        JLabel label2 = new JLabel("Model:");
        t2 = new JTextField("Model");
//        t2.setBounds(50,150, 200,30);
//        JLabel label3 = new JLabel("Age:");
        t3 = new JTextField("Age");
//        t3.setBounds(50,150, 200,30);
//        JLabel label4 = new JLabel("Carburation:");
        t4 = new JTextField("Carburation");
//        t4.setBounds(50,150, 200,30);
//        JLabel label5 = new JLabel("Condition:");
        t5 = new JTextField("Condition");
//        t5.setBounds(20,50,280,30);

        createVoitureBtn = new JButton("Add voiture to stock");
        createVoitureBtn.addActionListener(new addVoiture());
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

    // verify type of user input
    private void inputsValidation() {
        try {
            if (t1.getText().toString().length() < 1 ) {
                t1.setText("");
                t1.setBorder(new LineBorder(Color.red,1));
            } else {
                t1.setBorder(new LineBorder(Color.green,1));
            }

            if (t2.getText().toString().length() < 1 ) {
                t2.setText("");
                t2.setBorder(new LineBorder(Color.red,1));
            } else {
                t2.setBorder(new LineBorder(Color.green,1));
            }

            if (t3.getText().toString().length() < 1 || !t3.getText().toString().matches("\\d+") ) {
                t3.setText("");
                t3.setBorder(new LineBorder(Color.red,1));
            } else {
                t3.setBorder(new LineBorder(Color.green,1));
            }

            if (t4.getText().toString().length() < 1 ) {
                t4.setText("");
                t4.setBorder(new LineBorder(Color.red,1));
            } else {
                t4.setBorder(new LineBorder(Color.green,1));
            }

            if (t5.getText().toString().length() < 1 || t5.getText().toString().matches("\\d+")) {
                t5.setText("");
                t5.setBorder(new LineBorder(Color.red,1));
            } else {
                t5.setBorder(new LineBorder(Color.green,1));
            }

            System.out.println("all inputs are valid");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Event on check if letter is correct
    private class addVoiture implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // check if input is valid
            inputsValidation();
            // get file
            FileManager voituresFile = new FileManager("StockVoiture");
            //get index
            Integer nextId = voituresFile.getLenght();

            // new voiture
            Voiture newVoiture = new Voiture(nextId+1, t1.getText().toString(), t2.getText().toString(), Integer.parseInt(t3.getText()), t4.getText().toString(), t5.getText().toString());

            // write in json file
            voituresFile.writeInFile(newVoiture);
            // update table
            tableModel.insertRow(0, new Object[] { newVoiture.getBrand(), newVoiture.getModel(), newVoiture.getAge(), newVoiture.getCarburation(), newVoiture.getCondition() });

            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");
        }
    }

    private class deleteVoiture implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = new JTable();
            int selection = table.getSelectedRow();
            System.out.println(selection);

            if (table.getCellSelectionEnabled()) {
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                int rowIndex = table.getSelectedRow();
                int colIndex = table.getSelectedColumn();
                System.out.println(rowIndex);
                System.out.println(colIndex);
            }
            // check for selected row first
//            if(table.getSelectedRow() != -1) {
//                // remove selected row from the model
//                tableModel.removeRow(table.getSelectedRow());
//                JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
//            }

        }
    }
}



