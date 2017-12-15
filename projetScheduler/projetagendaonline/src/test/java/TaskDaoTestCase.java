import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import hei.devweb.scheduler.daos.DataSourceProvider;
import hei.devweb.scheduler.daos.TaskDao;
import hei.devweb.scheduler.entities.Category;
import hei.devweb.scheduler.entities.Task;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class TaskDaoTestCase {

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM tasks");
            stmt.executeUpdate("DELETE FROM category");
            stmt.executeUpdate("INSERT INTO `category`(`id_category`,`name`,`color`) VALUES (1,'professionnelle','#111111')");
            stmt.executeUpdate("INSERT INTO `category`(`id_category`,`name`,`color`) VALUES (2,'privee', '#222222')");
            stmt.executeUpdate(
                    "INSERT INTO `tasks`(`id`, name, description, start_date, start_time, category, duration) "
                            + "VALUES (1, 'my title 1','first description', '2017-11-26', '22:30', 1 , '01:30')");
            stmt.executeUpdate(
                    "INSERT INTO `tasks`(`id`,`name`, description, start_date, start_time, category, duration) "
                            + "VALUES (2, 'my title 2','second description', '2017-11-27', '20:30', 2 , '01:30')");
        }
    }

    @Test
    public void shouldListTasks() {
        // WHEN
        List<Task> tasks = TaskDao.ListTasks();
        // THEN
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting("id", "name", "description", "start_date", "start_time", "category.id_category", "category.name","category.color", "duration").containsOnly(
                tuple(1, "my title 1", "first description", LocalDate.of(2017, 11, 26),LocalTime.of(22,30), 1, "professionnelle", "#111111", LocalTime.of(1,30))	,
                tuple(2, "my title 2", "second description",LocalDate.of(2017, 11, 27), LocalTime.of(20,30), 2, "privee","#222222", LocalTime.of(1,30))
        );

    }

    @Test
    public void shouldGetTask() {
        // WHEN
        Task task = TaskDao.getTask(2);
        // THEN
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(2);
        assertThat(task.getName()).isEqualTo("my title 2");
        assertThat(task.getDescription()).isEqualTo("second description");
        assertThat(task.getStart_date()).isEqualTo(LocalDate.of(2017, 11, 27));
        assertThat(task.getStart_time()).isEqualTo(LocalTime.of(20,30));
        assertThat(task.getCategory().getId_category()).isEqualTo(2);
        assertThat(task.getCategory().getName_category()).isEqualTo("privee");
        assertThat(task.getCategory().getColor()).isEqualTo("#222222");
        assertThat(task.getDuration()).isEqualTo(LocalTime.of(1,30));
    }

        @Test
        public void shouldAddTask () throws Exception {
            // GIVEN
            Task newTask = new Task(3, "my new task", "doing homework with a friend", new Category(1, "professionnelle","#111111"),
                    LocalDate.of(2017, 11, 19), LocalTime.of(16, 0), LocalTime.of(3, 0));
            // WHEN
            Task createdTask = TaskDao.addTask(newTask);
            // THEN
            assertThat(createdTask).isNotNull();
            assertThat(createdTask.getId()).isNotNull();
            assertThat(createdTask.getId()).isGreaterThan(0);
            assertThat(createdTask.getName()).isEqualTo("my new task");
            assertThat(createdTask.getDescription()).isEqualTo("doing homework with a friend");
            assertThat(createdTask.getCategory().getId_category()).isEqualTo(1);
            assertThat(createdTask.getCategory().getName_category()).isEqualTo("professionnelle");
            assertThat(createdTask.getCategory().getColor()).isEqualTo("#111111");
            assertThat(createdTask.getStart_date()).isEqualTo(LocalDate.of(2017, 11, 19));
            assertThat(createdTask.getStart_time()).isEqualTo(LocalTime.of(16, 0));
            assertThat(createdTask.getDuration()).isEqualTo(LocalTime.of(3, 0));

            try (Connection connection = DataSourceProvider.getDataSource().getConnection();
                 Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE name = 'my new task'")) {
                    assertThat(rs.next()).isTrue();
                    assertThat(rs.getInt("id")).isEqualTo(createdTask.getId());
                    assertThat(rs.getString("name")).isEqualTo("my new task");
                    assertThat(rs.getString("description")).isEqualTo("doing homework with a friend");
                    assertThat(rs.getDate("start_date").toLocalDate()).isEqualTo(LocalDate.of(2017, 11, 19));
                    assertThat(rs.getTime("start_time").toLocalTime()).isEqualTo(LocalTime.of(16, 0));
                    assertThat(rs.getInt("category")).isEqualTo(1);
                    assertThat(rs.getTime("duration").toLocalTime()).isEqualTo(LocalTime.of(3, 0));

                    assertThat(rs.next()).isFalse();
                }
            }
        }

        @Test
        public  void shouldEditTask() throws Exception{
            Task newTask = new Task(6, "my new task", "doing homework with a friend", new Category(1, "professionnelle","#111111"),
                    LocalDate.of(2017, 11, 19), LocalTime.of(16, 0), LocalTime.of(3, 0));
            // WHEN
            Task createdTask = TaskDao.addTask(newTask);
            Task otherTask = new Task(createdTask.getId(), "changedTask", "doing homework alone", new Category(1, "professionnelle","#111111"),
                    LocalDate.of(2017, 11, 20), LocalTime.of(16, 30), LocalTime.of(4, 0));
            TaskDao.changeTask(otherTask);

            try (Connection connection = DataSourceProvider.getDataSource().getConnection();
                 Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE id=" + createdTask.getId())) {
                    rs.next();
                    assertThat(rs.getInt("id")).isEqualTo(createdTask.getId());
                    assertThat(rs.getString("name")).isEqualTo("changedTask");
                    assertThat(rs.getString("description")).isEqualTo("doing homework alone");
                    assertThat(rs.getDate("start_date").toLocalDate()).isEqualTo(LocalDate.of(2017, 11, 20));
                    assertThat(rs.getTime("start_time").toLocalTime()).isEqualTo(LocalTime.of(16, 30));
                    assertThat(rs.getInt("category")).isEqualTo(1);
                    assertThat(rs.getTime("duration").toLocalTime()).isEqualTo(LocalTime.of(4, 0));

                    assertThat(rs.next()).isFalse();
                }
            }

        }

        @Test
    public void shouldDeleteTask()throws Exception{
            Task newTask = new Task(7, "testTask", "last test ", new Category(2, "privee","#222222"),
                    LocalDate.of(2017, 12, 19), LocalTime.of(17, 0), LocalTime.of(2, 0));
            // WHEN
            Task createdTask = TaskDao.addTask(newTask);

            TaskDao.deleteTask(createdTask.getId());
            try (Connection connection = DataSourceProvider.getDataSource().getConnection();
                 Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM tasks WHERE id=" + createdTask.getId())) {
                    assertThat(rs.next()).isFalse();
                }
            }
        }
    }



