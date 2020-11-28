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

/**
 * @author kurokikazuyuki
 */
public class FilterSpotDao extends DaoBase{
	/*
	 * @param userId ユーザーID
	 * @param latitude 緯度
	 * @param longitude 経度
	 */
	public List<SpotBeans> getList(String userId,double latitude, double longitude)
			throws SQLException{
		if(con==null) return null;

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    SpotBeans spotBeans = new SpotBeans();

		try {
			//スポット一覧表示のSELECT文
			//X：経度　Y：緯度
			stmt = con.prepareStatement("SELECT sp.spot_name, sp.spot_id, sp.genre_id, re.review_image "
										+ "X (sp.position_infomation) , Y (sp.position_infomation) "
										+ "FROM spot as sp INNER JOIN review as re "
										+ "ON sp.user_id = re.user_id "
										+ "WHERE X (sp.position_infomation) = ? "
										+ "AND   Y (sp.position_infomation) = ? ");
			stmt.setDouble(1,longitude);
			stmt.setDouble(2,latitude);

			rs =stmt.executeQuery();

			while(rs.next()){
			    spotBeans = new SpotBeans();

				spotBeans.setSpotName(rs.getString("sp.spot_name"));
				spotBeans.setSpotId(rs.getString("sp.spot_id"));
				spotBeans.setGenreId(rs.getString("sp.genre_id"));
				spotBeans.setLongitude(rs.getDouble("Y (sp.position_infomation)"));
				spotBeans.setRatitude(rs.getDouble("X (sp.position_infomation)"));
				spotBeans.setSpotImage(rs.getString("X (re.review_image)"));

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
}