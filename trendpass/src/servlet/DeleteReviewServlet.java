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
import model.ReviewModel;
import model.SpotModel;

@WebServlet("/DeleteReviewServlet")
public class DeleteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{



        if ("/DeleteReviewServlet".equals(request.getServletPath())){

        	//�ｿｽ@�ｿｽw�ｿｽb�ｿｽ_�ｿｽ�ｿｽ�ｿｽﾈどセ�ｿｽb�ｿｽg
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	ObjectMapper mapper = new ObjectMapper();


        	String spotId = request.getParameter("spotId");
        	String userId = request.getParameter("userId");
        	String reviewNumber = request.getParameter("reviewNumber");

        	ReviewModel reviewModel = new ReviewModel();

    		boolean result = false;

    		try {
    			result = reviewModel.delete(spotId,reviewNumber,userId);

    		} catch (DBConnectException e) {
    			e.printStackTrace();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

        	Map<String, Object> resMap = new HashMap<>();
			resMap.put("result",result);

			//�ｿｽ@�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�ｿｽJson�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾉ変更
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }


	}

}
