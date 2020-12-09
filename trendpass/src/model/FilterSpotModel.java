package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SortBeans;
import beans.SpotBeans;
import beans.SpotReviewBeans;
import dao.FilterSpotDao;
import exception.DBConnectException;
import exception.SystemErrException;

public class FilterSpotModel {

	public List<SpotBeans> getMySpotList(String userId) throws DBConnectException, SQLException {

		FilterSpotDao filterSpotDao = new FilterSpotDao();
		List<SpotBeans> list = new ArrayList<SpotBeans>();

		try{
			//�@�ڑ�
			filterSpotDao.connect();

			//�@�������ʂ̎擾
	        list = filterSpotDao.getMySpotList(userId);

		}catch(SQLException e) {
			//�@�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�@�ڑ��i�R�l�N�V����)�����
			filterSpotDao.close();
		}

		return list;

	}

	public List<SpotReviewBeans> getMyReviewSpotList(List<SpotReviewBeans> list) throws SQLException, DBConnectException{

		FilterSpotDao filterSpotDao = new FilterSpotDao();
		List<SpotReviewBeans> result = new ArrayList<SpotReviewBeans>(list);

		try{
			//�@�ڑ�
			filterSpotDao.connect();

			//�@�������ʂ̎擾
	        result = filterSpotDao.getMySpotReviewList(list);

		}catch(SQLException e) {
			//�@�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�@�ڑ��i�R�l�N�V����)�����
			filterSpotDao.close();
		}

		return result;
	}

	public List<SpotBeans> getList(String userId,double latitude, double longitude) throws SystemErrException{

		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		FilterSpotDao filterSpotDao = new FilterSpotDao();

		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
		    spotList = filterSpotDao.getList(userId,latitude,longitude);

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


	public List<SpotBeans> getSortList(double latitude, double longitude, String userId, SortBeans sortBeans) throws SystemErrException {
		List<SpotBeans> sortListBeans = new ArrayList<SpotBeans>();
		FilterSpotDao filterSpotDao = new FilterSpotDao();
		try{
			//　接続
			filterSpotDao.connect();

			//　検索結果の取得
		    sortListBeans = filterSpotDao.getSortList(latitude,longitude,userId,sortBeans);

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
