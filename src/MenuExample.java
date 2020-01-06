import javax.swing.*;
import java.awt.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class MenuExample extends JFrame
{
    JMenu menu, menu2;
    JMenuItem i1, i2, i3, i4, i5, i6;

    private Java_Insert_Update_Delete_Display obj = new Java_Insert_Update_Delete_Display ();
    Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();


    //metoda odpowiadajaca za select do jTable
    public void Show_Products_In_JTable(){ {

        Connection connection;
        try
        {
            //  Connect to an Access Database

            // Tworzy obiekt properties
            Properties props = new Properties();
            File dbSettingsPropertyFile = new File("config2.properties");
            // Właściwości wykorzystają obiekt FileReader jako dane wejściowe.

            if(dbSettingsPropertyFile.exists())
            {
                System.out.println("Plik istnieje");
            }
            else
            {
                //dbSettingPropertyFile.createNewFile();
                PrintWriter dbSettingPropertyFile = new PrintWriter("config2.properties");
                dbSettingPropertyFile.println("db.conn.url=jdbc:mysql://localhost:55555/magazyn?serverTimezone=UTC");
                dbSettingPropertyFile.println("db.driver.class=com.mysql.cj.jdbc.Driver");
                dbSettingPropertyFile.println("db.username=root");
                dbSettingPropertyFile.println("db.password=");
                dbSettingPropertyFile.close();
                System.out.println("Plik został utworzony");
            }
            FileReader fReader = new FileReader(dbSettingsPropertyFile);

            //Załaduj właściwości związane z jdbc w powyższym pliku.
            props.load(fReader);

            // Zwraca wlasciwosci
            String dbConnUrl = props.getProperty("db.conn.url");
            String dbUserName = props.getProperty("db.username");
            String dbPasswordName = props.getProperty("password");
            connection = DriverManager.getConnection (dbConnUrl, dbUserName,dbPasswordName);
            System.out.println("Jest połączenie");

            //  wczytanie danych

            String sql = "Select * from produkt";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();


            for (int i = 1; i <= columns; i++)
            {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next())
            {
                Vector<Object> row = new Vector<Object>(columns);

                for (int i = 1; i <= columns; i++)
                {
                    row.addElement( rs.getObject(i) );
                }

                data.addElement( row );
            }

            rs.close();
            stmt.close();
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println( e );
        }
    }

    }

    MenuExample() throws IOException {


        //wywolanie metody
        Show_Products_In_JTable();

        JFrame f = new JFrame("Magazyn");
        JFrame dodaj = new JFrame();
        JMenuBar mb = new JMenuBar();


        //Pasek stanu
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        f.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(f.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        statusLabel.setText("Wyświetlono dane z bazy");


        //Tworzenie menu
        menu=new JMenu("Produkt");
        menu2=new JMenu("Pomoc");


        i1=new JMenuItem("Zarządzaj", KeyEvent.VK_N);
        i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        i1.setToolTipText("Kliknij, aby zarządzaj produktami.");

        i2=new JMenuItem("Wgraj plik", KeyEvent.VK_O);
        i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        i2.setToolTipText("Kliknij, aby wgrać swoje dane z pliku");

        i4=new JMenuItem("Odśwież", KeyEvent.VK_F5);
        i4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
        i4.setToolTipText("Kliknij, aby odświeżyć połączenie");


        i6=new JMenuItem("Wyjście", KeyEvent.VK_F4);
        i6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
        i6.setToolTipText("Kliknij, aby zamknąć program.");

        i5=new JMenuItem("O programie", KeyEvent.VK_F1);
        i5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
        i5.setToolTipText("Kliknij, aby zobaczyć informację o programie.");

        menu.add(i1);
        menu.add(i2);
        menu.add(i4);
        menu.addSeparator();
        menu.add(i6);
        menu2.add(i5);


        //zarzadzaj produktem wybierajac z paska menu
        Java_Insert_Update_Delete_Display frame = new Java_Insert_Update_Delete_Display();
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setSize(500,500);

                frame.setVisible(true);

                frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                statusLabel.setText("Wybrano opcje zarzadzania produktem");
            }
        });



        i2.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Tylko pliki .csv", "csv");
                jfc.setFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    jfc.getSelectedFile().getName();
//
                }
                statusLabel.setText("Wybrano opcje wgrania pliku");
            }


        }));


        i6.addActionListener(new ActionListener()
        {public void actionPerformed(ActionEvent e)
        {  if (JOptionPane.showConfirmDialog(f, "Czy na pewno chcesz zamknąć aplikację?",
                "Potwierdzenie wyjścia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            statusLabel.setText("Wybrano opcje wyjscia z programu");
            System.exit(0);
        } else
        {
            remove(i6);
        };}});

        i5.addActionListener(new ActionListener()
        {public void actionPerformed(ActionEvent e)
        { JOptionPane.showMessageDialog(f,"Autorami programu są Julia Kalinowska i Krzysztof Kołodenny");
            statusLabel.setText("Wybrano opcje informacji o autorach");
        }});


        mb.add(menu);
        mb.add(menu2);
        f.setJMenuBar(mb);
        f.setSize(600,600);


        //Tabela

        //  stworzenie modelu i zainicjowanie go danymi

        AbstractTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return getValueAt(0, column).getClass();
                // return Object.class;

            }
        };

        //tworzenie tabeli i wstawienie tam danych z modelu
        JTable table = new JTable(model);

        //menu kontekstowe
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem zarzadzaj = new JMenuItem("Zarzadzaj");
        zarzadzaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(500,500);
                frame.setVisible(true);
                statusLabel.setText("Wybrano opcje zarządzania produktem");
            }
        });
        popupMenu.add(zarzadzaj);
        table.setComponentPopupMenu(popupMenu);
        f.add(new JScrollPane(table), BorderLayout.CENTER);


        //odswiezanie polaczenia
        i4.addActionListener((new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                tableModel.setRowCount(0);
                Show_Products_In_JTable();
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(f,"Odświeżono połączenie");
                statusLabel.setText("Odświeżono połączenie i zwrócono dane z bazy");

            }

        }

        ));

        JScrollPane scrollPane = new JScrollPane( table );
        f.add(scrollPane);


        //dodanie scroll'a
        table.setFillsViewportHeight(true);


        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setVisible(true);

        //wyświetlenie tabeli
        f.pack();

        f.setLocationRelativeTo(null);
        //wyswietlenie tabeli na calym oknie
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);


    }


    public static void main(String args[]) throws IOException {
        new MenuExample();
    }}