package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import com.amazonaws.services.s3.model.GetObjectRequest;

/**
 * Servlet implementation class S3testServlet
 */
@WebServlet("/S3testServlet")
public class S3testServlet extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response
			)
			throws ServletException, IOException {

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

        // 拡張子を取得
        //String extension = filename_def.substring(filename_def.lastIndexOf("."));

     // 取得するファイルのバケット名とキー名(ファイルパス)を用意
        GetObjectRequest request2 = new GetObjectRequest("trendpasss", filepass);

     // InputStreamを取得して何か処理をする
     // 格納先のファイル
      //出力フォルダ作成
      		//makeDir(/usr/local/tomcat/webapps/trendpass/img/img);
        File file = new File("/usr/local/tomcat/webapps/trendpass/img/"+filepass);
        if(file == null) {
        	System.out.println("ファイルは空です");
        }
        System.out.println("指定フォルダにファイルを保存");
        // オブジェクトの取得
        client.getObject(request2, file);
        if( client.getObject(request2, file) == null) {
        	System.out.println("オブジェクト取得失敗");
        }

        request.setAttribute("file", file);
    //テスト
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test3.jsp");
  	dispatcher.forward(request, response);
	}
}