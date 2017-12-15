package hei.devweb.scheduler.entities;

public class Category {
    private int id_category;
    private String name;
    private String color;

    public Category(int id, String name, String color){
        this.id_category=id;
        this.name=name;
        this.color = color;
    }

    public int getId_category(){
        return id_category;
    }

    public String getName_category() {
        return name;
    }
    public void setName_category(String name){
        this.name=name;
    }

    public String getColor(){ return this.color;}
}
