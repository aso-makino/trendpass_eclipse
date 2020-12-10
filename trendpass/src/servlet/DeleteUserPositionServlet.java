package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.DBConnectException;
import model.UserPositionModel;

@WebServlet("/DeleteUserPositionServlet")
public class DeleteUserPositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

		UserPositionModel userPositionModel = new UserPositionModel();
		int result = 0;

		try {
			result = userPositionModel.delete();

		} catch (DBConnectException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("UserPositionDeleteResult: " + result + "åèÇÃà íuèÓïÒÇçÌèúÇµÇ‹ÇµÇΩÅB	");
	}

}
