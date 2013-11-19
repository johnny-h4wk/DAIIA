package ontologies;

import jade.content.AgentAction;

/**
 *
 * @author nickstanogias, Ioannis Kerkinos
 */
public class CreatePreferences implements AgentAction{
    
    private String genre;
    private String creator;
    
    public CreatePreferences(){
        
    }
    
    public CreatePreferences(String genre, String creator){
        this.genre = genre;
        this.creator = creator;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public String getCreator(){
        return creator;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public void setCreator(String creator){
        this.creator = creator;
    }
}
