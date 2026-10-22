
import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.math.BigDecimal;

public class homework4 {

    static String url = "jdbc:h2:mem:uni;DB_CLOSE_DELAY=-1";
    static DataSource dataSource;

    public static void main(String[] args) throws SQLException {

        task1();
        task2();
        task3();
        task4();

    }

    public static void task1() throws SQLException {

        System.out.println("ЗАДАНИЕ 1. ПОДКЛЮЧЕНИЕ");

        try (Connection conn =
                     DriverManager.getConnection(url, "sa", "")) {

            DatabaseMetaData md = conn.getMetaData();

            System.out.println("Подключение через DriverManager:");
            System.out.println("СУБД: " + md.getDatabaseProductName());
            System.out.println("Версия: " + md.getDatabaseProductVersion());
        }

        JdbcDataSource ds = new JdbcDataSource();

        ds.setURL(url);
        ds.setUser("sa");
        ds.setPassword("");

        dataSource = ds;

        try (Connection conn = dataSource.getConnection()) {

            DatabaseMetaData md = conn.getMetaData();

            System.out.println("\nПодключение через JdbcDataSource:");
            System.out.println("СУБД: " + md.getDatabaseProductName());
            System.out.println("Версия: " + md.getDatabaseProductVersion());
        }
    }

    public static void task2() throws SQLException {

        System.out.println("\nЗАДАНИЕ 2. СОЗДАНИЕ ТАБЛИЦЫ");

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {

            String sql = """
                CREATE TABLE students (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    group_name VARCHAR(20),
                    gpa DECIMAL(3,2)
                )
                """;

            st.execute(sql);

            System.out.println("Таблица students успешно создана.");
        }

        String insertSql =
                "INSERT INTO students (name, group_name, gpa) " +
                        "VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     insertSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            String[][] students = {
                    {"Настя", "IS-21", "3.90"},
                    {"Тимур", "IS-21", "3.40"},
                    {"Мадина", "IS-22", "3.95"}
            };

            for (String[] student : students) {

                ps.setString(1, student[0]);
                ps.setString(2, student[1]);
                ps.setBigDecimal(3, new BigDecimal(student[2]));

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {

                    if (keys.next()) {
                        System.out.println(
                                "Добавлена запись: " + student[0] +
                                        ", ID: " + keys.getInt(1)
                        );
                    }
                }
            }
        }
    }

    public static void task3() throws SQLException {

        System.out.println("\nЗАДАНИЕ 3. ВЫБОРКА ДАННЫХ");

        printByGroup("IS-21", 3.0);
        printByGroup("IS-22", 3.0);
    }

    public static void printByGroup(
            String group,
            double minGpa) throws SQLException {

        String sql =
                "SELECT id, name, group_name, gpa " +
                        "FROM students " +
                        "WHERE group_name = ? AND gpa >= ? " +
                        "ORDER BY gpa DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, group);
            ps.setDouble(2, minGpa);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println(
                        "\nРезультаты группы: " + group +
                                ", GPA от: " + minGpa
                );

                while (rs.next()) {

                    System.out.println(
                            "ID: " + rs.getInt("id") +
                                    ", Имя: " + rs.getString("name") +
                                    ", GPA: " + rs.getBigDecimal("gpa")
                    );
                }
            }
        }
    }

    public static void task4() throws SQLException {

        System.out.println("\nЗАДАНИЕ 4. ПРОВЕРКА SQL-ИНЪЕКЦИИ");

        String evil = "x' OR '1'='1";

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {

            String sql =
                    "SELECT COUNT(*) FROM students WHERE name = '" +
                            evil + "'";

            try (ResultSet rs = st.executeQuery(sql)) {

                if (rs.next()) {
                    System.out.println(
                            "Результат Statement: " + rs.getInt(1)
                    );
                }
            }
        }

        String sql =
                "SELECT COUNT(*) FROM students WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, evil);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println(
                            "Результат PreparedStatement: " +
                                    rs.getInt(1)
                    );
                }
            }
        }

        System.out.println(
                "\nВывод: PreparedStatement защищает от SQL-инъекций."
        );
    }
}
