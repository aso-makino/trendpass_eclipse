package model;

import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;

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

	public boolean mailCheck(String mail) throws SQLException, DBConnectException{

		//diaryDao¶¬
		UserDao userDao = new UserDao();
		boolean result =false;

		try {
			////////////////////
			//DBÚ‘±
			userDao.connect();

			//@‰ïˆõî•ñ‚Ì“o˜^
			result = userDao.mailCheck(mail);


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
}
