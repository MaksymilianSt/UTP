package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    private String url;
    private TravelData travelData;
    private int id;
    Connection connection;
    Statement statement;
    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
        id= 0;
    }
    public void create(){
        try {
            connection = DriverManager.getConnection(url+";create=true");
            statement     = connection.createStatement();
            createClientTable();
            addAllClients();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<String> findAll(){
        ResultSet resultSet = null;
        List<String> result = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("select * from Client");
            while(resultSet.next()){
                StringBuilder row = new StringBuilder();
                for (int i = 2; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    if(i!=resultSet.getMetaData().getColumnCount())
                        row.append(resultSet.getString(i)+"\t");
                    else  row.append(resultSet.getString(i));
                }
                result.add(row.toString());
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
       return result;
    }
    public List<String> findAll(String translateTo){
        List<String> allRows = findAll();

        return   allRows.stream()
                .map(e-> travelData.translateRow(translateTo,e.split("\t")))
                .collect(Collectors.toList());
    }

    private void createClientTable() throws SQLException {
        try {
            createClientTableStatement();
        } catch (SQLException e) {
            statement.executeUpdate("drop table Client");
            createClientTableStatement();
        }
    }

    private void createClientTableStatement() throws SQLException {
        statement.executeUpdate(
                "Create table Client (ID int primary key , loc varchar(6) ," +
                        " country varchar(30) ,date_from varchar(15) ,date_to varchar(15)," +
                        " place varchar(20),price varchar(20),currency varchar(3) )"
        );
    }
    private void addAllClients(){
        List<String> offersDescriptionsList = travelData.getOffersDescriptionsList();
        offersDescriptionsList.stream()
                .map(line -> line.split("\t"))
                .forEach(row -> addClient(row));
    }
    private     void addClient(String... columns){
        String query = "Insert into Client values("+
                ++id +","+
                Arrays.stream(columns)
                        .map(e-> "\'" + e + "\'")
                        .collect(Collectors.joining(","))
                +")";

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void showGui(){
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui(this);
            gui.setVisible(true);
        });
    }
}
