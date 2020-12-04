package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.UserBeans;
import exception.DBConnectException;
import model.UserModel;
import java.io.File;

@WebServlet("/SignUp")
@MultipartConfig(location = "C:/Users/neco2/temporary_image")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{


		if ("/SignUp".equals(request.getServletPath())){

			request.setCharacterEncoding("utf-8");

			String name= null;
			String userId = null;
			String userName = null;
  			String userMail = null;
  			String password = null;
  			String sexString;
  			int sex = 0;
  			int birth = 0;

			try {
	        //multipart/form-dataによって提供されるこのリクエストのすべてのPart要素を取得
	        for (Part part : request.getParts()) {
	        	System.out.println("part::" + part);
	            //　名前の取得
	            for (String cd : part.getHeader("Content-Disposition").split(";")) {
	                String str = cd.trim();
	                System.out.println("str::" + str);



	                if(str.startsWith("filename")) {
	                    String str2 = str.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	                    File f = new File(str2);

	                    final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS");
	                    final Date date = new Date(System.currentTimeMillis());

	                    //　画像ファイル名に日付を追加し、保存
	                    StringBuilder sb = new StringBuilder(f.getName());
	                    name = sb.insert(sb.indexOf(".")-1, df.format(date)).toString();
	                    part.write("C:/Users/neco2/output_imgfile/" + name);
	                }

	                if(str.startsWith("name=\"description\"")) {
	                	InputStream inputStream = part.getInputStream();
	                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
	                    String jsonText = br.readLine();

	                    jsonText = jsonText.trim().replace("{", "").replace("}", "").replace(":", "").replace("\"", "").trim();
	                    String[] userInfo = jsonText.split(",", 0);

	                    userId = userInfo[0].replace("userId", "").trim();
	                    userName  = userInfo[1].replace("userName", "").trim();
	          			userMail  = userInfo[2].replace("mail", "").trim();
	          			password  = userInfo[3].replace("password", "").trim();
	          			sexString  = userInfo[4].replace("sex", "").trim();
	          			birth  = Integer.parseInt(userInfo[5].replace("birth", "").trim());

	          			System.out.println(userId);
	          			System.out.println(userName);
	          			System.out.println(userMail);
	          			System.out.println(password);
	          			System.out.println(sexString);
	          			System.out.println(birth);


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
	                }
	            }
	        }
			}catch(Exception e){
				e.printStackTrace();
			}

    		ObjectMapper mapper = new ObjectMapper();

    		UserModel userModel = new UserModel();
    		UserBeans userBeans = new UserBeans();

    		userBeans.setUserId(userId);
    		userBeans.setUserName(userName);
    		userBeans.setUserIcon(name);
    		userBeans.setMail(userMail);
    		userBeans.setPassword(password);
    		userBeans.setSex(sex);
    		userBeans.setBirth(birth);

    		boolean result = false;

			try {
				result = userModel.insert(userBeans);
			} catch (DBConnectException | SQLException e) {
				e.printStackTrace();
			}


			//�@�w�b�_���ȂǃZ�b�g
    		response.setContentType("application/json");
    		response.setHeader("Cache-Control", "nocache");
    		response.setCharacterEncoding("utf-8");

    		Map<String, Object> resMap = new HashMap<>();
    		resMap.put("result",result);

    		//�@�I�u�W�F�N�g��Json������ɕύX
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);
    	    }
    	}
}