package model;

import java.sql.SQLException;

import dao.UserPositionDao;
import exception.DBConnectException;

public class UserPositionModel {

	public int delete() throws SQLException,DBConnectException{

		UserPositionDao userPositionDao = new UserPositionDao();
		int result = 0;

		try{
			//�@�ڑ�
			userPositionDao.connect();

			//�@�������ʂ̎擾
		    result = userPositionDao.delete();

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;
		}finally {
			//�@�ڑ��i�R�l�N�V�����j�����
			userPositionDao.close();
		}

		return result;
	}

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
