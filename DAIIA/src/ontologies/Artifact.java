package ontologies;
import jade.content.*;

/**
 *
 * @author nickstanogias Ipannis Kerkinos
 */
public class Artifact implements Concept{
    
    private int id;
    private String name;
    private String genre;
    private String creator;
    private String description;
    private String img;
    private String year;
    
    public Artifact(int id, String name, String genre, String creator, String description, String img, String year){
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.creator = creator;
        this.description = description;
        this.img = img;
        this.year = year;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public String getCreator(){
        return creator;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getImg(){
        return img;
    }
    
    public String getYear(){
        return year;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public void setCreator(String creator){
        this.creator = creator;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setImg(String img){
        this.img = img;
    }
    
    public void setYear(String year){
        this.year = year;
    }
    
    public String toString() {
        return " " + name;
    }
    
}
