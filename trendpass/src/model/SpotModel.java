package model;

import beans.SpotBeans;
import dao.SpotDao;

public class SpotModel{

	/**スポット情報を登録メソッド
	 * @param userId
	 * @param jenreId
	 * @param spotName
	 * @param filename
	 * @return spotBeans
	 */

	public static boolean insertSpot(SpotBeans spotBeans, int userId) {

		//戻り値
		boolean insert = true;

		//DAOの生成
		SpotDao spotDao = new SpotDao();

		try {
			//SpotDaoに接続
			spotDao.connect();
			//メソッド呼び出し
			insert = spotDao.insert(spotBeans, userId);
		}catch(Exception e){
			insert = false;
			e.printStackTrace();
		}finally {
			//SpotDaoとの接続を切る
			spotDao.close();
			}
		return insert;
		}
}
