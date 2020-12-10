package model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import beans.UserBeans;
import dao.UserDao;
import exception.DBConnectException;
import utils.DeleteImageFile;

public class UserModel{

	/**莨壼藤諠�蝣ｱ繧貞炎髯､縺吶ｋ繝｡繧ｽ繝�繝�
	 * @param userId
	 * @return userBeans
	 */
	public boolean delete(UserBeans userBeans) {

		//繝�繧ｹ繝育畑
		System.out.println("UserModel 蛻ｰ驕�");

		//謌ｻ繧雁�､
		boolean delete = true;

		//DAO縺ｮ逕滓��
		UserDao userDao = new UserDao();

		try {
			//ReviewDao縺ｫ謗･邯�
			System.out.println("try 髢句ｧ�");

			userDao.connect();

			System.out.println("繧ｳ繝阪け繧ｷ繝ｧ繝ｳ謌仙粥");

			//繝｡繧ｽ繝�繝牙他縺ｳ蜃ｺ縺�
			delete = userDao.delete(userBeans);
		}catch(Exception e){
			delete = false;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			String str = sw.toString();
			System.out.println(str);
			System.out.println("繧ｳ繝阪け繧ｷ繝ｧ繝ｳ螟ｱ謨�");
		}finally {
			//ReviewDao縺ｨ縺ｮ謗･邯壹ｒ蛻�繧�
			userDao.close();
			System.out.println("繧ｳ繝阪け繧ｷ繝ｧ繝ｳ邨ゆｺ�");
			}
		System.out.println("try 邨ゆｺ�");
		return delete;
	}

	public boolean insert(UserBeans userBeans) throws SQLException, DBConnectException{

		//diaryDao�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB�ｿｽﾚ托ｿｽ
			userDao.connect();

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ登�ｿｽ^
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

		//diaryDao�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		UserDao userDao = new UserDao();
		boolean result = false;


		try {
			////////////////////
			//DB�ｿｽﾚ托ｿｽ
			userDao.connect();


			// user_icon�ｿｽ�ｿｽ�ｿｽw�ｿｽ閧ｳ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽﾈゑｿｽ�ｿｽ鼾�ｿｽADB�ｿｽﾉ保托ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽl�ｿｽﾉゑｿｽ�ｿｽ�ｿｽ
			// user_icon�ｿｽ�ｿｽ�ｿｽw�ｿｽ閧ｳ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽ鼾�ｿｽA�ｿｽT�ｿｽ[�ｿｽo�ｿｽ[�ｿｽﾉア�ｿｽb�ｿｽv�ｿｽ�ｿｽ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽ�ｿｽt�ｿｽ@�ｿｽC�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ尞�

			String fileName = userDao.getUserIcon(userBeans.getUserId()); //DB�ｿｽﾉ格�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽﾄゑｿｽ�ｿｽ�ｿｽuser_icon�ｿｽﾌパ�ｿｽX�ｿｽ�ｿｽ�ｿｽ謫ｾ
			System.out.println(userBeans.getUserIcon());
			System.out.println(userBeans.getUserIcon().isEmpty());
			if(userBeans.getUserIcon().isEmpty()) {
				userBeans.setUserIcon(fileName);
			}else {
				DeleteImageFile.delete(fileName);
			}

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ登�ｿｽ^
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

		//diaryDao�ｿｽ�ｿｽ�ｿｽ�ｿｽ
		UserDao userDao = new UserDao();
		boolean result =false;

		try {
			////////////////////
			//DB�ｿｽﾚ托ｿｽ
			userDao.connect();

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ登�ｿｽ^
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
			//�ｿｽ@�ｿｽﾚ托ｿｽ
			userDao.connect();

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾊの取得
	        userBean = userDao.getBy(userId);

		}catch(SQLException e) {
			//�ｿｽ@�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽﾉコ�ｿｽ�ｿｽ�ｿｽ\�ｿｽ[�ｿｽ�ｿｽ�ｿｽﾉ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�ｿｽ@�ｿｽﾚ托ｿｽ�ｿｽi�ｿｽR�ｿｽl�ｿｽN�ｿｽV�ｿｽ�ｿｽ�ｿｽ�ｿｽ)�ｿｽ�ｿｽﾂゑｿｽ�ｿｽ�ｿｽ
			userDao.close();
		}

		return userBean;

	}
}
