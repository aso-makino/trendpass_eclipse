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
	 *@param latitude 邱ｯ蠎ｦ
	 *@param longitude 邨悟ｺｦ
	 *@param sex縲�諤ｧ蛻･
	 *@param genre 繧ｸ繝｣繝ｳ繝ｫ
	 *@param generation 蟷ｴ莉｣
	 *@param popular 莠ｺ豌鈴��
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{

		 String userId = "0000001";
		 String spotId = "000000001";
		 double latitude = 33.583476;
		 double longitude = 130.421318;
	//繧ｽ繝ｼ繝磯��逶ｮ
		 String sex = "逕ｷ";
		 boolean sexFlg = false;
		 String genre = "鬟ｲ鬟溷ｺ�";
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
		 //繧ｽ繝ｼ繝医＠縺溷�ｴ蜷�sortBeans縺ｫ繧ｽ繝ｼ繝磯��逶ｮ繧呈�ｼ邏�
		 Map<String,Boolean> sortMap = new HashMap<String,Boolean>();

		 sexFlg = sex.equals("") ? false : true;
		 genreFlg = genre.equals("") ? false : true;
		 generationFlg = generation.equals("") ? false : true;
		 distFlg = maxDistance.equals("") && minDistance.equals("")
				 ? false : true;

		 //驕ｸ謚槭＠縺ｦ縺�繧九た繝ｼ繝医ｒBeans縺ｨMap縺ｫ蜈･繧後ｋ
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
			//謨ｰ蛟､縺ｫ螟画鋤縺励※譬ｼ邏阪＠縺ｦ螟画鋤
			sortBeans.setGeneration(Integer.parseInt(generation));
		 }
		 if(distFlg == true) {
			sortMap.put(maxDistance,distFlg);
			sortMap.put(minDistance,distFlg);
			//謨ｰ蛟､繝ｻ繝｡繝ｼ繝医Ν縺ｫ螟画鋤縺励※譬ｼ邏�
			sortBeans.setMaxDistance(Integer.parseInt(maxDistance) * 100 );
			sortBeans.setMinDistance(Integer.parseInt(minDistance) * 100 );
		 }
		 if(popularFlg == true) {
			sortMap.put("莠ｺ豌鈴��",popularFlg);
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
		 		//spotBeans縺ｮ譖ｸ縺肴鋤縺�
		 		spotBeans = filterSpotModel.getSortList(latitude,longitude,sortBeans,sortMap);
		 	}catch(SystemErrException e){
		 		e.printStackTrace();
		 	}
		 }
		 //繧ｽ繝ｼ繝育ｵゅｏ繧�
		 session.setAttribute("spotBeans",spotBeans);
	  }
}
