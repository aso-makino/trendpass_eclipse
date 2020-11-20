package model;

import java.sql.SQLException;

import beans.SpotBeans;
import dao.FilterSpotDao;
import exception.DBConnectException;

public class FilterSpotModel {

	public SpotBeans getBy(String spotId) throws DBConnectException, SQLException{


		FilterSpotDao filterSpotDao = new FilterSpotDao();

		SpotBeans spotBeans = new SpotBeans();

		try {

			//DBê⁄ë±
			filterSpotDao.connect();


			spotBeans = filterSpotDao.getBy(spotId);

		}catch(SQLException e) {
			throw e;

		}catch(DBConnectException e) {
			throw e;
		}
		finally {
			filterSpotDao.close();
		}

		return spotBeans;

	}

}
