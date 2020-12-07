package model;

import java.io.PrintWriter;
import java.io.StringWriter;

import beans.UserBeans;
import dao.UserDao;

public class UserModel{

	/**会員情報を削除するメソッド
	 * @param userId
	 * @return userBeans
	 */
	public boolean delete(UserBeans userBeans) {

		//テスト用
		System.out.println("UserModel 到達");

		//戻り値
		boolean delete = true;

		//DAOの生成
		UserDao userDao = new UserDao();

		try {
			//ReviewDaoに接続
			System.out.println("try 開始");

			userDao.connect();

			System.out.println("コネクション成功");

			//メソッド呼び出し
			delete = userDao.delete(userBeans);
		}catch(Exception e){
			delete = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			String str = sw.toString();
			System.out.println(str);
			System.out.println("コネクション失敗");
		}finally {
			//ReviewDaoとの接続を切る
			userDao.close();
			System.out.println("コネクション終了");
			}
		System.out.println("try 終了");
		return delete;
	}

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
