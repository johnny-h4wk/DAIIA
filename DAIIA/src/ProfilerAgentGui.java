import jade.gui.GuiEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import ontologies.Artifact;
import ontologies.MuseumVocabulary;

/**
 * 
 * @author nickstanogias, Ioannis Kerkinos
 */
@SuppressWarnings("serial")
class ProfilerAgentGui extends JFrame implements ActionListener,
		MuseumVocabulary {
	// ----------------------------------------------------------------------------
	Object[] creators = { "", "Raphael", "Pablo Picasso", "Rembrandt",
			"Vincent Van Gogh" };
	Object[] genre = { "", "Renaissance", "Baroque", "19th Century",
			"20th Century" };
	String[] columnNames = { "Id", "Name", "Genre", "Creator", "Year" };
	private JPanel panel3;
	private Object[][] data;
	private Object[][] data2;

	private JComboBox<?> genreMenu;
	private JComboBox<?> creatorMenu;

	private JLabel creatorLabel;
	private JLabel genreLabel;
	private JLabel artifactLabel;

	private JTextField msg_text;

	private JButton ok;
	private JButton quit;

	private JTable artifactTable;

	private ProfilerAgent myAgent;
	private JTextPane textPane;

	public ProfilerAgentGui(ProfilerAgent a) {
		setPreferredSize(new Dimension(395, 439));
		getContentPane().setPreferredSize(new Dimension(400, 400));
		// ------------------------------------------- Constructor

		myAgent = a;

		setTitle("UserAgent - " + myAgent.getLocalName());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		msg_text = new JTextField(15);
		msg_text.setEditable(false);
		msg_text.setBackground(new Color(0, 0, 0));
		msg_text.setFont(new Font("Tahoma", Font.BOLD, 12));
		msg_text.setForeground(new Color(255, 255, 255));
		msg_text.setHorizontalAlignment(JTextField.CENTER);
		msg_text.setText("Please specify your interest!");

		genreLabel = new JLabel();
		genreLabel.setFont(new Font("Arial", 1, 12));
		genreLabel.setText("Genre");

		creatorLabel = new JLabel();
		creatorLabel.setFont(new Font("Arial", 1, 12));
		creatorLabel.setText("Creator");

		genreMenu = new JComboBox<Object>(genre);
		genreMenu.setBackground(new Color(153, 153, 153));
		genreMenu.setFont(new Font("Verdana", 0, 12));
		genreMenu.setForeground(new Color(0, 51, 51));

		creatorMenu = new JComboBox<Object>(creators);
		creatorMenu.setBackground(new Color(153, 153, 153));
		creatorMenu.setFont(new Font("Verdana", 0, 12));
		creatorMenu.setForeground(new Color(0, 51, 51));

		ok = new JButton();
		ok.setPreferredSize(new Dimension(40, 9));
		ok.setFont(new Font("DejaVu Sans Mono", 0, 12));
		ok.setText("OK");
		ok.addActionListener(this);

		quit = new JButton();
		quit.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
		quit.setText("QUIT");
		quit.setToolTipText("Stop agent and exit");
		quit.addActionListener(this);

		Object obj[][] = new Object[0][columnNames.length];
		TableModel model = new TableDataModel(obj, columnNames);
		getContentPane().setLayout(null);

		JPanel base = new JPanel();
		base.setBounds(0, 0, 400, 400);
		base.setPreferredSize(new Dimension(400, 400));
		base.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(base);
		base.setLayout(null);

		JPanel panel1 = new JPanel();
		panel1.setBounds(15, 15, 347, 21);
		panel1.setLayout(new BorderLayout(25, 0));
		panel1.add(msg_text);
		base.add(panel1);

		// JPanel panel2 = new JPanel();
		JPanel panel2_1 = new JPanel();
		panel2_1.setBounds(15, 47, 276, 46);
		JPanel panel2_2 = new JPanel();
		panel2_2.setBounds(301, 47, 72, 46);
		panel2_1.setLayout(new GridLayout(2, 2));
		panel2_2.setLayout(new BoxLayout(panel2_2, BoxLayout.Y_AXIS));
		// panel2.setLayout(new BorderLayout(100,10));
		panel2_1.add(genreLabel);
		panel2_1.add(genreMenu);
		panel2_2.add(ok);
		panel2_1.add(creatorLabel);
		panel2_1.add(creatorMenu);
		panel2_2.add(quit);
		// panel2.add(panel2_1, BorderLayout.WEST);
		// panel2.add(panel2_2, BorderLayout.EAST);
		base.add(panel2_1);
		base.add(panel2_2);

		panel3 = new JPanel();
		panel3.setBounds(15, 121, 347, 117);
		panel3.setLayout(null);
		artifactTable = new JTable(model);
		artifactTable.setBounds(0, 0, 345, 115);
		panel3.add(artifactTable);
		artifactTable.setFillsViewportHeight(true);
		artifactTable.setPreferredScrollableViewportSize(new Dimension(320, 100));
		artifactTable.setFont(new Font("Arial", Font.PLAIN, 11));
		artifactTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JFrame newwindow = new JFrame("New Window");
					int selection = artifactTable.getSelectedRow();
					System.out.println(selection);
					JPanel base = new JPanel();
					base.setBorder(new EmptyBorder(15, 15, 15, 15));
					base.setLayout(new BorderLayout(10, 10));
					newwindow.getContentPane().add(base);
					JPanel panel1 = new JPanel();
					panel1.setLayout(new GridLayout(3, 3));
					panel1.add(new JLabel("name:"));
					panel1.add(new JTextField((String) data[selection][1]));
					panel1.add(new JLabel("genre:"));
					panel1.add(new JTextField((String) data[selection][2]));
					panel1.add(new JLabel("creator:"));
					panel1.add(new JTextField((String) data[selection][3]));
					base.add(panel1, BorderLayout.CENTER);

					JLabel imgLabel = new JLabel();
					imgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
							"/images/" + (String) data2[selection][1])));
					base.add(imgLabel, BorderLayout.NORTH);

					JPanel panel3 = new JPanel();
					panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
					JTextArea text = new JTextArea(4, 5);
					JScrollPane scroller = new JScrollPane(text);
					text.setLineWrap(true);
					scroller
							.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					scroller
							.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					panel3.add(new JLabel("artifact discription:"));
					text.setText((String) data2[selection][0]);
					panel3.add(scroller);
					base.add(panel3, BorderLayout.SOUTH);

					newwindow.setSize(400, 450);
					newwindow.setVisible(true);
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 347, 117);
		panel3.add(scrollPane);
		base.add(panel3);
		
				artifactLabel = new JLabel();
				artifactLabel.setBounds(15, 104, 347, 15);
				base.add(artifactLabel);
				artifactLabel.setFont(new Font("Arial", 1, 12));
				artifactLabel.setText("Recommended Artifacts");
				
				JPanel panel = new JPanel();
				panel.setBounds(15, 272, 347, 117);
				base.add(panel);
				panel.setLayout(null);
				
				textPane = new JTextPane();	
				JScrollPane jsp = new JScrollPane(textPane);
				jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				jsp.setBounds(0, 0, 345, 115);
				panel.add(jsp);
				
				JLabel lblAuction = new JLabel();
				lblAuction.setText("Auction");
				lblAuction.setFont(new Font("Arial", Font.BOLD, 12));
				lblAuction.setBounds(15, 256, 347, 15);
				base.add(lblAuction);

		pack();
		setSize(385, 439);
		Rectangle r = getGraphicsConfiguration().getBounds();
		setLocation(r.x + (r.width - getWidth()) / 2, r.y
				+ (r.height - getHeight()) / 2);

	}

	public void actionPerformed(ActionEvent ae) {
		// ------------------------------------------

		if (ae.getSource() == quit) {
			shutDown();
		}
		else if (ae.getSource() == ok) {
			String gen = (String) genreMenu.getSelectedItem();
			String cre = (String) creatorMenu.getSelectedItem();
			GuiEvent ge = new GuiEvent(this, GET_RECOMMENDATIONS);
			// System.out.println(gen + cre);
			ge.addParameter(gen);
			ge.addParameter(cre);
			myAgent.postGuiEvent(ge);
		}
	}

	void shutDown() {
		// -------------- Control the closing of this gui

		int rep = JOptionPane.showConfirmDialog(this,
				"Are you sure you want to exit?", myAgent.getLocalName(),
				JOptionPane.YES_NO_CANCEL_OPTION);

		if (rep == JOptionPane.YES_OPTION) {
			GuiEvent ge = new GuiEvent(this, myAgent.QUIT);
			myAgent.postGuiEvent(ge);
		}
	}

	void alertInfo(String s) {
		// -----------------------

		Toolkit.getDefaultToolkit().beep();
		msg_text.setText(s);
	}

	public void alertResponse(Object o) {
		// ----------------------------------

		String s = "";
		if (o instanceof String) {
			s = (String) o;
		}
		msg_text.setText(s);
	}

	void displayArtifacts(List<Artifact> list) {
		// -------------------------------------------
		String s = "";

		s = "These are the recommended artifacts";
		data = new Object[list.size()][columnNames.length];
		data2 = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			Artifact artifact = (Artifact) list.get(i);
			data[i][0] = artifact.getId();
			data[i][1] = artifact.getName();
			data[i][2] = artifact.getGenre();
			data[i][3] = artifact.getCreator();
			data[i][4] = artifact.getYear();

			data2[i][0] = artifact.getDescription();
			data2[i][1] = artifact.getImg();
		}
		TableDataModel model = (TableDataModel) artifactTable.getModel();
		model.setData(data);
		artifactTable.setModel(model);
		artifactTable.updateUI();

		msg_text.setText(s);
	}

	// =========================== External class ============================//

	/*
	 * TableDataModel: -------------- External class for the definition of the
	 * tables data model, used to control the display of data within the different
	 * tables
	 */
	class TableDataModel extends AbstractTableModel {
		// ----------------------------------------------

		private String[] columns;
		private Object[][] data;

		public TableDataModel(Object[][] data, String[] columns) {
			// ---------------------------------------------------------- Constructor
			this.data = data;
			this.columns = columns;
		}

		public int getColumnCount() {
			// ----------------------------- Return the number of columns in the table
			return columns.length;
		}

		public int getRowCount() {
			// -------------------------- Return the number of rows in the table
			return data.length;
		}

		public String getColumnName(int col) {
			// -------------------------------------- Return the name of a column
			return columns[col];
		}

		public boolean isCellEditable(int row, int col) {
			// -------------------------------------------------
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col == 3) {
				return true;
			}
			else {
				return false;
			}
		}

		public Object getValueAt(int row, int col) {
			// -------------------------------------------- Return the value at a
			// specific
			// row and column
			if (data.length == 0)
				return null;
			return data[row][col];
		}

		public Class<?> getColumnClass(int col) {
			// -------------------------------------- Return the class of the values
			// held
			// by a column
			Object o = getValueAt(0, col);
			if (o == null)
				return columns[col].getClass();
			return getValueAt(0, col).getClass();
		}

		public void setValueAt(Object value, int row, int col) {
			// ------------------------------------------------------- Set the value
			// at a specific
			// row and column
			data[row][col] = value;
		}

		public void setData(Object[][] data) {
			// ------------------------------------- Update the entire data in the
			// table
			this.data = data;
		}

		Object[][] getData() {
			// --------------------- Return the entire data of the table
			return data;
		}
	}// end TableDataModel
	public void append(String s) {
		   try {
		      Document doc = textPane.getDocument();
		      doc.insertString(doc.getLength(), s, null);
		   } catch(BadLocationException exc) {
		      exc.printStackTrace();
		   }
		}
	public JTextPane getTextPane() {
		return textPane;
	}
}