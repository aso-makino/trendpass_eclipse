package servlet;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.DBConnectException;
import model.UserModel;

@WebServlet("/MailCheck")
public class MailCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		 if ("/MailCheck".equals(request.getServletPath())){


	        //　?mail=の部分を取得する
	        String mail = request.getParameter("mail");


	        UserModel userModel = new UserModel();
	        boolean result = false;

			try {
				result = userModel.mailCheck(mail);
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
