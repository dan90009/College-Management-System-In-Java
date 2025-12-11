package collegeapplication.faculty;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import collegeapplication.common.DataBaseConnection;
import collegeapplication.common.Notification;
import collegeapplication.common.NotificationData;
import collegeapplication.common.TimeUtil;

/*
 * Title : FacultyData.java
 * Created by : Ajaysinh Rathod
 * Purpose : Handling all the data related to faculty
 * Mail : ajaysinhrathod1290@gmail.com
 */
public class FacultyData {

	static Connection con = DataBaseConnection.getConnection();

	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ==========================
	// Small common DB helpers
	// ==========================

	/** Execute a "select count(*) from faculties ..." with optional WHERE. */
	private int getFacultyCount(String whereClause) {
		int count = 0;
		String query = "select count(*) from faculties" + (whereClause == null ? "" : whereClause);
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/** Map current ResultSet row to a Faculty object. */
	private Faculty mapFaculty(ResultSet rs) throws SQLException {
		Faculty f = new Faculty();
		f.setFacultyId(rs.getInt(1));
		f.setFacultyName(rs.getString(2));
		f.setState(rs.getString(3));
		f.setCity(rs.getString(4));
		f.setEmailId(rs.getString(5));
		f.setContactNumber(rs.getString(6));
		f.setQualification(rs.getString(7));
		f.setExperience(rs.getString(8));
		f.setBirthDate(rs.getString(9));
		f.setGender(rs.getString(10));
		f.setProfilePic(rs.getBytes(11));
		f.setCourceCode(rs.getString(12));
		f.setSemorYear(rs.getInt(13));
		f.setSubject(rs.getString(14));
		f.setPosition(rs.getString(15));
		// rs.getString(16) is skipped in original code (likely some column between position and lastlogin)
		f.setLastLogin(rs.getString(17));
		f.setPassword(rs.getString(18));
		f.setActiveStatus(rs.getBoolean(19));
		f.setJoinedDate(rs.getString(20));
		return f;
	}

	/** Execute a query that returns a single String value. */
	private String getSingleStringValue(String query) {
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return "";
	}

	/** Execute a query that returns a single boolean value. */
	private boolean getSingleBooleanValue(String query) {
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return false;
	}

	/** Execute a simple update/delete query and return affected rows. */
	private int executeUpdate(String query) {
		try (PreparedStatement pr = con.prepareStatement(query)) {
			return pr.executeUpdate();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return 0;
	}

	// ==========================
	// Public API
	// ==========================

	public int getTotalFaculaty() {
		return getFacultyCount("");
	}

	public int getTotalFaculaty(String courcecode, int sem) {
		String where = " where courcecode='" + courcecode + "' and semoryear=" + sem;
		return getFacultyCount(where);
	}

	/** This is logically the same as getTotalFaculaty(courcecode, sem). */
	public int getFaculaty(String courcecode, int sem) {
		return getTotalFaculaty(courcecode, sem);
	}

	public int createFacultyID() {
		// Original logic: base 101 + count(*)
		return 101 + getFacultyCount("");
	}

	public ResultSet getFacultyInfo(String condition) {
		ResultSet rs = null;
		try {
			String query = "select facultyid as 'Faculty ID',facultyname as 'Faculty Name',emailid as 'Email ID',qualification as 'Qualification',experience as 'Experience' from faculties  "
					+ condition + " order by facultyid";
			Statement st = con.createStatement();
			rs = st.executeQuery(query);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return rs;
	}

	public ResultSet searchFaculty(String query) {
		ResultSet rs = null;
		query += " order by facultyid";
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(query);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return rs;
	}

	public boolean isActive(String facultyid) {
		String query = "select activestatus from faculties where facultyid=" + facultyid;
		return getSingleBooleanValue(query);
	}

	public String getFacultyName(String facultyid) {
		String query = "select facultyname from faculties where facultyid=" + facultyid;
		return getSingleStringValue(query);
	}

	public int addFacultyData(Faculty f) {
		int result = 0;
		String query = "insert into faculties values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pr = con.prepareStatement(query);
			pr.setInt(1, f.getFacultyId());
			pr.setString(2, f.getFacultyName());
			pr.setString(3, f.getState());
			pr.setString(4, f.getCity());
			pr.setString(5, f.getEmailId());
			pr.setString(6, f.getContactNumber());
			pr.setString(7, f.getQualification());
			pr.setString(8, f.getExperience());
			pr.setString(9, f.getBirthDate());
			pr.setString(10, f.getGender());
			pr.setBytes(11, f.getProfilePicInBytes());
			pr.setString(12, "Not Assigned");
			pr.setInt(13, 0);
			pr.setString(14, "Not Assigned");
			pr.setString(15, "Not Assigned");
			pr.setInt(16, 0);
			pr.setString(17, null);
			pr.setString(18, f.getBirthDate());
			pr.setBoolean(19, f.getActiveStatus());
			pr.setString(20, f.generateJoinedDate());
			result = pr.executeUpdate();
			pr.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return result;
	}

	public int updateFacultyData(Faculty fold, Faculty f) {
		int result = 0;
		String query = "update faculties set facultyid=? , facultyname=? ,state=? , city=? , emailid=? , contactnumber=? , qualification=? , experience=? , birthdate=? , gender=? , profilepic=?,lastlogin=?,activestatus=? where facultyid="
				+ fold.getFacultyId();
		try {
			PreparedStatement pr = con.prepareStatement(query);
			pr.setInt(1, f.getFacultyId());
			pr.setString(2, f.getFacultyName());
			pr.setString(3, f.getState());
			pr.setString(4, f.getCity());
			pr.setString(5, f.getEmailId());
			pr.setString(6, f.getContactNumber());
			pr.setString(7, f.getQualification());
			pr.setString(8, f.getExperience());
			pr.setString(9, f.getBirthDate());
			pr.setString(10, f.getGender());
			pr.setBytes(11, f.getProfilePicInBytes());
			pr.setString(12, f.getLastLogin());
			pr.setBoolean(13, f.getActiveStatus());
			result = pr.executeUpdate();
			pr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Faculty getFacultyInfo(int row) {
		String query = "select * from faculties where sr_no=" + row;
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				return mapFaculty(rs);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return new Faculty();
	}

	public Faculty getFacultyInfobyId(int facultyid) {
		String query = "select * from faculties where facultyid=" + facultyid;
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				return mapFaculty(rs);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return new Faculty();
	}

	public ArrayList<Faculty> getTotalFaculty(String condition) {
		ArrayList<Faculty> list = new ArrayList<Faculty>();

		String query = "select * from faculties" + condition + " order by facultyid asc";
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				list.add(mapFaculty(rs));
			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return list;
	}

	public Faculty getFacultyInfobyUserId(String facultyid) {
		Faculty f = new Faculty();
		facultyid = facultyid.replaceAll("\\s", "");
		String query = "select * from faculties where facultyid=" + facultyid;
		try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
			if (rs.next()) {
				f = mapFaculty(rs);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return f;
	}

	public int assignSubject(Faculty fold, Faculty f) {
		int result = 0;
		try {

			if (!fold.getSubject().equals(f.getSubject()) || !fold.getCourceCode().equals(f.getCourceCode())
					|| fold.getSemorYear() != f.getSemorYear() || !fold.getPosition().equals(f.getPosition())) {
				this.deleteNotificationHistory(f);
				Notification n = new Notification();
				n.setUserProfile("Student");
				n.setCourceCode(f.getCourceCode());
				n.setSemorYear(f.getSemorYear());
				n.setTitle("Subject Faculty");
				n.setUserId(f.getFacultyId() + "");
				n.setMessage(f.getFacultyName() + " is your " + f.getSubject() + " subject's new " + f.getPosition()
						+ ".");
				n.setTime(TimeUtil.getCurrentTime());
				new NotificationData().addNotification(n);
				n.setMessage(f.getFacultyName() + " is new " + f.getPosition() + " in " + f.getSubject() + " subject.");
				n.setUserProfile("Faculty");
				new NotificationData().addNotification(n);

			}

			String query = "update faculties set courcecode='" + f.getCourceCode() + "',semoryear=" + f.getSemorYear()
					+ ",subject='" + f.getSubject() + "',position='" + f.getPosition() + "' where facultyid="
					+ f.getFacultyId();
			PreparedStatement pr = con.prepareStatement(query);
			result = pr.executeUpdate();
			pr.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return result;
	}

	public int deleteNotificationHistory(Faculty f) {
		String query = "delete from notification where userid='" + f.getFacultyId() + "'";
		return executeUpdate(query);
	}

	public ResultSet getFacultySubjectInfo(String condition) {
		ResultSet rs = null;
		try {
			String query = "select facultyid as 'Faculty ID',facultyname as 'Faculty Name',courcecode as 'Class',semoryear as 'Sem/Year',subject as 'Subject',position as 'Position' from faculties "
					+ condition + " order by facultyid asc";
			Statement st = con.createStatement();
			rs = st.executeQuery(query);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return rs;
	}

	public boolean checkPassword(String facultyid, String password) {
		boolean result = false;
		try {

			if (facultyid.isEmpty() || facultyid.equalsIgnoreCase(" Enter faculty user-id")) {
				JOptionPane.showMessageDialog(null, "Incorrect User-Id or Password", "Error",
						JOptionPane.ERROR_MESSAGE);
				result = false;
			} else {
				String query = "select count(*) from faculties where facultyid=" + facultyid + " and password='"
						+ password + "'";
				try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
					if (rs.next() && rs.getInt(1) > 0) {
						result = true;
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect User-Id or Password", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		} catch (Exception exp) {

			exp.printStackTrace();
		}
		return result;
	}

	public void setActiveStatus(boolean activestatus, int facultyid) {
		String query = "update faculties set activestatus=" + activestatus + " where facultyid=" + facultyid;
		executeUpdate(query);
	}

	public String getLastLogin(String userid) {
		String query = "select lastlogin from faculties where facultyid=" + userid + "";
		// original method returned null on error, so keep empty string only when ok
		String value = getSingleStringValue(query);
		return value.isEmpty() ? null : value;
	}

	public Image getProfilePic(String userid) {
		Image image = null;
		try {
			String query = "select profilepic from faculties where facultyid=" + userid;
			try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
				if (rs.next()) {
					byte[] imagedata = rs.getBytes(1);
					image = Toolkit.getDefaultToolkit().createImage(imagedata);
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return image;
	}

	public int changePassword(String userid, String password) {
		String query = "update faculties set password='" + password + "' where facultyid=" + userid;
		return executeUpdate(query);
	}

}