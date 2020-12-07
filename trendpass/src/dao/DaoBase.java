package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoBase {

	protected Connection con = null;
	//テスト用後で消す
	//ReviewBeans reviewBeans = new ReviewBeans();


	public DaoBase() {
	}

	public DaoBase(Connection con) {
		this.con = con;
	}

	public void connect() {

		if (con != null) {
			//接続済みの場合は何もしない
			return;
		}

		InitialContext ctx;
		try {
			///////////////////////////////////
			//DBの接続
			String jndi = "java:comp/env/jdbc/MySQL";
			ctx = new InitialContext();

			DataSource ds = (DataSource) ctx.lookup(jndi);

			// MySQLに接続
			con = ds.getConnection();


		} catch (NamingException e) {
			//reviewBeans.setError2("NamingException");
			e.printStackTrace();
			//本当は例外をスローして上位側に処理を任せた方がよい
		} catch (SQLException e) {
			e.printStackTrace();
			//reviewBeans.setError3("NamingException");
			//本当は例外をスローして上位側に処理を任せた方がよい
		}

	}

	public void close() {

		if (con != null) {
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return con;
	}
	public void beginTranzaction() throws SQLException{
		if(con!=null) {
			con.setAutoCommit(false);
		}
	}
	public void commit() throws SQLException {
        if(con != null){
            try {
                con.commit();
            } finally{
                con.setAutoCommit(true);
            }
        }
    }
	  public void rollback() {
	        if(con != null){
	            try {
	                con.rollback();
	            }catch(SQLException e){
	                System.out.println("rollebackに失敗しました：");
	            } finally{
	                try{
	                    con.setAutoCommit(true);
	                }catch(SQLException e){
	                    System.out.println("setAutoCommitに失敗しました：");
	                }
	            }
	        }
	  }
}