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
				userBean.setSex(rs.getString("sex"));
				userBean.setBirth(rs.getInt("birth"));
				userBean.setMail(rs.getString("mail"));
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return userBean;
	}

}
