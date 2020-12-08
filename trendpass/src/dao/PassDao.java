package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import beans.UserPositionBeans;

public class PassDao extends DaoBase{

	public void insert(UserPositionBeans userPosition, List<String> passUserIdList) throws SQLException{
		//DB接続確認
		if(con==null) {
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String registDate = sdf.format(userPosition.getStayStart());

		PreparedStatement stmt = null;

		try {

			if(passUserIdList != null) {
				for(String passUserId : passUserIdList) {

					stmt = con.prepareStatement("INSERT INTO pass (user_id_1, user_id_2, regist_date) "
											+ "VALUES(?,?,?)");

					stmt.setString(1, userPosition.getUserId());
					stmt.setString(2, passUserId);
					stmt.setString(3, registDate);
					stmt.executeUpdate();
				}
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<String> passCheck(UserPositionBeans userPosition) throws SQLException{

		//DB接続確認
		if(con==null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		//　すれちがいチェックの時間上限
		String upperLimit = sdf.format(userPosition.getStayStart());
		System.out.println(upperLimit);


		//　時間を5分前にする
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(userPosition.getStayStart());
        calendar.add(Calendar.MINUTE, -5);
        String lowerLimit = sdf.format(calendar.getTime());
        System.out.println(lowerLimit);


		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "(SELECT user_id FROM user_position " +
				"WHERE stay_start BETWEEN ? AND ? " +
				"AND X(position_infomation) BETWEEN ? - 0.0023 AND ? + 0.0023 " +
				"AND Y(position_infomation) BETWEEN ? - 0.0023 AND ? + 0.0023 " +
				"AND user_id <> ?) " +
				"EXCEPT " +
				"SELECT passuser.user_id " +
				"FROM " +
				"(SELECT user_id_1 AS user_id FROM pass WHERE user_id_2 = ? " +
				"UNION " +
				"SELECT  user_id_2 AS user_id FROM pass WHERE user_id_1 = ?) AS passuser";

		List<String> passUserIdList = new ArrayList<String>();

		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, lowerLimit);
			stmt.setString(2, upperLimit);
			stmt.setDouble(3, userPosition.getLatitude());
			stmt.setDouble(4, userPosition.getLatitude());
			stmt.setDouble(5, userPosition.getLongitude());
			stmt.setDouble(6, userPosition.getLongitude());
			stmt.setString(7, userPosition.getUserId());
			stmt.setString(8, userPosition.getUserId());
			stmt.setString(9, userPosition.getUserId());

			rs = stmt.executeQuery();

			while( rs.next() ) {
				passUserIdList.add(rs.getString("user_id"));

			}

			if(passUserIdList.isEmpty()){
			    return null;
			}

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return passUserIdList;
	}
}
