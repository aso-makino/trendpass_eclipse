package model;

import java.io.PrintWriter;
import java.io.StringWriter;

import beans.SpotBeans;
import dao.SpotDao;

public class SpotModel{

	/**スポット情報を登録メソッド
	 * @param userId
	 * @param jenreId
	 * @param spotName
	 * @param spotReview
	 * @param ratingBar
	 * @param filename
	 * @param longitude
	 * @param latitude
	 * @return insert
	 */

	public static boolean insertSpot(SpotBeans spotBeans, int userId) {

		//テスト用
		System.out.println("SpotModel 到達");

		//戻り値
		boolean insert = true;

		//DAOの生成
		SpotDao spotDao = new SpotDao();

		try {
			//SpotDaoに接続
			System.out.println("try 開始");

			spotDao.connect();

			System.out.println("コネクション成功");

			//メソッド呼び出し
			insert = spotDao.insert(spotBeans, userId);
		}catch(Exception e){
			insert = true;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			String str = sw.toString();
			System.out.println(str);
			System.out.println("コネクション失敗");

		}finally {
			//SpotDaoとの接続を切る
			spotDao.close();
			System.out.println("コネクション終了");
			}
		System.out.println("try 終了");
		return insert;
		}
}
