package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.ReviewBeans;

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
			String error = "コネクション失敗";
			reviewBeans.setError(error);
			System.out.println("コネクションエラー");
			return false;
		}

		//変数生成
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String error = "Daoまで接続";
		reviewBeans.setError(error);
		int review_number = 0;

		//戻り値
		boolean insert = true;

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
		+ "user_id,review_title)"
		+ "VALUES ( ? , ? , ? , ? , ? , ? );";


		try {
			System.out.println("sql 開始");


			//SELECT文の実行
			stmt = con.prepareStatement( sql_1 );
			stmt.setInt( 1 , reviewBeans.getSpotId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				review_number = rs.getInt("Max(review_number)");
			}

			//連番処理
			review_number ++;

			//SELECT文の実行
			stmt = con.prepareStatement( sql_2 );
			stmt.setInt( 1 , reviewBeans.getSpotId() );
			stmt.setInt( 2 , review_number );
			stmt.setString( 3 , reviewBeans.getReviewContent() );
			stmt.setInt( 4 , reviewBeans.getEvaluation() );
			stmt.setInt( 5 , reviewBeans.getUserId() );
			stmt.setString(6, reviewBeans.getReviewTitle());


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
			System.out.println( reviewBeans.getReviewContent());
			System.out.println(reviewBeans.getEvaluation());
			System.out.println(reviewBeans.getUserId() );
			System.out.println(reviewBeans.getReviewTitle());

			System.out.println("sql commit 開始");

			//SQL文実行
			stmt.executeUpdate();

			//con.commit();

			System.out.println("sql commit 終了");

			System.out.println("sql 終了");

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				error = "SQLException";
				reviewBeans.setError(error);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				String str = sw.toString();
				System.out.println(str);
				insert = false;
				System.out.println("SQLException発生");

			}
		return insert;
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
					reviewBeans.setEvaluation(rs.getInt("evaluation"));
					reviewBeans.setUserId(rs.getString("user_id"));


					list.add(reviewBeans);
				}


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}
}
