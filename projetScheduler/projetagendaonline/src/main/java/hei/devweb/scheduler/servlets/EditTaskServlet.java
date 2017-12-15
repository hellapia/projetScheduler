package hei.devweb.scheduler.servlets;

import hei.devweb.scheduler.entities.Category;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/editTask")
public class EditTaskServlet extends abstractServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETERS
        String titre = null;
        String description = null;
        LocalDate dateTache = null;
        LocalTime heureTache = null;
        LocalTime dureeTache = null;
        Category categorie = null;

        int id = idTask;

        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            titre = req.getParameter("titre");
            description = req.getParameter("description");
            String start_dateString = req.getParameter("dateTache");
            dateTache = LocalDate.parse(req.getParameter("dateTache"));
            heureTache = LocalTime.parse(req.getParameter("heureTache"));
            dureeTache = LocalTime.parse(req.getParameter("dureeTache"));
            categorie = TaskLibrary.getInstance().getCategory(Integer.parseInt(req.getParameter("categorie")));


        } catch (NumberFormatException | DateTimeParseException ignored) {
            ignored.printStackTrace();
        }



        Task newTask = new Task(id, titre, description, categorie, dateTache, heureTache, dureeTache);
        try {
            TaskLibrary.getInstance().editTask(newTask);


            resp.sendRedirect(String.format("task?id=%d", newTask.getId()));
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
            resp.sendRedirect("liste");
        }
    }
    private int idTask;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Category> categories = TaskLibrary.getInstance().listCategory();
        context.setVariable("categoryList", categories);

        String taskId = req.getParameter("id");
        idTask=Integer.parseInt(taskId);
        Task task = TaskLibrary.getInstance().getTask(Integer.parseInt(taskId));
        context.setVariable("task", task);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("editer-tache", context, resp.getWriter());

    }
}
