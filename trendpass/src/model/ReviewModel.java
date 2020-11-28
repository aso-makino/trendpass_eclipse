package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import beans.SpotReviewBeans;
import dao.ReviewDao;
import exception.DBConnectException;

public class ReviewModel {

	public List<SpotReviewBeans> getMyReviewList(String userId) throws DBConnectException, SQLException {

		ReviewDao reviewDao = new ReviewDao();
		List<SpotReviewBeans> list = new ArrayList<SpotReviewBeans>();

		try{
			//�@�ڑ�
			reviewDao.connect();

			//�@�������ʂ̎擾
	        list = reviewDao.getMyReviewList(userId);

		}catch(SQLException e) {
			//�@�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�@�ڑ��i�R�l�N�V����)�����
			reviewDao.close();
		}

		return list;

	}

	public List<SpotBeans> getSpotImage(List<SpotBeans> list) throws DBConnectException, SQLException {

		ReviewDao reviewDao = new ReviewDao();
		List<SpotBeans> result = new ArrayList<SpotBeans>(list);

		try{
			//�@�ڑ�
			reviewDao.connect();

			//�@�������ʂ̎擾
	        result = reviewDao.getSpotImage(list);

		}catch(SQLException e) {
			//�@�G���[���������ꍇ�ɃR���\�[���Ƀ��O���o�͂���
			e.printStackTrace();
			throw e;

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw e;

		}finally {
			//�@�ڑ��i�R�l�N�V����)�����
			reviewDao.close();
		}

		return result;

	}


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
