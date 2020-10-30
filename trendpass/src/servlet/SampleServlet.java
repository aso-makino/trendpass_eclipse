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

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/SampleServlet")
public class SampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    public void doGet(HttpServletRequest request,HttpServletResponse response)
	    		throws ServletException, IOException{

	    	String a = request.getServletPath();
	        if ("/SampleServlet".equals(request.getServletPath())){

	        	//�@�w�b�_���ȂǃZ�b�g
	        	response.setContentType("application/json");
	        	response.setHeader("Cache-Control", "nocache");
	        	response.setCharacterEncoding("utf-8");

	        	ObjectMapper mapper = new ObjectMapper();

	        	String name  = request.getParameter("name");
	            String greet = "こんにちは" + name + "さん";

	        	Map<String, Object> resMap = new HashMap<>();
				resMap.put("str",greet);

				//�@�I�u�W�F�N�g��Json������ɕύX
				String resJson = mapper.writeValueAsString(resMap);

	            PrintWriter out = response.getWriter();
	            out.print(resJson);

	        }

	    }

}
