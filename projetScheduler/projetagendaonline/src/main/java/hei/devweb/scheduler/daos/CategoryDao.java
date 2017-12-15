package hei.devweb.scheduler.daos;

import hei.devweb.scheduler.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hei.devweb.scheduler.daos.DaoBase.createStatement;
import static hei.devweb.scheduler.daos.DaoBase.prepareStatement;

public class CategoryDao {

    public static List<Category> listCategory() {
        String query = "SELECT * FROM category ORDER BY name;";
        List<Category> listOfCategories = new ArrayList<>();
        try (
                Statement statement = createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                listOfCategories.add(new Category(resultSet.getInt("id_category"), resultSet.getString("name"), resultSet.getString("color")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCategories;
    }


    public static Category getCategory(Integer id) {
        String query = "SELECT * FROM category WHERE id_category=?";
        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Category(resultSet.getInt("id_category"), resultSet.getString("name"), resultSet.getString("color"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void addCategory(String nom, String color) {
        String query = "INSERT INTO category(name,color) VALUES(?,?)";
        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, color);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
