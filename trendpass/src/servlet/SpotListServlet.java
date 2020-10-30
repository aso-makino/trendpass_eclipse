package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.SpotBeans;
import exception.SystemErrException;
import model.FilterSpotModel;
/**
 * 周辺スポットを一覧表示するクラス
 */

public class SpotListServlet extends HttpServlet {
	  public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{
		//仮データ
		 String userId = "00000001";
		 String spotId = "00000001";
		 double latitude = 33.583476;
		 double longitude = 130.421318;

		 FilterSpotModel filterSpotModel = new FilterSpotModel();
		 List<SpotBeans> spotListBeans = (List<SpotBeans>) new SpotBeans();
		 	try {
		 		spotListBeans = filterSpotModel.getList();
		 	}catch(SystemErrException e){
		 		e.printStackTrace();
				throw e;
		 	}
		//  ReviewModel/getImage,FilterSpotModel/getList

		//  ReviewModel/getImage,FilterSpotModel/getSortList

	  }
 }


