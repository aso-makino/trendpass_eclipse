package servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SpotBeans;
import model.SpotModel;

/**
 * Servlet implementation class InsertReviewServlet
 */
@WebServlet("/InsertSpot")
@MultipartConfig(location = "/data/upload_tmp")
public class InsertSpotServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;


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
		//spotIdはAutoIncrementのため不要
		int userId = 0000000;
		String spotName = "博多駅";
		String genreId = "00";
		Part spotImage = request.getPart("image");
		InputStream inputStream = spotImage.getInputStream();

		//spotImage.write("image/test.file");
		//LatLng positionInfo; 位置情報

		//画像ストリームの取得
		byte[] bytes = convertInputStreamToByteArray(inputStream);

        //Modelインスタンス生成
        SpotModel spotModel = new SpotModel();

        //Beansインスタンス生成
        SpotBeans spotBeans = new SpotBeans();

        //スポット投稿メソッド
        spotBeans.setGenreId(genreId);
        spotBeans.setSpotName(spotName);
        boolean insertSpot = SpotModel.insertSpot(spotBeans,userId,inputStream);

        //RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test.jsp");
		//dispatcher.forward(request, response);

        //判別
        boolean isSelect = true;
        if(insertSpot == false) {
        	isSelect = false;
        }
        //データ送信
        String resJson = mapper.writeValueAsString(isSelect);


	}
	 //InputStreamをByte配列にする
    public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16777215];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
