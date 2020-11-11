package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotReviewBeans;
import dao.ReviewDao;
import exception.DBConnectException;

public class ReviewModel {

	public List<SpotReviewBeans> getMyReviewList(String userId) throws DBConnectException, SQLException {

		ReviewDao reviewDao = new ReviewDao();
		List<SpotReviewBeans> list = new ArrayList<SpotReviewBeans>();

		try{
			//　接続
			reviewDao.connect();

			//　検索結果の取得
	        list = reviewDao.getMyReviewList(userId);

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//　接続（コネクション)を閉じる
			reviewDao.close();
		}

		return list;

		}
}