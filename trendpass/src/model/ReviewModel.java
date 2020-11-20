package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ReviewBeans;
import beans.SpotBeans;
import dao.FilterSpotDao;
import dao.ReviewDao;
import exception.DBConnectException;

public class ReviewModel {

	public List<ReviewBeans> getList(String spotId) throws DBConnectException, SQLException{


		List<ReviewBeans> list = new ArrayList<ReviewBeans>();

		ReviewDao reviewDao = new ReviewDao();

		try {

			//DBê⁄ë±
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
