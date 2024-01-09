package ru.kindcat.webmedo.filters.signin;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author dreamer
 * @version 1.0.0-SNAPSHOT-0
 */
public class ExistsSessionFilter implements Filter {

    private final Logger logger;

    public ExistsSessionFilter() {
        logger = Logger.getLogger(ExistsSessionFilter.class);
    }

    /**
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
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            //исключаю обработку фильтром сss, js и png файлы
            if (httpRequest.getRequestURI().endsWith(".css")) {
                httpResponse.setContentType("text/css");
                chain.doFilter(request, response);
            } else if (httpRequest.getRequestURI().endsWith(".js")) {
                httpResponse.setContentType("text/javascript");
                chain.doFilter(request, response);
            } else if (httpRequest.getRequestURI().endsWith(".png")) {
                httpResponse.setContentType("image/png");
                chain.doFilter(request, response);
                //если пользователь отправил данные авторизациии, то фильтруется сервлет для создания сессии
            } else if ("/createSessionServlet".equals(httpRequest.getServletPath())) {
                //если запрос формы авторизации получен через POST
                if ("POST".equals(httpRequest.getMethod())) {
                    chain.doFilter(request, response);
                } else {
                    logger.warn("Пользователь умудрился отправить форму авторизации get-запросом");
                    httpRequest.getRequestDispatcher("/signin.jsp").forward(httpRequest, httpResponse);
                }
            } else {
                HttpSession session = httpRequest.getSession();//получаю текущую сессию
                //если сессия существует и в ней есть атрибуты
                if (session.getAttribute("login") != null && session.getAttribute("idUser") != null && session.getAttribute("fName") != null) {
                    chain.doFilter(request, response);//пропускаем выполнение запросов для перехода по ссылкам
                    //если нет сессии и атрибутов сессии
                } else if (session.getAttribute("login") == null || session.getAttribute("idUser") == null || (String) session.getAttribute("fName") == null) {
                    httpRequest.getRequestDispatcher("signInServlet").forward(httpRequest, httpResponse);
                }
            }
        } catch (ServletException | IOException ex) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("Программная ошибка при работе фильтра для проверки авторизации");
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
