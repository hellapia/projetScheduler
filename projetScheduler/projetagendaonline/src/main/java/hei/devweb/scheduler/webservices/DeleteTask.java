package hei.devweb.scheduler.webservices;

import com.google.gson.Gson;
import hei.devweb.scheduler.entities.Task;
import hei.devweb.scheduler.managers.TaskLibrary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/deleteTask")
public class DeleteTask{
    @GET
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") int id){
        try {
            TaskLibrary.getInstance().deleteTask(id);
            return Response.status(200).entity(true).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(500).entity(false).build();
        }

    }

}

