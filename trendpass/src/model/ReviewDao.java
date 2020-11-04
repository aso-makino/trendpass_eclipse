package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import dao.DaoBase;

public class ReviewDao extends DaoBase {


	public List<SpotBeans> getImage(String userId) throws SQLException{

		if(con == null) {
			return null;
		}
		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		SpotBeans spotBeans = new SpotBeans();
		try {
			//SELECT文の発行
			stmt = con.prepareStatement("SELECT review_image "
									   +"FROM review "
									   + "WHERE userId = ?");

			//�@�l�̃Z�b�g
			stmt.setString(1, userId);

			//�@SQL�̎��s
			rs = stmt.executeQuery();

			while(rs.next()){

				 spotBeans = new SpotBeans();

				 spotBeans.setSpotImage(rs.getString("review_image"));

				 spotList.add(spotBeans);
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return spotList;
	}
}
