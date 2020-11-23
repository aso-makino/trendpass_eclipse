package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import beans.SpotReviewBeans;

public class FilterSpotDao extends DaoBase{

	public List<SpotBeans> getMySpotList(String userId)
			throws SQLException{

		List<SpotBeans> list = new ArrayList<SpotBeans>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT genre_id, X(position_infomation) as longitude, Y(position_infomation) as ratitude, spot_id, spot_name "
										+ "FROM spot WHERE user_id = ?");

			stmt.setString(1, userId);

			System.out.println(stmt);
			rs = stmt.executeQuery();

			while( rs.next() ){
				SpotBeans spotBeans = new SpotBeans();

				spotBeans.setGenreId(rs.getString("genre_id"));
				spotBeans.setLongitude(rs.getDouble("longitude"));
				spotBeans.setRatitude(rs.getDouble("ratitude"));
				spotBeans.setSpotId(rs.getString("spot_id"));
				spotBeans.setSpotName(rs.getString("spot_name"));

				list.add(spotBeans);
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}

	public List<SpotReviewBeans> getMySpotReviewList(List<SpotReviewBeans> list) throws SQLException{

		List<SpotReviewBeans> result = new ArrayList<SpotReviewBeans>(list);
		PreparedStatement stmt = null;
		ResultSet rs = null;

		for(SpotReviewBeans spotReviewBeans : list) {
			try {
				stmt = con.prepareStatement("SELECT genre_id, X(position_infomation) as longitude, Y(position_infomation) as ratitude, spot_id, spot_name "
											+ "FROM spot WHERE spot_id = ?");

				stmt.setString(1, spotReviewBeans.getSpotId());

				rs = stmt.executeQuery();

				while( rs.next() ){

					spotReviewBeans.setGenreId(rs.getString("genre_id"));
					spotReviewBeans.setLongitude(rs.getDouble("longitude"));
					spotReviewBeans.setRatitude(rs.getDouble("ratitude"));
					spotReviewBeans.setSpotName(rs.getString("spot_name"));

				}

			}catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return result;
	}

}
