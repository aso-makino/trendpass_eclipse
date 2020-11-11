package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotReviewBeans;

public class ReviewDao extends DaoBase{

	public List<SpotReviewBeans> getMyReviewList(String userId)
			throws SQLException{

		List<SpotReviewBeans> list = new ArrayList<SpotReviewBeans>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM review WHERE user_id = ?");

			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			while( rs.next() ){
				SpotReviewBeans spotReviewBeans = new SpotReviewBeans();

				spotReviewBeans.setUserId(rs.getString("user_id"));
				spotReviewBeans.setSpotId(rs.getString("spot_id"));
				spotReviewBeans.setReviewNumber(rs.getString("review_number"));
				spotReviewBeans.setReviewContent(rs.getString("review_content"));
				spotReviewBeans.setReviewImage(rs.getString("review_image"));
				spotReviewBeans.setEvaluation(rs.getInt("evaluation"));

				list.add(spotReviewBeans);
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}

}
