package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.ReviewBeans;
import model.ReviewModel;

/**
 * Servlet implementation class InsertReviewServlet
 */
@WebServlet("/InsertReviewServlet")
@MultipartConfig(location = "C:/Users/neco2/temporary_image")
public class InsertReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name= null;
		String userId = null;
		String spotId = null;
		String reviewContent = null;
		String rating = null;
		try {
	        //multipart/form-dataによって提供されるこのリクエストのすべてのPart要素を取得
	        for (Part part : request.getParts()) {
	        	System.out.println("part::" + part);
	            //　名前の取得
	            for (String cd : part.getHeader("Content-Disposition").split(";")) {
	                String str = cd.trim();
	                System.out.println("str::" + str);


	                if(str.startsWith("filename")) {
	                    String str2 = str.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	                    File f = new File(str2);

	                    final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS");
	                    final Date date = new Date(System.currentTimeMillis());

	                    //　画像ファイル名に日付を追加し、保存
	                    StringBuilder sb = new StringBuilder(f.getName());
	                    name = sb.insert(sb.indexOf(".")-1, df.format(date)).toString();
	                    part.write("C:/Users/neco2/output_imgfile/" + name);
	                }

	                if(str.startsWith("name=\"description\"")) {
	                	InputStream inputStream = part.getInputStream();
	                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
	                    String jsonText = br.readLine();

	                    jsonText = jsonText.trim().replace("{", "").replace("}", "").replace(":", "").replace("\"", "").trim();
	                    String[] userInfo = jsonText.split(",", 0);

	                    userId = userInfo[0].replace("userId", "").trim();
	                    spotId  = userInfo[1].replace("spotId", "").trim();
	          			rating  = userInfo[2].replace("rating", "").trim();
	          			reviewContent  = userInfo[3].replace("reviewContent", "").trim();

	                }
	            }
	        }
			}catch(Exception e){
				e.printStackTrace();
			}
	//Android縺ｨ縺ｮ騾壻ｿ｡隕冗ｴ�
	response.setContentType("application/json");
	response.setHeader("Cache-Control", "nocache");
	response.setCharacterEncoding("utf-8");

	//繝�繝ｼ繧ｿ蜿嶺ｿ｡
	ObjectMapper mapper = new ObjectMapper();

    //Model繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
    ReviewModel reviewModel = new ReviewModel();

    //Beans繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
    ReviewBeans reviewBeans = new ReviewBeans();

    //蜿｣繧ｳ繝滓兜遞ｿ繝｡繧ｽ繝�繝�
    reviewBeans.setUserId(userId);
    reviewBeans.setSpotId(spotId);
    reviewBeans.setReviewContent(reviewContent);
    reviewBeans.setEvaluation(rating);
    reviewBeans.setReviewImage(name);
    //繧ｨ繝ｩ繝ｼ繝�繧ｹ繝�
    boolean insertReview = reviewModel.insertReview(reviewBeans);


  //Android縺ｨ縺ｮ騾壻ｿ｡隕冗ｴ�
	response.setContentType("application/json");
	response.setHeader("Cache-Control", "nocache");
	response.setCharacterEncoding("utf-8");


	//繝�繧ｹ繝�
	Map<String, Object> resMap = new HashMap<>();
	resMap.put("result",insertReview);
    //繝�繝ｼ繧ｿ騾∽ｿ｡
    String resJson = mapper.writeValueAsString(resMap);

    PrintWriter out = response.getWriter();
    out.print(resJson);
	}
}
