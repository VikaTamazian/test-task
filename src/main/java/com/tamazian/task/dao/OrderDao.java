package com.tamazian.task.dao;

import com.tamazian.task.util.ConnectionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDao {

    public static final String PATH = "src/main/resources/orders.csv";
    public static String line = null;
    public static int batch = 20;

    public static final OrderDao INSTANCE = new OrderDao();
    public static final String INSERT_SQL = """
            INSERT INTO orders (ID, DATE_TIME) 
            VALUES (?, ?)
            """;

    public OrderDao() {
    }

    public static OrderDao getINSTANCE() {
        return INSTANCE;
    }

    public void write() {


        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            //   int count = 20;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String dateTime = data[1];

                preparedStatement.setString(1, id);
                preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));
                preparedStatement.addBatch();

//                if (count % batch == 0) {
//                    preparedStatement.executeBatch();
//                }
//                reader.close();
                preparedStatement.executeBatch();
                //        connection.commit();
                System.out.println("Perfect!");

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }
}
