package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SpotBeans;
import beans.SpotReviewBeans;
import dao.ReviewDao;
import exception.DBConnectException;
import exception.SystemErrException;

public class ReviewModel {

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
