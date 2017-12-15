import hei.devweb.scheduler.daos.CategoryDao;
import hei.devweb.scheduler.daos.DataSourceProvider;
import hei.devweb.scheduler.entities.Category;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class CategoryDaoTestCase {

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM tasks");
            stmt.executeUpdate("DELETE FROM category");
            stmt.executeUpdate("INSERT INTO `category`(`id_category`,`name`,`color`) VALUES (1,'Professionnelle', '#111111')");
            stmt.executeUpdate("INSERT INTO `category`(`id_category`,`name`, `color`) VALUES (2,'Privée', '#222222')");

        }
    }

    @Test
    public void shouldListCategories() {
        // WHEN
        List<Category> categories = CategoryDao.listCategory();
        // THEN
        assertThat(categories).hasSize(2);
        assertThat(categories).extracting("id_category", "name").containsOnly(tuple(2, "Privée", "#222222"),
                tuple(1, "Professionnelle", "#111111"));
    }

    @Test
    public void shouldGetCategory() {
        // WHEN
        Category category = CategoryDao.getCategory(1);
        // THEN
        assertThat(category).isNotNull();
        assertThat(category.getId_category()).isEqualTo(1);
        assertThat(category.getName_category()).isEqualTo("Professionnelle");
        assertThat(category.getColor()).isEqualTo("#111111");
    }

    @Test
    public void shouldNotGetUnknownCategory() {
        // WHEN
        Category category = CategoryDao.getCategory(-1);
        // THEN
        assertThat(category).isNull();
    }

    @Test
    public void shouldAddCategory() throws Exception {
        // WHEN
        CategoryDao.addCategory("test","#333333");
        // THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM category WHERE name = 'test'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("id_category")).isGreaterThan(0);
                assertThat(rs.getString("name")).isEqualTo("test");
                assertThat(rs.getString("color")).isEqualTo("#333333");
                assertThat(rs.next()).isFalse();
            }
        }
    }
}
