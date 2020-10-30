package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;

public class FilterSpotDao extends DaoBase{

	public List<SpotBeans> getList()
			throws SQLException{
		List<SpotBeans> spotList = new ArrayList<SpotBeans>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		SpotBeans spotBeans = new SpotBeans();

		try {
			//スポット一覧表示のSELECT文
			stmt = con.prepareStatement("SELECT sp.spot_name, sp.spot_id, ge.genre_id, us_p.position_infomation "
										+ "FROM spot as sp INNER JOIN genre as ge "
										+ "sp.spot_id = ge.spot_id "
										+ "INNER JOIN sp.user_id = us_p.user_id"
										+ "WHERE  user_id = ? ");
			stmt.setString(1,userId);
			rs =stmt.executeQuery();

			while(rs.next()){
				 spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("sp.spot_name"));
				spotBeans.setSpotId(rs.getString("sp.spot_id"));
				spotBeans.setGenreId(rs.getString("ge.genre_id"));
				spotBeans.setRatitude(rs.getDouble(""));
				spotBeans.setLongitude(rs.getDouble(""));

				spotList.add(spotBeans);
			}

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				e.printStackTrace();
				throw e;
			}

		return spotList;
	}



}
