package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.UserPositionBeans;
import exception.DBConnectException;
import model.PassModel;
import model.UserPositionModel;


@WebServlet("/SetPositionServlet")
public class SetPositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if ("/SetPositionServlet".equals(request.getServletPath())){

            String userId = request.getParameter("userId");
    		String latitudeStr = request.getParameter("latitude");
    		String longitudeStr = request.getParameter("longitude");
    		String stayStartStr = request.getParameter("stayStart");

    		double latitude = Double.parseDouble(latitudeStr);
    		double longitude = Double.parseDouble(longitudeStr);

    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
    	    Date stayStart = null;
    		try {
    			stayStart = format.parse(stayStartStr);
    			System.out.println(stayStart);
    		} catch (ParseException e1) {
    			// TODO 自動生成された catch ブロック
    			e1.printStackTrace();
    		}

    		UserPositionBeans userPosition = new UserPositionBeans();
    		userPosition.setUserId(userId);
    		userPosition.setLatitude(latitude);
    		userPosition.setLongitude(longitude);
    		userPosition.setStayStart(stayStart);

    		//　ユーザー位置情報保存
    		UserPositionModel userPositionModel = new UserPositionModel();
    		try {
    			userPositionModel.insert(userPosition);
    		} catch (DBConnectException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		}

    		//　すれちがい登録
    		PassModel passModel = new PassModel();
    		try {
    			passModel.insert(userPosition);
    		} catch (DBConnectException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		}

    		//　ヘッダ情報などセット
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	ObjectMapper mapper = new ObjectMapper();
            String greet = "setPosition";

        	Map<String, Object> resMap = new HashMap<>();
			resMap.put("greet",greet);

			//　オブジェクトをJson文字列に変更
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }
	}

}
