package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.ReviewBeans;
import beans.SpotBeans;
import beans.SpotReviewBeans;
import beans.UserBeans;
import exception.DBConnectException;
import model.FilterSpotModel;
import model.ReviewModel;
import model.UserModel;


@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if ("/MyPageServlet".equals(request.getServletPath())){

        	//�@�w�b�_���ȂǃZ�b�g
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	String userId = request.getParameter("userId");

        	// model����
        	FilterSpotModel filterSpotModel = new FilterSpotModel();
        	ReviewModel reviewModel = new ReviewModel();

        	// getUserInfo
        	UserModel userModel = new UserModel();
        	UserBeans userInfo = null;
        	try {
				userInfo = userModel.getBy(userId);

        	} catch (DBConnectException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

        	// getMySpotList
        	List<SpotBeans> spotList = null;

			try {
				spotList = filterSpotModel.getMySpotList(userId);
				spotList = reviewModel.getSpotImage(spotList);
			} catch (DBConnectException e) {
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}


			// getMyReviewList
			List<SpotReviewBeans> spotReviewList = null;

			try {
				spotReviewList = reviewModel.getMyReviewList(userId);
				spotReviewList = filterSpotModel.getMyReviewSpotList(spotReviewList);
			} catch (DBConnectException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}


        	ObjectMapper mapper = new ObjectMapper();
        	Map<String, Object> resMap = new HashMap<>();

        	resMap.put("userInfo",userInfo);
        	resMap.put("spotSize",spotList.size());
        	resMap.put("spotList",spotList);
        	resMap.put("reviewSize", spotReviewList.size());
        	resMap.put("spotReviewList",spotReviewList);


			//�@�I�u�W�F�N�g��Json������ɕύX
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }
	}

}
