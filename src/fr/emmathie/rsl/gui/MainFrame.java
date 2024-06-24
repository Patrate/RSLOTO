package fr.emmathie.rsl.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Script;
import fr.emmathie.rsl.elements.Element;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7390751268399407408L;
	private JTextArea consoleArea;
	private JTextField commandField;
	private JList<Script> leftList;
	private JList<String> rightList;
	private JList<Element> elementList;

	private JLabel scriptNameLabel;

	private DefaultListModel<Script> leftListModel;
	private DefaultListModel<Element> elementListModel;

	private GridBagConstraints gbc_commandPanel;
	private static MainFrame mainFrame;
	private GridBagConstraints gbc_1;
	private GridBagConstraints gbc_2;
	private GridBagConstraints gbc_3;

	public static MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame = new MainFrame();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Application Swing");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Barre d'outils
		JToolBar toolBar = new JToolBar();
		JButton newButton, saveButton, runButton;
		toolBar.add(newButton = new JButton("Nouveau"));
		toolBar.add(saveButton = new JButton("Sauvegarder"));
		toolBar.add(runButton = new JButton("Executer"));

		newButton.addActionListener(new NewButtonListener());
		saveButton.addActionListener(new SaveButtonListener());
		runButton.addActionListener(new RunButtonListener());

		// Liste gauche
		leftListModel = new DefaultListModel<>();
		leftListModel.addAll(Main.getInstance().getScripts());
		leftList = new JList<Script>(leftListModel);
		leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		@SuppressWarnings("serial")
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				((JLabel) c).setText(((Script) value).getScriptName());
				return c;
			}
		};
		leftList.setCellRenderer(dlcr);
		leftList.setPrototypeCellValue(new Script("abcdef123456789"));
		leftList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseGlobal(e);
			}

			public void mouseReleased(MouseEvent e) {
				mouseGlobal(e);
			}

			private void mouseGlobal(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int clickedIndex = leftList.locationToIndex(e.getPoint());
					Script clickedItem = leftList.getModel().getElementAt(clickedIndex);

					JPopupMenu menu = new JPopupMenu();
					JMenuItem rename = new JMenuItem("Rename");
					rename.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String s = (String) JOptionPane.showInputDialog(null, "Script name",
									clickedItem.getScriptName());
							if (s == null || s.isBlank())
								return;
							print("new script named " + s);
							clickedItem.setScriptName(s);
							leftList.updateUI();
						}
					});
					JMenuItem delete = new JMenuItem("Delete");
					delete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int choice = JOptionPane.showConfirmDialog(null,
									"Delete " + clickedItem.getScriptName() + "?", "Warning",
									JOptionPane.YES_NO_OPTION);
							if (choice == JOptionPane.YES_OPTION) {
								leftListModel.remove(clickedIndex);
								Main.getInstance().getScripts().remove(clickedIndex);
							}
						}
					});

					menu.add(rename);
					menu.add(delete);

					menu.show(leftList, 5, leftList.getCellBounds(clickedIndex, clickedIndex).y + 5);
				}
			}

		});
		leftList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Script script = leftList.getSelectedValue();
				elementListModel.clear();
				if (script != null) {
					scriptNameLabel.setText(script.getScriptName());
					elementListModel.addAll(script.getElements());
				} else {
					scriptNameLabel.setText("<No script selected>");
				}
			}

		});
		// Liste droite
		DefaultListModel<String> rightListModel = new DefaultListModel<>();
		rightListModel.addElement("Item A");
		rightListModel.addElement("Item B");
		rightListModel.addElement("Item C");
		rightList = new JList<>(rightListModel);

		// Console en bas avec zone de texte et champ texte
		JPanel commandPanel = new JPanel(new BorderLayout());
		consoleArea = new JTextArea(5, 20);
		consoleArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) consoleArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
		JScrollPane scrollPane = new JScrollPane(consoleArea);

		commandField = new JTextField(80);
		commandField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = commandField.getText();
				consoleArea.append("Commande: " + command + "\n");
				commandField.setText("");
			}
		});

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc_commandField = new GridBagConstraints();
		gbc_commandField.weightx = 1.0;
		gbc_commandField.gridy = 0;
		gbc_commandField.gridx = 0;
		gbc_commandField.gridwidth = 5;
		buttonsPanel.add(commandField, gbc_commandField);

		JButton buttonA = new JButton("A");
		JButton buttonB = new JButton("B");
		JButton buttonC = new JButton("C");

		buttonA.addActionListener(new CommandButtonListener());
		buttonB.addActionListener(new CommandButtonListener());
		buttonC.addActionListener(new CommandButtonListener());

		GridBagConstraints gbc_buttonA = new GridBagConstraints();
		gbc_buttonA.anchor = GridBagConstraints.EAST;
		gbc_buttonA.gridy = 0;
		gbc_buttonA.gridx = 5;
		buttonsPanel.add(buttonA, gbc_buttonA);

		GridBagConstraints gbc_buttonB = new GridBagConstraints();
		gbc_buttonB.anchor = GridBagConstraints.EAST;
		gbc_buttonB.gridy = 0;
		gbc_buttonB.gridx = 6;
		buttonsPanel.add(buttonB, gbc_buttonB);

		GridBagConstraints gbc_buttonC = new GridBagConstraints();
		gbc_buttonC.anchor = GridBagConstraints.EAST;
		gbc_buttonC.gridy = 0;
		gbc_buttonC.gridx = 7;
		buttonsPanel.add(buttonC, gbc_buttonC);

		commandPanel.add(scrollPane, BorderLayout.CENTER);
		commandPanel.add(buttonsPanel, BorderLayout.SOUTH);

		// Utilisation de GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWidths = new int[] { 160, 160, 160 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		getContentPane().setLayout(gridBagLayout);
		gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 0);

		// Ajout de la barre d'outils
		gbc_1.gridx = 0;
		gbc_1.gridy = 0;
		gbc_1.gridwidth = 3;
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(toolBar, gbc_1);

		// Ajout de la liste de gauche
		gbc_2 = new GridBagConstraints();
		gbc_2.insets = new Insets(0, 0, 5, 5);
		gbc_2.gridx = 0;
		gbc_2.gridy = 1;
		gbc_2.gridwidth = 1;
		gbc_2.gridheight = 2;
		gbc_2.fill = GridBagConstraints.BOTH;
		getContentPane().add(new JScrollPane(leftList), gbc_2);

		// Ajout de la liste de droite
		gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 0);
		gbc_3.gridx = 2;
		gbc_3.gridy = 1;
		gbc_3.gridwidth = 1;
		gbc_3.gridheight = 2;
		gbc_3.fill = GridBagConstraints.BOTH;
		getContentPane().add(new JScrollPane(rightList), gbc_3);

		// Ajout de la zone centrale
		JPanel superCentralPane = new JPanel();
		GridBagConstraints gbc_superCentralPane = new GridBagConstraints();
		gbc_superCentralPane.weighty = 1.0;
		gbc_superCentralPane.weightx = 1.0;
		gbc_superCentralPane.insets = new Insets(0, 0, 5, 5);
		gbc_superCentralPane.fill = GridBagConstraints.BOTH;
		gbc_superCentralPane.gridx = 1;
		gbc_superCentralPane.gridy = 1;
		getContentPane().add(superCentralPane, gbc_superCentralPane);
		superCentralPane.setLayout(new BorderLayout(0, 0));

		JSplitPane centralPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		centralPane.setResizeWeight(0.5);
		superCentralPane.add(centralPane);

		scriptNameLabel = new JLabel("<No script selected>");
		superCentralPane.add(scriptNameLabel, BorderLayout.NORTH);

		JPanel elementPanel = new JPanel();
		centralPane.setTopComponent(elementPanel);
		elementPanel.setLayout(new BorderLayout(0, 0));

		elementListModel = new DefaultListModel<Element>();
		elementList = new JList<Element>(elementListModel);
		elementList.setVisibleRowCount(4);
		elementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		@SuppressWarnings("serial")
		DefaultListCellRenderer dlcrElement = new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				((JLabel) c).setText(index + ": " + ((Element) value).getClass().getSimpleName());
				return c;
			}
		};
		elementList.setCellRenderer(dlcrElement);
		elementPanel.add(elementList, BorderLayout.CENTER);

		JPanel elementDataPanel = new JPanel();
		centralPane.setBottomComponent(elementDataPanel);

		// Ajout de la console en bas
		gbc_commandPanel = new GridBagConstraints();
		gbc_commandPanel.insets = new Insets(0, 0, 5, 0);
		gbc_commandPanel.gridx = 0;
		gbc_commandPanel.gridy = 3;
		gbc_commandPanel.gridwidth = 3;
		gbc_commandPanel.gridheight = 1;
		gbc_commandPanel.weightx = 1.0;
		gbc_commandPanel.weighty = 0.2;
		gbc_commandPanel.fill = GridBagConstraints.BOTH;
		getContentPane().add(commandPanel, gbc_commandPanel);

		JPanel panel = new JPanel();
		commandPanel.add(panel, BorderLayout.NORTH);
	}

	private class CommandButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			print("Bouton " + source.getText() + " cliqué");
			action(e);
		}

		protected void action(ActionEvent e) {
			return;
		}
	}

	private class RunButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Script script = leftList.getSelectedValue();
			if (script == null) {
				print("Sélectionnez un script");
				return;
			}
			if (script.isAlive()) {
				print(script.getScriptName() + " already running");
				return;
			}
			// TODO execute as subProcess
			// TODO add process listener to monitor progress and end
			script.start();
		}
	}

	private class NewButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = (String) JOptionPane.showInputDialog(null, "Script name", "New Script");
			print("new script named " + s);
			if (s == null || s.isBlank())
				return;
			Script nscript = new Script(s);
			Main.getInstance().getScripts().add(nscript);
			leftListModel.addElement(nscript);
			// Verify that the name is correct
			// Create the script and add to the list
		}
	}

	private class SaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Script script = leftList.getSelectedValue();
			if (script == null) {
				print("Sélectionnez un script");
				return;
			}
			JOptionPane.showMessageDialog(null, "TODO: save the script " + script.getScriptName());
			// Check that a script is selected, otherwise exit
			// Save to file if exist
			// otherwise select save and file location
			// Save
		}
	}

	public void print(String message) {
		consoleArea.append("\n" + message);
	}
}
