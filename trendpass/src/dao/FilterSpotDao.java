package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.SpotBeans;

public class FilterSpotDao extends DaoBase {

	public SpotBeans getBy(String spotId) throws SQLException{

		if( con == null ){
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		SpotBeans spotBeans = null;


		try{
			stmt = con.prepareStatement("SELECT spot_id,spot_name,X(position_infomation) as longitude,Y(position_infomation) as ratitude ,genre_id "
					+ "FROM spot WHERE spot_id = ?");

			stmt.setString(1, spotId);


			rs = stmt.executeQuery();

			while( rs.next() ) {
				spotBeans = new SpotBeans();
				spotBeans.setSpotId(rs.getString("spot_id"));
				spotBeans.setSpotName(rs.getString("spot_name"));
				spotBeans.setRatitude(rs.getDouble("ratitude"));
				spotBeans.setLongitude(rs.getDouble("longitude"));
				spotBeans.setGenreId(rs.getString("genre_id"));

			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return spotBeans;
	}


}
