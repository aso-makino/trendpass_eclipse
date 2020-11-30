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


	public List<SpotBeans> getImage(String userId ) throws SystemErrException{

		ReviewDao revDao = new ReviewDao();
		List<SpotBeans> spotList = new ArrayList<SpotBeans>();
		/*
		try{
			//縲�謗･邯�
			revDao.connect();

			//縲�讀懃ｴ｢邨先棡縺ｮ蜿門ｾ�
		    spotList = revDao.getImage(userId);

		}catch(SQLException e) {
			//縲�繧ｨ繝ｩ繝ｼ逋ｺ逕溘＠縺溷�ｴ蜷医↓繧ｳ繝ｳ繧ｽ繝ｼ繝ｫ縺ｫ繝ｭ繧ｰ繧貞�ｺ蜉帙☆繧�
			e.printStackTrace();
			throw new SystemErrException(e);

		}catch(DBConnectException e) {
			e.printStackTrace();
			throw new SystemErrException(e);

		}finally {
			//縲�謗･邯夲ｼ医さ繝阪け繧ｷ繝ｧ繝ｳ�ｼ峨ｒ髢峨§繧�
			revDao.close();
		}
		*/
		return spotList;
	}
}
