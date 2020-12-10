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

	//繧ｻ繝�繧ｷ繝ｧ繝ｳ髢句ｧ�
	HttpSession session = request.getSession();

	//繝輔ぃ繧､繝ｫ繝代せ
	String filepass = "2020-12-03 07:50:03housenmasanori.jpg";

    /*逕ｻ蜒上ｒS3縺ｫ菫晏ｭ倥☆繧九Γ繧ｽ繝�繝�*/

	//螟画焚螳｣險�
    final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
    final String S3_REGION               = "ap-northeast-1";
	final String access_key = "";
	final String Secret_access_key = "";

	//繧ｭ繝ｼ縺ｮ逕滓��
	 AWSCredentials credentials
	 = new BasicAWSCredentials(access_key,Secret_access_key);
	 System.out.println("AWS-ClientKey縺ｮ逕滓��");

	 // S3繧ｯ繝ｩ繧､繧｢繝ｳ繝医�ｮ逕滓��
	 ClientConfiguration clientConfig = new ClientConfiguration();
	 	clientConfig.setProtocol(Protocol.HTTP);  // 繝励Ο繝医さ繝ｫ
	    clientConfig.setConnectionTimeout(30000);  // 謗･邯壹ち繧､繝�繧｢繧ｦ繝�(ms)
	    System.out.println("AWS-Client縺ｮ諠�蝣ｱ險ｭ螳�");

	 // 繧ｨ繝ｳ繝峨�昴う繝ｳ繝郁ｨｭ螳�
        EndpointConfiguration endpointConfiguration =
        		new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);

        AmazonS3 client = AmazonS3ClientBuilder
        		.standard()
        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfig)
                .withEndpointConfiguration(endpointConfiguration).build();
        System.out.println("繧ｨ繝ｳ繝峨�昴う繝ｳ繝医�ｮ險ｭ螳�");

        // 諡｡蠑ｵ蟄舌ｒ蜿門ｾ�
        //String extension = filename_def.substring(filename_def.lastIndexOf("."));

     // 蜿門ｾ励☆繧九ヵ繧｡繧､繝ｫ縺ｮ繝舌こ繝�繝亥錐縺ｨ繧ｭ繝ｼ蜷�(繝輔ぃ繧､繝ｫ繝代せ)繧堤畑諢�
        GetObjectRequest request2 = new GetObjectRequest("trendpasss", filepass);

     // InputStream繧貞叙蠕励＠縺ｦ菴輔°蜃ｦ逅�繧偵☆繧�
     // 譬ｼ邏榊�医�ｮ繝輔ぃ繧､繝ｫ
      //蜃ｺ蜉帙ヵ繧ｩ繝ｫ繝�菴懈��
      		//makeDir(/usr/local/tomcat/webapps/trendpass/img/img);
        File file = new File("/usr/local/tomcat/webapps/trendpass/img/"+filepass);
        if(file == null) {
        	System.out.println("繝輔ぃ繧､繝ｫ縺ｯ遨ｺ縺ｧ縺�");
        }
        System.out.println("謖�螳壹ヵ繧ｩ繝ｫ繝�縺ｫ繝輔ぃ繧､繝ｫ繧剃ｿ晏ｭ�");
        // 繧ｪ繝悶ず繧ｧ繧ｯ繝医�ｮ蜿門ｾ�
        client.getObject(request2, file);
        if( client.getObject(request2, file) == null) {
        	System.out.println("繧ｪ繝悶ず繧ｧ繧ｯ繝亥叙蠕怜､ｱ謨�");
        }

        request.setAttribute("file", file);
    //繝�繧ｹ繝�
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/test3.jsp");
  	dispatcher.forward(request, response);
	}
}