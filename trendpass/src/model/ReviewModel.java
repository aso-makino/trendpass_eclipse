package model;

import beans.ReviewBeans;
import dao.ReviewDao;


public class ReviewModel{

	/**投稿情報を登録メソッド
	 * @param userId
	 * @param spotId
	 * @param reviewContent
	 * @param evaluation
	 * @return spotBeans
	 */

	public static boolean insertReview(ReviewBeans reviewBeans) {

		//戻り値
		boolean insert = true;

		//DAOの生成
		ReviewDao reviewDao = new ReviewDao();

		try {
			//ReviewDaoに接続
			reviewDao.connect();
			//メソッド呼び出し
			insert = reviewDao.insert(reviewBeans);
		}catch(Exception e){
			insert = false;
			e.printStackTrace();
		}finally {
			//ReviewDaoとの接続を切る
			reviewDao.close();
			}
		return insert;
		}
}
