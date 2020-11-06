package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse responce)
	                                              throws ServletException,IOException{
		  RequestDispatcher dispatcher=request.getRequestDispatcher("WEB-INF/jsp/Login.jsp");
		  dispatcher.forward(request, responce);
		 }

}
