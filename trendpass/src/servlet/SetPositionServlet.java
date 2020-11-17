package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserPositionBeans;


@WebServlet("/SetPositionServlet")
public class SetPositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");
		String latitudeStr = request.getParameter("latitude");
		String longitudeStr = request.getParameter("longitude");
		String stayStartStr = request.getParameter("stayStart");
		String stayEndStr = request.getParameter("starEnd");

		double latitude = Double.parseDouble(latitudeStr);
		double longitude = Double.parseDouble(longitudeStr);

		long stayStart = Long.parseLong(stayStartStr);
		long stayEnd = Long.parseLong(stayEndStr);

		UserPositionBeans userPosition = new UserPositionBeans();
		userPosition.setUserId(userId);
		userPosition.setLatitude(latitude);
		userPosition.setLongitude(longitude);
		userPosition.setStayStart(stayStart);
		userPosition.setStayEnd(stayEnd);
		
		
		

	}

}
