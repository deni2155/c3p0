package ru.kindcat.webmedo.db.utils.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author dreamer
 * @version 1.0.0-SNAPSHOT-2 Класс для создания пула соединений к БД
 */
public class ConnectionPool {

    private static ComboPooledDataSource dataSource;

    /**
     * @return объект созданного пула соединений
     */
    public static ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    /**
     * Создаю пул соединений
     */
    public static void setDataSource() {
        Logger logger = Logger.getLogger(ConnectionPool.class);

        StringBuilder logBuilder = new StringBuilder();
        StackTraceElement[] stackTrace;
        logger.debug("========================  Запущено создание пула соединений к БД  ========================");
        try {
            if (dataSource == null) {
                try {
                    logger.debug("Запущено чтение параметров из конфигурационного файла c3p0.properties");
                    Properties properties = new Properties();
                    InputStream inputStream = ComboPooledDataSource.class.getClassLoader().getResourceAsStream("c3p0.properties");
                    properties.load(inputStream);

                    String jdbcUrl = properties.getProperty("c3p0.jdbcUrl");
                    String driverClass = properties.getProperty("c3p0.driverClass");
                    String user = properties.getProperty("c3p0.user");
                    String password = properties.getProperty("c3p0.password");
                    String minPoolSize = (properties.getProperty("c3p0.minPoolSize"));
                    String maxPoolSize = properties.getProperty("c3p0.maxPoolSize");
                    String acquireIncrement = properties.getProperty("c3p0.acquireIncrement");
                    String maxIdleTime = properties.getProperty("c3p0.maxIdleTime");
                    String initialPoolSize = properties.getProperty("c3p0.initialPoolSize");
                    if (jdbcUrl != null && driverClass != null && user != null && password != null && minPoolSize != null && maxPoolSize != null && acquireIncrement != null && maxIdleTime != null && initialPoolSize != null) {
                        dataSource = new ComboPooledDataSource();
                        dataSource.setJdbcUrl(jdbcUrl);
                        dataSource.setDriverClass(driverClass);
                        dataSource.setUser(user);
                        dataSource.setPassword(password);
                        dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
                        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
                        dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
                        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
                        dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
                        logger.debug("Из конфигурационного файла c3p0.properties успешно получены параметры");
                        logger.debug("========================  Создан объект пула соединений к БД  ========================");
                    } else {
                        logger.warn("Заполнены не все параметры конфигурационного файла c3p0.properties для создания пула соединений к БД");
                    }

                } catch (PropertyVetoException ex) {
                    logBuilder.setLength(0);
                    logBuilder.append("При чтении параметров из конфигурационного файла c3p0.properties произошла программная ошибка");
                    logBuilder.append("\n");
                    logBuilder.append(ex.getMessage());
                    logBuilder.append("\n");
                    stackTrace = ex.getStackTrace();
                    for (StackTraceElement element : stackTrace) {
                        logBuilder.append(element.toString());
                        logBuilder.append("\n");
                    }
                    logger.error(logBuilder.toString());
                }
            }
        } catch (IOException ex) {
            logBuilder.setLength(0);
            logBuilder.append("При создании пула соединений для подключения к БД произошла программная ошибка");
            logBuilder.append("\n");
            logBuilder.append(ex.getMessage());
            logBuilder.append("\n");
            stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                logBuilder.append(element.toString());
                logBuilder.append("\n");
            }
            logger.error(logBuilder.toString());
        }
    }
}
