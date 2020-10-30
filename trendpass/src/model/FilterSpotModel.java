package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import dao.FilterSpotDao;
import exception.DBConnectException;
import exception.SystemErrException;

public class FilterSpotModel {


	public List<SpotBeans> getList() throws SystemErrException{

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		FilterSpotDao filterSpotDao = new FilterSpotDao();

		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
		    spotList = filterSpotDao.getList();

		}catch(SQLException e) {
			//　エラー発生した場合にコンソールにログを出力する
			e.printStackTrace();
			throw new SystemErrException(e);

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw new SystemErrException(e);

		}finally {
			//　接続（コネクション）を閉じる
			filterSpotDao.close();
		}

		return spotList;
	}
}
