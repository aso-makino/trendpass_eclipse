package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.LoginBeans;

public class LoginDao extends DaoBase {
	public LoginBeans getBy(String email, String password) throws SQLException{

		if(con == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		LoginBeans loginBeans = null;


		try {
			stmt = con.prepareStatement("SELECT user_id, user_name, user_icon "
					+ "FROM user "
					+ "WHERE mail = ? AND password = ?");

			stmt.setString(1,email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if(rs.next()) {
				loginBeans = new LoginBeans();
				loginBeans.setUserId(rs.getString("user_id"));
				loginBeans.setUserName(rs.getString("user_name"));
				loginBeans.setUserIcon(rs.getString("user_icon"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return loginBeans;
	}

}
