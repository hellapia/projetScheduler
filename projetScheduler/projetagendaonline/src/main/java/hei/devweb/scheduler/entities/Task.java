package hei.devweb.scheduler.entities;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class Task {
    private int id;
    private String name;
    private String description;
    private Category category;
    private LocalDate start_date;
    private LocalTime start_time;
    private LocalTime duration;

    public Task(int id, String name, String description, Category category, LocalDate start_date, LocalTime start_time, LocalTime duration){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.start_date = start_date;
        this.start_time = start_time;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public Category getCategory() {
        return category;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }
}
