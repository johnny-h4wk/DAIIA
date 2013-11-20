import jade.gui.GuiEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import ontologies.Artifact;
import ontologies.MuseumVocabulary;

import javax.swing.JScrollBar;


@SuppressWarnings("serial")
public class CuratorGui extends JFrame implements ActionListener, MuseumVocabulary {

	private JPanel contentPane;
	private JButton startButton;
	private JTextArea text;
	private JList<?> list;
	private JScrollPane scroller;
	
	private CuratorAgent myAgent;

	/**
	 * Create the frame.
	 */
	public CuratorGui(CuratorAgent a) {
		
		myAgent = a;
		
		setTitle("UserAgent - " + myAgent.getLocalName());
	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList<Object>(myAgent.getArtifactList().toArray());
		list.setVisibleRowCount(5);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroller = new JScrollPane(list);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(6, 19, 109, 135);
		contentPane.add(scroller);
		
		
		text = new JTextArea();
		text.setBounds(127, 163, 223, 95);
		contentPane.add(text);
		
		startButton = new JButton("Start Auction");
		startButton.setBounds(233, 79, 117, 29);
		startButton.addActionListener(this);
		contentPane.add(startButton);
	}
	public JButton getStartButton() {
		return startButton;
	}
	public JTextArea getText() {
		return text;
	}
	public JList<?> getList() {
		return list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int selection = list.getSelectedIndex();
		Artifact a = myAgent.getArtifactList().get(selection);
		GuiEvent ge = new GuiEvent(this, GET_ARTIFACT);
		// System.out.println(gen + cre);
		ge.addParameter(a);
		myAgent.postGuiEvent(ge);
		
	}
}
