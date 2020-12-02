package model;

import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;
import utils.DeleteImageFile;

public class UserModel {

	public boolean insert(UserBeans userBeans) throws SQLException, DBConnectException{

		//diaryDao¶¬
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DBÚ‘±
			userDao.connect();

			//@‰ïˆõî•ñ‚Ì“o˜^
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

		//diaryDao¶¬
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DBÚ‘±
			userDao.connect();

			//@‰ïˆõî•ñ‚Ì“o˜^
			String fileName = userDao.getUserIcon(userBeans.getUserId());
			DeleteImageFile.delete(fileName);
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

		//diaryDao¶¬
		UserDao userDao = new UserDao();
		boolean result =false;

		try {
			////////////////////
			//DBÚ‘±
			userDao.connect();

			//@‰ïˆõî•ñ‚Ì“o˜^
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

		//diaryDao¶¬
		UserDao userDao = new UserDao();
		UserBeans userBeans = new UserBeans();

		try {
			////////////////////
			//DBÚ‘±
			userDao.connect();

			//@‰ïˆõî•ñ‚Ì“o˜^
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
