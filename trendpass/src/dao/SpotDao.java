package dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.SpotBeans;

public class SpotDao  extends DaoBase{

	/**スポット情報を登録メソッド
	 * @param userId
	 * @param jenreId
	 * @param spotName
	 * @param is
	 * @return insert
	 */

	public boolean insert(SpotBeans spotBeans, int userId, InputStream inputStream) {

		if(con == null) {
			return false;
		}

		//変数生成
		PreparedStatement stmt = null;

		//戻り値
		boolean insert = true;

		//位置情報仮データ
		String location = "ST_GeomFromText('POINT(35.681300 139.767165)')";///東京


		//SQL文生成
		String sql = "INSERT INTO spot (spot_id,spot_name,spot_image,position_information,genre_id,user_id)"
				+ "VALUES (null,?,?,?,?,?);";

		try {
			//INSERT文の発行
			stmt = con.prepareStatement(sql);
			stmt.setString(1, spotBeans.getSpotName());
			stmt.setBinaryStream(2,inputStream );
			stmt.setString(3, location);
			stmt.setString(4,spotBeans.getGenreId());
			stmt.setInt(5, userId);

			//SQL文実行
			stmt.executeUpdate();
		}catch(SQLException e) {
			//エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			insert = false;
		}
		return insert;
	}





}
