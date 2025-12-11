package collegeapplication.student;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import collegeapplication.admin.AdminMain;
import collegeapplication.cource.Cource;
import collegeapplication.cource.CourceData;
import collegeapplication.faculty.FacultyMain;
import collegeapplication.subject.SubjectData;

/*
 * Title : MarkSheetReportPanel.java
 * Created by : Ajaysinh Rathod
 * Purpose : To display all students marks in class/subject/student wice
 * Mail : ajaysinhrathod1290@gmail.com
 */

@SuppressWarnings("serial")
public class MarkSheetReportPanel extends JPanel implements ActionListener {

	private JComboBox<String> courcenamecombo;
	private JComboBox<String> semoryearcombo;
	private JComboBox<String> subjectorrollcombo;
	private JTable table;
	private JScrollPane scrollPane;
	private int totalstudent = 0;
	private JLabel Errorlabel;
	private JButton studentwicebutton;
	private JButton classwicebutton;
	private JButton subjectwicebutton;
	private JButton fetchdetailsbutton;
	private JLabel label3;
	private JLabel label1;
	private JLabel label2;
	private JButton declareresultbutton;
	private JButton submitbutton;
	private JLabel nodatafoundlabel;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1096, this.getHeight());
	}

	private MarkSheetReportPanel() {

		setBorder(null);
		setBackground(new Color(255, 255, 255));
		this.setSize(1116, 544);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(10, 0, 1077, 183);
		add(panel);
		panel.setLayout(null);
		JLabel headingLabel = new JLabel("Marksheet Report");
		headingLabel.setIcon(null);
		headingLabel.setBounds(10, 65, 272, 44);
		panel.add(headingLabel);
		headingLabel.setBackground(new Color(32, 178, 170));
		headingLabel.setHorizontalAlignment(SwingConstants.LEFT);
		headingLabel.setForeground(Color.WHITE);
		headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
		headingLabel.setOpaque(true);

		declareresultbutton = new JButton("Declare Result");
		declareresultbutton.setForeground(new Color(0, 139, 139));
		declareresultbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		declareresultbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		declareresultbutton.setBackground(new Color(255, 255, 255));
		declareresultbutton.setBounds(417, 139, 146, 33);
		declareresultbutton.setFocusable(false);
		this.disableButton(declareresultbutton);
		declareresultbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				activeButton(declareresultbutton);
				disableButton(classwicebutton);
				disableButton(subjectwicebutton);
				disableButton(studentwicebutton);
				semoryearcombo.setVisible(false);
				subjectorrollcombo.setVisible(false);
				label2.setVisible(false);
				label3.setVisible(false);
				scrollPane.setVisible(false);
				fetchdetailsbutton.setLocation(fetchdetailsbutton.getX(), semoryearcombo.getY());
				scrollPane.setLocation(scrollPane.getX(), subjectorrollcombo.getY());
				resetFiltersForModeChange();
			}

		});
		panel.add(declareresultbutton);

		subjectwicebutton = new JButton("Subject Wice");
		subjectwicebutton.setForeground(new Color(0, 139, 139));
		subjectwicebutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		subjectwicebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		subjectwicebutton.setBackground(new Color(255, 255, 255));
		subjectwicebutton.setBounds(577, 139, 146, 33);
		panel.add(subjectwicebutton);

		studentwicebutton = new JButton("Student Wice");
		studentwicebutton.setForeground(new Color(0, 139, 139));
		studentwicebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		studentwicebutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		studentwicebutton.setBackground(Color.WHITE);
		studentwicebutton.setBounds(742, 139, 146, 33);
		panel.add(studentwicebutton);

		classwicebutton = new JButton("Class Wice");
		classwicebutton.setForeground(new Color(0, 139, 139));
		classwicebutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		classwicebutton.setBackground(Color.WHITE);
		classwicebutton.setBounds(907, 139, 146, 33);
		classwicebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(classwicebutton);

		label1 = new JLabel("Cource Name   :");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setForeground(Color.DARK_GRAY);
		label1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label1.setBounds(29, 213, 163, 37);
		add(label1);

		label2 = new JLabel("Semester or Years   :");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setForeground(Color.DARK_GRAY);
		label2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label2.setBounds(23, 270, 169, 40);
		add(label2);

		label3 = new JLabel("Select Subject  :");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setForeground(Color.DARK_GRAY);
		label3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label3.setBounds(29, 339, 163, 32);
		add(label3);

		courcenamecombo = new JComboBox<String>(new CourceData().getCourceName());
		courcenamecombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		courcenamecombo.addActionListener(this);
		courcenamecombo.setFocusable(false);
		courcenamecombo.setBackground(new Color(255, 255, 255));
		courcenamecombo.setBounds(204, 211, 872, 40);
		add(courcenamecombo);

		semoryearcombo = new JComboBox<String>();
		semoryearcombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		semoryearcombo.setBackground(Color.WHITE);
		semoryearcombo.setBounds(204, 270, 872, 40);
		semoryearcombo.addActionListener(this);
		semoryearcombo.setFocusable(false);
		add(semoryearcombo);

		subjectorrollcombo = new JComboBox<String>();
		subjectorrollcombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		subjectorrollcombo.setFocusable(false);
		subjectorrollcombo.addActionListener(this);
		subjectorrollcombo.setBackground(Color.WHITE);
		subjectorrollcombo.setBounds(204, 335, 872, 40);
		add(subjectorrollcombo);

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(21, 460, 1055, 84);
		scrollPane.getVerticalScrollBar().setUnitIncrement(80);
		for (Component c : scrollPane.getComponents()) {
			c.setBackground(Color.white);
		}
		add(scrollPane);
		scrollPane.setVisible(false);
		table = new JTable();
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));
		table.setForeground(new Color(0, 0, 0));
		table.setRowHeight(40);
		table.getTableHeader().setBackground(new Color(32, 178, 170));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(new Dimension(50, 40));
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setDragEnabled(false);
		table.setFocusable(false);
		table.setGridColor(Color.LIGHT_GRAY);
		table.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(table);
		Errorlabel = new JLabel("This is required question  !");
		Errorlabel.setVisible(false);
		Errorlabel.setForeground(Color.RED);
		Errorlabel.setFont(new Font("Arial", Font.PLAIN, 14));
		Errorlabel.setBounds(233, 45, 225, 17);
		add(Errorlabel);
		activeButton(subjectwicebutton);
		disableButton(studentwicebutton);
		disableButton(classwicebutton);
		disableButton(declareresultbutton);

		fetchdetailsbutton = new JButton("Fetch Details");
		fetchdetailsbutton.setForeground(Color.WHITE);
		fetchdetailsbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		fetchdetailsbutton.setFocusPainted(false);
		fetchdetailsbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fetchdetailsbutton.addActionListener(this);
		fetchdetailsbutton.setBorder(new LineBorder(new Color(255, 255, 255)));
		fetchdetailsbutton.setBackground(new Color(32, 178, 170));
		fetchdetailsbutton.setBounds(926, 399, 151, 37);
		add(fetchdetailsbutton);

		submitbutton = new JButton("Declare Result");
		submitbutton.setForeground(Color.WHITE);
		submitbutton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		submitbutton.setVisible(false);
		submitbutton.setFocusPainted(false);
		submitbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		submitbutton.addActionListener(new DeclareResult());
		submitbutton.setBorder(new LineBorder(new Color(255, 255, 255)));
		submitbutton.setBackground(new Color(32, 178, 170));
		submitbutton.setBounds(926, 399, 151, 37);
		add(submitbutton);

		nodatafoundlabel = new JLabel("");
		nodatafoundlabel.setHorizontalAlignment(SwingConstants.CENTER);
		try {
			Image image = ImageIO.read(new File("./assets/notfound2.png"));
			nodatafoundlabel.setIcon(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

		} catch (IOException e) {
			e.printStackTrace();
		}
		nodatafoundlabel.setText("No Data Found...!");
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

	// ==== Extracted helper: reset filter combos & hide table when mode changes ====
	private void resetFiltersForModeChange() {
		subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		courcenamecombo.setSelectedIndex(0);
		semoryearcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		scrollPane.setVisible(false);
	}

	// ==== Extracted helper: position Fetch & Scroll relative to subject/roll combo
	// (Admin layout) ====
	private void positionFetchAndScrollRelativeToSubject(int fetchDelta) {
		fetchdetailsbutton.setLocation(fetchdetailsbutton.getX(), subjectorrollcombo.getY() + fetchDelta);
		scrollPane.setLocation(scrollPane.getX(), fetchdetailsbutton.getY() + 60);
	}

	// ==== Extracted helper: position Fetch & Scroll for Faculty layout ====
	private void positionFetchAndScrollForFaculty() {
		fetchdetailsbutton.setLocation(fetchdetailsbutton.getX(), semoryearcombo.getY());
		scrollPane.setLocation(scrollPane.getX(), fetchdetailsbutton.getY() + 50);
	}

	// ==== Extracted helper: configure Admin "subject-wise" or "student-wise" mode
	// ====
	private void configureAdminModeWithSubjectOrRoll(JButton active, String labelText) {
		activeButton(active);
		disableButton(studentwicebutton);
		disableButton(subjectwicebutton);
		disableButton(classwicebutton);
		disableButton(declareresultbutton);

		label2.setVisible(true);
		courcenamecombo.setVisible(true);
		semoryearcombo.setVisible(true);
		subjectorrollcombo.setVisible(true);
		label3.setVisible(true);
		label3.setText(labelText);

		positionFetchAndScrollRelativeToSubject(65);
		resetFiltersForModeChange();
	}

	// ==== Extracted helper: configure Admin "class-wise" mode ====
	private void configureAdminClassMode() {
		subjectwicebutton.setName("Active");
		courcenamecombo.setVisible(true);
		semoryearcombo.setVisible(true);
		activeButton(classwicebutton);
		disableButton(studentwicebutton);
		disableButton(subjectwicebutton);
		disableButton(declareresultbutton);
		label2.setVisible(true);
		subjectorrollcombo.setVisible(false);
		label3.setVisible(false);
		positionFetchAndScrollRelativeToSubject(0);
		resetFiltersForModeChange();
	}

	// ==== Extracted helper: configure Faculty "subject-wise" or "student-wise" mode
	// ====
	private void configureFacultySubjectOrStudentMode(JButton active, JButton other1, JButton other2,
													  String labelText) {
		activeButton(active);
		disableButton(other1);
		disableButton(other2);
		disableButton(declareresultbutton);
		label3.setVisible(true);
		subjectorrollcombo.setVisible(true);
		label3.setText(labelText);
		fetchdetailsbutton.setVisible(true);
		subjectorrollcombo.setLocation(courcenamecombo.getLocation());
		positionFetchAndScrollForFaculty();
		scrollPane.setVisible(false);
	}

	// ==== Extracted helper: configure Faculty "class-wise" mode ====
	private void configureFacultyClassMode() {
		subjectwicebutton.setName("Active");
		activeButton(classwicebutton);
		disableButton(studentwicebutton);
		disableButton(subjectwicebutton);
		disableButton(declareresultbutton);
		subjectorrollcombo.setVisible(false);
		label3.setVisible(false);
		scrollPane.setLocation(scrollPane.getX(), courcenamecombo.getY());
		fetchdetailsbutton.setVisible(false);
	}

	// ==== Extracted helper: build Student from current selected table row ====
	private Student getSelectedStudentFromTable() {
		int row = table.getSelectedRow();
		if (row < 0) {
			return null;
		}
		String strsem = table.getValueAt(row, 2) + "";
		int dashIndex = strsem.indexOf('-');
		if (dashIndex <= 0) {
			return null;
		}
		int sem = Integer.parseInt(strsem.substring(dashIndex + 1));
		String courcecode = strsem.substring(0, dashIndex);
		String strroll = table.getValueAt(row, 0) + "";
		long rollnumber = Long.parseLong(strroll);
		return new StudentData().getStudentDetails(courcecode, sem, rollnumber);
	}

	// ==== NEW helpers: common marks-report logic to reduce duplication ====

	/** Choose the correct marks list based on the active mode (student/subject/class). */
	private ArrayList<Marks> getMarksReportList(Marks m) {
		if (studentwicebutton.getName().equals("Active")) {
			return new StudentData().getMarkssheetOfStudent(m.getCourceCode(), m.getSemorYear(), m.getRollNumber());
		} else if (subjectwicebutton.getName().equals("Active")) {
			return new StudentData().getMarksheetReportBySubject(m);
		} else if (classwicebutton.getName().equals("Active")) {
			return new StudentData().getMarksheetReportByClass(m);
		}
		return new ArrayList<>();
	}

	/** Add a single marks row into the model (centralized row logic). */
	private void addMarksRow(DefaultTableModel model, Marks context, Marks rowData, int rowIndex) {
		model.addRow(new Object[0]);
		model.setValueAt(rowData.getRollNumber(), rowIndex, 0);
		model.setValueAt(rowData.getStudentName(), rowIndex, 1);
		model.setValueAt(context.getCourceCode() + "-" + context.getSemorYear(), rowIndex, 2);
		model.setValueAt(rowData.getSubjectName(), rowIndex, 3);
		model.setValueAt(rowData.getMaxPracticalMarks() + rowData.getMaxTheoryMarks(), rowIndex, 4);
		model.setValueAt(rowData.getPracticalMarks() + rowData.getTheoryMarks(), rowIndex, 5);
	}

	/** Center specific columns and apply a common renderer. */
	private void configureCenteredColumns(int... columns) {
		DefaultTableCellRenderer cellrenderer = new DefaultTableCellRenderer();
		cellrenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int col : columns) {
			table.getColumnModel().getColumn(col).setCellRenderer(cellrenderer);
		}
	}

	/** Common table selection & resize configuration (used in both modes). */
	private void configureCommonTableSelectionAndResize() {
		table.setSelectionBackground(new Color(240, 255, 255));
		table.setSelectionForeground(Color.black);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	public MarkSheetReportPanel(AdminMain am) {
		this();
		table.addMouseListener(new MouseAdapterForTable(am));

		subjectwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureAdminModeWithSubjectOrRoll(subjectwicebutton, "Select Subject :");
			}

		});
		studentwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureAdminModeWithSubjectOrRoll(studentwicebutton, "Select Roll Number :");
			}

		});
		classwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureAdminClassMode();
			}

		});

	}

	public MarkSheetReportPanel(FacultyMain fm) {

		this();
		declareresultbutton.setVisible(false);
		courcenamecombo.setSelectedItem(new CourceData().getcourcename(fm.f.getCourceCode()));
		semoryearcombo.setModel(
				new DefaultComboBoxModel<String>(new CourceData().getSemorYear(courcenamecombo.getSelectedItem() + "")));
		String[] totalsub = new SubjectData().getSubjectinCource(fm.f.getCourceCode(), fm.f.getSemorYear());
		subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(totalsub));
		semoryearcombo.setSelectedIndex(fm.f.getSemorYear());
		subjectorrollcombo.setSelectedItem(fm.f.getSubject());
		courcenamecombo.setVisible(false);
		semoryearcombo.setVisible(false);
		label3.setLocation(label1.getLocation());
		label1.setVisible(false);
		label2.setVisible(false);
		subjectorrollcombo.setLocation(courcenamecombo.getLocation());
		this.fetchdetailsbutton.setLocation(fetchdetailsbutton.getX(), semoryearcombo.getY());
		scrollPane.setLocation(scrollPane.getX(), fetchdetailsbutton.getY() + 50);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1) {

					Student s = getSelectedStudentFromTable();
					if (s != null) {
						fm.viewstudentpanel = new ViewStudentPanel(s, fm, fm.marksheetreportpanelscroll);
						fm.viewstudentpanel.setVisible(true);
						fm.marksheetreportpanelscroll.setVisible(false);
						fm.viewstudentpanel.setLocation(fm.panelx, 0);
						fm.viewstudentpanel.setVisible(true);
						fm.viewstudentpanel.setFocusable(true);
						fm.contentPane.add(fm.viewstudentpanel);
					}
				}

			}
		});
		studentwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureFacultySubjectOrStudentMode(studentwicebutton, subjectwicebutton, classwicebutton,
						"Select Roll Number :");
				subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(
						new StudentData().getRollNumber(fm.f.getCourceCode(), semoryearcombo.getSelectedIndex())));
			}

		});
		subjectwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureFacultySubjectOrStudentMode(subjectwicebutton, studentwicebutton, classwicebutton,
						"Select Subject :");
				String[] totalsub = new SubjectData().getSubjectinCource(fm.f.getCourceCode(), fm.f.getSemorYear());
				subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(totalsub));
			}

		});
		classwicebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				configureFacultyClassMode();
				createtablemodel();
			}

		});

	}

	public void activeButton(JButton button) {
		if (submitbutton != null) {
			submitbutton.setVisible(false);
		}
		button.setBorder(new LineBorder(new Color(255, 255, 255)));
		button.setForeground(new Color(0, 139, 139));
		button.setBackground(new Color(255, 255, 255));
		button.setFocusPainted(false);
		button.setName("Active");
	}

	public void disableButton(JButton button) {

		button.setBorder(new LineBorder(new Color(255, 255, 255)));
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(32, 178, 170));
		button.setFocusPainted(false);
		button.setName("Deactive");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Errorlabel.setVisible(false);
		if (e.getSource() == courcenamecombo) {
			courcenamecombo.setFocusable(false);

			subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
			if (courcenamecombo.getSelectedIndex() == 0) {
				semoryearcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));

			} else {
				String cource = (String) courcenamecombo.getSelectedItem();
				semoryearcombo.setModel(new DefaultComboBoxModel<String>(new CourceData().getSemorYear(cource)));
			}

		}
		if (e.getSource() == semoryearcombo && semoryearcombo.getSelectedIndex() > 0) {
			String cource = (String) courcenamecombo.getSelectedItem();

			if (subjectwicebutton.getName().equals("Active")) {
				String[] totalsub = new SubjectData()
						.getSubjectinCource(new CourceData().getCourcecode(cource), semoryearcombo.getSelectedIndex());
				if (totalsub != null) {
					subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(totalsub));
				} else {
					subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "No Subject Found" }));
				}
			} else if (studentwicebutton.getName().equals("Active")) {
				subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(new StudentData().getRollNumber(
						new CourceData().getCourcecode(cource), semoryearcombo.getSelectedIndex())));
			}
		} else if (e.getSource() == semoryearcombo) {
			subjectorrollcombo.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
		}
		if (e.getSource() == fetchdetailsbutton) {

			if (declareresultbutton.getName().equals("Active")) {

				if (courcenamecombo.getSelectedIndex() == 0) {
					showerror(courcenamecombo);
				} else {
					createTableForDeclareResult(courcenamecombo.getSelectedItem() + "");
				}
			} else {
				if (courcenamecombo.getSelectedIndex() == 0) {
					showerror(courcenamecombo);
				} else if (semoryearcombo.getSelectedIndex() == 0) {
					showerror(semoryearcombo);
				} else if (subjectorrollcombo.isVisible()
						&& subjectorrollcombo.getSelectedItem().equals("No Subject Found")) {
					Component tf = subjectorrollcombo;
					Errorlabel.setVisible(true);
					Errorlabel.setText("No Subject Found !");
					Errorlabel.setBounds(tf.getX(), tf.getY() + tf.getHeight() - 5, 400, 26);
				} else if (subjectorrollcombo.isVisible() && subjectorrollcombo.getSelectedIndex() == 0) {
					showerror(subjectorrollcombo);
				} else {
					this.createtablemodel();

				}
			}
		}
	}

	public void showerror(JComponent tf) {
		Errorlabel.setVisible(true);
		Errorlabel.setText("This is required question !");
		Errorlabel.setBounds(tf.getX(), tf.getY() + tf.getHeight() - 5, 400, 26);
	}

	public void createTableForDeclareResult(String cource) {
		submitbutton.setVisible(true);
		String columnname[] = { "Cource", "Sem", "Cource Name", "" };
		DefaultTableModel model = new DefaultTableModel(columnname, 0) {
			boolean isEdit[] = { false, false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEdit[column];
			}

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return Boolean.class;
					default:
						return String.class;
				}
			}
		};
		ArrayList<Cource> list = new CourceData().getCourcesForDeclareResult(cource);
		for (int i = 0; i < list.size(); i++) {
			model.addRow(new Object[0]);
			Cource c = list.get(i);
			model.setValueAt(c.getCourceCode(), i, 0);
			model.setValueAt(c.getSemorYear(), i, 1);
			model.setValueAt(c.getCourceName(), i, 2);
			model.setValueAt(c.getIsDeclared(), i, 3);

		}
		table.setModel(model);
		totalstudent = list.size();
		scrollPane.setSize(scrollPane.getWidth(), 40 + (totalstudent * 40));
		submitbutton.setLocation(submitbutton.getX(), scrollPane.getY() + scrollPane.getHeight() + 20);
		this.setSize(1116, scrollPane.getY() + 80 + totalstudent * 40 + 40);

		table.getColumnModel().getColumn(3).setHeaderRenderer(
				new HeaderRendererForCheckBox(table.getTableHeader(), 3));

		// Use new helpers instead of inline duplication:
		configureCenteredColumns(0, 1, 2);
		configureCommonTableSelectionAndResize();

		scrollPane.setVisible(true);
		if (list.size() == 0) {
			noDataFound();
		}
	}

	public void setIcons(JTable table, int column, Icon icon, Icon selectedIcon) {
		JCheckBox cellRenderer = (JCheckBox) table.getCellRenderer(0, column);
		cellRenderer.setSelectedIcon(selectedIcon);
		cellRenderer.setIcon(icon);

		DefaultCellEditor cellEditor = (DefaultCellEditor) table.getCellEditor(0, column);
		JCheckBox editorComponent = (JCheckBox) cellEditor.getComponent();
		editorComponent.setSelectedIcon(selectedIcon);
		editorComponent.setIcon(icon);
	}

	public void createtablemodel() {
		nodatafoundlabel.setVisible(false);
		if (courcenamecombo.getSelectedIndex() == 0
				|| (semoryearcombo.isVisible() && semoryearcombo.getSelectedIndex() == 0)
				|| (subjectorrollcombo.isVisible() && subjectorrollcombo.getSelectedIndex() == 0)) {
			scrollPane.setVisible(false);
		} else {
			Marks m = new Marks();
			m.setCourceCode(new CourceData().getCourcecode(courcenamecombo.getSelectedItem() + ""));
			m.setSemorYear(semoryearcombo.getSelectedIndex());
			if (subjectwicebutton.getName().equals("Active")) {
				m.setSubjectName(subjectorrollcombo.getSelectedItem() + "");
				m.setSubjectCode(
						new SubjectData().getSubjectCode(m.getCourceCode(), m.getSemorYear(), m.getSubjectName()));
			} else if (classwicebutton.getName().equals("Active")) {
				m.setSubjectName("All");
			} else if (studentwicebutton.getName().equals("Active")) {
				m.setRollNumber(Long.parseLong(subjectorrollcombo.getSelectedItem() + ""));
			}
			table.setModel(createModel(m));
			scrollPane.setSize(scrollPane.getWidth(), 40 + (totalstudent * 40));
			this.setSize(1116, scrollPane.getY() + scrollPane.getHeight() + 40);

			table.getColumnModel().getColumn(0).setMaxWidth(200);
			table.getColumnModel().getColumn(1).setMaxWidth(250);
			table.getColumnModel().getColumn(2).setMaxWidth(200);
			table.getColumnModel().getColumn(3).setMaxWidth(250);
			table.getColumnModel().getColumn(4).setMaxWidth(230);
			table.getColumnModel().getColumn(5).setMaxWidth(200);

			// Use shared helpers for renderer + selection:
			configureCenteredColumns(0, 2, 3, 4, 5);
			configureCommonTableSelectionAndResize();

			scrollPane.setVisible(true);
			if (totalstudent == 0) {
				noDataFound();
			}
		}

	}

	public DefaultTableModel createModel(Marks m) {
		String Column[] = { "Roll Number", "Student Name", "Class", "Subject", "Total Marks", "Obtained Marks" };

		DefaultTableModel model = new DefaultTableModel(Column, 0) {
			boolean isEdit[] = { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return isEdit[column];
			}
		};

		ArrayList<Marks> list = getMarksReportList(m);  // <— helper replaces duplicated if/else
		for (int i = 0; i < list.size(); i++) {
			addMarksRow(model, m, list.get(i), i);      // <— helper replaces per-row assignments
		}
		totalstudent = list.size();
		table.setEnabled(true);
		return model;

	}

	public void noDataFound() {
		scrollPane.setVisible(false);
		nodatafoundlabel.setVisible(true);
		nodatafoundlabel.setLocation(nodatafoundlabel.getX(), scrollPane.getY() - 100);

	}

	class MouseAdapterForTable extends MouseAdapter {
		AdminMain am = null;

		public MouseAdapterForTable(AdminMain am) {
			this.am = am;
		}

		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1
					&& !declareresultbutton.getName().equals("Active")) {

				Student s = getSelectedStudentFromTable();
				if (s != null) {
					am.viewstudentpanel = new ViewStudentPanel(s, am, am.marksheetreportpanelscroll);
					am.viewstudentpanel.setVisible(true);
					am.marksheetreportpanelscroll.setVisible(false);
					am.viewstudentpanel.setLocation(am.panelx, 0);
					am.viewstudentpanel.setVisible(true);
					am.viewstudentpanel.setFocusable(true);
					am.contentPane.add(am.viewstudentpanel);
				}
			}

		}

	}

	private class DeclareResult implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < table.getRowCount(); i++) {
				Cource cource = new Cource();
				cource.setCourceCode(table.getValueAt(i, 0) + "");
				cource.setSemorYear(Integer.parseInt(table.getValueAt(i, 1) + ""));
				cource.setIsDeclared(Boolean.parseBoolean(table.getValueAt(i, 3) + ""));
				new CourceData().declareResult(cource);
			}
			JOptionPane.showMessageDialog(null, "Result Declared");
			scrollPane.setVisible(false);
			submitbutton.setVisible(false);
			setSize(getWidth(), 600);
		}

	}
}
