package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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
@MultipartConfig(location = "")
public class InsertSpotServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

@Override
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
		String genreId = "0";
		//Part spotImage = request.getPart("image");
		//InputStream inputStream = spotImage.getInputStream();
		//spotImage.write("image/test.file");
		//LatLng positionInfo; 位置情報

		//画像の文字列のパラメータを取得
		String title = getStringParamFromPart(request);

		//Windowsとmacのパスの差を吸収
		String outputDir = System.getProperty("user.home")+"/output_imgfile";

		//送られてきた画像の情報を取得
		Part part = request.getPart("image");

		//ファイル情報取得
		String filename = this.getFileName(part);

		//出力フォルダ作成
		makeDir(outputDir);

		//画像をoutputDirで指定した場所へコピー
		part.write(outputDir + "/" + filename);

		//画像ストリームの取得
		//byte[] inputStream_byte = convertInputStreamToByteArray(inputStream);

        //Modelインスタンス生成
        SpotModel spotModel = new SpotModel();

        //Beansインスタンス生成
        SpotBeans spotBeans = new SpotBeans();

        //スポット投稿メソッド
        spotBeans.setGenreId(genreId);
        spotBeans.setSpotName(spotName);
        spotBeans.setFilename(filename);
        boolean insertSpot = SpotModel.insertSpot(spotBeans,userId);

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
	/*
    public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16777215];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    */
	/**
     * アップロードされたファイル名をヘッダ情報より取得する
     * @param part
     * @return
     */
    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }
    /**
     * Multipartから文字列パラメータを取得する
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws ServletException
     */
    private String getStringParamFromPart(HttpServletRequest request) throws  IllegalStateException, IOException, ServletException{
        String sparam = null;
        Part part;

        part = request.getPart("image");    //この「title」はJSPで指定されたname属性 <input type="text" name="image"・・・
        String contentType = part.getContentType();

        if ( contentType == null) {
            try(InputStream inputStream = part.getInputStream()) {
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
                sparam = bufReader.lines().collect(Collectors.joining(System.getProperty("line.separator")));

            } catch (IOException e) {
                throw e;
            }
        }

        return sparam;

    }

    /**
     * ディレクトリ作成
     * @param dir
     */
    public static void makeDir(String dir){
        //Fileオブジェクトを生成する
        File f = new File(dir);

        if (!f.exists()) {
            //フォルダ作成実行
            f.mkdirs();
        }
    }
}
