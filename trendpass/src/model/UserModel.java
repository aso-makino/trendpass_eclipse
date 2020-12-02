package model;

import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;

public class UserModel {

	public boolean insert(UserBeans userBeans) throws SQLException, DBConnectException{

		//diaryDao生成
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB接続
			userDao.connect();

			//　会員情報の登録
			result = userDao.insert(userBeans);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			userDao.close();


		}
		return result;

	}

	public boolean update(UserBeans userBeans) throws SQLException, DBConnectException{

		//diaryDao生成
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB接続
			userDao.connect();

			//　会員情報の登録
			result = userDao.update(userBeans);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			userDao.close();


		}
		return result;

	}

	public boolean mailCheck(String mail ,String userId) throws SQLException, DBConnectException{

		//diaryDao生成
		UserDao userDao = new UserDao();
		boolean result =false;

		try {
			////////////////////
			//DB接続
			userDao.connect();

			//　会員情報の登録
			result = userDao.mailCheck(mail,userId);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			userDao.close();


		}
		return result;

	}

	public UserBeans getUserInfo(String userId) throws SQLException, DBConnectException{

		//diaryDao生成
		UserDao userDao = new UserDao();
		UserBeans userBeans = new UserBeans();

		try {
			////////////////////
			//DB接続
			userDao.connect();

			//　会員情報の登録
			userBeans = userDao.getUserInfo(userId);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			userDao.close();


		}
		return userBeans;

	}
}
