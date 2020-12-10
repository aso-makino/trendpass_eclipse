package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exception.DBConnectException;

public class DaoBase {

	protected Connection con = null;
	//繝�繧ｹ繝育畑蠕後〒豸医☆
	//ReviewBeans reviewBeans = new ReviewBeans();


	public DaoBase() {
	}

	public DaoBase(Connection con) {
		this.con = con;
	}

	public void connect() throws DBConnectException{

		if (con != null) {
			//謗･邯壽ｸ医∩縺ｮ蝣ｴ蜷医�ｯ菴輔ｂ縺励↑縺�
			return;
		}

		InitialContext ctx;
		try {
			///////////////////////////////////
			//DB縺ｮ謗･邯�
			String jndi = "java:comp/env/jdbc/MySQL";
			ctx = new InitialContext();

			DataSource ds = (DataSource) ctx.lookup(jndi);

			// MySQL縺ｫ謗･邯�
			con = ds.getConnection();


		} catch (NamingException e) {
			//reviewBeans.setError2("NamingException");
			e.printStackTrace();
			throw new DBConnectException(e);
			//譛ｬ蠖薙�ｯ萓句､悶ｒ繧ｹ繝ｭ繝ｼ縺励※荳贋ｽ榊�ｴ縺ｫ蜃ｦ逅�繧剃ｻｻ縺帙◆譁ｹ縺後ｈ縺�
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBConnectException(e);
			//reviewBeans.setError3("NamingException");
			//譛ｬ蠖薙�ｯ萓句､悶ｒ繧ｹ繝ｭ繝ｼ縺励※荳贋ｽ榊�ｴ縺ｫ蜃ｦ逅�繧剃ｻｻ縺帙◆譁ｹ縺後ｈ縺�
		}catch(Exception e) {
			e.printStackTrace();
			throw new DBConnectException(e);
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
	                System.out.println("rolleback縺ｫ螟ｱ謨励＠縺ｾ縺励◆�ｼ�");
	            } finally{
	                try{
	                    con.setAutoCommit(true);
	                }catch(SQLException e){
	                    System.out.println("setAutoCommit縺ｫ螟ｱ謨励＠縺ｾ縺励◆�ｼ�");
	                }
	            }
	        }
	  }
}