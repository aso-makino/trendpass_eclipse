package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import beans.UserPositionBeans;

public class UserPositionDao extends DaoBase{

	public void insert(UserPositionBeans userPosition) throws SQLException{
		//DB�ڑ��m�F
		if(con==null) {
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		//�@���ꂿ�����`�F�b�N�̎��ԏ��
		String stayStart = sdf.format(userPosition.getStayStart());

		PreparedStatement stmt = null;
		String sql = "INSERT INTO user_position(user_id, position_infomation, stay_start) "
				+ "VALUES(?,ST_GeomFromText(\"POINT(" + userPosition.getLatitude() + " " + userPosition.getLongitude() + ")\"),?)";

		try {
			//insert���̔��s
			stmt = con.prepareStatement(sql);

			stmt.setString(1,userPosition.getUserId());
			stmt.setString(2, stayStart);
			stmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
