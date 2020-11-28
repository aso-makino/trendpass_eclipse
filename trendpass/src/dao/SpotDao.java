package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpotDao extends DaoBase {

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

}
