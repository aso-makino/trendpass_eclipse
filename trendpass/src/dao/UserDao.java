package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.UserBeans;

public class UserDao extends DaoBase{

	/**会員情報を削除するメソッド
	 * @param user_Id
	 * @return delete
	 */

	public boolean delete(UserBeans userBeans) {

		System.out.println("UserDao 到達");

		if(con == null) {

			System.out.println("コネクションエラー");

			return false;
		}

		//変数生成
		PreparedStatement stmt = null;

		//戻り値
		boolean delete = true;

		//SQL文生成
		String sql = "UPDATE trendpass.user SET flg = false WHERE user_id = ?";

		try {

			//UPDATE文の発行
			System.out.println("try 開始");
			System.out.println("sql 開始");

			stmt = con.prepareStatement( sql );
			stmt.setInt( 1 , userBeans.getUserId() );

			//AWSテスト反映用
			System.out.println( userBeans.getUserId() );

			//System.out.println("sql commit 開始");

			//SQL文実行
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
				delete = false;
				}
		System.out.println("try 終了");
		return delete;
	}

}
