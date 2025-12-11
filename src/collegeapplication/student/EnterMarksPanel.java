package collegeapplication.student;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import collegeapplication.cource.CourceData;
import collegeapplication.faculty.FacultyMain;
import collegeapplication.subject.SubjectData;

/*
 * Title : EnterMarksPanel.java
 * Created by : Ajaysinh Rathod
 * Purpose : For entering marks of student
 * Mail : ajaysinhrathod1290@gmail.com
 */

@SuppressWarnings("serial")
public class EnterMarksPanel extends JPanel implements ActionListener {

	private JComboBox<String> courcenamecombo, semoryearcombo, subjectnamecombo;
	private JLabel Errorlabel;
	private JScrollPane scrollPane;
	private JTable table;
	private int totalstudent = 0;
	private JButton submitbutton;
	private JButton theorymarksbutton;
	private JButton practicalmarksbutton;
	private JLabel TableErrorlabel;
	private Timer timer;
	private JLabel label3;
	private JLabel label2;
	private JLabel label1;
	private JPanel selectcourcepanel;
	private JLabel nodatafoundlabel;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1116, this.getHeight());
	}

	public EnterMarksPanel(FacultyMain fm) {
		this();
		courcenamecombo.setSelectedItem(fm.f.getCourceName());
		semoryearcombo.setModel(
				new DefaultComboBoxModel<String>(new CourceData().getSemorYear(courcenamecombo.getSelectedItem() + "")));
		String[] totalsub = new SubjectData().getSubjectinCource(fm.f.getCourceCode(), fm.f.getSemorYear());
		subjectnamecombo.setModel(new DefaultComboBoxModel<String>(totalsub));
		semoryearcombo.setSelectedIndex(fm.f.getSemorYear());
		subjectnamecombo.setSelectedItem(fm.f.getSubject());
		selectcourcepanel.setVisible(false);
		this.createtablemodel();
		scrollPane.setLocation(scrollPane.getX(), selectcourcepanel.getY());
		submitbutton.setLocation(submitbutton.getX(), scrollPane.getY() + scrollPane.getHeight() + 40);
	}

	public EnterMarksPanel() {

		timer = new Timer(10000, this);
		timer.start();
		setBorder(null);
		setBackground(new Color(255, 255, 255));
		this.setSize(1116, 544);
		setLayout(null);

		initHeaderPanel();
		initCourseSelectionPanel();
		initTableAndScrollPane();
		initSubmitAndErrorLabels();
		initNoDataFoundLabel();
	}

	/* -------------------- UI INIT HELPERS -------------------- */

	private void initHeaderPanel() {
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 1076, 117);
		panel.setBackground(new Color(32, 178, 170));
		add(panel);
		panel.setLayout(null);

		theorymarksbutton = new JButton("Theory Marks");
		theorymarksbutton.setBorder(new LineBorder(new Color(255, 255, 255)));
		theorymarksbutton.setForeground(new Color(0, 139, 139));
		theorymarksbutton.setBackground(new Color(255, 255, 255));
		theorymarksbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		theorymarksbutton.setName("Active");
		theorymarksbutton.addActionListener(this);
		theorymarksbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		theorymarksbutton.setFocusable(false);
		theorymarksbutton.setBounds(727, 69, 148, 33);
		panel.add(theorymarksbutton);

		practicalmarksbutton = new JButton("Practical Marks");
		practicalmarksbutton.setBorder(new LineBorder(new Color(255, 255, 255)));
		practicalmarksbutton.setForeground(new Color(255, 255, 255));
		practicalmarksbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		practicalmarksbutton.setBackground(new Color(32, 178, 170));
		practicalmarksbutton.setBounds(893, 69, 148, 33);
		practicalmarksbutton.setFocusable(false);
		practicalmarksbutton.addActionListener(this);
		practicalmarksbutton.setName("Deactive");
		practicalmarksbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(practicalmarksbutton);

		JLabel lblEnterStudentMarks = new JLabel("Enter Student Marks");
		lblEnterStudentMarks.setForeground(new Color(255, 255, 255));
		lblEnterStudentMarks.setBackground(new Color(255, 255, 255));
		lblEnterStudentMarks.setFont(new Font("Segoe UI", Font.BOLD, 27));
		lblEnterStudentMarks.setBounds(10, 65, 309, 33);
		panel.add(lblEnterStudentMarks);
	}

	private void initCourseSelectionPanel() {
		selectcourcepanel = new JPanel();
		selectcourcepanel.setBounds(10, 159, 1073, 222);
		add(selectcourcepanel);
		selectcourcepanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		selectcourcepanel.setBackground(Color.WHITE);
		selectcourcepanel.setLayout(null);

		courcenamecombo = new JComboBox<String>(new CourceData().getCourceName());
		courcenamecombo.setFocusable(false);
		courcenamecombo.setBackground(Color.WHITE);
		courcenamecombo.addActionListener(this);
		courcenamecombo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		courcenamecombo.setBounds(236, 0, 825, 40);
		selectcourcepanel.add(courcenamecombo);

		semoryearcombo = new JComboBox<String>();
		semoryearcombo.setFocusable(false);
		semoryearcombo.setBackground(Color.WHITE);
		semoryearcombo.setBounds(236, 73, 825, 40);
		semoryearcombo.addActionListener(this);
		semoryearcombo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		selectcourcepanel.add(semoryearcombo);

		subjectnamecombo = new JComboBox<String>();
		subjectnamecombo.setFocusable(false);
		subjectnamecombo.setBackground(Color.WHITE);
		subjectnamecombo.setBounds(236, 143, 825, 40);
		subjectnamecombo.addActionListener(this);
		subjectnamecombo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		selectcourcepanel.add(subjectnamecombo);

		label1 = new JLabel("Select Cource   :");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label1.setBounds(10, -1, 200, 40);
		selectcourcepanel.add(label1);

		label2 = new JLabel("Select Sem/Year  :");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label2.setBounds(10, 72, 200, 40);
		selectcourcepanel.add(label2);

		label3 = new JLabel("Select Subject   :");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label3.setBounds(10, 142, 200, 40);
		selectcourcepanel.add(label3);

		Errorlabel = new JLabel("This is required question  !");
		Errorlabel.setVisible(false);
		Errorlabel.setForeground(Color.RED);
		Errorlabel.setFont(new Font("Arial", Font.PLAIN, 14));
		Errorlabel.setBounds(233, 45, 225, 17);
		selectcourcepanel.add(Errorlabel);
	}

	private void initTableAndScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setBounds(10, 415, 1062, 40 + (totalstudent * 40));
		for (Component c : scrollPane.getComponents()) {
			c.setBackground(Color.white);
		}
		add(scrollPane);

		table = new JTable();
		table.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		table.setBackground(Color.white);
		table.setRowHeight(40);
		table.getTableHeader().setBackground(new Color(32, 178, 170));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(new Dimension(50, 40));
		table.setDragEnabled(false);
		table.setFocusable(false);
		table.setSelectionModel(new ForcedListSelectionModel());
		table.setSelectionBackground(new Color(240, 255, 255));
		table.setSelectionForeground(Color.black);
		table.setGridColor(Color.LIGHT_GRAY);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		scrollPane.setVisible(false);
	}

	private void initSubmitAndErrorLabels() {
		submitbutton = new JButton("Submit Marks");
		submitbutton.setForeground(Color.WHITE);
		submitbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		submitbutton.setFocusable(false);
		submitbutton.setBackground(new Color(32, 178, 170));
		submitbutton.setBounds(923, 490, 149, 37);
		submitbutton.setVisible(false);
		submitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		submitbutton.setBorder(new EmptyBorder(0, 0, 0, 0));
		submitbutton.addActionListener(this);
		add(submitbutton);

		TableErrorlabel = new JLabel("Marks is greater than max marks  !");
		TableErrorlabel.setHorizontalAlignment(SwingConstants.RIGHT);
		TableErrorlabel.setForeground(new Color(255, 0, 0));
		TableErrorlabel.setFont(new Font("candara", Font.PLAIN, 18));
		TableErrorlabel.setBounds(20, 458, 1052, 21);
		TableErrorlabel.setVisible(false);
		add(TableErrorlabel);
	}

	private void initNoDataFoundLabel() {
		nodatafoundlabel = new JLabel("");
		nodatafoundlabel.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			Image image = ImageIO.read(new File("./assets/notfound2.png"));
			nodatafoundlabel.setIcon(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

		} catch (IOException e) {
			e.printStackTrace();
		}
		nodatafoundlabel.setText("No Students Found...!");
		nodatafoundlabel.setVerticalTextPosition(JLabel.BOTTOM);
		nodatafoundlabel.setBorder(null);
		nodatafoundlabel.setBackground(new Color(245, 245, 245));
		nodatafoundlabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		nodatafoundlabel.setHorizontalTextPosition(JLabel.CENTER);
		nodatafoundlabel.setIconTextGap(20);
		nodatafoundlabel.setVisible(false);
		nodatafoundlabel.setBounds(300, 380, 480, 321);
		add(nodatafoundlabel);
	}

	/* -------------------- ACTION LISTENER -------------------- */

	@Override
	public void actionPerformed(ActionEvent e) {

		Errorlabel.setVisible(false);
		TableErrorlabel.setVisible(false);

		Object src = e.getSource();

		if (src == courcenamecombo) {
			handleCourseSelection();
		} else if (src == semoryearcombo) {
			handleSemSelection();
		} else if (src == subjectnamecombo) {
			handleSubjectSelection();
		} else if (src == submitbutton) {
			handleSubmitMarks();
		} else if (src == theorymarksbutton) {
			handleTheoryButtonClick();
		} else if (src == practicalmarksbutton) {
			handlePracticalButtonClick();
		}

		resetViewIfSelectionIncomplete();
		hideSubmitIfNoStudents();
	}

	/* -------------------- EVENT HANDLER HELPERS -------------------- */

	private void handleCourseSelection() {
		courcenamecombo.setFocusable(false);

		subjectnamecombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		if (courcenamecombo.getSelectedIndex() == 0) {
			semoryearcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		} else {
			String cource = (String) courcenamecombo.getSelectedItem();
			semoryearcombo.setModel(new DefaultComboBoxModel<String>(new CourceData().getSemorYear(cource)));
		}
	}

	private void handleSemSelection() {
		if (semoryearcombo.getSelectedIndex() > 0) {
			String cource = (String) courcenamecombo.getSelectedItem();
			String courceCode = new CourceData().getCourcecode(cource);
			String[] totalsub = new SubjectData().getSubjectinCource(courceCode, semoryearcombo.getSelectedIndex());
			if (totalsub != null) {
				subjectnamecombo.setModel(new DefaultComboBoxModel<String>(totalsub));
			} else {
				subjectnamecombo
						.setModel(new DefaultComboBoxModel<String>(new String[] { "No Subject Found" }));
			}
		} else {
			subjectnamecombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		}
	}

	private void handleSubjectSelection() {
		if (!validateSelections()) {
			return;
		}
		createtablemodel();
	}

	private void handleSubmitMarks() {
		if (table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}
		if (theorymarksbutton.getName().equals("Active")) {
			addMarks(true);
		} else {
			addMarks(false);
		}
	}

	private void handleTheoryButtonClick() {
		ActiveButton(theorymarksbutton);
		DeactiveButton(practicalmarksbutton);
		if (isAllSelectionDone()) {
			createtablemodel();
		}
	}

	private void handlePracticalButtonClick() {
		DeactiveButton(theorymarksbutton);
		ActiveButton(practicalmarksbutton);
		if (isAllSelectionDone()) {
			createtablemodel();
		}
	}

	private void resetViewIfSelectionIncomplete() {
		if (!isAllSelectionDone()) {
			scrollPane.setVisible(false);
			submitbutton.setVisible(false);
			totalstudent = 0;
			this.setSize(1116, 544 + (totalstudent * 40));
		}
	}

	private void hideSubmitIfNoStudents() {
		if (totalstudent == 0) {
			submitbutton.setVisible(false);
		}
	}

	private boolean isAllSelectionDone() {
		return courcenamecombo.getSelectedIndex() != 0 &&
				semoryearcombo.getSelectedIndex() != 0 &&
				subjectnamecombo.getSelectedIndex() != 0;
	}

	private boolean validateSelections() {
		if (courcenamecombo.getSelectedIndex() == 0) {
			showerror(courcenamecombo);
			return false;
		} else if (semoryearcombo.getSelectedIndex() == 0) {
			showerror(semoryearcombo);
			return false;
		} else if (subjectnamecombo.getSelectedItem().equals("No Subject Found")) {
			Component tf = subjectnamecombo;
			Errorlabel.setVisible(true);
			Errorlabel.setText("No Subject Found !");
			Errorlabel.setBounds(tf.getX(), tf.getY() + tf.getHeight() - 5, 400, 26);
			return false;
		} else if (subjectnamecombo.getSelectedIndex() == 0) {
			showerror(subjectnamecombo);
			return false;
		}
		return true;
	}

	/* -------------------- TABLE / MODEL HELPERS -------------------- */

	public void showerror(JComponent tf) {
		Errorlabel.setVisible(true);
		Errorlabel.setText("This is required question !");
		Errorlabel.setBounds(tf.getX(), tf.getY() + tf.getHeight() - 5, 400, 26);
	}

	public void createtablemodel() {

		nodatafoundlabel.setVisible(false);

		if (theorymarksbutton.getName().equals("Active")) {
			table.setModel(createTheoryMarksModel());
		} else {
			table.setModel(createPracticalMarksModel());
		}
		totalstudent = table.getRowCount();

		configureTableLayout();
		configureMarksEditableColumn();

		scrollPane.setVisible(true);
		if (totalstudent == 0) {
			noDataFound();
		}
	}

	private void configureTableLayout() {
		scrollPane.setBounds(10, scrollPane.getY(), 1062, 40 + (totalstudent * 40));
		this.setSize(1116, 544 + (totalstudent * 40));

		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(250);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		table.getColumnModel().getColumn(3).setMaxWidth(250);
		table.getColumnModel().getColumn(4).setMaxWidth(250);
		table.setSelectionBackground(new Color(240, 255, 255));
		table.setSelectionForeground(Color.black);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		submitbutton.setLocation(submitbutton.getX(), scrollPane.getY() + scrollPane.getHeight() + 40);
	}

	private void configureMarksEditableColumn() {
		JTextField textField = new JTextField();
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textField.setBorder(new LineBorder(Color.BLACK));
		textField.setEnabled(true);
		textField.setEditable(true);

		DefaultCellEditor dce = new DefaultCellEditor(textField);
		table.getColumnModel().getColumn(4).setCellEditor(dce);
	}

	public void noDataFound() {
		scrollPane.setVisible(false);
		submitbutton.setVisible(false);
		nodatafoundlabel.setVisible(true);
		nodatafoundlabel.setLocation(nodatafoundlabel.getX(), scrollPane.getY() - 100);
	}

	public DefaultTableModel createTheoryMarksModel() {

		String courcecode = new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + "");
		int sem = semoryearcombo.getSelectedIndex();
		String subjectname = subjectnamecombo.getSelectedItem() + "";
		String[] columnname = { "Roll Number", "Student Name", "Subject Name", "Max Theory Marks", "Theory Marks" };

		DefaultTableModel model = new DefaultTableModel(columnname, 0) {

			boolean[] canEdit = new boolean[] { false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		ArrayList<Marks> listdata = new StudentData().getStudentTheoryMarksDetails(courcecode, sem, subjectname);

		for (Marks m : listdata) {
			Object[] data = { m.getRollNumber(), m.getStudentName(), m.getSubjectName(), m.getMaxTheoryMarks(),
					m.getTheoryMarks() };
			model.addRow(data);
		}
		submitbutton.setVisible(true);
		table.setEnabled(true);

		return model;
	}

	public DefaultTableModel createPracticalMarksModel() {
		String courcecode = new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + "");
		int sem = semoryearcombo.getSelectedIndex();
		String subjectname = subjectnamecombo.getSelectedItem() + "";
		String[] columnname = { "Roll Number", "Student Name", "Subject Name", "Max Practical Marks",
				"Practical Marks" };

		DefaultTableModel model = new DefaultTableModel(columnname, 0) {

			boolean[] canEdit = new boolean[] { false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		ArrayList<Marks> listdata = new StudentData().getStudentPracticalMarksDetails(courcecode, sem, subjectname);

		for (Marks m : listdata) {
			Object[] data = { m.getRollNumber(), m.getStudentName(), m.getSubjectName(),
					m.getMaxPracticalMarks(), m.getPracticalMarks() };
			model.addRow(data);
		}
		table.setEnabled(true);
		submitbutton.setVisible(true);

		return model;
	}

	/* -------------------- ADD MARKS (DUPLICATION REMOVED) -------------------- */

	private void addMarks(boolean theory) {
		int result = 0;
		int i = 0;

		for (i = 0; i < table.getRowCount(); i++) {
			Marks m = buildMarksFromRow(i, theory);
			if (m == null) {
				// invalid number format
				TableError("Must be a Number !");
				break;
			}

			if (!validateMarksRange(m, theory)) {
				break;
			}

			result = saveMarksForRow(m, theory);
		}

		handleMarksSubmissionResult(result, i, theory);
	}

	private Marks buildMarksFromRow(int rowIndex, boolean theory) {
		Marks m = new Marks();
		m.setCourceCode(new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + ""));
		m.setSemorYear(semoryearcombo.getSelectedIndex());

		if (theory) {
			m.setSubjectName(subjectnamecombo.getSelectedItem() + "");
		} else {
			m.setSubjectName(table.getValueAt(rowIndex, 2) + "");
		}

		m.setSubjectCode(new SubjectData().getSubjectCode(m.getCourceCode(), m.getSemorYear(), m.getSubjectName()));
		m.setRollNumber(Long.parseLong("" + table.getValueAt(rowIndex, 0)));

		try {
			if (theory) {
				m.setMaxTheoryMarks(Integer.parseInt(table.getValueAt(rowIndex, 3) + ""));
				m.setTheoryMarks(Integer.parseInt(table.getValueAt(rowIndex, 4) + ""));
			} else {
				m.setMaxPracticalMarks(Integer.parseInt(table.getValueAt(rowIndex, 3) + ""));
				m.setPracticalMarks(Integer.parseInt(table.getValueAt(rowIndex, 4) + ""));
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return m;
	}

	private boolean validateMarksRange(Marks m, boolean theory) {
		int marks = theory ? m.getTheoryMarks() : m.getPracticalMarks();
		int max = theory ? m.getMaxTheoryMarks() : m.getMaxPracticalMarks();

		if (marks < 0) {
			TableError("Marks must be positive  !");
			return false;
		} else if (marks > max) {
			TableError("Marks must be less than or equal to Maximum Marks !");
			return false;
		}
		return true;
	}

	private int saveMarksForRow(Marks m, boolean theory) {
		if (theory) {
			return new StudentData().addStudentTheoryMarks(m);
		} else {
			return new StudentData().addStudentPracticalMarks(m);
		}
	}

	private void handleMarksSubmissionResult(int result, int lastIndex, boolean theory) {
		boolean allRowsProcessed = (lastIndex == table.getRowCount());
		String successMessage = theory ? "Theory Marks Succesfully submitted"
				: "Practical Marks Succesfully submitted";

		if (result > 0 && allRowsProcessed) {
			TableErrorlabel.setForeground(new Color(34, 139, 34));
			TableError(successMessage);
			timer.restart();
			createtablemodel();
			return;
		}

		if (!allRowsProcessed) {
			TableErrorlabel.setForeground(Color.red);
			timer.restart();
			markErrorRow(lastIndex);
		}
	}

	private void markErrorRow(int rowIndex) {
		table.setSelectionBackground(Color.red);
		table.setSelectionForeground(Color.white);
		table.addRowSelectionInterval(rowIndex, rowIndex);
	}

	/* -------------------- BUTTON STYLE HELPERS -------------------- */

	public void ActiveButton(JButton button) {
		button.setBorder(new LineBorder(new Color(255, 255, 255)));
		button.setForeground(new Color(0, 139, 139));
		button.setBackground(new Color(255, 255, 255));
		button.setName("Active");
	}

	public void DeactiveButton(JButton button) {
		button.setBorder(new LineBorder(new Color(255, 255, 255)));
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(32, 178, 170));
		button.setName("Deactive");
	}

	public void TableError(String error) {
		TableErrorlabel.setVisible(true);
		TableErrorlabel.setText(error);
		TableErrorlabel.setLocation(10, scrollPane.getY() + scrollPane.getHeight() + 10);
	}
}

@SuppressWarnings("serial")
class ForcedListSelectionModel extends DefaultListSelectionModel {

	public ForcedListSelectionModel() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public void clearSelection() {
	}

	@Override
	public void removeSelectionInterval(int index0, int index1) {
	}

}