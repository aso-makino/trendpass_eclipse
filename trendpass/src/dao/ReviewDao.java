package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
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

	public List<SpotBeans> getSpotImage(List<SpotBeans> list) throws SQLException{

		List<SpotBeans> result = new ArrayList<SpotBeans>(list);
		PreparedStatement stmt = null;
		ResultSet rs = null;

		for(SpotBeans spotBean : list) {
			try {
				stmt = con.prepareStatement("SELECT review_image FROM review "
											+ "WHERE spot_id = ? "
											+ "AND review_number = (SELECT MAX(review_number) FROM review WHERE spot_id = ?)");

				stmt.setString(1, spotBean.getSpotId());
				stmt.setString(2, spotBean.getSpotId());
				rs = stmt.executeQuery();

				while( rs.next() ){
					spotBean.setSpotImage(rs.getString("review_image"));
				}

			}catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return result;
	}
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
