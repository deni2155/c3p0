package ru.kindcat.webmedo.filters.coding;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author dreamer
 * @version webmedo-1.0.0-SNAPSHOT-0 Класс для перекодирования всех сервлетов
 */
public class EncodingRequestFilter implements Filter {

    private final Logger logger;

    public EncodingRequestFilter() {
        logger = Logger.getLogger(EncodingRequestFilter.class);
    }

    /**
     * Инициализация фильтра
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Запущена инициализация фильтра");
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("Выполняется фильтр");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        } catch (IOException ex) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("Программная ошибка при работе фильтра для изменения кодировки запросов");
            logBuilder.append("\n");
            logBuilder.append(ex.getMessage());
            logBuilder.append("\n");
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                logBuilder.append(element.toString());
                logBuilder.append("\n");
            }
            logger.error(logBuilder.toString());
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
        logger.debug("Фильтр завершил работу");
    }
}
