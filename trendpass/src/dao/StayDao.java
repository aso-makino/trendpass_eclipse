package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StayDao extends DaoBase {

	public boolean stayCheck()
			throws SQLException{
		if(con==null) return false;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			//SQL:
			//X：経度　Y：緯度
			stmt = con.prepareStatement("");

			rs =stmt.executeQuery();

			while(rs.next()){

			}

			}catch(SQLException e) {
				//エラー発生した場合にコンソールにログを出力する
				e.printStackTrace();
				throw e;
			}

		return result;
	}

}
