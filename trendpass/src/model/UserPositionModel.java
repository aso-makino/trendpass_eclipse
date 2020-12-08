package model;

import java.sql.SQLException;

import beans.UserPositionBeans;
import dao.UserPositionDao;
import exception.DBConnectException;

public class UserPositionModel {

	public void insert(UserPositionBeans userPosition) throws SQLException, DBConnectException{

		UserPositionDao userPositionDao = new UserPositionDao();

		try {
			////////////////////
			//DB�ڑ�
			userPositionDao.connect();

			//�@�ԐM���̓o�^
			userPositionDao.insert(userPosition);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			userPositionDao.close();
		}
	}
}
