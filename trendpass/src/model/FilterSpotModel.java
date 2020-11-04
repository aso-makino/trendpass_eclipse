package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import beans.SortBeans;
import beans.SpotBeans;
import dao.FilterSpotDao;
import exception.DBConnectException;
import exception.SystemErrException;

public class FilterSpotModel {


	public List<SpotBeans> getList(String userId,double latitude, double longitude) throws SystemErrException{

		List<SpotBeans> spotListBeans = new ArrayList<SpotBeans>();
		FilterSpotDao filterSpotDao = new FilterSpotDao();

		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
		    spotListBeans = filterSpotDao.getList(userId,latitude,longitude);

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

		return spotListBeans;
	}


	public List<SpotBeans> getSortList(double latitude, double longitude, SortBeans sortBeans, Map<String, Boolean> sortMap) throws SystemErrException {
		List<SpotBeans> sortListBeans = new ArrayList<SpotBeans>();
		FilterSpotDao filterSpotDao = new FilterSpotDao();
		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
		    sortListBeans = filterSpotDao.getSortList(latitude,longitude,sortBeans,sortMap);

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
		return sortListBeans;
	}
}
