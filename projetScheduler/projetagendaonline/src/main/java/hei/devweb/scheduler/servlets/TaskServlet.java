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

@WebServlet("/task")
public class TaskServlet extends abstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String taskId = req.getParameter("id");
        Task task = TaskLibrary.getInstance().getTask(Integer.parseInt(taskId));
        context.setVariable("task", task);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("voir-tache", context, resp.getWriter());
    }
}

