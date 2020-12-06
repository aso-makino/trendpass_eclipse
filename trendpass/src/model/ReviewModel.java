package model;

import java.io.PrintWriter;
import java.io.StringWriter;

import beans.ReviewBeans;
import dao.ReviewDao;


public class ReviewModel{

	/**投稿情報を登録メソッド
	 * @param userId
	 * @param spotId
	 * @param review_content
	 * @param review_title
	 * @param evaluation
	 * @return insert
	 */

	public boolean insertReview(ReviewBeans reviewBeans) {

		System.out.println("ReviewModel 到達");
		//テスト用
		String error = "modelまで";
		reviewBeans.setError(error);

		//戻り値
		boolean insert = true;


		//DAOの生成
		ReviewDao reviewDao = new ReviewDao();

		try {
			//ReviewDaoに接続
			reviewDao.connect();
			reviewBeans.setError("コネクション成功");
			System.out.println("コネクション成功");
			//メソッド呼び出し
			insert = reviewDao.insert(reviewBeans);
		}catch(Exception e){
			insert = true;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			String str = sw.toString();
			System.out.println(str);
			System.out.println("コネクション失敗");
			error = "コネクション失敗";
			reviewBeans.setError(error);
		}finally {
			//ReviewDaoとの接続を切る
			reviewDao.close();
			System.out.println("コネクション終了");
			}
		return insert;
		}
}
