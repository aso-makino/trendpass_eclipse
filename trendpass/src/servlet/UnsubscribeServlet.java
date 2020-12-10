package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.UserBeans;
import model.UserModel;

/**
 * Servlet implementation class Unsubscribe
 */
@WebServlet("/Unsubscribe")
public class UnsubscribeServlet extends HttpServlet {

	//繝�繧ｹ繝育畑 蠕後〒POST騾壻ｿ｡縺ｫ螟画峩
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Android縺ｨ縺ｮ騾壻ｿ｡隕冗ｴ�
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		//繝�繝ｼ繧ｿ蜿嶺ｿ｡
		ObjectMapper mapper = new ObjectMapper();

		//莉ｮ繝�繝ｼ繧ｿ
		String userId = request.getParameter("userId");

		System.out.println(userId);
	    //Model繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
	    UserModel userModel = new UserModel();

	    //Beans繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
	    UserBeans userBeans = new UserBeans();

	    //騾�莨壼�ｦ逅�繝｡繧ｽ繝�繝�
	    userBeans.setUserId(userId);
	    System.out.println("deleteUser繝｡繧ｽ繝�繝蛾幕蟋�");
	    boolean deleteUser = userModel.delete(userBeans);

	    System.out.println("deleteUser繝｡繧ｽ繝�繝臥ｵゆｺ�");

	    Map<String, Object> resMap = new HashMap<>();
		resMap.put("result",deleteUser);
	    //繝�繝ｼ繧ｿ騾∽ｿ｡
	    String resJson = mapper.writeValueAsString(resMap);

        PrintWriter out = response.getWriter();
        out.print(resJson);

		}
}
