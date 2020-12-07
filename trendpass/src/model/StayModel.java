package model;

import java.sql.SQLException;
import dao.StayDao;
import exception.DBConnectException;

public class StayModel {

	public boolean stayCheck() throws SQLException,DBConnectException{

		StayDao stayDao = new StayDao();
		boolean result = false;

		try{
			//謗･邯�
			stayDao.connect();

			//讀懃ｴ｢邨先棡縺ｮ蜿門ｾ�
		    stayDao.stayCheck();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//縲�謗･邯夲ｼ医さ繝阪け繧ｷ繝ｧ繝ｳ�ｼ峨ｒ髢峨§繧�
			stayDao.close();
		}
		return result;

	}
}
