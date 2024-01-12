package ru.kindcat.webmedo.sevlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import ru.kindcat.webmedo.db.dao.UserDao;

/**
 *
 * @author dreamer
 * @version 1.0.0-SNAPSHOT-2
 */
@WebServlet(name = "CreateSessionServlet", urlPatterns = {"/createSessionServlet"})
public class CreateSessionServlet extends HttpServlet {

    /**
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(CreateSessionServlet.class);
        StringBuilder logBuilder = new StringBuilder();
        StackTraceElement[] stackTrace;

        UserDao userBeans = new UserDao();
        userBeans.setName("admin");
        logger.debug(userBeans.getName());
        PrintWriter out = response.getWriter();
        out.println("false");
        out.println("123");
    }

    /**
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
