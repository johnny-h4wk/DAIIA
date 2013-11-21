import jade.core.*;
import jade.core.behaviours.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.*;
import jade.proto.ContractNetInitiator;
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
import jade.content.onto.basic.*;
import jade.domain.FIPANames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;

import ontologies.*;

/**
 * 
 * @author nickstanogias, Ioannis Kerkinos
 */
@SuppressWarnings("serial")
public class CuratorAgent extends GuiAgent implements MuseumVocabulary {

	private Codec codec = new SLCodec();
	private Ontology ontology = MuseumOntology.getInstance();
	private AID server;
	private int command;
	transient protected CuratorGui myGui; // The gui
	private int round = 0;

	private List<Artifact> artifactList = new ArrayList<Artifact>();

	protected void setup() {
		Artifact a1 = new Artifact(
				1,
				"The last super",
				"Renaissance",
				"Leonardo da Vinci",
				"A fifteenth century mural painting done in Milan by da Vinci, "
						+ "The Last Supper depicts the final feast Jesus had with his "
						+ "Twelve Apostles during which he announces one of them would betray him.",
				"Da_Vinci_The_Last_Supper_small.jpg", "1498", 10000000, 5000000);

		Artifact a2 = new Artifact(
				2,
				"School of Athens",
				"Renaissance",
				"Raphael",
				"The School of Athens (or Scuola di Atene in Italian) was "
						+ "one of Raphaelâ€™s commissions in the Stanze di Raffaello "
						+ "in the Vatican. The School of Athens is considered Raphaelâ€™s "
						+ "master artwork and is considered the perfect example of High Renaissance art.",
				"Raphael-School-of-Athens-small.jpg", "1510", 10000000, 5000000);

		Artifact a3 = new Artifact(
				3,
				"Mona Lisa",
				"Renaissance",
				"Leonardo da Vinci",
				"This painting depicts Lisa del Giocondo whose expression is "
						+ "well-known for the enigmatic aura emanating from it. The Mona Lisa "
						+ "is possibly the most famous painting in the world of all time.",
				"Mona_Lisa_by_Leonardo_da_Vinci_small.jpg", "1519", 10000000, 5000000);

		Artifact a4 = new Artifact(
				4,
				"The Night Watch",
				"Baroque",
				"Rembrandt",
				"Rembrandtâ€™s painting of a city guard led by Captain Frans Banning Cocq "
						+ "moving out is famous for three reasons: its large size of 11â€™10â€� x 14â€™4â€�, effective "
						+ "utilization of chiaroscuro (light and shadow balance), and its portrayal of motion in "
						+ "what would have been a traditionally static painting. Rembrandt completed the piece at "
						+ "the height of the Dutch Golden Age.",
				"The_Nightwatch_by_Rembrandt_small.jpg", "1642", 10000000, 5000000);

		Artifact a5 = new Artifact(
				5,
				"Las Meninas",
				"Baroque",
				"Diego Velazquez",
				"Las Meninas, or The Maids of Honor, depicts a room in the Madrid palace "
						+ "of Spainâ€™s King Philip IV. The painting is famous for its complexities regarding "
						+ "reality and illusion. Uncertainty is played out in the relations between the viewers "
						+ "and the figures, as well as between the figures themselves. These complex uncertainties "
						+ "have welcomed much discussion and analysis among critics and scholars.",
				"Las_Meninas_by_Diego_Velazquez-small.jpg", "1656", 10000000, 5000000);

		Artifact a6 = new Artifact(
				6,
				"Girl with a Pearl Earing",
				"Baroque",
				"Johannes Vermeer",
				"One of Vermeerâ€™s masterpieces, this painting utilizes a pearl earring "
						+ "as a focal point. It is sometimes known as 'the Dutch Mona Lisa' "
						+ "or 'the Mona Lisa of the North.'",
				"Johannes_Vermeer_-_The_Girl_With_The_Pearl_Earring-small.jpg", "1665",
				10000000, 5000000);

		Artifact a7 = new Artifact(
				7,
				"Whistler's Mother",
				"19th Century",
				"James McNeill Whistler",
				"Whistler painted his mother, Anna McNeill Whistler, when the original model "
						+ "failed to come to the appointment. The painting was not well-received "
						+ "when he submitted it to the Royal Academy of Art in London for exhibition, "
						+ "but shortly later the public showed much respect and deference for it, quickly "
						+ "restoring Whistlerâ€™s honor.",
				"Whistlers-Mother-small.jpg", "1871", 10000000, 5000000);

		Artifact a8 = new Artifact(
				8,
				"Starry Night over the Rhone",
				"19th Century",
				"Vincent Van Gogh",
				"One of van Goghâ€™s paintings of Arles at a riverbank not far from the Yellow "
						+ "House he was residing at the time. The night scenery, lighting, and stars "
						+ "provided subjects for his more famous paintings, such as The Starry Night.",
				"Starry_Night_Over_the_Rhone-small.jpg", "1888", 10000000, 5000000);

		Artifact a9 = new Artifact(9, "Starry Night", "19th Century",
				"Vincent Van Gogh",
				"Considered to be the best and most famous work of Vincent van Gogh, "
						+ "The Starry Night was created from memory and portrays the "
						+ "sight outside the window of his sanitarium room at night.",
				"Van_Gogh_-_Starry_Night-small.jpg", "1889", 10000000, 5000000);

		Artifact a10 = new Artifact(10, "American Gothic", "20th Century",
				"Grant Wood",
				"American Gothic was inspired by the Dibble House in Iowa. "
						+ "Wood saw its Gothic architectural style and decided "
						+ "to paint the house along with the kind of people he "
						+ "imagined might live in it. The painting depicts a farmer "
						+ "and his daughter, both modeled by his dentist and his sister. "
						+ "Each element of the painting was done separately; the models "
						+ "sat separately and were never at the house.",
				"American_Gothic-small.jpg", "1930", 10000000, 5000000);

		Artifact a11 = new Artifact(11, "The Persistence of Memory",
				"20th Century", "Salvador Dali",
				"A widely known surrealist piece often referenced in pop "
						+ "culture. The Persistence of Memory depicts melting "
						+ "watches at a beach scene. Dali mentions that he was "
						+ "motivated by a surrealist concept of Camembert cheese "
						+ "melting under the sun.", "The_Persistence_of_Memory-small.jpg",
				"1931", 10000000, 5000000);

		Artifact a12 = new Artifact(12, "Guernica", "20th Century",
				"Pablo Picasso",
				"Pablo Picassoâ€™s detest of the Spanish Civil War is "
						+ "manifested in an art piece known as Guernica. "
						+ "The piece was commissioned by the Spanish Republican "
						+ "government to portray the pain and suffering caused by "
						+ "wars. Guernica would become a monumental symbol of anti-war "
						+ "and peace.", "Picasso-Guernica-small.jpg", "1937", 10000000,
				5000000);

		artifactList.add(a1);
		artifactList.add(a2);
		artifactList.add(a3);
		artifactList.add(a4);
		artifactList.add(a5);
		artifactList.add(a6);
		artifactList.add(a7);
		artifactList.add(a8);
		artifactList.add(a9);
		artifactList.add(a10);
		artifactList.add(a11);
		artifactList.add(a12);

		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		// Set this agent main behaviour
		SequentialBehaviour sb = new SequentialBehaviour();
		sb.addSubBehaviour(new RegisterInDF(this));
		sb.addSubBehaviour(new WakerBehaviour(this, 1000) {

			protected void handleElapsedTimeout() {
				MakeOperation mo = new MakeOperation();
				// send artifacts list to the tour-guide agent
				mo.setType(SEND_ARTIFACTS_LIST);
				mo.setList(artifactList);
				// System.out.println("Curator: " + artifactList);
				sendMessage(ACLMessage.INFORM, mo);
			}
		});
		//sb.addSubBehaviour(new ReceiveMessages(this));
		addBehaviour(sb);

		// Set up the gui
		myGui = new CuratorGui(this);
		myGui.setVisible(true);
	}

	class RegisterInDF extends OneShotBehaviour {
		// ------------------------------------------ Register in the DF for the
		// other agents to
		// be able to retrieve its AID
		RegisterInDF(Agent a) {
			super(a);
		}

		public void action() {

			ServiceDescription sd = new ServiceDescription();
			sd.setType(CURATOR_AGENT);
			sd.setName(getName());
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			dfd.addServices(sd);
			try {
				DFAgentDescription[] dfds = DFService.search(myAgent, dfd);
				if (dfds.length > 0) {
					DFService.deregister(myAgent, dfd);
				}
				DFService.register(myAgent, dfd);
				System.out.println(getLocalName() + " is ready.");
			}
			catch (Exception ex) {
				System.out.println("Curator: Failed registering with DF!");
				ex.printStackTrace();
				doDelete();
			}
		}
	}

	class SearchInDF extends OneShotBehaviour {

			Artifact artifact;
			ACLMessage msg;
			int nResponders;
		SearchInDF(Agent a, Artifact ar) {
			super(a);
			artifact = ar;
		}

		public void action() {

			ServiceDescription sd = new ServiceDescription();
			sd.setType(PROFILER_AGENT);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.addServices(sd);
		  msg = new ACLMessage(ACLMessage.CFP);
			try {
				DFAgentDescription[] dfds = DFService.search(myAgent, dfd);
				for (int i = 0; i < dfds.length; ++i) {
					nResponders ++;
					msg.addReceiver(dfds[i].getName());
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				//alertGui("Failed searching in the DF!");
			}
			
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_DUTCH_AUCTION);
			// We want to receive a reply in 10 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			try {
				msg.setContentObject(artifact);
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			round++;
			
			if (round == 1){
				myGui.getTextPane().setText("");
			}
			
			myGui.append("---------ROUND "+round+"----------\n");
			addBehaviour(new ContractNetInitiator(myAgent, msg) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("Auctioneer "+propose.getSender().getLocalName()+" proposed ");
					myGui.append("Auctioneer "+propose.getSender().getLocalName()+" proposed "+"\n");
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Auctioneer "+refuse.getSender().getLocalName()+" refused");
					myGui.append("Auctioneer "+refuse.getSender().getLocalName()+" refused"+"\n");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("Auctioneer "+failure.getSender().getLocalName()+" failed");
						
					}
					// Immediate failure --> we will not receive a response from this agent
					nResponders--;
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("Timeout expired: missing "+(nResponders - responses.size())+" responses");
					}
					// Evaluate proposals.
					long bestProposal = Long.MAX_VALUE;
					AID bestProposer = null;
					ACLMessage accept = null;
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
							long proposal = Long.parseLong(msg.getContent());
							if (proposal < bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}
						}
					}
					// Accept the proposal of the fastest proposer
					if (accept != null) {
						System.out.println("Accepting proposal "+" from responder "+bestProposer.getLocalName());
						myGui.append("Accepting proposal "+" from responder "+bestProposer.getLocalName()+"\n");
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						round = 0;
						
					}			
					else {
						int  price = artifact.getMaxPrice();
						price = (int) (price*0.9);
						if (price>artifact.getReservePrice()){
							artifact.setMaxPrice(price);
							addBehaviour(new SearchInDF(myAgent,artifact));
						}
						else{
							System.out.println("Auction is over. Artifact was not sold");
							myGui.append("Auction is over. Artifact was not sold"+"\n");
							round = 0;
							
						}
					}
				}
				
				protected void handleInform(ACLMessage inform) {
					System.out.println("Auctioner "+inform.getSender().getLocalName()+" successfully bought the artifact");
					myGui.append("Auctioner "+inform.getSender().getLocalName()+" successfully bought the artifact"+"\n");
					artifactList.remove(myGui.getSelection());
					myGui.setList(new JList<Object>(artifactList.toArray()));
					myGui.getScroller().setViewportView(myGui.getList());
					
				}
				
			} 
			);
		}

	}

	class ReceiveMessages extends CyclicBehaviour {
		// -------------------------------------------- Receive requests and queries
		// from profiler and
		// tour-guide agent and launch appropriate handlers

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
				Concept action = ((Action) content).getAction();

				switch (msg.getPerformative()) {

				case (ACLMessage.REQUEST):

					replyNotUnderstood(msg);

					break;

				default:
					replyNotUnderstood(msg);
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	void replyNotUnderstood(ACLMessage msg) {
		// --------------------------------------

		try {
			ContentElement content = getContentManager().extractContent(msg);
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
			getContentManager().fillContent(reply, content);
			send(reply);
			System.out.println("Server: Not understood!");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// --------------------------- Utility methods ----------------------------//

	void sendMessage(int performative, AgentAction action) {
		// -----------------------------------------------------

		ServiceDescription sd = new ServiceDescription();
		sd.setType(TOUR_GUIDE_AGENT);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addServices(sd);
		try {
			DFAgentDescription[] dfds = DFService.search(this, dfd);
			if (dfds.length > 0) {
				server = dfds[0].getName();
			}
			else {
				System.out.println("Curator: Couldn't localize tour-guide!");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Curator: Failed searching in the DF!");
		}

		ACLMessage msg = new ACLMessage(performative);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		Result result = new Result(action, artifactList);
		try {
			getContentManager().fillContent(msg, result);
			msg.addReceiver(server);
			send(msg);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<Artifact> getArtifactList() {
		return artifactList;
	}

	public void setArtifactList(List<Artifact> artifactList) {
		this.artifactList = artifactList;
	}

	protected void onGuiEvent(GuiEvent ev) {
		command = ev.getType();
		if(command == GET_ARTIFACT){
			Artifact a = (Artifact) ev.getParameter(0);
			String arName = a.getName();
			addBehaviour(new SearchInDF(this, a));
		}
		
	}
	
	
}
