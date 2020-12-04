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
import model.SpotModel;

@WebServlet("/DeleteSpotServlet")
public class DeleteSpotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{



        if ("/DeleteSpotServlet".equals(request.getServletPath())){

        	//�@�w�b�_���ȂǃZ�b�g
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	ObjectMapper mapper = new ObjectMapper();


        	String spotId = request.getParameter("spotId");
        	String userId = request.getParameter("userId");

        	SpotModel spotModel = new SpotModel();

    		int result = 0;

    		try {
    			result = spotModel.delete(spotId,userId);

    		} catch (DBConnectException e) {
    			e.printStackTrace();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

    		String message ="";

    		if(result == 1) {
    			message="正常に削除できました";
    		}else {
    			message="削除できませんでした";
    		}


        	Map<String, Object> resMap = new HashMap<>();
			resMap.put("message",message);

			//�@�I�u�W�F�N�g��Json������ɕύX
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }






	}

}