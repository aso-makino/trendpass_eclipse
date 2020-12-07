package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SpotBeans;
import exception.DBConnectException;
import exception.SystemErrException;
import model.FilterSpotModel;
import model.ReviewModel;


@WebServlet("/SpotListServlet")
public class SpotListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");
		double latitude = 33.583476;
		double longitude = 130.421318;

		FilterSpotModel filterSpotModel = new FilterSpotModel();
		ReviewModel revModel = new ReviewModel();
		List<SpotBeans> spotList = new ArrayList<SpotBeans>();

		try {
			spotList = filterSpotModel.getList(userId,latitude,longitude);
		 	spotList = revModel.getSpotImage(spotList);
		}catch(SystemErrException | DBConnectException | SQLException e){
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
       	Map<String, Object> resMap = new HashMap<>();

       	resMap.put("spotSize",spotList.size());
       	resMap.put("spotList",spotList);


		//　オブジェクトをJson文字列に変更
		String resJson = mapper.writeValueAsString(resMap);

        PrintWriter out = response.getWriter();
        out.print(resJson);
	}

}
