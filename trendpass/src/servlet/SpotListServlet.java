package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.SpotBeans;
import exception.SystemErrException;
import model.FilterSpotModel;
import model.ReviewModel;
	/**
	 * @author kurokikazuyuki
	 *@param userId ユーザーID
     *@param spotId スポットID
     *@param latitude 緯度
     *@param longitude 経度
     **/

public abstract class SpotListServlet extends HttpServlet {


	  public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{
		 //Android側からデータを受け取る


		//仮データ
		 String userId = "00000001";
		 String spotId = "00000001";
		 double latitude = 33.583476;
		 double longitude = 130.421318;


		 FilterSpotModel filterSpotModel = new FilterSpotModel();
		 ReviewModel revModel = new ReviewModel();
		 List<SpotBeans> spotBeans = (List<SpotBeans>) new SpotBeans();

		 	try {
		 		spotBeans = filterSpotModel.getList(userId,latitude,longitude);
		 	}catch(SystemErrException e){
		 		e.printStackTrace();
		 	}
		 	//画像の取得
		 	try {
		 		spotBeans = revModel.getImage(userId);
		 	}catch(SystemErrException e){
		 		e.printStackTrace();
		 	}
		 	HttpSession session = request.getSession();
		 	session.setAttribute("spotBeans",spotBeans);


	  }
 }


