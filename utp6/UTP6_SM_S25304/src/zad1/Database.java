package zad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url;
    private TravelData travelData;
    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }
    public void create(){
        try {
            Connection connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showGui(){}
}
