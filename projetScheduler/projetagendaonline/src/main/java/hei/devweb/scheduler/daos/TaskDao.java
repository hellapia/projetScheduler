package hei.devweb.scheduler.daos;

import hei.devweb.scheduler.entities.Category;
import hei.devweb.scheduler.entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends DaoBase {

      public static List<Task> ListTasks(){
        String query = "SELECT * FROM tasks INNER JOIN category ON tasks.category = category.id_category ORDER BY start_time";
        List<Task> listOfTasks = new ArrayList<>();
        try (
            Statement statement = createStatement();
            ResultSet resultSet = statement.executeQuery(query)
        ){
            while (resultSet.next()){
                listOfTasks.add(
                        new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                                new Category(resultSet.getInt("id_category"), resultSet.getString("category.name"), resultSet.getString("color")),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getTime("start_time").toLocalTime(),
                        resultSet.getTime("duration").toLocalTime()


                        ));}
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listOfTasks;
    }

    public static List<Task> ListTasksOfDay( java.sql.Date startDate){
        String query = "SELECT * FROM tasks INNER JOIN category ON tasks.category = category.id_category WHERE start_date=? ORDER BY start_time";
        List<Task> listOfTasks = new ArrayList<>();
        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setDate(1, startDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listOfTasks.add(new Task(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            new Category(resultSet.getInt("id_category"), resultSet.getString("category.name"), resultSet.getString("color")),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getTime("start_time").toLocalTime(),
                            resultSet.getTime("duration").toLocalTime()));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return listOfTasks;
    }

    public static Task getTask(Integer id) {
        String query = "SELECT * FROM tasks INNER JOIN category ON tasks.category = category.id_category WHERE tasks.id=?";
        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Task(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            new Category(resultSet.getInt("id_category"), resultSet.getString("category.name"), resultSet.getString("color")),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getTime("start_time").toLocalTime(),
                            resultSet.getTime("duration").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public static Task addTask(Task task) {
        String query = "INSERT INTO tasks(name, description, start_date, start_time, category, duration) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(task.getStart_date()));
            statement.setTime(4, java.sql.Time.valueOf( task.getStart_time()));
            statement.setInt (5, task.getCategory().getId_category());
            statement.setTime(6, java.sql.Time.valueOf( task.getDuration()));
            statement.executeUpdate();

            try (ResultSet ids = statement.getGeneratedKeys()) {
                if(ids.next()) {
                    int generatedId = ids.getInt(1);
                    task.setId(generatedId);
                    return task;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Task changeTask(Task task) {
        String query = "UPDATE tasks SET name=?, description=? , start_date=? , start_time=? , category=?, duration=? WHERE id=?";
        try (PreparedStatement statement = prepareStatement(query)) {
            try (Connection connection = DataSourceProvider.getDataSource().getConnection();) {
                statement.setString(1, task.getName());
                statement.setString(2, task.getDescription());
                statement.setDate(3, java.sql.Date.valueOf(task.getStart_date()));
                statement.setTime(4, java.sql.Time.valueOf(task.getStart_time()));
                statement.setInt(5, task.getCategory().getId_category());
                statement.setTime(6, java.sql.Time.valueOf(task.getDuration()));
                statement.setInt(7, task.getId());
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;
    }

    public static void deleteTask(int id){
        String query = "DELETE FROM `tasks` WHERE id=? ";
        try (PreparedStatement statement = prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
