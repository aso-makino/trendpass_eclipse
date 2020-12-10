package model;

import java.sql.SQLException;

import dao.UserPositionDao;
import exception.DBConnectException;

public class UserPositionModel {

	public int delete() throws SQLException,DBConnectException{

		UserPositionDao userPositionDao = new UserPositionDao();
		int result = 0;

		try{
			//　接続
			userPositionDao.connect();

			//　検索結果の取得
		    result = userPositionDao.delete();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//　接続（コネクション）を閉じる
			userPositionDao.close();
		}

		return result;
	}
}
