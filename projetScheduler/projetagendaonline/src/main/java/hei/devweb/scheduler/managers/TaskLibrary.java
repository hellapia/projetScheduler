package hei.devweb.scheduler.managers;

import hei.devweb.scheduler.daos.CategoryDao;
import hei.devweb.scheduler.daos.TaskDao;
import hei.devweb.scheduler.entities.Category;
import hei.devweb.scheduler.entities.Task;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskLibrary {

    private static TaskLibrary ourInstance = new TaskLibrary();
    private TaskDao taskDaoDao= new TaskDao();
    public static TaskLibrary getInstance() {
        return ourInstance;
    }

    public List<Task> listTasks(){
        return TaskDao.ListTasks();
    }
    public List<Category> listCategory(){ return CategoryDao.listCategory();}
    public Category getCategory(int id){return CategoryDao.getCategory(id);}
    public  void deleteTask(int id){ TaskDao.deleteTask(id);}

    public Task getTask(int id){return TaskDao.getTask(id);}
    public List<Task> getTasksOfDay(LocalDate date){return TaskDao.ListTasksOfDay(Date.valueOf(date));}

    public Task editTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("The task should not be null.");
        }
        if (task.getName() == null || "".equals(task.getName())) {
            throw new IllegalArgumentException("The task's name should not be empty.");
        }
        if (task.getDescription() == null || "".equals(task.getDescription())) {
            throw new IllegalArgumentException("The task's description should not be empty.");
        }
        if (task.getStart_date()== null ) {
            throw new IllegalArgumentException("The task's date should not be empty.");
        }
        if (task.getStart_time() == null ) {
            throw new IllegalArgumentException("The task's start time should not empty");
        }
        if (task.getDuration() == null) {
            throw new IllegalArgumentException("The task's duration should not be null.");
        }
        if (task.getCategory() == null) {
            throw new IllegalArgumentException("The task's category should not be null.");
        }

        return TaskDao.changeTask(task);
    }


    public Task addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("The task should not be null.");
        }
        if (task.getName() == null || "".equals(task.getName())) {
            throw new IllegalArgumentException("The task's name should not be empty.");
        }
        if (task.getDescription() == null || "".equals(task.getDescription())) {
            throw new IllegalArgumentException("The task's description should not be empty.");
        }
        if (task.getStart_date()== null ) {
            throw new IllegalArgumentException("The task's date should not be empty.");
        }
        if (task.getStart_time() == null ) {
            throw new IllegalArgumentException("The task's start time should not empty");
        }
        if (task.getDuration() == null) {
            throw new IllegalArgumentException("The task's duration should not be null.");
        }
        if (task.getCategory() == null) {
            throw new IllegalArgumentException("The task's category should not be null.");
        }

        return TaskDao.addTask(task);
    }


}
