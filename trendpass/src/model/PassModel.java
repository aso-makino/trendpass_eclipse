package model;

import java.sql.SQLException;
import java.util.List;

import beans.UserPositionBeans;
import dao.PassDao;
import exception.DBConnectException;

public class PassModel {

	public void insert(UserPositionBeans userPosition) throws SQLException, DBConnectException{

		PassDao passDao = new PassDao();

		try {
			////////////////////
			passDao.connect();

			List<String> passUserIdList = passDao.passCheck(userPosition);

			passDao.insert(userPosition,passUserIdList);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			passDao.close();
		}

	}
}
