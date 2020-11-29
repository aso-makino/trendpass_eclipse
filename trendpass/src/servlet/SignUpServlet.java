package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException{


    	if ("/SignUp".equals(request.getServletPath())){

    		System.out.println("3");

    		request.setCharacterEncoding( "UTF-8" );

    		String userName = null;
  			String userMail = null;
  			String password = null;
  			String sexString;
  			int sex = 0;
  			int birth = 0;
  			String userIcon = null;

    	    try{
    	    	//. JSON テキストを全部取り出す
    	    	BufferedReader br = new BufferedReader( request.getReader() );
    	      	String jsonText = br.readLine();
    	      	System.out.println( jsonText );

    	      	//. JSON オブジェクトに変換
    	      	JSONParser parser = new JSONParser();
    	      	JSONObject jsonObj = (JSONObject)parser.parse(jsonText);

    	      	userName  = ( String )jsonObj.get("userName");
      			userMail  = ( String )jsonObj.get("userMail");
      			password  = ( String )jsonObj.get("password");
      			sexString  = ( String )jsonObj.get("sex");

      			switch (sexString){
                case "男性":
                    sex = 1;
                    break;
                case "女性":
                	sex = 2;
                    break;
                case "未選択":
                	sex = 3;
                    break;
            }
      			birth  = Integer.parseInt(( String )jsonObj.get("birth"));
      		userIcon  = ( String )jsonObj.get("userIcon");

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

    		userBeans.setUserName(userName);
    		userBeans.setMail(userMail);
    		userBeans.setPassword(password);
    		userBeans.setSex(sex);
    		userBeans.setBirth(birth);
    		userBeans.setUserIcon(userIcon);

    		boolean result = false;

			try {
				result = userModel.insert(userBeans);
			} catch (DBConnectException | SQLException e) {
				e.printStackTrace();
			}



    		Map<String, Object> resMap = new HashMap<>();
    		resMap.put("result",result);

    		//　オブジェクトをJson文字列に変更
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);
    	    }
    	}
}