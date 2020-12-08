package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SpotBeans;
import model.SpotModel;

/**
 * Servlet implementation class InsertReviewServlet
 */
@WebServlet("/InsertSpot")
@MultipartConfig(location = "")
public class InsertSpotServlet extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response
			)
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
		int userId = 1;
		String spotName = "大分駅";
		int genreId = 1;
		int ratingBar = 5;
		String spotReview = "テスト";
		//int userId = Integer.parseInt(request.getParameter("userId"));
		//int genreId = Integer.parseInt(request.getParameter("genreId"));
		//int ratingBar = Integer.parseInt(request.getParameter("ratingBar"));
		//double latitude = Double.parseInt(request.getParameter("latitude"));//緯度
		//double longitude = Double.parseInt(request.getParameter("longitude"));//経度
		//String spotName = request.getParameter("spotName");
		//String review = request.getParameter("spotReview");


		//画像の文字列のパラメータを取得
		//String title = getStringParamFromPart(request);
		//System.out.println("画像の文字列のパラメータを取得");

		//Windowsとmacのパスの差を吸収
		//String outputDir = System.getProperty("user.home")+"/output_imgfile";
		//String outputDir = "/usr/local/tomcat/work/output_imgfile";

		//送られてきた画像の情報を取得
		Part part = request.getPart("image");
		System.out.println("画像の情報を取得");

		//ファイル名取得
		String filename_def = this.getFileName(part);
		System.out.println("ファイルの名前を取得");

		/*ファイル名の重複回避処理*/

		//変数宣言
		String duplicate_avoidance = null;
		String filename = null;
		Date data = new Date();
		//スラッシュではなくミドルバーで指定する S3にアップロードするため
		SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//日付(yyyy-MM-dd HH:mm:ss)取得
		System.out.println("日付取得処理");
		System.out.println("try開始");

		try {

			duplicate_avoidance= dataformat.format(data);

		} catch (Exception e) {

			 System.out.println(e);
			 System.out.println("本日の日付取得失敗");
			 duplicate_avoidance = "1970-01-01 00:00:00";

	       }

		System.out.println("try終了");

		//ファイル名生成
		filename = duplicate_avoidance + filename_def ;

        //Modelインスタンス生成
        SpotModel spotModel = new SpotModel();

        /*画像をS3に保存するメソッド*/

		//変数宣言
        final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
        final String S3_REGION               = "ap-northeast-1";
		final String access_key = "セキュリティのため保護";
		final String Secret_access_key = "セキュリティのため保護";

		//キーの生成
		 AWSCredentials credentials
		 = new BasicAWSCredentials(access_key,Secret_access_key);
		 System.out.println("AWS-ClientKeyの生成");

		 //S3クライアントの生成
		 ClientConfiguration clientConfig = new ClientConfiguration();
		 	clientConfig.setProtocol(Protocol.HTTP);  // プロトコル
		    clientConfig.setConnectionTimeout(30000);  // 接続タイムアウト(ms)
		    System.out.println("AWS-Clientの情報設定");

		 //エンドポイント設定
	        EndpointConfiguration endpointConfiguration =
	        		new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);

	        AmazonS3 client = AmazonS3ClientBuilder
	        		.standard()
	        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .withClientConfiguration(clientConfig)
	                .withEndpointConfiguration(endpointConfiguration).build();
	        System.out.println("エンドポイントの設定");

	        TransferManager manager = TransferManagerBuilder.standard()
	        		  .withS3Client(client)
	        		  .withMultipartUploadThreshold((long) (1024L*1024L*1024L*1024L*1024L))
	        		  .withExecutorFactory(() -> Executors.newFixedThreadPool(1000))
	        		  .build();
	        System.out.println("送信方法の追加設定");

	        // 拡張子を取得
	        String extension = filename_def.substring(filename_def.lastIndexOf("."));

	        //データストリーム開始
	        InputStream inputStream = part.getInputStream();
	        System.out.println("DataStreamの開放");

	        //メタ情報を生成
	        ObjectMetadata putMetaData = new ObjectMetadata();
	        putMetaData.setContentLength(part.getSize());
	        System.out.println("メタ情報を追加");

	        //upload-リクエストを生成
	        System.out.println("S3uploadリクエスト開始");
	        Upload upload =
        		manager.upload(
        				"trendpasss",//Bucket名
        				filename,//保存される際の名前
        				inputStream,//画像データ
        				putMetaData//expect_byte
        				);

	        System.out.println("try開始");

	        try {

	        	upload.waitForCompletion();
	        	System.out.println("アップロード成功");

	        } catch (InterruptedException e1) {

	        	new IOException(e1);
	        	System.out.println(e1);
	        	System.out.println("アップロード失敗");

        }

	        System.out.println("try終了");
	        System.out.println("S3uploadリクエスト終了");

	        inputStream.close();
	        System.out.println("datastream閉鎖");

	        //Beansインスタンス生成
	        SpotBeans spotBeans = new SpotBeans();

	        //スポット投稿メソッド
	        spotBeans.setGenreId(genreId);
	        spotBeans.setSpotName(spotName);
	        spotBeans.setFilename(filename);
	        spotBeans.setRatingBar(ratingBar);
	        spotBeans.setSpotReview(spotReview);
	        //spotBeans.setRatitude(ratitude);
	        //spotBeans.setLongitude(longitude);
	        System.out.println("insertSpotメソッド開始");
	        boolean insertSpot = SpotModel.insertSpot(spotBeans,userId);

	        System.out.println("insertSpotメソッド終了");

	        //判別
	        boolean isSelect = true;
	        if(insertSpot == false) {
	        	isSelect = false;
	        }

	        //データ送信
	        //String resJson = mapper.writeValueAsString(isSelect);

	        //テスト
	        request.setAttribute("isSelect", isSelect);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test2.jsp");
	      	dispatcher.forward(request, response);

	}

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
	        System.out.println("アップロードされたファイル名をヘッダ情報より取得する");
	        return name;
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
	        System.out.println("ディレクトリ作成");
	    }

	}
