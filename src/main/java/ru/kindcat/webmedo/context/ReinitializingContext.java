package ru.kindcat.webmedo.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import ru.kindcat.webmedo.db.utils.pool.ConnectionPool;

/**
 *
 * @author dreamer
 * @version 1.0.0-SNAPSHOT-2
 */
public class ReinitializingContext implements ServletContextListener {
    
    private final Logger logger;
    
    public ReinitializingContext() {
        logger = Logger.getLogger(ReinitializingContext.class);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent arg) {
        logger.info("Запущена инициализация контекста приложения");
        ConnectionPool.setDataSource();
        logger.debug("Выполнена инициализация контекста приложения");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent arg) {
        logger.info("Контекст приложения очищен");
    }
}
