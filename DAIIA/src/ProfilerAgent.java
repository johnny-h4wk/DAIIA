import java.util.List;

import jade.core.*;
import jade.content.*;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;
import ontologies.*;

/**
 * 
 * @author nickstanogias, Ioannis Kerkinos
 */
@SuppressWarnings("serial")
public class ProfilerAgent extends GuiAgent implements MuseumVocabulary {

	static final int WAIT = -1;
	static final int QUIT = 0;
	private int command = WAIT;
	private AID server;
	public int myprice;

	private Codec codec = new SLCodec();
	private Ontology ontology = MuseumOntology.getInstance();
	transient protected ProfilerAgentGui myGui; // The gui

	protected void setup() {
		// ---------------------

		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		// Register in the DF
		try {
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType(PROFILER_AGENT);
			sd.setName(getName());
			dfd.addServices(sd);
			DFService.register(this, dfd);
			System.out.println(getLocalName() + " registered with the DF");
		}
		catch (FIPAException fe) {
			System.out.println("Failed registering with DF!");
			fe.printStackTrace();
		}

		// Set up the gui
		myGui = new ProfilerAgentGui(this);
		myGui.setVisible(true);

		MessageTemplate template = MessageTemplate.and(MessageTemplate
				.MatchProtocol(FIPANames.InteractionProtocol.FIPA_DUTCH_AUCTION),
				MessageTemplate.MatchPerformative(ACLMessage.CFP));

		
		
		addBehaviour(new ContractNetResponder(this, template) {
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp)
					throws NotUnderstoodException, RefuseException {
				try {
					
					myGui.append("---------NEW ROUND----------\n");
					
					Artifact a = (Artifact) cfp.getContentObject();
					System.out.println("Auctioner " + getLocalName()
							+ ": CFP received from auctioneer " + cfp.getSender().getLocalName()
							+ ". Artifact is " + a.getName() + ", price is "
							+ Integer.toString(a.getMaxPrice()));
					myGui.append("Auctioner " + getLocalName()
							+ ": CFP received from auctioneer " + cfp.getSender().getLocalName()
							+ ". Artifact is " + a.getName() + ", price is "
							+ Integer.toString(a.getMaxPrice())+"\n");
					// System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+
					// a.getName());
					boolean proposal = evaluateAction(a.getMaxPrice());
					if (proposal) {
						// We provide a proposal
						System.out.println("Auctioner " + getLocalName()
								+ ": Proposing to pay, was willing to pay " + myprice);
						myGui.append("Auctioner " + getLocalName()
								+ ": Proposing to pay, was willing to pay " + myprice+"\n");
						ACLMessage propose = cfp.createReply();
						propose.setPerformative(ACLMessage.PROPOSE);
						propose.setContent(String.valueOf(System.currentTimeMillis()));
						

						
						return propose;
					}
					else {
						// We refuse to provide a proposal
						System.out.println("Auctioner " + getLocalName()
								+ ": Refuse to pay, was willing to pay " + myprice);
						myGui.append("Auctioner " + getLocalName()
								+ ": Refuse to pay, was willing to pay " + myprice+"\n");
						ACLMessage refuse = cfp.createReply();
						refuse.setPerformative(ACLMessage.REFUSE);
						refuse.setContent(String.valueOf(refuse));

						
						return refuse;
					}
				}
				catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp,
					ACLMessage propose, ACLMessage accept) throws FailureException {

				
				System.out.println("Auctioner " + getLocalName() + ": Proposal accepted");
				myGui.append("Auctioner " + getLocalName() + ": Proposal accepted"+"\n");

				System.out.println("Auctioner " + getLocalName()
						+ ": Artifact was bought!!");
				myGui.append("Auctioner " + getLocalName()
						+ ": Artifact was bought!!"+"\n");
				ACLMessage inform = accept.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				
	
				
				return inform;

			}

			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose,
					ACLMessage reject) {
				
				myGui.append("---------NEW ROUND----------\n");
				
				System.out.println("Auctioner " + getLocalName()
						+ ": Proposal rejected, not fast enough");
				myGui.append("Auctioner " + getLocalName()
						+ ": Proposal rejected, not fast enough"+"\n");
		
			}

		});
	}

	private boolean evaluateAction(int price) {
		myprice = (0) + (int) (Math.random() * (price * 1.1));
		// Simulate an evaluation by generating a random number
		if (myprice >= price) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void takeDown() {
		// ------------------------ Terminate the program properly

		System.out.println(getLocalName() + " is now shutting down.");
		if (myGui != null) {
			myGui.setVisible(false);
			myGui.dispose();
		}
	}

	protected void onGuiEvent(GuiEvent ev) {
		// ------------------------------------- Receive user command via the gui

		command = ev.getType();
		if (command == QUIT) {
			alertGui("Bye!");
			doDelete();
			// System.exit(0);
		}
		else if (command == GET_RECOMMENDATIONS) {
			String genre = (String) ev.getParameter(0);
			String creator = (String) ev.getParameter(1);
			CreatePreferences cp = new CreatePreferences(genre, creator);
			// System.out.println("Profiler: " + cp.getGenre() + " " +
			// cp.getCreator());
			sendMessage(ACLMessage.REQUEST, cp, command);
		}
	}

	void alertGui(Object response) {
		// ----------------------------- Process the response of the server
		// to the gui for display
		myGui.alertResponse(response);
	}

	void alertGuiArtifacts(List<Artifact> artifacts) {
		myGui.displayArtifacts(artifacts);
	}

	class WaitServerResponse extends ParallelBehaviour {
		// ---------------------------------------------------- launch a
		// SimpleBehaviour to receive
		// servers response and a WakerBehaviour
		// to terminate the waiting if there is
		// no response from the server
		WaitServerResponse(Agent a) {

			super(a, 1);

			addSubBehaviour(new ReceiveResponse(myAgent));

			addSubBehaviour(new WakerBehaviour(myAgent, 5000) {

				protected void handleElapsedTimeout() {
					alertGui("No response from server. Please, try later!");
				}
			});
		}
	}

	class ReceiveResponse extends SimpleBehaviour {
		// --------------------------------------------- // Receive and handle
		// server responses

		private boolean finished = false;

		ReceiveResponse(Agent a) {
			super(a);
		}

		public void action() {
			ACLMessage msg = receive();

			if (msg == null) {
				block();
				return;
			}
			if (msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD) {
				alertGui("Response: NOT UNDERSTOOD!");
			}
			else if (msg.getPerformative() != ACLMessage.INFORM) {
				alertGui("Unexpected msg from server!");
			}
			else {
				try {
					ContentElement content = getContentManager().extractContent(msg);
					// Concept action = ((Action)content).getAction();
					if (content instanceof Result) {
						Result result = (Result) content;
						if (result.getValue() instanceof java.util.List) {
							// System.out.println("Profiler: " +
							// (java.util.List)result.getValue());
							alertGuiArtifacts((List<Artifact>) result.getValue());
						}
						else
							alertGui("Unexpected result from server!");
					}
					else {
						alertGui("Unable to decode response!");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			finished = true;
		}

		public boolean done() {
			return finished;
		}

		public int onEnd() {
			command = WAIT;
			return 0;
		}
	}

	void lookupServer(int command) {
		// ----------------------------- Search in the DF to retrieve the curator
		// or tour-guide AID

		ServiceDescription sd = new ServiceDescription();

		if (command == GET_RECOMMENDATIONS) {
			sd.setType(TOUR_GUIDE_AGENT);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.addServices(sd);
			try {
				DFAgentDescription[] dfds = DFService.search(this, dfd);
				if (dfds.length > 0) {
					server = dfds[0].getName();
				}
				else {
					alertGui("Couldn't localize tour-guide!");
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				alertGui("Failed searching in the DF!");
			}
		}
		else {
			sd.setType(CURATOR_AGENT);
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.addServices(sd);
			try {
				DFAgentDescription[] dfds = DFService.search(this, dfd);
				if (dfds.length > 0) {
					server = dfds[0].getName();
				}
				else {
					alertGui("Couldn't localize curator!");
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				alertGui("Failed searching int the DF!");
			}
		}
	}

	// --------------------------- Utility methods ----------------------------//

	void sendMessage(int performative, AgentAction action, int command) {
		// ------------------------------------------------------------------

		lookupServer(command);
		if (server == null) {
			alertGui("Unable to localize the server! Operation aborted!");
			return;
		}
		ACLMessage msg = new ACLMessage(performative);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		try {
			getContentManager().fillContent(msg, new Action(server, action));
			msg.addReceiver(server);
			send(msg);
			addBehaviour(new WaitServerResponse(this));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
