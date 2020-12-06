package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.SpotBeans;

public class SpotDao  extends DaoBase{

	/**スポット情報を登録するメソッド
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

	public boolean insert(SpotBeans spotBeans, int userId) {

		System.out.println("SpotDao 到達");

		if(con == null) {

			System.out.println("コネクションエラー");

			return false;
		}

		//変数生成
		PreparedStatement stmt = null;
		//ReviewBeans errorBeans = new ReviewBeans();

		//戻り値
		boolean insert = true;

		//位置情報仮データ
		double latitude = 35.681300;//緯度
		//double latitude = spotBeans.getLatitude();//緯度
		double longitude = 139.767165;//経度
		//double longitude = spotBeans.getLongitude();//経度
		String location = "POINT("+ latitude +" "+ longitude +")";///東京の位置情報


		//SQL文生成
		//String query = "SET @g = GEOMETRYCOLLECTION(POINT(1 1),LINESTRING(0 0,1 1,2 2,3 3,4 4)); ";
		String sql = "INSERT INTO trendpass.spot (spot_id,spot_name,spot_image,position_information"
				+ ",genre_id,user_id,spot_review,ratingbar)"
				+ "VALUES (null,?,?,ST_GeomFromText(?),?,?,?,?)";

		try {

			//INSERT文の発行
			System.out.println("try 開始");
			System.out.println("sql 開始");

			stmt = con.prepareStatement( sql );
			stmt.setString( 1, spotBeans.getSpotName() );
			stmt.setString( 2, spotBeans.getFilename() );
			stmt.setString( 3 , location );
			stmt.setInt( 4, spotBeans.getGenreId() );
			stmt.setInt( 5 , userId );
			stmt.setString( 6, spotBeans.getSpotReview() );
			stmt.setInt( 7, spotBeans.getRatingBar() );

			//AWSテスト反映用
			System.out.println( spotBeans.getSpotId());
			System.out.println( spotBeans.getSpotName());
			System.out.println( spotBeans.getFilename());
			System.out.println( spotBeans.getGenreId() );
			System.out.println( spotBeans.getSpotReview());
			System.out.println( spotBeans.getRatingBar());
			System.out.println( userId);

			//System.out.println("sql commit 開始");

			//SQL文実行

			//con.commit();

			stmt.executeUpdate();

			System.out.println("sql commit 終了");

			System.out.println("sql 終了");

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				String str = sw.toString();
				System.out.println(str);
				insert = false;
				System.out.println("SQLException発生");

			}
		System.out.println("try 終了");
		return insert;
	}
}
