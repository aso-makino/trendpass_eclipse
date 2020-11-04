package servlet;

import java.io.IOException;

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
	int userId = 0000000;
	int spotId = 12;
	int review_number = 1;//？(・＿・)?口コミ番号…？？なんやこれ…
	int evaluation = Integer.parseInt(request.getParameter("evaluation"));
	String review_content = request.getParameter("review");

    //Modelインスタンス生成
    ReviewModel reviewModel = new ReviewModel();

    //Beansインスタンス生成
    ReviewBeans reviewBeans = new ReviewBeans();

    //口コミ投稿メソッド
    reviewBeans.setUserId(userId);
    reviewBeans.setSpotId(spotId);
    reviewBeans.setReviewNumber(review_number);
    reviewBeans.setReviewContent(review_content);
    reviewBeans.setEvaluation(evaluation);
    boolean insertReview = ReviewModel.insertReview(reviewBeans);

    //RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test.jsp");
	//dispatcher.forward(request, response);

    //判別
    boolean isSelect = true;
    if(insertReview == false) {
    	isSelect = false;
    }
    //データ送信
    String resJson = mapper.writeValueAsString(isSelect);

	}
}
