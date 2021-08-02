package com.tamazian.task.dao;

import com.tamazian.task.util.ConnectionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class ProductDao {

    public static final String PATH = "src/main/resources/products.csv";
    public static String line = null;
    public static int batch = 20;

    public static final ProductDao INSTANCE = new ProductDao();
    public static final String INSERT_SQL = """
            INSERT INTO products (ID, NAME, PRICE_PER_UNIT) 
            VALUES (?, ?, ?)
            """;

    public ProductDao() {
    }

    public static ProductDao getINSTANCE() {
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
                String name = data[1];
                String pricePerUnit = data[2];

                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, parseInt(pricePerUnit));
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
