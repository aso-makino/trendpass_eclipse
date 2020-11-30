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
			//�@�ڑ�
			userDao.connect();

			//�@�������ʂ̎擾
	        userBean = userDao.getBy(userId);

		}catch(SQLException e) {
			//�@�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�@�ڑ��i�R�l�N�V����)�����
			userDao.close();
		}

		return userBean;

	}
}
