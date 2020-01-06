import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
public class ConnectionProperties {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Log4j.logger.trace("Struktura połączenia: " + "db.conn.url=jdbc:mysql://localhost:55555/magazyn?serverTimezone=UTC");
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
            conn = DriverManager.getConnection (dbConnUrl, dbUserName,dbPasswordName);
            System.out.println("Jest połączenie");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
        return conn;
    }
}