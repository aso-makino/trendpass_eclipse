package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.UserPositionBeans;
import exception.DBConnectException;
import model.PassModel;
import model.UserPositionModel;


@WebServlet("/SetPositionServlet")
public class SetPositionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if ("/SetPositionServlet".equals(request.getServletPath())){

            String userId = request.getParameter("userId");
    		String latitudeStr = request.getParameter("latitude");
    		String longitudeStr = request.getParameter("longitude");
    		String stayStartStr = request.getParameter("stayStart");

    		double latitude = Double.parseDouble(latitudeStr);
    		double longitude = Double.parseDouble(longitudeStr);

    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
    	    Date stayStart = null;
    		try {
    			stayStart = format.parse(stayStartStr);
    			System.out.println(stayStart);
    		} catch (ParseException e1) {
    			// TODO �����������ꂽ catch �u���b�N
    			e1.printStackTrace();
    		}

    		UserPositionBeans userPosition = new UserPositionBeans();
    		userPosition.setUserId(userId);
    		userPosition.setLatitude(latitude);
    		userPosition.setLongitude(longitude);
    		userPosition.setStayStart(stayStart);

    		//�@���[�U�[�ʒu���ۑ�
    		UserPositionModel userPositionModel = new UserPositionModel();
    		try {
    			userPositionModel.insert(userPosition);
    		} catch (DBConnectException e) {
    			// TODO �����������ꂽ catch �u���b�N
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO �����������ꂽ catch �u���b�N
    			e.printStackTrace();
    		}

    		//�@���ꂿ�����o�^
    		PassModel passModel = new PassModel();
    		try {
    			passModel.insert(userPosition);
    		} catch (DBConnectException e) {
    			// TODO �����������ꂽ catch �u���b�N
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO �����������ꂽ catch �u���b�N
    			e.printStackTrace();
    		}

    		//�@�w�b�_���ȂǃZ�b�g
        	response.setContentType("application/json");
        	response.setHeader("Cache-Control", "nocache");
        	response.setCharacterEncoding("utf-8");

        	ObjectMapper mapper = new ObjectMapper();
            String greet = "setPosition";

        	Map<String, Object> resMap = new HashMap<>();
			resMap.put("greet",greet);

			//�@�I�u�W�F�N�g��Json������ɕύX
			String resJson = mapper.writeValueAsString(resMap);

            PrintWriter out = response.getWriter();
            out.print(resJson);

        }
	}

}
