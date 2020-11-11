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
}