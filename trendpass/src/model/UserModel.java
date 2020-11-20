package model;

import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;

public class UserModel {

	public UserBeans getBy(String userId) throws SQLException, DBConnectException{

		UserDao userDao = new UserDao();
		UserBeans userBean = null;

		try{
			//　接続
			userDao.connect();

			//　検索結果の取得
	        userBean = userDao.getBy(userId);

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//　接続（コネクション)を閉じる
			userDao.close();
		}

		return userBean;

	}
}
