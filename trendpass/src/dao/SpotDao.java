package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import beans.SpotBeans;

public class SpotDao extends DaoBase {

	public int delete(String spotId ,String userId )throws SQLException{


		if( con == null ){

			return 0;

		}

		int result = 0;

		PreparedStatement stmt = null;

		try{
			stmt = con.prepareStatement("DELETE FROM spot WHERE spot_id=? AND user_id=? ");
			stmt.setString(1, spotId);
			stmt.setString(2, userId);

			System.out.println(stmt);

			result = stmt.executeUpdate();


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}
	public int insert(List<SpotBeans> list) throws SQLException {

		System.out.println(list.size());
		String sql = "";
		PreparedStatement stmt = null;
		int result = 0;

		for(int i = 0; i < list.size(); i++) {
			sql += "INSERT INTO spot (spot_name,position_infomation,genre_id,user_id) "
					+ "VALUES (?,ST_GeomFromText(\"POINT(" + list.get(i).getRatitude() + " " + list.get(i).getLongitude() + ")\"),?,?);\n";

		}

		try {
			System.out.println(sql);
			stmt = con.prepareStatement(sql);

			for(int i = 0; i < list.size(); i++) {
				stmt.setString(1+i*3, list.get(i).getSpotName());
				stmt.setString(2+i*3, list.get(i).getGenreId());
				stmt.setString(3+i*3, "0000001");
			}

			System.out.println(stmt);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
