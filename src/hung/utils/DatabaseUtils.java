package hung.utils;

import hung.models.Game;
import hung.models.Participant;
import org.hsqldb.Server;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnguyen on 5/17/17.
 */
public class DatabaseUtils {

    public static final String DB_NAME = "HungDB";
    public static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
    public static final String DB_FILE_PATH = "database/";
    public static final String DB_TYPE = "file:";
    public static final String DB_SCHEME = "jdbc:hsqldb:";
    public static final String DB_PATH = DB_TYPE + DB_FILE_PATH + DB_NAME;
    public static final String DB_URL = DB_SCHEME + DB_PATH;

    public static final String TABLE_PARTICIPANTS = Participant.class.getSimpleName();
    public static final String TABLE_GAMES = Game.class.getSimpleName();

    /**
     * Setup database directory, load database driver, then connect to the database and return the connection
     * @return The connection to the database
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        File dbDirecrtory = new File(DB_FILE_PATH);
        if (!dbDirecrtory.exists()) { // Create database directory if not exist
            dbDirecrtory.mkdir();
        }

        Class.forName(DB_DRIVER);

        return DriverManager.getConnection(DB_URL, "sa", "");
    }

    /**
     * Get the server working with the database
     * @return
     */
    public static Server getServer() {
        Server hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, DB_NAME);
        hsqlServer.setDatabasePath(0, DB_PATH);
        return hsqlServer;
    }

    /**
     * Determine whether a table already existed.
     * @param connection The connection to the database
     * @param tableName The name of the table to check
     * @return True if the table already existed.
     * @throws SQLException
     */
    public static boolean tableExisted(Connection connection, String tableName) throws SQLException {
        List<String> tableNames = getAllTableNames(connection);
        for (String name : tableNames) {
            if (name.equalsIgnoreCase(tableName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all existing table names with a given connection
     * @param connection
     * @return all existing table names
     * @throws SQLException
     */
    public static List<String> getAllTableNames(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        List<String> tableNames = new ArrayList<>();

        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }

        return tableNames;
    }

    public static ResultSet getAllRecords(Statement statement, String tableName) throws SQLException {
        return statement.executeQuery("SELECT * FROM " + tableName);
    }

    /**
     * Drop a table if exists and return the number of affected rows
     * @param statement The statement executing the drop
     * @param tableName The name of the table to drop
     * @return The number of affected rows
     * @throws SQLException
     */
    public static int dropTable(Statement statement, String tableName) throws SQLException {
        return statement.executeUpdate("drop table " + tableName + " if exists;");
    }
}
