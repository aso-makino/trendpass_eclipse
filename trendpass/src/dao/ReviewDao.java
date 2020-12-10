package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.ReviewBeans;
import beans.SpotBeans;
import beans.SpotReviewBeans;

public class ReviewDao extends DaoBase{

	/**スポット情報を登録メソッド
	 * @param user_Id
	 * @param spot_Id
	 * @param review_number
	 * @param review_title
	 * @param review_content
	 * @param review_image
	 * @param evaluation
	 * @return insert
	 */

	public boolean insert(ReviewBeans reviewBeans) {

		System.out.println("ReviewDao 到達");

		if(con == null) {
			return false;
		}

		//変数生成
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int review_number = 0;

		//戻り値
		boolean result = false;

		/*
		//SQL文生成
		//String query = "SET @g = GEOMETRYCOLLECTION(POINT(1 1),LINESTRING(0 0,1 1,2 2,3 3,4 4)); ";
		String sql = "INSERT INTO trendpass.review (spot_id,review_number,review_content,evaluation,"
				+ "user_id,review_title)"
				+ "VALUES ( ? , @new_review_number , ? , ? , ? , ? );";
		//DBから現在のスポット連番数を取得
		String sql2 = "SET @max_review_number = "
				+ "(SELECT MAX(review_number) FROM trendpass.review WHERE spot_id = ?);";
		//分岐処理
		String sql3 = "SET @new_review_number = "
				+  "(SELECT IF(@max_review_number IS NULL,1,@max_review_number + 1));";
		*/
		//一つ目のSQL
		String sql_1 = "SELECT MAX(review_number) FROM trendpass.review WHERE spot_id = ? ;";

		//二つ目のSQL
		String sql_2 =  "INSERT INTO trendpass.review (spot_id,review_number,review_content,evaluation,"
		+ "user_id,review_image)"
		+ "VALUES ( ? , ? , ? , ? , ? , ? );";

		String sql3 = "INSERT INTO trendpass.stay (spot_id,user_id,stay_start)"
				+ "VALUES ( ? , ? , ? );";


		System.out.println(sql_2);
		try {
			System.out.println("sql 開始");


			//SELECT文の実行
			stmt = con.prepareStatement( sql_1 );
			stmt.setString( 1 , reviewBeans.getSpotId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				review_number = rs.getInt("Max(review_number)");
			}

			//　連番処理
			review_number ++;
			System.out.println(review_number);

			//SELECT文の実行
			stmt = con.prepareStatement( sql_2 );
			stmt.setString( 1 , reviewBeans.getSpotId() );
			stmt.setInt( 2 , review_number );
			stmt.setString( 3 , reviewBeans.getReviewContent() );
			stmt.setString( 4 , reviewBeans.getEvaluation() );
			stmt.setString( 5 , reviewBeans.getUserId() );
			stmt.setString( 6 , reviewBeans.getReviewImage() );

			//SQL文実行
			int count = stmt.executeUpdate();
			if(count == 1) {
				result = true;

				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            final Date date = new Date(System.currentTimeMillis());
	            String stayTime = sdf.format(date);


				stmt = con.prepareStatement( sql3 );
				stmt.setString( 1 , reviewBeans.getSpotId() );
				stmt.setString( 2 , reviewBeans.getUserId() );
				stmt.setString( 3 , stayTime );
				int count2 = stmt.executeUpdate();

				if(count2 == 1) {
					result = true;
				}
			}





			//SELECT文の実行
			//con.setAutoCommit(false);

			/*
			//SELECT文の発行
			stmt = con.prepareStatement( sql2 );
			stmt.setInt( 1 , reviewBeans.getSpotId());
			stmt.executeQuery();

			//SELECT文の発行
			stmt = con.prepareStatement( sql3 );

			//INSERT文の発行
			stmt = con.prepareStatement( sql2 + sql3 + sql );
			stmt.setInt( 1 , reviewBeans.getSpotId());
			stmt.setInt( 2 , reviewBeans.getSpotId() );
			//レビューナンバーは@SET
			stmt.setString( 3 , reviewBeans.getReviewContent() );
			stmt.setInt( 4 , reviewBeans.getEvaluation() );
			stmt.setInt( 5 , reviewBeans.getUserId() );
			stmt.setString(6, reviewBeans.getReviewTitle());
			 */

			//AWSテスト
			System.out.println(reviewBeans.getSpotId());
			System.out.println(reviewBeans.getReviewContent());
			System.out.println(reviewBeans.getEvaluation());
			System.out.println(reviewBeans.getUserId() );


			System.out.println("sql commit 開始");



			//con.commit();

			System.out.println("sql commit 終了");

			System.out.println("sql 終了");

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				e.printStackTrace();
			}
		return result;
	}


	public List<SpotReviewBeans> getMyReviewList(String userId)
			throws SQLException{

		List<SpotReviewBeans> list = new ArrayList<SpotReviewBeans>();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM review WHERE user_id = ?");

			stmt.setString(1, userId);

			rs = stmt.executeQuery();

			while( rs.next() ){
				SpotReviewBeans spotReviewBeans = new SpotReviewBeans();

				spotReviewBeans.setUserId(rs.getString("user_id"));
				spotReviewBeans.setSpotId(rs.getString("spot_id"));
				spotReviewBeans.setReviewNumber(rs.getString("review_number"));
				spotReviewBeans.setReviewContent(rs.getString("review_content"));
				spotReviewBeans.setReviewImage(rs.getString("review_image"));
				spotReviewBeans.setEvaluation(rs.getInt("evaluation"));

				list.add(spotReviewBeans);
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}

	public List<SpotBeans> getSpotImage(List<SpotBeans> list) throws SQLException{

		List<SpotBeans> result = new ArrayList<SpotBeans>(list);
		PreparedStatement stmt = null;
		ResultSet rs = null;

		for(SpotBeans spotBean : list) {
			try {
				stmt = con.prepareStatement("SELECT review_image FROM review "
											+ "WHERE spot_id = ? "
											+ "AND review_number = (SELECT MAX(review_number) FROM review WHERE spot_id = ?)");

				stmt.setString(1, spotBean.getSpotId());
				stmt.setString(2, spotBean.getSpotId());
				rs = stmt.executeQuery();

				while( rs.next() ){
					spotBean.setSpotImage(rs.getString("review_image"));
				}

			}catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return result;
	}
	public List<ReviewBeans> getList(String spotId)
			throws SQLException{
		List<ReviewBeans> list = new ArrayList<ReviewBeans>();
		ReviewBeans reviewBeans = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			///////////////////////////////////
			//SELECT譁�逋ｺ陦�
				stmt = con.prepareStatement("SELECT * FROM review WHERE spot_id = ? "
						+ "ORDER BY review_number DESC");
				stmt.setString(1,spotId);

				rs = stmt.executeQuery();


				while( rs.next() ){
					reviewBeans = new ReviewBeans();

					reviewBeans.setSpotId(rs.getString("spot_id"));
					reviewBeans.setReviewNumber(rs.getString("review_number"));
					reviewBeans.setReviewContent(rs.getString("review_content"));
					reviewBeans.setReviewImage(rs.getString("review_image"));
					reviewBeans.setEvaluation(rs.getString("evaluation"));
					reviewBeans.setUserId(rs.getString("user_id"));


					list.add(reviewBeans);
				}


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}

	public boolean delete(String spotId ,String reviewNumber,String userId )throws SQLException{


		if( con == null ){

			return false;

		}

		int count = 0;
		boolean result = false;

		PreparedStatement stmt = null;

		try{
			stmt = con.prepareStatement("DELETE FROM review WHERE spot_id=? AND user_id=? AND review_number=?");
			stmt.setString(1, spotId);
			stmt.setString(2, userId);
			stmt.setString(3, reviewNumber);

			System.out.println(stmt);

			count = stmt.executeUpdate();

			if(count > 0) {
				result = true;
			}


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
