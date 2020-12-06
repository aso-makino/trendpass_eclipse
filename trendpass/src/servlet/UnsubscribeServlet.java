package servlet;

import java.io.IOException;

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

	//テスト用 後でPOST通信に変更
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//Androidとの通信規約
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		//データ受信
		ObjectMapper mapper = new ObjectMapper();

		//セッション開始
		HttpSession session = request.getSession();

		//仮データ
		int userId = 0000000;

	    //Modelインスタンス生成
	    UserModel userModel = new UserModel();

	    //Beansインスタンス生成
	    UserBeans userBeans = new UserBeans();

	    //退会処理メソッド
	    userBeans.setUserId(userId);
	    System.out.println("deleteUserメソッド開始");
	    boolean deleteUser = userModel.delete(userBeans);

	    System.out.println("deleteUserメソッド終了");

	    //RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test.jsp");
		//dispatcher.forward(request, response);

	    //判別
	    boolean isSelect = true;
	    if(deleteUser == false) {
	    	isSelect = false;
	    }
	    //データ送信
	    String resJson = mapper.writeValueAsString(isSelect);

		}
}
