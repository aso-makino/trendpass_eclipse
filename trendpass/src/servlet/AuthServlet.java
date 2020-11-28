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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.LoginBeans;
import exception.DBConnectException;
import model.LoginModel;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if ("/AuthServlet".equals(request.getServletPath())){

        	//　ヘッダ情報などセット
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	ObjectMapper mapper = new ObjectMapper();

        	String email = request.getParameter("email");
    		String password = request.getParameter("password");

    		LoginModel loginModel = new LoginModel();
    		LoginBeans loginBeans = new LoginBeans();

    		try {
    			loginBeans = loginModel.login(email,password);

    		}catch(SQLException e) {
    			e.printStackTrace();
    		}catch(DBConnectException e) {
    			e.printStackTrace();
    		}


    		Map<String, Object> resMap = new HashMap<>();

    		if(loginBeans != null) {
    			resMap.put("loginInfo", loginBeans);

    		}else {
    			resMap.put("loginInfo", null);
    			resMap.put("loginErrMsg","メールアドレスまたはパスワードが間違っています");

    		}


			//　オブジェクトをJson文字列に変更
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
