package model;

import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;
import utils.DeleteImageFile;

public class UserModel {

	public boolean insert(UserBeans userBeans) throws SQLException, DBConnectException{

		//diaryDao����
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB�ڑ�
			userDao.connect();

			//�@������̓o�^
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

		//diaryDao����
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB�ڑ�
			userDao.connect();


			// user_icon���w�肳��Ă��Ȃ��ꍇ�ADB�ɕۑ�����Ă���l�ɂ���
			// user_icon���w�肳��Ă���ꍇ�A�T�[�o�[�ɃA�b�v����Ă����t�@�C�����폜

			String fileName = userDao.getUserIcon(userBeans.getUserId()); //DB�Ɋi�[����Ă���user_icon�̃p�X���擾
			System.out.println(userBeans.getUserIcon());
			System.out.println(userBeans.getUserIcon().isEmpty());
			if(userBeans.getUserIcon().isEmpty()) {
				userBeans.setUserIcon(fileName);
			}else {
				DeleteImageFile.delete(fileName);
			}

			//�@������̓o�^
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

		//diaryDao����
		UserDao userDao = new UserDao();
		boolean result =false;

		try {
			////////////////////
			//DB�ڑ�
			userDao.connect();

			//�@������̓o�^
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

		//diaryDao����
		UserDao userDao = new UserDao();
		UserBeans userBeans = new UserBeans();

		try {
			////////////////////
			//DB�ڑ�
			userDao.connect();

			//�@������̓o�^
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
