package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import beans.UserBeans;
import exception.DBConnectException;
import model.UserModel;
import java.io.File;

@WebServlet("/SignUp")
@MultipartConfig(location = "/usr/local/tomcat/webapps/img")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{


		if ("/SignUp".equals(request.getServletPath())){

			request.setCharacterEncoding("utf-8");

			String name= null;
			String userId = null;
			String userName = null;
  			String userMail = null;
  			String password = null;
  			String sexString;
  			int sex = 0;
  			int birth = 0;

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

	                  //螟画焚螳｣險�
	                    final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
	                  	final String S3_REGION               = "ap-northeast-1";
	                  	final String access_key = "";
	                	final String Secret_access_key = "";

	          		//繧ｭ繝ｼ縺ｮ逕滓��
	          			AWSCredentials credentials
	          			= new BasicAWSCredentials(access_key,Secret_access_key);
	          			System.out.println("AWS-ClientKey縺ｮ逕滓��");

	          		 //S3繧ｯ繝ｩ繧､繧｢繝ｳ繝医�ｮ逕滓��
	          			ClientConfiguration clientConfig = new ClientConfiguration();
	          		 	clientConfig.setProtocol(Protocol.HTTP);  // 繝励Ο繝医さ繝ｫ
	          		    clientConfig.setConnectionTimeout(30000);  // 謗･邯壹ち繧､繝�繧｢繧ｦ繝�(ms)
	          		    System.out.println("AWS-Client縺ｮ諠�蝣ｱ險ｭ螳�");

	         		 //繧ｨ繝ｳ繝峨�昴う繝ｳ繝郁ｨｭ螳�
	          	        EndpointConfiguration endpointConfiguration =
	          	        		new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);

	          	        AmazonS3 client = AmazonS3ClientBuilder
	          	        		.standard()
	          	        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
	          	                .withClientConfiguration(clientConfig)
	          	                .withEndpointConfiguration(endpointConfiguration).build();
	          	        System.out.println("繧ｨ繝ｳ繝峨�昴う繝ｳ繝医�ｮ險ｭ螳�");

	          	        TransferManager manager = TransferManagerBuilder.standard()
	          	        		  .withS3Client(client)
	          	        		  .withMultipartUploadThreshold((long) (1024L*1024L*1024L*1024L*1024L))
	          	        		  .withExecutorFactory(() -> Executors.newFixedThreadPool(1000))
	          	        		  .build();
	          	        System.out.println("騾∽ｿ｡譁ｹ豕輔�ｮ霑ｽ蜉�險ｭ螳�");


	          	    //繝�繝ｼ繧ｿ繧ｹ繝医Μ繝ｼ繝�髢句ｧ�
	        	        InputStream inputStream = part.getInputStream();
	        	        System.out.println("DataStream縺ｮ髢区叛");

	        	        //繝｡繧ｿ諠�蝣ｱ繧堤函謌�
	        	        ObjectMetadata putMetaData = new ObjectMetadata();
	        	        putMetaData.setContentLength(part.getSize());
	        	        System.out.println("繝｡繧ｿ諠�蝣ｱ繧定ｿｽ蜉�");

	        	        //upload-繝ｪ繧ｯ繧ｨ繧ｹ繝医ｒ逕滓��
	        	        System.out.println("S3upload繝ｪ繧ｯ繧ｨ繧ｹ繝磯幕蟋�");
	        	        Upload upload =
	                		manager.upload(
	                				"trendpasss",//Bucket蜷�
	                				name,//菫晏ｭ倥＆繧後ｋ髫帙�ｮ蜷榊燕
	                				inputStream,//逕ｻ蜒上ョ繝ｼ繧ｿ
	                				putMetaData//expect_byte
	                				);

	        	        System.out.println("try髢句ｧ�");

	        	        try {

	        	        	upload.waitForCompletion();
	        	        	System.out.println("繧｢繝�繝励Ο繝ｼ繝画�仙粥");

	        	        } catch (InterruptedException e1) {

	        	        	new IOException(e1);
	        	        	System.out.println(e1);
	        	        	System.out.println("繧｢繝�繝励Ο繝ｼ繝牙､ｱ謨�");

	                }

	        	        System.out.println("try邨ゆｺ�");
	        	        System.out.println("S3upload繝ｪ繧ｯ繧ｨ繧ｹ繝育ｵゆｺ�");

	        	        inputStream.close();
	        	        System.out.println("datastream髢蛾事");


	                }

	                if(str.startsWith("name=\"description\"")) {
	                	InputStream inputStream = part.getInputStream();
	                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
	                    String jsonText = br.readLine();

	                    jsonText = jsonText.trim().replace("{", "").replace("}", "").replace(":", "").replace("\"", "").trim();
	                    String[] userInfo = jsonText.split(",", 0);


	                    userName  = userInfo[0].replace("userName", "").trim();
	          			userMail  = userInfo[1].replace("mail", "").trim();
	          			password  = userInfo[2].replace("password", "").trim();
	          			sexString  = userInfo[3].replace("sex", "").trim();
	          			birth  = Integer.parseInt(userInfo[4].replace("birth", "").trim());

	          			System.out.println(userId);
	          			System.out.println(userName);
	          			System.out.println(userMail);
	          			System.out.println(password);
	          			System.out.println(sexString);
	          			System.out.println(birth);


	          			switch (sexString){
	                    case "男性":
	                        sex = 1;
	                        break;
	                    case "女性":
	                    	sex = 2;
	                        break;
	                    case "未選択":
	                    	sex = 3;
	                        break;
	          			}
	                }
	            }
	        }
			}catch(Exception e){
				e.printStackTrace();
			}

    		ObjectMapper mapper = new ObjectMapper();

    		UserModel userModel = new UserModel();
    		UserBeans userBeans = new UserBeans();

    		userBeans.setUserId(userId);
    		userBeans.setUserName(userName);
    		userBeans.setUserIcon(name);
    		userBeans.setMail(userMail);
    		userBeans.setPassword(password);
    		userBeans.setSex(sex);
    		userBeans.setBirth(birth);

    		boolean result = false;

			try {
				result = userModel.insert(userBeans);
			} catch (DBConnectException | SQLException e) {
				e.printStackTrace();
			}


			//�@�w�b�_���ȂǃZ�b�g
    		response.setContentType("application/json");
    		response.setHeader("Cache-Control", "nocache");
    		response.setCharacterEncoding("utf-8");

    		Map<String, Object> resMap = new HashMap<>();
    		resMap.put("result",result);

    		//�@�I�u�W�F�N�g��Json������ɕύX
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);
    	    }
    	}
}