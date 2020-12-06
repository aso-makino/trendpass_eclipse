package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
			//X：経度　Y：緯度
			stmt = con.prepareStatement("SELECT * FROM " +
								"(SELECT DISTINCT stay.spot_id AS spot_id FROM stay WHERE stay.user_id IN " +
								"(SELECT user_id_1 AS user_id FROM pass WHERE user_id_2 = ? UNION SELECT user_id_2 AS user_id FROM pass WHERE user_id_1 = ?))AS userstay " +
								"INNER JOIN spot ON userstay.spot_id = spot.spot_id " +
								"WHERE X (spot.position_infomation) BETWEEN ? - 1 AND ? + 1 " +
								"AND Y (spot.position_infomation) BETWEEN ? - 1 AND ? + 1");

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
				spotBeans.setLongitude(rs.getDouble("Y(sp.position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X(sp.position_infomation)"));

				spotList.add(spotBeans);
			}

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				e.printStackTrace();
				throw e;
			}

		return spotList;
	}

	public List<SpotBeans> getRankingList(double latitude, double longitude)
			throws SQLException{
		if(con==null) return null;

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    SpotBeans spotBeans = null;

		try {
			//SQL:
			//X：経度　Y：緯度
			stmt = con.prepareStatement("SELECT near.spot_id, near.spot_name, X(position_infomation), Y(position_infomation), near.genre_id FROM " +
					"(SELECT * FROM spot WHERE X (spot.position_infomation) BETWEEN ? - 1 AND ? + 1 AND Y (spot.position_infomation) BETWEEN ? - 1 AND ? + 1) AS near " +
					"INNER JOIN stay ON near.spot_id = stay.spot_id GROUP BY near.spot_id ORDER BY COUNT(*) DESC");

			stmt.setDouble(1,latitude);
			stmt.setDouble(2,latitude);
			stmt.setDouble(3,longitude);
			stmt.setDouble(4,longitude);


			rs =stmt.executeQuery();

			while(rs.next()){
			    spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("spot_name"));
				spotBeans.setSpotId(rs.getString("spot_id"));
				spotBeans.setGenreId(rs.getString("genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y(position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X(position_infomation)"));

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
	public List<SpotBeans> getSortList(double latitude, double longitude, SortBeans sortBeans,Map<String, Boolean> sortMap) throws SQLException{
		if(con==null) return null;

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		SpotBeans spotBeans = new SpotBeans();

		PreparedStatement stmt = null;
		ResultSet rs = null;

	    Calendar calendar = Calendar.getInstance();
	    int year = calendar.get(Calendar.YEAR);

	    Integer generation = sortBeans.getGeneration();
	    Integer minDist = sortBeans.getMinDistance();
	    Integer maxDist = sortBeans.getMaxDistance();

		try {
			this.beginTranzaction();
			//X：経度 Y：緯度
			String sql = "SELECT sp.spot_name, sp.spot_id, sp.genre_id, re.review_image "
					+ "X (sp.position_infomation) , Y (sp.position_infomation) "
					+ "FROM spot as sp INNER JOIN review as re "
					+ "ON sp.user_id = re.user_id "
					+ "WHERE X (sp.position_infomation) = ? "
					+ "AND   Y (sp.position_infomation) = ? ";

			stmt.setDouble(1,longitude);
			stmt.setDouble(2,latitude);

			//年代ソート
			if(generation!=null) {
			//生まれ年から現在の年を引いて年齢を出す
			stmt = con.prepareStatement(" SELECT user.birth - ? FROM user WHERE user.user_id = ? ");
			stmt.setInt(1,year);
			//stmt.setString(2,userId);
			rs = stmt.executeQuery();

			//rsの結果を取得する

			String genSortSql = " AND BETWEEN ? AND < ?  ";
			stmt.setInt(1,year);
			stmt.setInt(2,year+10);
			sql += genSortSql;
			}
			//性別ソート
			if(!sortBeans.getSex().equals("")) {
				sql += " AND user.sex = ? ";
				stmt.setString(1,sortBeans.getSex());
			}
			//ジャンルソート
			if(!sortBeans.getGenre().equals("")) {
				sql += " AND ge.genre_name = ? ";
				stmt.setString(1,sortBeans.getGenre());
			}
			//距離ソート
			if(minDist!=null) {

			}
			if(maxDist!=null) {

			}
			//人気ソートを選択
			if(sortBeans.isPopularOrder()) {
				sql += " GROUP BY sp.spot_id "
					+  " OREDE BY AVG(genre.evaluation) ";
			}

			stmt = con.prepareStatement(sql);

			/*
			//人気順 spot_idごとの平均評価の降順 (AVG(evaluation))
			//ジャンル genre.genre_name = ?

			*/

			rs =stmt.executeQuery();

			while(rs.next()){
				spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("spot_name"));
				spotBeans.setSpotId(rs.getString("spot_id"));
				spotBeans.setGenreId(rs.getString("genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y (position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X (position_infomation)"));

				spotList.add(spotBeans);
			}

			this.commit();

			}catch(SQLException e) {
				this.rollback();
				e.printStackTrace();
				throw e;
			}

		return spotList;
	}
	/*
	SELECT sp.spot_name, sp.spot_id, sp.genre_id, re.review_image
	X (sp.position_infomation) , Y (sp.position_infomation)
	 FROM spot as sp INNER JOIN review as re
	 ON sp.user_id = re.user_id
	 WHERE X (sp.position_infomation) =  X (sp.position_infomation)
	 AND   Y (sp.position_infomation)  =  Y (sp.position_infomation)
     GROUP BY  spot_id
	HAVING MAX(AVG(re.evaluation))
	*/

	public List<SpotBeans> getNearSpotList(double latitude,double longitude)
			throws SQLException{

		List<SpotBeans> list = new ArrayList<SpotBeans>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT *, ABS(? * ? - X(position_infomation) * Y(position_infomation)) AS distance, X(position_infomation),Y(position_infomation) "
					+ "FROM spot "
					+ "WHERE X(position_infomation) BETWEEN ? - 0.0089 AND ? + 0.0089 "
					+ "AND Y(position_infomation) BETWEEN ? - 0.0089 AND ? + 0.0089 "
					+ "ORDER BY distance ASC");

			stmt.setDouble(1,latitude);
			stmt.setDouble(2,longitude);
			stmt.setDouble(3,latitude);
			stmt.setDouble(4,latitude);
			stmt.setDouble(5,longitude);
			stmt.setDouble(6,longitude);

			rs = stmt.executeQuery();

			while( rs.next() ){
				SpotBeans spotBeans = new SpotBeans();

				spotBeans.setGenreId(rs.getString("genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y(position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X(position_infomation)"));
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
}
