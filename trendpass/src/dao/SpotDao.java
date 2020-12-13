package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import beans.SpotBeans;

public class SpotDao  extends DaoBase{

	/**繧ｹ繝昴ャ繝域ュ蝣ｱ繧堤匳骭ｲ縺吶ｋ繝｡繧ｽ繝�繝�
	 * @param userId
	 * @param jenreId
	 * @param spotName
	 * @param spotReview
	 * @param ratingBar
	 * @param filename
	 * @param latitude
	 * @param longitude
	 * @return insert
	 */

	public String insert(SpotBeans spotBeans, String userId) {

		System.out.println("SpotDao 蛻ｰ驕�");

		if(con == null) {

			System.out.println("繧ｳ繝阪け繧ｷ繝ｧ繝ｳ繧ｨ繝ｩ繝ｼ");

			return null;
		}

		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//螟画焚逕滓��
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String spotId = "";
		//ReviewBeans errorBeans = new ReviewBeans();

		//菴咲ｽｮ諠�蝣ｱ莉ｮ繝�繝ｼ繧ｿ
		//double latitude = spotBeans.getLatitude();//邱ｯ蠎ｦ
		//double longitude = spotBeans.getLongitude();//邨悟ｺｦ



		//SQL譁�逕滓��
		//String query = "SET @g = GEOMETRYCOLLECTION(POINT(1 1),LINESTRING(0 0,1 1,2 2,3 3,4 4)); ";
		String sql = "INSERT INTO trendpass.spot (spot_name,position_infomation,genre_id,user_id) "
				+ "VALUES (?,ST_GeomFromText(\"POINT(" + spotBeans.getLatitude() + " " + spotBeans.getLongitude() + ")\"),?,?);\n";


		try {

			//INSERT譁�縺ｮ逋ｺ陦�
			System.out.println("try 髢句ｧ�");
			System.out.println("sql 髢句ｧ�");

			stmt = con.prepareStatement( sql );
			stmt.setString( 1, spotBeans.getSpotName() );
			stmt.setString( 2, spotBeans.getGenreId() );
			stmt.setString( 3 , userId );

			//AWS繝�繧ｹ繝亥渚譏�逕ｨ
			System.out.println( spotBeans.getSpotName());
			System.out.println( spotBeans.getGenreId() );
			System.out.println( userId);

			//System.out.println("sql commit 髢句ｧ�");

			//SQL譁�螳溯｡�

			//con.commit();

			int count = stmt.executeUpdate();

			if(count == 1) {
				String sql2 = "SELECT LAST_INSERT_ID() as spot_id FROM spot";

				stmt = con.prepareStatement( sql2 );
				rs = stmt.executeQuery();

				while( rs.next() ) {
					spotId = rs.getString("spot_id");
					System.out.println(spotId);
				}
			}

			try {
				con.commit();
			}finally {
				con.setAutoCommit(true);
			}

			System.out.println("sql commit 邨ゆｺ�");

			System.out.println("sql 邨ゆｺ�");

			}catch(SQLException e) {
				//繧ｨ繝ｩ繝ｼ逋ｺ逕溘＠縺溷�ｴ蜷医↓繧ｳ繝ｳ繧ｽ繝ｼ繝ｫ縺ｫ繝ｭ繧ｰ繧貞�ｺ蜉帙☆繧�
				e.printStackTrace();
			}
		System.out.println("try 邨ゆｺ�");
		return spotId;
	}

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
					+ "VALUES (?,ST_GeomFromText(\"POINT(" + list.get(i).getLatitude() + " " + list.get(i).getLongitude() + ")\"),?,?);\n";

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
