package dao;

import java.util.Calendar;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserPositionDao extends DaoBase{

	public int delete() throws SQLException{

		if(con == null) {
			return 0;
		}

		int result = 0;

		//　Date型で日付取得
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        //　Date型をCalendar型に変換し、日付を加算
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -7);

        //　Date型に戻す
        date = cal.getTime();

        //　String型に変換
        String dateStr = df.format(date);

		PreparedStatement stmt = null;

		try {
		    stmt = con.prepareStatement("DELETE FROM user_position WHERE stay_start < ?");
		    stmt.setString(1, dateStr);

		    System.out.println(stmt);

			result = stmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}
}
