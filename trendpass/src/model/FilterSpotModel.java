package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import beans.SpotReviewBeans;
import dao.FilterSpotDao;
import exception.DBConnectException;

public class FilterSpotModel {

	public List<SpotBeans> getMySpotList(String userId) throws DBConnectException, SQLException {

		FilterSpotDao filterSpotDao = new FilterSpotDao();
		List<SpotBeans> list = new ArrayList<SpotBeans>();

		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
	        list = filterSpotDao.getMySpotList(userId);

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//　接続（コネクション)を閉じる
			filterSpotDao.close();
		}

		return list;

	}

	public List<SpotReviewBeans> getMyReviewSpotList(List<SpotReviewBeans> list) throws SQLException, DBConnectException{

		FilterSpotDao filterSpotDao = new FilterSpotDao();
		List<SpotReviewBeans> result = new ArrayList<SpotReviewBeans>(list);

		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
	        result = filterSpotDao.getMySpotReviewList(list);

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//　接続（コネクション)を閉じる
			filterSpotDao.close();
		}

		return result;
	}
}
