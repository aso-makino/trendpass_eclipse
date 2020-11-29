package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserBeans;

public class UserDao extends DaoBase {

	public boolean insert(UserBeans userBeans) throws SQLException{

		if(con == null) {
			return false;
		}

		PreparedStatement stmt = null;
		boolean result = false;



		try {
			////////////////////
		    //INSERT��
			stmt = con.prepareStatement("INSERT INTO user ( "
					+ "user_name,user_icon,mail,password,sex,birth) "
					+ "VALUES(?,?,?,?,?,?)");

			/////////////////////////////////
			//�@�l���Z�b�g
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

	public boolean mailCheck(String mail) throws SQLException{

		if(con == null) {
			return false;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean result = false;



		try {
			////////////////////
		    //SELECT��
			stmt = con.prepareStatement("SELECT COUNT(*) as count FROM user WHERE mail = ?");

			/////////////////////////////////
			//�@�l���Z�b�g
			stmt.setString(1,mail);
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

}
