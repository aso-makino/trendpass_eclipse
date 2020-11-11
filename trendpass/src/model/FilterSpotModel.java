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
}
