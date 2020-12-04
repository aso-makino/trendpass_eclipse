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
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.DBConnectException;
import model.UserModel;

@WebServlet("/MailCheck")
public class MailCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		 if ("/MailCheck".equals(request.getServletPath())){

			 String mail  = "";
			 String userId  = "";


		//. JSON テキストを全部取り出す
 	    	BufferedReader br = new BufferedReader(request.getReader());
 	      	String jsonText = br.readLine();

 	      	//. JSON オブジェクトに変換
 	      	JSONParser parser = new JSONParser();
 	      	JSONObject jsonObj;
			try {
				jsonObj = (JSONObject)parser.parse(jsonText);
				mail  = ( String )jsonObj.get("mail");
	   			userId  = ( String )jsonObj.get("userId");

			} catch (ParseException e1) {
				e1.printStackTrace();
			}


	        UserModel userModel = new UserModel();
	        boolean result = false;

			try {
				result = userModel.mailCheck(mail,userId);
			} catch (DBConnectException | SQLException e) {
				e.printStackTrace();
			}

	         //　ヘッダ情報などセット
	    		response.setContentType("application/json");
	    		response.setHeader("Cache-Control", "nocache");
	    		response.setCharacterEncoding("utf-8");

	    		ObjectMapper mapper = new ObjectMapper();

	            Map<String, Object> resMap = new HashMap<>();
	    		resMap.put("res",result);

	    		//　オブジェクトをJson文字列に変更
	    		String resJson = mapper.writeValueAsString(resMap);

	    		PrintWriter outPW = response.getWriter();
	    		outPW.print(resJson);

		 }
	   }
}
