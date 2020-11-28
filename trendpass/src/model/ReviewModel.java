package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import exception.DBConnectException;
import exception.SystemErrException;

public class ReviewModel {
	public List<SpotBeans> getImage(String userId ) throws SystemErrException{

		ReviewDao revDao = new ReviewDao();
		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		try{
			//　接続
			revDao.connect();

			//　検索結果の取得
		    spotList = revDao.getImage(userId);

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw new SystemErrException(e);

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw new SystemErrException(e);

		}finally {
			//　接続（コネクション）を閉じる
			revDao.close();
		}

		return spotList;
	}
}
