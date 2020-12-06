
package model;

import java.sql.SQLException;
import java.util.List;

import beans.SpotBeans;
import dao.SpotDao;
import exception.DBConnectException;

public class SpotModel {

	public int delete(String spotId ,String userId) throws SQLException,DBConnectException{

		SpotDao spotDao = new SpotDao();
		int result = 0;

		try{
			//謗･邯�
			spotDao.connect();

			//讀懃ｴ｢邨先棡縺ｮ蜿門ｾ�
		    result = spotDao.delete(spotId ,userId);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//縲�謗･邯夲ｼ医さ繝阪け繧ｷ繝ｧ繝ｳ�ｼ峨ｒ髢峨§繧�
			spotDao.close();
		}

		return result;
	}

	public int insert(List<SpotBeans> list) throws SQLException,DBConnectException{

		SpotDao spotDao = new SpotDao();
		int result = 0;

		try{
			//謗･邯�
			spotDao.connect();

			//讀懃ｴ｢邨先棡縺ｮ蜿門ｾ�
		    result = spotDao.insert(list);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//縲�謗･邯夲ｼ医さ繝阪け繧ｷ繝ｧ繝ｳ�ｼ峨ｒ髢峨§繧�
			spotDao.close();
		}

		return result;
	}
}