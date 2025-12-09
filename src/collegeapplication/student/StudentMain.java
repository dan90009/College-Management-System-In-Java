package collegeapplication.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import collegeapplication.admin.AdminProfilePanel;
import collegeapplication.chat.ChatData;
import collegeapplication.chat.ChatMainPanel;
import collegeapplication.common.DataBaseConnection;
import collegeapplication.common.HomePanel;
import collegeapplication.common.NotificationData;
import collegeapplication.common.NotificationPanel;
import collegeapplication.common.SearchPanel;
import collegeapplication.common.TimeUtil;
import collegeapplication.faculty.FacultyPanel;
import collegeapplication.faculty.ViewFacultyPanel;
import collegeapplication.login.LoginPageFrame;
import collegeapplication.subject.AssignSubjectPanel;
import collegeapplication.subject.SubjectPanel;

/*
 * Duplicated blocks = 15
 * Title : StudentMain.java
 * Created by : Ajaysinh Rathod
 * Purpose : Student Main Frame
 * Mail : ajaysinhrathod1290@gmail.com
 */

@SuppressWarnings("serial")
public class StudentMain extends JFrame implements ActionListener {

	public JPanel contentPane;
	private JLabel studentnamelabel;
	private JLabel studentprofilepiclabel;
	private JPanel profilepanel;
	private JButton homebutton;
	private JButton studentsbutton;
	private JButton subjectbutton;
	private JButton faculitiesbutton;
	private JButton marksheetbutton;
	private JButton attandancereportbutton;
	private JButton searchbutton;
	private JButton notificationbutton;
	private JButton contactusbutton;
	private JButton logoutbutton;
	private JButton exitbutton;
	private Color buttonbcolor = Color.DARK_GRAY;
	private Color buttonfcolor = Color.LIGHT_GRAY;
	private Font buttonfont = new Font("Tw Cen MT", Font.PLAIN, 20);
	public SubjectPanel subjectpanel;
	public HomePanel homepanel;
	public StudentPanel studentpanel;
	public ViewStudentPanel viewstudentpanel;
	public MarkSheetPanel marksheetpanel;
	public JScrollPane marksheetpanelscroll;
	public ViewFacultyPanel viewfacultypanel;
	public AssignSubjectPanel assignsubjectpanel;
	public EnterMarksPanel entermarkspanel;
	public JScrollPane entermarkspanelscroll;
	private JScrollPane markattandancepanelscroll;
	public AttandanceReportPanel attandancereportpanel;
	public JScrollPane attandancereportpanelscroll;
	public FacultyPanel facultypanel;
	public AdminProfilePanel adminprofilepanel;
	public SearchPanel searchpanel;
	private ChatMainPanel chatmainpanel;
	public NotificationPanel notificationpanel;
	public int panely = 0, panelx = 250;
	private JButton btn;
	private JButton myprofilebutton;
	private String lastlogin;
	public Student s;
	private int row = 63;
	private JButton assignedsubjectbutton;
	private JButton chatbutton;
	public Socket socket;
	private Timer timer;
	private BufferedImage messagecount;
	private JLabel totalnewnotification;
	private JLabel totalnewchatmessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					if (DataBaseConnection.checkconnection()) {

						Student s = new StudentData().getStudentDetails("CE", 1, 1001);
						StudentMain frame = new StudentMain(s);
						frame.setVisible(true);
					} else {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						JOptionPane.showMessageDialog(null, "You Are Not Connected To DataBase", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentMain(Student s) {

		ActionListener setActive = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = new StudentData().setActiveStatus(s.getActiveStatus(), s.getUserId());
				if (result == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "Your account is deleted by Admin", "Account deleted",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				} else {
					int notification = new NotificationData().getUnreadNotification(s.getUserId(), "Student",
							s.getCourceCode(), s.getSemorYear(), s.getAdmissionDate());
					updateBadgeLabel(totalnewnotification, notification, 24, false);

					int chat = new ChatData().getUndreadMessageCountStudent(s);
					updateBadgeLabel(totalnewchatmessage, chat, 26, true);
				}
			}

		};
		try {
			messagecount = ImageIO.read(new File("./assets/messagecount.png"));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		timer = new Timer(1000, setActive);
		timer.start();
		this.s = s;
		Color bgColor = new Color(32, 178, 170);
		Color frColor = Color.white;
		UIManager.put("ComboBoxUI", "com.sun.java.swing.plaf.windows.WindowsComboBoxUI");

		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(bgColor));
		UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
		UIManager.put("ComboBox.foreground", new ColorUIResource(Color.DARK_GRAY));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(frColor));
		UIManager.put("ScrollBarUI", "com.sun.java.swing.plaf.windows.WindowsScrollBarUI");

		this.setResizable(false);
		setTitle("Collage Data Management");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.setBounds(-2, 0, 1370, 733);
		createpanel();
		JPanel sidebarpanel = new JPanel();
		sidebarpanel.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(64, 64, 64)));
		sidebarpanel.setBackground(Color.DARK_GRAY);
		sidebarpanel.setBounds(5, 11, 240, 706);
		contentPane.add(sidebarpanel);
		sidebarpanel.setLayout(null);

		profilepanel = new JPanel();
		profilepanel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.LIGHT_GRAY));
		profilepanel.setBackground(Color.DARK_GRAY);
		profilepanel.setBounds(0, 0, 240, 61);
		sidebarpanel.add(profilepanel);
		profilepanel.setLayout(null);

		studentnamelabel = new JLabel();
		studentnamelabel.setForeground(Color.WHITE);
		studentnamelabel.setHorizontalAlignment(SwingConstants.LEFT);
		studentnamelabel.setFont(new Font("Tw Cen MT", Font.BOLD, 21));
		studentnamelabel.setBackground(Color.DARK_GRAY);
		studentnamelabel.setOpaque(true);
		studentnamelabel.setBounds(65, 5, 171, 36);
		profilepanel.add(studentnamelabel);

		studentprofilepiclabel = new JLabel();
		studentprofilepiclabel.setBounds(5, 0, 50, 50);
		profilepanel.add(studentprofilepiclabel);
		studentprofilepiclabel.setHorizontalAlignment(SwingConstants.CENTER);
		studentprofilepiclabel.setBackground(Color.DARK_GRAY);

		studentprofilepiclabel.setBorder(new LineBorder(Color.black, 0));
		studentprofilepiclabel.setOpaque(true);

		homebutton = createButton("Home");
		sidebarpanel.add(homebutton);
		btn = homebutton;

		studentsbutton = createButton("Classmates", "Students");
		sidebarpanel.add(studentsbutton);

		subjectbutton = createButton("Subjects");
		sidebarpanel.add(subjectbutton);

		faculitiesbutton = createButton("Faculities");
		sidebarpanel.add(faculitiesbutton);

		assignedsubjectbutton = createButton("Assigned Subject", "Assign Subject");
		sidebarpanel.add(assignedsubjectbutton);

		marksheetbutton = createButton("Mark Sheet", "Marksheet Report");
		sidebarpanel.add(marksheetbutton);

		attandancereportbutton = createButton("Attandance Report");
		sidebarpanel.add(attandancereportbutton);

		chatbutton = createButton("Chat");
		chatbutton.setLayout(new BorderLayout());
		sidebarpanel.add(chatbutton);
		int chat = new ChatData().getUndreadMessageCountStudent(s);
		totalnewchatmessage = createBadgeLabel();
		chatbutton.add(totalnewchatmessage, BorderLayout.LINE_END);
		updateBadgeLabel(totalnewchatmessage, chat, 26, true);

		searchbutton = createButton("Search");
		sidebarpanel.add(searchbutton);

		notificationbutton = createButton("Notification");
		notificationbutton.setLayout(new BorderLayout());
		sidebarpanel.add(notificationbutton);

		int notification = new NotificationData().getUnreadNotification(s.getUserId(), "Student", s.getCourceCode(),
				s.getSemorYear(), s.getAdmissionDate());
		totalnewnotification = createBadgeLabel();
		notificationbutton.add(totalnewnotification, BorderLayout.LINE_END);
		updateBadgeLabel(totalnewnotification, notification, 26, false);

		myprofilebutton = createButton("My Profile", "Profile");
		sidebarpanel.add(myprofilebutton);

		contactusbutton = createButton("Contact Us");
		sidebarpanel.add(contactusbutton);

		logoutbutton = createButton("logout");
		sidebarpanel.add(logoutbutton);

		exitbutton = createButton("Exit");
		sidebarpanel.add(exitbutton);

		activeButton(homebutton);

		homepanel.setVisible(true);

		this.setCollageDetails();
		lastlogin = s.getLastLogin();
		homepanel.setLastLogin(lastlogin);

		s.setLastLogin(TimeUtil.getCurrentTime());
		s.setActiveStatus(true);
		new StudentData().updateStudentData(s, s);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent windowenent) {
				openPanel(exitbutton);
			}
		});

	}

	public void createpanel() {

		homepanel = new HomePanel(s);
		homepanel.setLocation(panelx, panely);
		homepanel.setVisible(true);
		homepanel.setFocusable(true);
		contentPane.add(homepanel);

	}

	public void activeButton(JButton button) {
		btn.setBackground(buttonbcolor);
		btn.setForeground(buttonfcolor);
		btn.setFont(buttonfont);
		btn.setDisabledIcon(new ImageIcon(""));
		btn.setIcon(new ImageIcon("./assets/" + btn.getName() + "dac.png"));
		btn = button;
		btn.setForeground(Color.white);
		btn.setFont(new Font("Tw Cen MT", Font.BOLD, 23));
		btn.setIcon(new ImageIcon("./assets/" + btn.getName() + "ac.png"));
		disablepanel();
	}

	public JButton createButton(String text, String name) {
		JButton button = createButton(text);
		button.setName(name);
		button.setIcon(new ImageIcon("./assets/" + button.getName() + "dac.png"));
		return button;
	}

	public JButton createButton(String text) {
		JButton button = new JButton();
		button.setForeground(buttonfcolor);
		button.setFont(buttonfont);
		button.setBackground(buttonbcolor);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFocusable(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new EmptyBorder(0, 0, 0, 0));
		button.setText(text);
		button.setName(text);
		button.setIcon(new ImageIcon("./assets/" + button.getName() + "dac.png"));
		button.addActionListener(this);
		button.setIconTextGap(10);
		button.setLocation(0, row);
		button.setSize(234, 40);
		row += 40;
		return button;
	}

	public void disablepanel() {
		if (homepanel != null && homepanel.isVisible()) {
			homepanel.setVisible(false);
		} else if (subjectpanel != null && subjectpanel.isVisible()) {
			subjectpanel.setVisible(false);
		} else if (studentpanel != null && studentpanel.isVisible()) {
			studentpanel.setVisible(false);
		} else if (viewstudentpanel != null && viewstudentpanel.isVisible()) {
			viewstudentpanel.setVisible(false);
		} else if (facultypanel != null && facultypanel.isVisible()) {
			facultypanel.setVisible(false);
		} else if (viewfacultypanel != null && viewfacultypanel.isVisible()) {
			viewfacultypanel.setVisible(false);
		} else if (assignsubjectpanel != null && assignsubjectpanel.isVisible()) {
			assignsubjectpanel.setVisible(false);
		} else if (entermarkspanelscroll != null && entermarkspanelscroll.isVisible()) {
			entermarkspanelscroll.setVisible(false);
		} else if (marksheetpanelscroll != null && marksheetpanelscroll.isVisible()) {
			marksheetpanelscroll.setVisible(false);
		} else if (markattandancepanelscroll != null && markattandancepanelscroll.isVisible()) {
			markattandancepanelscroll.setVisible(false);
		} else if (attandancereportpanelscroll != null && attandancereportpanelscroll.isVisible()) {
			attandancereportpanelscroll.setVisible(false);
		} else if (adminprofilepanel != null && adminprofilepanel.isVisible()) {
			adminprofilepanel.setVisible(false);
		} else if (searchpanel != null && searchpanel.isVisible()) {
			searchpanel.setVisible(false);
		} else if (notificationpanel != null && notificationpanel.isVisible()) {
			notificationpanel.setVisible(false);
		} else if (chatmainpanel != null && chatmainpanel.isVisible()) {
			try {
				if (chatmainpanel.chatpanel.subchatpanel != null
						&& chatmainpanel.chatpanel.subchatpanel.socket != null
						&& !chatmainpanel.chatpanel.subchatpanel.socket.isClosed()) {
					chatmainpanel.chatpanel.subchatpanel.socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			chatmainpanel.setVisible(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.openPanel(e.getSource());
	}

	public void setCollageDetails() {
		studentprofilepiclabel.setIcon(new ImageIcon(s.getRoundedProfilePic(50, 50, 50)));
		studentnamelabel.setText(s.getFullName());

	}

	// ==== helper: create badge label ====
	private JLabel createBadgeLabel() {
		JLabel label = new JLabel();
		label.setSize(60, 30);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(Color.white);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setVisible(false);
		return label;
	}

	// ==== helper: update badge label (chat/notification) ====
	private void updateBadgeLabel(JLabel label, int count, int baseSize, boolean hideWhenZero) {
		if (count > 0) {
			label.setText(count > 999 ? "999+" : String.valueOf(count));
			label.setVisible(true);
			label.setIcon(new ImageIcon(messagecount.getScaledInstance(
					baseSize + label.getText().length(), baseSize, Image.SCALE_SMOOTH)));
		} else if (hideWhenZero) {
			label.setVisible(false);
		}
	}

	// ==== helper: show standard main panel at (panelx, panely) ====
	private void showMainPanel(JPanel panel) {
		panel.setLocation(panelx, panely);
		panel.setVisible(true);
		panel.setFocusable(true);
		contentPane.add(panel);
	}

	// ==== helper: show top panel at (panelx, 0) (e.g., ViewStudent) ====
	private void showTopPanel(JPanel panel) {
		panel.setLocation(panelx, 0);
		panel.setVisible(true);
		panel.setFocusable(true);
		contentPane.add(panel);
	}

	// ==== helper: wrap panel in scroll pane ====
	private JScrollPane createPanelScroll(JPanel panel, String name, int unitIncrement, boolean colorChildrenWhite) {
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(panelx, panely, 1116, 705);
		scroll.setVisible(true);
		scroll.getVerticalScrollBar().setUnitIncrement(unitIncrement);
		if (name != null) {
			scroll.setName(name);
		}
		if (colorChildrenWhite) {
			for (Component c : scroll.getComponents()) {
				c.setBackground(Color.white);
			}
		}
		contentPane.add(scroll);
		return scroll;
	}

	// ==== helper: confirmation dialog ====
	private boolean confirmAction(String title, String message) {
		int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}

	// ==== helper: deactivate current student and stop timer ====
	private void deactivateCurrentStudent() {
		s.setActiveStatus(false);
		timer.stop();
		new StudentData().setActiveStatus(false, s.getUserId());
	}

	public void openPanel(Object source) {
		if (source == homebutton) {
			activeButton(homebutton);
			homepanel = new HomePanel(s);
			showMainPanel(homepanel);
			homepanel.setLastLogin(lastlogin);

		} else if (source == subjectbutton) {
			activeButton(subjectbutton);
			subjectpanel = new SubjectPanel(this);
			showMainPanel(subjectpanel);

		} else if (source == studentsbutton) {
			activeButton(studentsbutton);
			studentpanel = new StudentPanel(this);
			showMainPanel(studentpanel);

		} else if (source == faculitiesbutton) {

			activeButton(faculitiesbutton);
			facultypanel = new FacultyPanel(this);
			showMainPanel(facultypanel);

		} else if (source == assignedsubjectbutton) {
			activeButton(assignedsubjectbutton);
			assignsubjectpanel = new AssignSubjectPanel(this);
			showMainPanel(assignsubjectpanel);

		} else if (source == marksheetbutton) {
			activeButton(marksheetbutton);
			marksheetpanel = new MarkSheetPanel(this, s);
			marksheetpanel.setVisible(true);
			marksheetpanelscroll = createPanelScroll(marksheetpanel, null, 16, false);

		} else if (source == attandancereportbutton) {
			activeButton(attandancereportbutton);
			attandancereportpanel = new AttandanceReportPanel(this);
			attandancereportpanel.setVisible(true);
			attandancereportpanelscroll = createPanelScroll(attandancereportpanel,
					"Attadance Report Panel Scroll", 16, true);

		} else if (source == chatbutton) {
			activeButton(chatbutton);
			chatmainpanel = new ChatMainPanel(this);
			showMainPanel(chatmainpanel);

		} else if (source == searchbutton) {
			activeButton(searchbutton);
			searchpanel = new SearchPanel(this);
			showMainPanel(searchpanel);

		} else if (source == notificationbutton) {
			activeButton(notificationbutton);
			if (totalnewnotification != null && totalnewnotification.isVisible()) {
				totalnewnotification.setVisible(false);
			}
			notificationpanel = new NotificationPanel(this);
			showMainPanel(notificationpanel);

		} else if (source == myprofilebutton) {
			activeButton(myprofilebutton);
			viewstudentpanel = new ViewStudentPanel(s, this);
			showTopPanel(viewstudentpanel);

		} else if (source == contactusbutton) {
			activeButton(contactusbutton);
			adminprofilepanel = new AdminProfilePanel();
			showMainPanel(adminprofilepanel);

		} else if (source == exitbutton) {
			if (confirmAction("Exit", "Do you want to exit this application ?")) {
				try {
					deactivateCurrentStudent();
					DataBaseConnection.closeConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.disablepanel();
				System.exit(0);
			}

		} else if (source == logoutbutton) {
			if (confirmAction("Logout", "Do you want to logout this application ?")) {
				deactivateCurrentStudent();
				LoginPageFrame loginpageframe = new LoginPageFrame();
				loginpageframe.setVisible(true);
				loginpageframe.setLocationRelativeTo(null);
				this.disablepanel();
				this.dispose();
			}
		}

	}
}
