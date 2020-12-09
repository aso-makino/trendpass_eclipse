package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SortBeans;
import beans.SpotBeans;
import exception.DBConnectException;
import exception.SystemErrException;
import model.FilterSpotModel;
import model.ReviewModel;

@WebServlet("SortSpotListServlet")
public class SortSpotListServlet extends HttpServlet {
	 //ソート項目
	 String genre,sex,generation,minDist,maxDist;

	 /**
	 *@author kurokikazuyuki
	 *@param latitude 緯度
	 *@param longitude 経度
	 *@param sex 性別
	 *@param genre ジャンル
	 *@param generation 年代｣
	 *@param popular 人気順
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{

		// String userId = "0000001";
		// String spotId = "000000001";
		 double latitude = 33.583476;
		 double longitude = 130.421318;

	     String userId = request.getParameter("userId");

		 if(request.getParameter("genre") != null) {
		   genre = request.getParameter("genre");
		 }
		 if(request.getParameter("sex") != null) {
		   sex = request.getParameter("sex");
		 }
		 if(request.getParameter("genre") != null) {
		   generation = request.getParameter("generation") ;
		 }
		 if(request.getParameter("minDist") != null && request.getParameter("maxDist") != null) {
		   minDist = request.getParameter("minDist");
		   maxDist = request.getParameter("maxDist");
		 }
		 boolean isPopular = Boolean.valueOf ( request.getParameter("maxDist")  ) ;


		 HttpSession session = request.getSession();
		 List<SpotBeans> spotList = (List<SpotBeans>) session.getAttribute("spotBeans");
		 SortBeans sortBeans = new SortBeans();
		 FilterSpotModel filterSpotModel = new FilterSpotModel();
		 ReviewModel revModel = new ReviewModel();
		//ソート項目をソートビーンズに入れる
		 if(genre != null) {
			sortBeans.setSex(genre);
		 }
		 if(sex != null) {
			sortBeans.setGenre(sex);
		 }
		 if(generation != null) {
			//謨ｰ蛟､縺ｫ螟画鋤縺励※譬ｼ邏阪＠縺ｦ螟画鋤
			sortBeans.setGeneration(Integer.parseInt(generation));
		 }
		// kmに変換するため 1000かける
		 if(minDist != null && maxDist != null) {
			sortBeans.setMaxDistance(Integer.parseInt(maxDist) * 1000 );
			sortBeans.setMinDistance(Integer.parseInt(minDist) * 1000 );
		 }
		 if(isPopular) {
		    sortBeans.setPopularOrder(isPopular);
		 }

		 if(sortBeans!=null) {
		 	try {
		 		spotList = filterSpotModel.getSortList(latitude,longitude,userId,sortBeans);
			 	spotList = revModel.getSpotImage(spotList);
		 	}catch(SystemErrException | DBConnectException | SQLException e){
		 		e.printStackTrace();
		 	}
		 }

			ObjectMapper mapper = new ObjectMapper();
	       	Map<String, Object> resMap = new HashMap<>();

	       	resMap.put("spotList",spotList);
	       	resMap.put("spotSize",spotList.size() );

	       	String resJson = mapper.writeValueAsString(resMap);

	        PrintWriter out = response.getWriter();
	        out.print(resJson);
	  }
}
