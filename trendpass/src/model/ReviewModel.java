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

	public List<SpotReviewBeans> getMyReviewList(String userId) throws DBConnectException, SQLException {

		ReviewDao reviewDao = new ReviewDao();
		List<SpotReviewBeans> list = new ArrayList<SpotReviewBeans>();

		try{
			//�ｿｽ@�ｿｽﾚ托ｿｽ
			reviewDao.connect();

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾊの取得
	        list = reviewDao.getMyReviewList(userId);

		}catch(SQLException e) {
			//�ｿｽ@�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽﾉコ�ｿｽ�ｿｽ�ｿｽ\�ｿｽ[�ｿｽ�ｿｽ�ｿｽﾉ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�ｿｽ@�ｿｽﾚ托ｿｽ�ｿｽi�ｿｽR�ｿｽl�ｿｽN�ｿｽV�ｿｽ�ｿｽ�ｿｽ�ｿｽ)�ｿｽ�ｿｽﾂゑｿｽ�ｿｽ�ｿｽ
			reviewDao.close();
		}

		return list;

	}

	public List<SpotBeans> getSpotImage(List<SpotBeans> list) throws DBConnectException, SQLException {

		ReviewDao reviewDao = new ReviewDao();
		List<SpotBeans> result = new ArrayList<SpotBeans>(list);

		try{
			//�ｿｽ@�ｿｽﾚ托ｿｽ
			reviewDao.connect();

			//�ｿｽ@�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾊの取得
	        result = reviewDao.getSpotImage(list);

		}catch(SQLException e) {
			//�ｿｽ@�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ鼾�ｿｽﾉコ�ｿｽ�ｿｽ�ｿｽ\�ｿｽ[�ｿｽ�ｿｽ�ｿｽﾉ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽo�ｿｽﾍゑｿｽ�ｿｽ�ｿｽ
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�ｿｽ@�ｿｽﾚ托ｿｽ�ｿｽi�ｿｽR�ｿｽl�ｿｽN�ｿｽV�ｿｽ�ｿｽ�ｿｽ�ｿｽ)�ｿｽ�ｿｽﾂゑｿｽ�ｿｽ�ｿｽ
			reviewDao.close();
		}

		return result;

	}

	public List<ReviewBeans> getList(String spotId) throws DBConnectException, SQLException{


		List<ReviewBeans> list = new ArrayList<ReviewBeans>();

		ReviewDao reviewDao = new ReviewDao();

		try {

			//DB�ڑ�
			reviewDao.connect();


			list = reviewDao.getList(spotId);

		}catch(SQLException e) {
			throw e;

		}catch(DBConnectException e) {
			throw e;
		}
		finally {
			reviewDao.close();
		}

		return list;

	}
}
