
import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;
import ontologies.*;
import static ontologies.MuseumVocabulary.CURATOR_AGENT;
import static ontologies.MuseumVocabulary.GET_RECOMMENDATIONS;
import static ontologies.MuseumVocabulary.TOUR_GUIDE_AGENT;

/**
 *
 * @author nickstanogias, Ioannis Kerkinos
 */
public class TourGuideAgent extends Agent implements MuseumVocabulary{
    
    private List artifacts = new ArrayList();
    
    private Codec codec = new SLCodec();
    private Ontology ontology = MuseumOntology.getInstance();
	
    protected void setup() {
    // ---------------------

        // Register language and ontology
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        // Set this agent main behaviour
        SequentialBehaviour sb = new SequentialBehaviour();
        sb.addSubBehaviour(new RegisterInDF(this));
        sb.addSubBehaviour(new ReceiveMessages(this));
        addBehaviour(sb);  
    }
    

    class RegisterInDF extends OneShotBehaviour {
    // ------------------------------------------ Register in the DF for the user agent
    //                                            be able to retrieve its AID
        RegisterInDF(Agent a) {
            super(a);
        }

        public void action() {

            ServiceDescription sd = new ServiceDescription();
            sd.setType(TOUR_GUIDE_AGENT);
            sd.setName(getName());
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            dfd.addServices(sd);
            try {
                DFAgentDescription[] dfds = DFService.search(myAgent, dfd);
                if (dfds.length > 0 ) {
                    DFService.deregister(myAgent, dfd);
                }
                DFService.register(myAgent, dfd);
                System.out.println(getLocalName() + " is ready.");
            }
            catch (Exception ex) {
                System.out.println("Failed registering with DF! Shutting down...");
                ex.printStackTrace();
                doDelete();
            }
        }
    }
    
    class ReceiveMessages extends CyclicBehaviour {
    // --------------------------------------------  Receive requests and queries from profiler
    //                                               agent and launch appropriate handlers

        public ReceiveMessages(Agent a) {
            super(a);
	}

	public void action() {
            ACLMessage msg = receive();
            if (msg == null) { 
                block(); 
                return; 
            }
            try {
                
                ContentElement content = getContentManager().extractContent(msg);		
		switch (msg.getPerformative()) {
                    case (ACLMessage.REQUEST):	
                        Concept action = ((Action)content).getAction();
                        if (action instanceof CreatePreferences) {
                            CreatePreferences cp = (CreatePreferences)((Action)content).getAction();
                            System.out.println("Tour-Guide: " + cp.getCreator() + " " + cp.getGenre());
                            addBehaviour(new HandleCreatePreferences(myAgent, msg));
                            
                        }
                        else {
                            replyNotUnderstood(msg);
                        }
                    break;
						
                    case (ACLMessage.INFORM):	
                        if(content instanceof Result){
                            Result result = (Result) content;
                            artifacts =(java.util.List)result.getValue() ;
                            //System.out.println("Tour-Guide: " + artifacts);
                        }   
                    break;					
                    default: replyNotUnderstood(msg);
                }
            }
            catch(Exception ex) { 
                ex.printStackTrace(); 
            }
        }
    }
    
    void replyNotUnderstood(ACLMessage msg) {
    // --------------------------------------
		
        try{
            ContentElement content = getContentManager().extractContent(msg);
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            getContentManager().fillContent(reply, content);
            send(reply);
            System.out.println("Not understood!");
        }
        catch(Exception ex) { ex.printStackTrace(); }
    }
    
    class HandleCreatePreferences extends OneShotBehaviour {
    // ----------------------------------------------------- Handler for a CreatePreferences request

        private ACLMessage request;
        private List l = new ArrayList();

        HandleCreatePreferences(Agent a, ACLMessage request) {
            super(a);
            this.request = request;
	}

	public void action() {

            try {
                ContentElement content = getContentManager().extractContent(request);
		CreatePreferences cp = (CreatePreferences)((Action)content).getAction();
                for(int i=0; i<artifacts.size(); i++){
                    Artifact a = (Artifact) artifacts.get(i);
                    if((a.getGenre().equals(cp.getGenre())) || (a.getCreator().equals(cp.getCreator()))){
                        l.add(a);
                    }
                }
                //System.out.println("Tour-Guide: " + l);
		Result result = new Result((Action)content, l);
		ACLMessage reply = request.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		getContentManager().fillContent(reply, result);
		send(reply);
				
            }
            catch(Exception ex) { 
                ex.printStackTrace(); 
            }
	}
    }

}
