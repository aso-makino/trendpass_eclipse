package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.SortBeans;
import beans.SpotBeans;
import exception.SystemErrException;
import model.FilterSpotModel;


public abstract class SortSpotListServlet extends HttpServlet {
	 /**
	 *@author kurokikazuyuki
	 *@param latitude 緯度
	 *@param longitude 経度
	 *@param sex　性別
	 *@param genre ジャンル
	 *@param generation 年代
	 *@param popular 人気順
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{

		//仮データ
			 String userId = "0000001";
			 String spotId = "000000001";
			 double latitude = 33.583476;
			 double longitude = 130.421318;
		//ソート項目
			 String sex = "男";
			 boolean sexFlg = false;
			 String genre = "飲食店";
			 boolean genreFlg = false;

			 boolean popularFlg = false;
			 String generation = "10";
			 boolean generationFlg = false;
			 String minDistance = "100";
			 String maxDistance = "1000";
			 boolean distFlg = false;

			 HttpSession session = request.getSession();
			 List<SpotBeans> spotBeans = (List<SpotBeans>) session.getAttribute("spotBeans");
			 SortBeans sortBeans = new SortBeans();
			 FilterSpotModel filterSpotModel = new FilterSpotModel();
			 //ソートした場合sortBeansにソート項目を格納
			 Map<String,Boolean> sortMap = new HashMap<String,Boolean>();

			 sexFlg = sex.equals("") ? false : true;
			 genreFlg = genre.equals("") ? false : true;
			 generationFlg = generation.equals("") ? false : true;
			 distFlg = maxDistance.equals("") && minDistance.equals("")
					 ? false : true;

			 //選択しているソートをBeansとMapに入れる
			 if(sexFlg == true) {
				sortMap.put(sex,sexFlg);
				sortBeans.setSex(sex);
			 }
			 if(genreFlg == true) {
				sortMap.put(genre,genreFlg);
				sortBeans.setGenre(genre);

			 }
			 if(generationFlg == true) {
				sortMap.put(generation,generationFlg);
				//数値に変換して格納して変換
				sortBeans.setGeneration(Integer.parseInt(generation));
			 }
			 if(distFlg == true) {
				sortMap.put(maxDistance,distFlg);
				sortMap.put(minDistance,distFlg);
				//数値・メートルに変換して格納
				sortBeans.setMaxDistance(Integer.parseInt(maxDistance) * 100 );
				sortBeans.setMinDistance(Integer.parseInt(minDistance) * 100 );
			 }
			 if(popularFlg == true) {
				sortMap.put("人気順",popularFlg);
			    sortBeans.setPopularOrder(popularFlg);
			 }
			 /*
			 if(!sex.equals("")) {
				sortBeans.setSex(sex);
			}
			 if(!genre.equals("")) {
				sortBeans.setGenre(genre);
			}
			 if(!generation.equals("")) {
				sortBeans.setGeneration(generation);
			}
			 if(!distance.equals("")) {
				sortBeans.setDistance(Integer.parseInt(distance));
			}
			 if(popularFlg == true) {
			    sortBeans.setPopularOrder(popularFlg);
			}
			*/
			 if(sortBeans!=null) {
			 	try {
			 		//spotBeansの書き換え
			 		spotBeans = filterSpotModel.getSortList(latitude,longitude,sortBeans,sortMap);
			 	}catch(SystemErrException e){
			 		e.printStackTrace();
			 	}
			 }
			 //ソート終わり
			 session.setAttribute("spotBeans",spotBeans);
	  }
}
