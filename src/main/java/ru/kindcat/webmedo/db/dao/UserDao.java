package ru.kindcat.webmedo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import ru.kindcat.webmedo.db.utils.pool.ConnectionPool;

/**
 *
 * @author dreamer
 * @version 1.0.0-SNAPSHOT-2
 */
public class UserDao {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String login) {
        String stringlog;
        StackTraceElement[] stackTrace;
        StringBuilder logBuilder = new StringBuilder();
        Logger logger = Logger.getLogger(UserDao.class);
        try (Connection conn = ConnectionPool.getDataSource().getConnection()) {
            try (PreparedStatement prepareStmt = conn.prepareStatement("SELECT * FROM account WHERE login=?")) {
                prepareStmt.setString(1, login);
                ResultSet resultSet = prepareStmt.executeQuery();
                if (resultSet.next()) {
                    name = resultSet.getString("fio");
                }
                prepareStmt.close();
            } catch (SQLException ex) {
                logBuilder.setLength(0);
                logBuilder.append("Программная ошибка при работе фильтра для перекодировки запросов");
                logBuilder.append("\n");
                logBuilder.append(ex.getMessage());
                logBuilder.append("\n");
                stackTrace = ex.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    logBuilder.append(element.toString());
                    logBuilder.append("\n");
                }
                stringlog = logBuilder.toString();
                logger.error(stringlog);
            }
            conn.close();
        } catch (SQLException ex) {
                logBuilder.setLength(0);
                logBuilder.append("Программная ошибка при работе фильтра для перекодировки запросов");
                logBuilder.append("\n");
                logBuilder.append(ex.getMessage());
                logBuilder.append("\n");
                stackTrace = ex.getStackTrace();
                for (StackTraceElement element : stackTrace) {
                    logBuilder.append(element.toString());
                    logBuilder.append("\n");
                }
                stringlog = logBuilder.toString();
                logger.error(stringlog);
        }
    }
}
