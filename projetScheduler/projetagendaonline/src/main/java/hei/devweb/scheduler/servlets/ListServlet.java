package hei.devweb.scheduler.servlets;

import hei.devweb.scheduler.entities.Task;
import hei.devweb.scheduler.managers.TaskLibrary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/list")
public class ListServlet extends abstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Task> listOfTasks = TaskLibrary.getInstance().listTasks();
        context.setVariable("taskList", listOfTasks);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("liste", context, resp.getWriter());

    }
}
