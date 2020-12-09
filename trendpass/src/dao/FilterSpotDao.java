package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import beans.SortBeans;
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

	public List<SpotBeans> getList(String userId,double latitude, double longitude)
			throws SQLException{
		if(con==null) return null;

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    SpotBeans spotBeans = null;

		try {
			//SQL:[ログインユーザとすれちがったユーザ ] が訪れたスポットでかつ、ログインユーザを中点とした1辺222kmの正方形の範囲でスポット情報を取得
			//X：経度 130....　Y：緯度33...
			stmt = con.prepareStatement("SELECT * FROM " +
								"(SELECT DISTINCT stay.spot_id AS spot_id FROM stay WHERE stay.user_id IN " +
								"(SELECT user_id_1 AS user_id FROM pass WHERE user_id_2 = ? UNION SELECT user_id_2 AS user_id FROM pass WHERE user_id_1 = ?))AS userstay " +
								"INNER JOIN spot ON userstay.spot_id = spot.spot_id " +
								"WHERE X (spot.position_infomation) BETWEEN ? - 1 AND ? + 1 " +
								"AND Y (spot.position_infomation) BETWEEN ? - 1 AND ? + 1");

			//博多駅　130.421318　33.583476
			stmt.setString(1,userId);
			stmt.setString(2,userId);
			stmt.setDouble(3,longitude);
			stmt.setDouble(4,longitude);
			stmt.setDouble(5,latitude);
			stmt.setDouble(6,latitude);

			rs =stmt.executeQuery();

			while(rs.next()){
			    spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("spot.spot_name"));
				spotBeans.setSpotId(rs.getString("spot.spot_id"));
				spotBeans.setGenreId(rs.getString("spot.genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y (spot.position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X (spot.position_infomation)"));

				spotList.add(spotBeans);
			}

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				e.printStackTrace();
				throw e;
			}

		return spotList;
	}

	/*
	 * @param sortBeans ソート情報
	 * @param latitude 緯度
	 * @param longitude 経度
	 */
	public List<SpotBeans> getSortList(double latitude, double longitude, String userId, SortBeans sortBeans) throws SQLException{
		if(con==null) return null;

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		SpotBeans spotBeans = new SpotBeans();

		LinkedHashMap<String,Object> sortMap = new LinkedHashMap<String,Object> ();

		PreparedStatement stmt = null;
		ResultSet rs = null;


	    Calendar calendar = Calendar.getInstance();
	    int year = calendar.get(Calendar.YEAR);

	    Integer generation = sortBeans.getGeneration();
	    Integer minDist = sortBeans.getMinDistance();
	    Integer maxDist = sortBeans.getMaxDistance();

		try {

			//SQL:[ログインユーザとすれちがったユーザ ] が訪れたスポットでかつ、ログインユーザを中点とした1辺222kmの正方形の範囲でスポット情報を取得
			StringBuffer strBuf =  new StringBuffer();

			strBuf.append		("SELECT * FROM " +
								"(SELECT DISTINCT stay.spot_id AS spot_id FROM stay WHERE stay.user_id IN " +
								"(SELECT user_id_1 AS user_id FROM pass WHERE user_id_2 = ? UNION SELECT user_id_2 AS user_id FROM pass WHERE user_id_1 = ?))AS userstay " +
								"INNER JOIN spot ON userstay.spot_id = spot.spot_id " +
								"WHERE X (spot.position_infomation) BETWEEN ? - 1 AND ? + 1 " +
								"AND Y (spot.position_infomation) BETWEEN ? - 1 AND ? + 1 ")
								;

			sortMap.put("userId1",userId);
			sortMap.put("userId2",userId);
			sortMap.put("latitude1",longitude);
			sortMap.put("latitude2",longitude);
			sortMap.put("latitude1",latitude);
			sortMap.put("latitude2",latitude);

			//年代ソート
			if(generation!=null) {
				strBuf.append (" AND BETWEEN ? - user.birth AND ? ") ;
				sortMap.put("year1",year);
				sortMap.put("year2",year + 10);
			}
			//性別ソート
			if(sortBeans.getSex() != null) {
				strBuf.append ( " AND user.sex = ? " ) ;
				sortMap.put("sex",sortBeans.getSex() );
			}
			//ジャンルソート
			if(sortBeans.getGenre() != null ) {
				strBuf.append ( " AND genre.genre_name = ? " );
				sortMap.put("genre",sortBeans.getGenre());
			}
			//距離ソート
			if(minDist != null && maxDist != null) {
				strBuf.append ( " AND  " );

				sortMap.put("minDist",minDist);
				sortMap.put("maxDist",maxDist);
			}

			//人気ソートを選択
			if(sortBeans.isPopularOrder()) {
				strBuf.append( " GROUP BY spot.spot_id "
					+  " OREDE BY AVG(genre.evaluation) " ) ;
			}

			stmt = con.prepareStatement( strBuf.toString() );

			//
			int paramNumber = 1;
			for (String paramName : sortMap.keySet()) {

			    Object paramValue = sortMap.get(paramName);

			    //バインド変数の型を検査しセットしていく
			    if (paramValue != null) {
			        if (paramValue instanceof String ) {
			            stmt.setString(paramNumber, (String) paramValue);
			        } else if(paramValue instanceof Integer){
			            stmt.setInt(paramNumber, (Integer) paramValue);
			        }else{
			        	stmt.setDouble(paramNumber, (Double) paramValue);
			        }
			        paramNumber ++;
			    }
			}

			//実行
			rs = stmt.executeQuery();

			while(rs.next()){
				spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("spot_name"));
				spotBeans.setSpotId(rs.getString("spot_id"));
				spotBeans.setGenreId(rs.getString("genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y (position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X (position_infomation)"));

				spotList.add(spotBeans);
			}

			}catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}

		return spotList;
	}
}
