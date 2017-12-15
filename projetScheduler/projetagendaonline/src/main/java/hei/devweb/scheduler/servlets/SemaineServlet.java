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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/semaine")
public class SemaineServlet extends abstractServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ArrayList<LocalDate> dates= new ArrayList<>();
        for (int i = 0; i<7; i++){
            dates.add(LocalDate.now().plusDays(i));
        }
        context.setVariable("listeDates",dates);

        List<Task> list1 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now());
        context.setVariable("list1", list1);

        List<Task> list2 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(1));
        context.setVariable("list2", list2);

        List<Task> list3 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(2));
        context.setVariable("list3", list3);

        List<Task> list4 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(3));
        context.setVariable("list4", list4);

        List<Task> list5 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(4));
        context.setVariable("list5", list5);

        List<Task> list6 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(5));
        context.setVariable("list6", list6);

        List<Task> list7 = TaskLibrary.getInstance().getTasksOfDay(LocalDate.now().plusDays(6));
        context.setVariable("list7", list7);

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("cette-semaine", context, resp.getWriter());

    }
}
