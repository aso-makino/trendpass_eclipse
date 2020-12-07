package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import exception.DBConnectException;
import model.FilterSpotModel;
import model.ReviewModel;


@WebServlet("/DispSpotDetail")
public class DispSpotDetailServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException{

    	if ("/DispSpotDetail".equals(request.getServletPath())){

    		//　ヘッダ情報などセット
    		response.setContentType("application/json");
    		response.setHeader("Cache-Control", "nocache");
    		response.setCharacterEncoding("utf-8");

    		ObjectMapper mapper = new ObjectMapper();

    		String spotId  = request.getParameter("spotId");

    		FilterSpotModel filterSpotModel = new FilterSpotModel();

    		SpotBeans spotBeans = null;
			try {
				spotBeans = filterSpotModel.getBy(spotId);
			} catch (DBConnectException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

    		ReviewModel reviewModel = new ReviewModel();

    		List<ReviewBeans> list = null;
			try {
				list = reviewModel.getList(spotId);
			} catch (DBConnectException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

    		Map<String, Object> resMap = new HashMap<>();
    		resMap.put("spot",spotBeans);
    		resMap.put("review",list);
    		resMap.put("reviewCount",list.size());

    		//　オブジェクトをJson文字列に変更
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);

    	}

	}
}
