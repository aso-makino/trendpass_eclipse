package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.UserBeans;
import exception.DBConnectException;
import model.UserModel;

@WebServlet("/GetUserInfo")
public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{


	    	if ("/GetUserInfo".equals(request.getServletPath())){

	       		request.setCharacterEncoding( "UTF-8" );

	    		String userId = null;


	    	    try{
	    	    	//. JSON テキストを全部取り出す
	    	    	BufferedReader br = new BufferedReader( request.getReader() );
	    	      	String jsonText = br.readLine();


	    	      	//. JSON オブジェクトに変換
	    	      	JSONParser parser = new JSONParser();
	    	      	JSONObject jsonObj = (JSONObject)parser.parse(jsonText);

	    	      	userId  = ( String )jsonObj.get("userId");
	    	    }catch( Exception e ){
	    	   		e.printStackTrace();
	    	    }


	    		//　ヘッダ情報などセット
	    		response.setContentType("application/json");
	    		response.setHeader("Cache-Control", "nocache");
	    		response.setCharacterEncoding("utf-8");

	    		ObjectMapper mapper = new ObjectMapper();

	    		UserModel userModel = new UserModel();

	    		UserBeans userBeans = new UserBeans();

				try {
					userBeans = userModel.getUserInfo(userId);
				} catch (DBConnectException | SQLException e) {
					e.printStackTrace();
				}


	    		Map<String, Object> resMap = new HashMap<>();
	    		resMap.put("user",userBeans);

	    		//　オブジェクトをJson文字列に変更
	    		String resJson = mapper.writeValueAsString(resMap);

	    		PrintWriter outPW = response.getWriter();
	    		outPW.print(resJson);

	    	}
	}
}