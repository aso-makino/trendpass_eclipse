package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.UserBeans;

public class UserDao extends DaoBase{

	public UserBeans getBy(String userId) throws SQLException{

		UserBeans userBean = null;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM user WHERE user_id = ?");

			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			while( rs.next() ){

				userBean = new UserBeans();
				userBean.setUserId(rs.getString("user_id"));
				userBean.setUserName(rs.getString("user_name"));
				userBean.setUserIcon(rs.getString("user_icon"));
				userBean.setSex(rs.getInt("sex"));
				userBean.setBirth(rs.getInt("birth"));
				userBean.setMail(rs.getString("mail"));
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return userBean;
	}

	public boolean insert(UserBeans userBeans) throws SQLException{

		if(con == null) {
			return false;
		}

		PreparedStatement stmt = null;
		boolean result = false;



		try {
			////////////////////
		    //INSERT�ｿｽ�ｿｽ
			stmt = con.prepareStatement("INSERT INTO user ( "
					+ "user_name,user_icon,mail,password,sex,birth) "
					+ "VALUES(?,?,?,?,?,?)");

			/////////////////////////////////
			//�ｿｽ@�ｿｽl�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
			stmt.setString(1,userBeans.getUserName());
			stmt.setString(2,userBeans.getUserIcon());
			stmt.setString(3,userBeans.getMail());
			stmt.setString(4,userBeans.getPassword());
			stmt.setInt(5,userBeans.getSex());
			stmt.setInt(6,userBeans.getBirth());
			int count = stmt.executeUpdate();

			if(count == 1) {
				result = true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	public boolean update(UserBeans userBeans) throws SQLException{

		if(con == null) {
			return false;
		}

		PreparedStatement stmt = null;
		boolean result = false;



		try {
			////////////////////
		    //INSERT�ｿｽ�ｿｽ
			stmt = con.prepareStatement("UPDATE user SET "
					+ "user_name = ?,user_icon = ?,mail = ?,password = ?,sex = ?,birth = ? "
					+ "WHERE user_id = ? ");

			/////////////////////////////////
			//�ｿｽ@�ｿｽl�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
			stmt.setString(1,userBeans.getUserName());
			stmt.setString(2,userBeans.getUserIcon());
			stmt.setString(3,userBeans.getMail());
			stmt.setString(4,userBeans.getPassword());
			stmt.setInt(5,userBeans.getSex());
			stmt.setInt(6,userBeans.getBirth());
			stmt.setString(7, userBeans.getUserId());
			int count = stmt.executeUpdate();

			if(count == 1) {
				result = true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}


	public boolean mailCheck(String mail,String userId) throws SQLException{

		if(con == null) {
			return false;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean result = false;



		try {
			////////////////////
		    //SELECT�ｿｽ�ｿｽ
			stmt = con.prepareStatement("SELECT COUNT(*) as count FROM user WHERE mail = ? AND user_id <> ?");

			/////////////////////////////////
			//�ｿｽ@�ｿｽl�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
			stmt.setString(1,mail);
			stmt.setString(2,userId);
			rs = stmt.executeQuery();

			while( rs.next() ) {

				if(rs.getInt("count") == 0) {
					result = true;
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	public UserBeans getUserInfo(String userId) throws SQLException{

		if(con == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		UserBeans userBeans = null;


		try {
			////////////////////
		    //SELECT�ｿｽ�ｿｽ
			stmt = con.prepareStatement("SELECT * FROM user WHERE user_id = ?");

			/////////////////////////////////
			//�ｿｽ@�ｿｽl�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
			stmt.setString(1,userId);
			rs = stmt.executeQuery();

			while( rs.next() ) {
				userBeans = new UserBeans();
				userBeans.setUserName(rs.getString("user_name"));
				userBeans.setUserIcon(rs.getString("user_icon"));
				userBeans.setMail(rs.getString("mail"));
				userBeans.setSex(rs.getInt("sex"));
				userBeans.setBirth(rs.getInt("birth"));
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return userBeans;
	}

	public String getUserIcon(String userId) throws SQLException{

		if(con == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String userIcon = null;

		try {
			////////////////////
		    //SELECT�ｿｽ�ｿｽ
			stmt = con.prepareStatement("SELECT user_icon FROM user WHERE user_id = ?");

			/////////////////////////////////
			//�ｿｽ@�ｿｽl�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
			stmt.setString(1,userId);
			rs = stmt.executeQuery();

			while( rs.next() ) {
				userIcon = rs.getString("user_icon");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return userIcon;
	}

}
