import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Java_Insert_Update_Delete_Display extends javax.swing.JFrame {

    public Java_Insert_Update_Delete_Display() throws FileNotFoundException, IOException {

        initComponents();
        Show_Products_In_JTable();
    }

    Connection connection = null;

    public ArrayList<Product> getProductsList()
    {
        ArrayList<Product> productsList = new ArrayList<Product>();
        Connection connection = ConnectionProperties.getConnection();
        String sql = "SELECT * FROM produkt";
        Statement stmt;
        ResultSet rs;

        try
        {
            stmt = connection.createStatement();
            rs = stmt.executeQuery( sql );
            Product product;
            while(rs.next())
            {
                product = new Product(rs.getInt("id"),rs.getString("nazwa"),rs.getString("material"),rs.getString("typ"),rs.getString("cena"));
                productsList.add(product);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return productsList;

    }

    public void Show_Products_In_JTable(){
        ArrayList<Product> list =getProductsList();
        DefaultTableModel model = (DefaultTableModel) jTable_Display.getModel();
        Object[] row = new Object[5];
        for(int i=0; i < list.size(); i++)
        {
            row[0] = list.get(i).getInsertId();
            row[1] = list.get(i).getInsertNazwa();
            row[2] = list.get(i).getInsertMaterial();
            row[3] = list.get(i).getInsertTyp();
            row[4] = list.get(i).getInsertCena();

            model.addRow(row);
        }
    }

    public void executeSQLQuery(String query, String message)
    {
        Connection connection = ConnectionProperties.getConnection();
        Statement st;
        try{
            st=connection.createStatement();
            if((st.executeUpdate(query)==1))
            {
                JOptionPane.showMessageDialog(null,"Pomyślnie zrealizowano "+message);

            }else{
                JOptionPane.showMessageDialog(null,"Nie wykonano");
            }
        }catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Display = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Id = new javax.swing.JTextField();
        jTextField_Nazwa = new javax.swing.JTextField();
        jTextField_Material = new javax.swing.JTextField();
        jTextField_Typ = new javax.swing.JTextField();
        jTextField_Cena = new javax.swing.JTextField();
        JTextField_FilterText  = new javax.swing.JTextField();
        jButton_Dodaj = new javax.swing.JButton();
        jButton_Edytuj = new javax.swing.JButton();
        jButton_Usun = new javax.swing.JButton();
        jButton_Filtruj = new javax.swing.JButton();
        Warring = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zarządzaj");

        jLabel1.setText("ID");

        jLabel2.setText("Nazwa");

        jLabel3.setText("Material");

        jLabel4.setText("Typ");

        jLabel5.setText("Cena");

        jLabel6.setText("Filtruj");

        jTextField_Id.setEnabled(false);

        jTextField_Nazwa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //               jTextField_Nazwa(evt);
            }
        });

        jTable_Display.setModel(new javax.swing.table.DefaultTableModel(
                new Object [] [] {

                },
                new String [] {
                        "ID",
                        "Nazwa",
                        "Materiał",
                        "Typ",
                        "Cena"
                }
        ));


        ListSelectionModel cellSelectionModel = jTable_Display.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {


                int[] selectedRow = jTable_Display.getSelectedRows();
                int[] selectedColumns = jTable_Display.getSelectedColumns();
                for (int i = 0; i < selectedRow.length; i++) {

                    String id = jTable_Display.getValueAt(selectedRow[i], 0).toString();

                    String nazwa = jTable_Display.getValueAt(selectedRow[i], 1).toString();

                    String material = jTable_Display.getValueAt(selectedRow[i], 2).toString();

                    String typ = jTable_Display.getValueAt(selectedRow[i], 3).toString();

                    String cena = jTable_Display.getValueAt(selectedRow[i], 4).toString();

                    jTextField_Id.setText(id);
                    jTextField_Nazwa.setText(nazwa);
                    jTextField_Material.setText(material);
                    jTextField_Typ.setText(typ);
                    jTextField_Cena.setText(cena);
                    jButton_Edytuj.setEnabled(true);
                    jButton_Usun.setEnabled(true);
                    System.out.println("Zaznaczono: " + id);
                    System.out.println("Zaznaczono: " + nazwa);
                    System.out.println("Zaznaczono: " + material);
                    System.out.println("Zaznaczono: " + typ);
                    System.out.println("Zaznaczono: " + cena);

                }
            }
        });


        jScrollPane1.setViewportView(jTable_Display);
        jButton_Dodaj.setIcon(new javax.swing.ImageIcon("Grafiki/newPlus.png")); // NOI18N
        jButton_Dodaj.setText("Dodaj");
        jButton_Dodaj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_DodajMouseClicked(evt);
            }
        });

        jButton_Usun.setEnabled(false);
        jButton_Usun.setIcon(new javax.swing.ImageIcon("Grafiki/newMinus.jpg")); // NOI18N
        jButton_Usun.setText("Usuń");
        jButton_Usun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_UsunMouseClicked(evt);
            }
        });

        jButton_Filtruj.setText("Filtruj");
        jButton_Filtruj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_FiltrujMouseClicked(evt);
            }
        });
        jButton_Edytuj.setEnabled(false);
        jButton_Edytuj.setIcon(new javax.swing.ImageIcon("Grafiki/newUpdate.jpg")); // NOI18N
        jButton_Edytuj.setText("Edytuj");
        jButton_Edytuj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_EdytujMouseClicked(evt);
            }
        });
        Warring.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6))

                                                .addGap(41, 41, 41)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField_Id, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                                        .addComponent(jTextField_Nazwa)
                                                        .addComponent(jTextField_Material)
                                                        .addComponent(jTextField_Typ)
                                                        .addComponent(jTextField_Cena)
                                                        .addComponent(JTextField_FilterText)))

                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton_Dodaj)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_Edytuj)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_Usun)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton_Filtruj))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(Warring, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(66, 66, 66)
                                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 1583, GroupLayout.DEFAULT_SIZE)))
                                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField_Nazwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField_Material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jTextField_Typ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jTextField_Cena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(JTextField_FilterText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)

                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton_Dodaj)
                                        .addComponent(jButton_Edytuj)
                                        .addComponent(jButton_Usun)
                                        .addComponent(jButton_Filtruj))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Warring, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTable_Display.setFillsViewportHeight(true);
        jTable_Display.setAutoCreateRowSorter(true);
        pack();


    }


    private void jButton_DodajMouseClicked(java.awt.event.MouseEvent evt) {
        String query = "insert into produkt(nazwa,material,typ,cena) " +
                "values('" + jTextField_Nazwa.getText() + "','" + jTextField_Material.getText() + "','" + jTextField_Typ.getText() + "','" + jTextField_Cena.getText() + "')";
        executeSQLQuery(query,"dodanie produktu");

        refresh();
    }

    private void jButton_EdytujMouseClicked(java.awt.event.MouseEvent evt) {
        String query="update produkt SET nazwa='" + jTextField_Nazwa.getText() + "'"
                +", material='" + jTextField_Material.getText() +"'"
                +", typ='" + jTextField_Typ.getText() +"'"
                +", cena='" + jTextField_Cena.getText() +"'where id='"+jTextField_Id.getText() + "'";
        executeSQLQuery(query,"aktualizacje produktu");
        refresh();

    }

    private void jButton_UsunMouseClicked(java.awt.event.MouseEvent evt) {
        String sql = "delete FROM produkt where nazwa='" + jTextField_Nazwa.getText() + "'";

        executeSQLQuery(sql,"usunięcie produktu");
        refresh();

    }

    private void jButton_FiltrujMouseClicked(java.awt.event.MouseEvent evt)
    {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_Display.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        jTable_Display.setRowSorter(sorter);
        String text = JTextField_FilterText.getText();
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(text));
        }
    }


    public void refresh()
    {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_Display.getModel();
        tableModel.setRowCount(0);
        Show_Products_In_JTable();

    }


    public static void main(String args[]) {


        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Test".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Java_Insert_Update_Delete_Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Java_Insert_Update_Delete_Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Java_Insert_Update_Delete_Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Java_Insert_Update_Delete_Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Java_Insert_Update_Delete_Display().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private javax.swing.JLabel Warring;
    private javax.swing.JButton jButton_Dodaj;
    private javax.swing.JButton jButton_Edytuj;
    private javax.swing.JButton jButton_Usun;
    private javax.swing.JButton jButton_Filtruj;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable_Display;
    private javax.swing.JTextField jTextField_Id;
    private javax.swing.JTextField jTextField_Nazwa;
    private javax.swing.JTextField jTextField_Material;
    private javax.swing.JTextField jTextField_Typ;
    private javax.swing.JTextField jTextField_Cena;
    private javax.swing.JTextField JTextField_FilterText;

}
