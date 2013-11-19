package ontologies;

import jade.content.AgentAction;
import java.util.List;

/**
 *
 * @author nickstanogias, Ioannis Kerkinos
 */
public class MakeOperation implements AgentAction{
    
    private int type;
    private List list;
    
    public int getType(){
        return type;
    }
    
    public List getList(){
        return list;
    }
    
    public void setList(List list){
        this.list = list;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
}
