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

import beans.SpotBeans;
import exception.DBConnectException;
import model.FilterSpotModel;
import model.ReviewModel;

@WebServlet("/NearSpotList")
public class NearSpotListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if ("/NearSpotList".equals(request.getServletPath())){

        	//　ヘッダ情報などセット
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	String latitude = request.getParameter("latitude");
        	String longitude = request.getParameter("longitude");

        	// model生成
        	FilterSpotModel filterSpotModel = new FilterSpotModel();
        	ReviewModel reviewModel = new ReviewModel();

        	// getNearSpotList
        	List<SpotBeans> spotList = null;

			try {
				spotList = filterSpotModel.getNearSpotList(latitude,longitude);
				spotList = reviewModel.getSpotImage(spotList);
			} catch (DBConnectException e) {
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}


        	ObjectMapper mapper = new ObjectMapper();
        	Map<String, Object> resMap = new HashMap<>();

        	resMap.put("spotCount",spotList.size());
        	resMap.put("spotList",spotList);

			//　オブジェクトをJson文字列に変更
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }
	}

}
