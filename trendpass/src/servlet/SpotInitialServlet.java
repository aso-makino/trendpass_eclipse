package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.SpotBeans;
import exception.DBConnectException;
import model.SpotModel;

/**
 * Servlet implementation class SpotInitialServlet
 */
@WebServlet("/SpotInitialServlet")
public class SpotInitialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Start initializing spot!!!");

		// input this!!!
		String input = "";

    	List<SpotBeans> list = new ArrayList<>();

        //　入力データをトリミング
        input = input.trim().replace("{", "").replace("}", "").replace("\"", "").replace("[","")
        				.replace("]", "").replace("\r", "").replace("\n", "").replace(" ", "").trim();

        // スポットのデータのみを抽出(データの個数などのヘッダー情報を除去)
        String[] datas = input.split("@attributes:order:");


        //　スポット情報を1件ずつに分解
        for(int i = 0; i < datas.length; i++) {
        	System.out.println(i + "件目のデータ///" + datas[i]);
        	String[] dataInfos = datas[i].split(",");

        	//　beansを生成（最初のデータはスルー）
        	SpotBeans spot = null;
        	if(i > 0) {
        		spot = new SpotBeans();
        	}

        	//　1件のスポット情報を項目ごとに分解
        	for(int j = 0; j < dataInfos.length; j++) {
        		System.out.println(i + " in " + j + "///" + dataInfos[j]);

        		//　各情報からDBに保存する項目を取得し、beansに格納
        		if(dataInfos[j].startsWith("name:")) {
        			String spotName = dataInfos[j].replace("name:","");
        			spot.setSpotName(spotName);

        		}else if(dataInfos[j].startsWith("latitude:")) {
        			double latitude = Double.parseDouble(dataInfos[j].replace("latitude:",""));
        			spot.setLatitude(latitude);

        		}else if(dataInfos[j].startsWith("longitude:")) {
        			double longitude = Double.parseDouble(dataInfos[j].replace("longitude:",""));
        			spot.setLongitude(longitude);;
        		}
        	}

        	if(spot!= null) {
	        	//　ジャンルは飲食店とする
	    		spot.setGenreId("1");
	        	//　リストに追加
	    		list.add(spot);
        	}
        }

        SpotModel spotModel = new SpotModel();
        int result = 0;

        try {
			result = spotModel.insert(list);
		} catch (DBConnectException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        System.out.println("スポット登録："+ result + "件のスポットを登録しました。");
    }
}
