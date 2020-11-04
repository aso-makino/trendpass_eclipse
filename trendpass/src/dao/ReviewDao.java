package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.ReviewBeans;

public class ReviewDao extends DaoBase{

	/**スポット情報を登録メソッド
	 * @param userId
	 * @param jenreId
	 * @param spotName
	 * @param filename
	 * @return insert
	 */

	public boolean insert(ReviewBeans reviewBeans) {

		if(con == null) {
			return false;
		}

		//変数生成
		PreparedStatement stmt = null;

		//戻り値
		boolean insert = true;

		//SQL文生成
		//String query = "SET @g = GEOMETRYCOLLECTION(POINT(1 1),LINESTRING(0 0,1 1,2 2,3 3,4 4)); ";
		String sql = "INSERT INTO spot (spot_id,review_number,review_content,evaluation,user_id)"
				+ "VALUES (null,?,?,?,?)";

		try {
			//INSERT文の発行
			//stmt = con.prepareStatement( query + sql );
			stmt = con.prepareStatement( sql );
			stmt.setInt( 1, reviewBeans.getReviewNumber() );
			stmt.setString( 2, reviewBeans.getReviewContent() );
			stmt.setInt( 3 , reviewBeans.getEvaluation() );
			stmt.setInt( 4 , reviewBeans.getUserId() );

			//SQL文実行
			stmt.executeUpdate();
			//}catch(SQLFeatureNotSupportedException e) {
				//エラー発生した場合にコンソールにログを出力する
				//e.printStackTrace();
				//insert = false;
				}catch(SQLException e) {
					//エラー発生した場合にコンソールにログを出力する
					e.printStackTrace();
					insert = false;
				}
		return insert;
	}

}
