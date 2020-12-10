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

		//繧ｻ繝�繧ｷ繝ｧ繝ｳ髢句ｧ�
		HttpSession session = request.getSession();

		//繝輔ぃ繧､繝ｫ繝代せ
		String filepass = "2020-12-03 07:50:03housenmasanori.jpg";

	    /*逕ｻ蜒上ｒS3縺ｫ菫晏ｭ倥☆繧九Γ繧ｽ繝�繝�*/

		//螟画焚螳｣險�
	    final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
	    final String S3_REGION               = "ap-northeast-1";
		final String access_key = "セキュリティのため保護";
		final String Secret_access_key = "セキュリティのため保護";

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

	        /*Bucket蜀�縺ｮ逕ｻ蜒上ｒ蜑企勁*/
	        String bucketName = "trendpasss";
	        String keyName = "2020-12-03 07:50:03housenmasanori.jpg";

	        client.deleteObject(new DeleteObjectRequest(bucketName, keyName));


		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
