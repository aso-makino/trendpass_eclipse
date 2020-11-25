package model;

import java.sql.SQLException;

import beans.LoginBeans;
import dao.LoginDao;
import exception.DBConnectException;

public class LoginModel {
	public LoginBeans login(String email,String password) throws SQLException,DBConnectException{

		LoginBeans loginBeans = null;
		LoginDao loginDao = new LoginDao();


		try {

			loginDao.connect();

			loginBeans = loginDao.getBy(email ,password);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			loginDao.close();
		}

		return loginBeans;
	}


}
