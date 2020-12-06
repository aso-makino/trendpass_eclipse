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
}
