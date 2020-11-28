
package model;

import java.sql.SQLException;

import dao.SpotDao;
import exception.DBConnectException;

public class SpotModel {

	public int delete(String spotId ,String userId) throws SQLException,DBConnectException{

		SpotDao spotDao = new SpotDao();
		int result = 0;

		try{
			//接続
			spotDao.connect();

			//検索結果の取得
		    result = spotDao.delete(spotId ,userId);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//　接続（コネクション）を閉じる
			spotDao.close();
		}

		return result;
	}
}