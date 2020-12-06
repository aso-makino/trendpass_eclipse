package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.ReviewBeans;
import model.ReviewModel;

/**
 * Servlet implementation class InsertReviewServlet
 */
@WebServlet("/InsertReviewServlet")
public class InsertReviewServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
	int userId = 1;
	int spotId = 1;
	int evaluation = Integer.parseInt(request.getParameter("evaluation"));
	String review_title = request.getParameter("title");
	String review_content = request.getParameter("review2");

	//int userId = Integer.parseInt(request.getParameter("userId"));
	//int spotId = Integer.parseInt(request.getParameter("spotId"));
	//int evaluation = Integer.parseInt(request.getParameter("evaluation"));
	//String review_title = request.getParameter("title");
	//String review_content = request.getParameter("review2");
	//String review_image = request.getPart("image");

    //Modelインスタンス生成
    ReviewModel reviewModel = new ReviewModel();

    //Beansインスタンス生成
    ReviewBeans reviewBeans = new ReviewBeans();

    //口コミ投稿メソッド
    reviewBeans.setUserId(userId);
    reviewBeans.setSpotId(spotId);
    reviewBeans.setReviewTitle(review_title);
    reviewBeans.setReviewContent(review_content);
    reviewBeans.setEvaluation(evaluation);
    //エラーテスト
    reviewBeans.setError("Servletまでしか到達してない");
    boolean insertReview = reviewModel.insertReview(reviewBeans);

    //判別
	boolean isSelect = true;
	if(insertReview == false) {
		isSelect = false;
	}

	//データ送信
	//String resJson = mapper.writeValueAsString(isSelect);

	//テスト
	request.setAttribute("isSelect", isSelect);
	request.setAttribute("error",reviewBeans.getError());//エラー確認
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test2.jsp");
  	dispatcher.forward(request, response);

	}
}
