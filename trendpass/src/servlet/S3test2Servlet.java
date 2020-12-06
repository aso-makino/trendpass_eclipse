package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

/**
 * Servlet implementation class S3test2Servlet
 */
@WebServlet("/S3test2Servlet")
public class S3test2Servlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッション開始
		HttpSession session = request.getSession();

		//ファイルパス
		String filepass = "2020-12-03 07:50:03housenmasanori.jpg";

	    /*画像をS3に保存するメソッド*/

		//変数宣言
	    final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
	    final String S3_REGION               = "ap-northeast-1";
		final String access_key = "AKIA4USE7UMYWNYOFI46";
		final String Secret_access_key = "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";

		//キーの生成
		 AWSCredentials credentials
		 = new BasicAWSCredentials(access_key,Secret_access_key);
		 System.out.println("AWS-ClientKeyの生成");

		 // S3クライアントの生成
		 ClientConfiguration clientConfig = new ClientConfiguration();
		 	clientConfig.setProtocol(Protocol.HTTP);  // プロトコル
		    clientConfig.setConnectionTimeout(30000);  // 接続タイムアウト(ms)
		    System.out.println("AWS-Clientの情報設定");

		 // エンドポイント設定
	        EndpointConfiguration endpointConfiguration =
	        		new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);

	        AmazonS3 client = AmazonS3ClientBuilder
	        		.standard()
	        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .withClientConfiguration(clientConfig)
	                .withEndpointConfiguration(endpointConfiguration).build();
	        System.out.println("エンドポイントの設定");

	        /*Bucket内の画像を削除*/
	        String bucketName = "trendpasss";
	        String keyName = "2020-12-03 07:50:03housenmasanori.jpg";

	        client.deleteObject(new DeleteObjectRequest(bucketName, keyName));


		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
