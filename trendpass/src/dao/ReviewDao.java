package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ReviewBeans;
import beans.ReviewBeans;

public class ReviewDao extends DaoBase{

	public List<ReviewBeans> getList(String spotId)
			throws SQLException{
		List<ReviewBeans> list = new ArrayList<ReviewBeans>();
		ReviewBeans reviewBeans = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			///////////////////////////////////
			//SELECT譁�逋ｺ陦�
				stmt = con.prepareStatement("SELECT * FROM review WHERE spot_id = ? "
						+ "ORDER BY review_number DESC");
				stmt.setString(1,spotId);

				rs = stmt.executeQuery();


				while( rs.next() ){
					reviewBeans = new ReviewBeans();

					reviewBeans.setSpotId(rs.getString("spot_id"));
					reviewBeans.setReviewNumber(rs.getString("review_number"));
					reviewBeans.setReviewContent(rs.getString("review_content"));
					reviewBeans.setReviewImage(rs.getString("review_image"));
					reviewBeans.setEvaluation(rs.getInt("evaluation"));
					reviewBeans.setUserId(rs.getString("user_id"));


					list.add(reviewBeans);
				}


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}
}
