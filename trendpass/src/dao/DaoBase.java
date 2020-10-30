package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exception.DBConnectException;

public class DaoBase {

	protected Connection con = null;

	public void connect() throws DBConnectException{

		if( con != null ){
			return;
		}

		InitialContext context = null;

		try {
			String jndi = "java:comp/env/jdbc/MySQL";

			context = new InitialContext();

			DataSource dataSource = (DataSource) context.lookup(jndi);

			con = dataSource.getConnection();

		}catch(NamingException e) {
			throw new DBConnectException(e);
		}catch(SQLException e) {
			throw new DBConnectException(e);
		}catch(Exception e) {
			throw new DBConnectException(e);
		}

	}


	public void close(){

		if( con != null ){
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				System.out.println("close");
			}
		}
	}

}
