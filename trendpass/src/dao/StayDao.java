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
			//X�F�o�x�@Y�F�ܓx
			stmt = con.prepareStatement("");

			rs =stmt.executeQuery();

			while(rs.next()){

			}

			}catch(SQLException e) {
				//�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
				e.printStackTrace();
				throw e;
			}

		return result;
	}

}
