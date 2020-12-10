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

import beans.ReviewBeans;
import beans.SpotBeans;
import model.FilterSpotModel;
import model.ReviewModel;
import model.SpotModel;

/**
 * Servlet implementation class InsertReviewServlet
 */
@WebServlet("/InsertSpot")
@MultipartConfig(location = "C:/Users/neco2/temporary_image")
public class InsertSpotServlet extends HttpServlet {

protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	request.setCharacterEncoding("utf-8");

	String name= null;
	String spotName = null;
	String genreId = null;
	String rating= null;
	String spotReview = null;
	String latitude = null;
	String longitude = null;
	String userId = null;

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

                spotName = userInfo[0].replace("spotName", "").trim();
                genreId  = userInfo[1].replace("genreId", "").trim();
      			rating  = userInfo[2].replace("rating", "").trim();
      			spotReview  = userInfo[3].replace("spotReview", "").trim();
      			latitude  = userInfo[4].replace("latitude", "").trim();
      			longitude  = userInfo[5].replace("longitude", "").trim();
      			userId  = userInfo[6].replace("userId", "").trim();


      			System.out.println(spotName);
      			System.out.println(genreId);
      			System.out.println(rating);
      			System.out.println(spotReview);
      			System.out.println(latitude);
      			System.out.println(longitude);
      			System.out.println(userId);


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

		//逕ｻ蜒上�ｮ譁�蟄怜�励�ｮ繝代Λ繝｡繝ｼ繧ｿ繧貞叙蠕�
		//String title = getStringParamFromPart(request);
		//System.out.println("逕ｻ蜒上�ｮ譁�蟄怜�励�ｮ繝代Λ繝｡繝ｼ繧ｿ繧貞叙蠕�");

		//Windows縺ｨmac縺ｮ繝代せ縺ｮ蟾ｮ繧貞精蜿�
		//String outputDir = System.getProperty("user.home")+"/output_imgfile";
		//String outputDir = "/usr/local/tomcat/work/output_imgfile";

		//騾√ｉ繧後※縺阪◆逕ｻ蜒上�ｮ諠�蝣ｱ繧貞叙蠕�
//		Part part = request.getPart("image");
//		System.out.println("逕ｻ蜒上�ｮ諠�蝣ｱ繧貞叙蠕�");

		//繝輔ぃ繧､繝ｫ蜷榊叙蠕�
//		String filename_def = this.getFileName(part);
//		System.out.println("繝輔ぃ繧､繝ｫ縺ｮ蜷榊燕繧貞叙蠕�");

		/*繝輔ぃ繧､繝ｫ蜷阪�ｮ驥崎､�蝗樣∩蜃ｦ逅�*/

		//螟画焚螳｣險�
//		String duplicate_avoidance = null;
//		String filename = null;
//		Date data = new Date();
//		//繧ｹ繝ｩ繝�繧ｷ繝･縺ｧ縺ｯ縺ｪ縺上Α繝峨Ν繝舌�ｼ縺ｧ謖�螳壹☆繧� S3縺ｫ繧｢繝�繝励Ο繝ｼ繝峨☆繧九◆繧�
//		SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		//譌･莉�(yyyy-MM-dd HH:mm:ss)蜿門ｾ�
//		System.out.println("譌･莉伜叙蠕怜�ｦ逅�");
//		System.out.println("try髢句ｧ�");

//		try {
//
//			duplicate_avoidance= dataformat.format(data);
//
//		} catch (Exception e) {
//
//			 System.out.println(e);
//			 System.out.println("譛ｬ譌･縺ｮ譌･莉伜叙蠕怜､ｱ謨�");
//			 duplicate_avoidance = "1970-01-01 00:00:00";
//
//	       }
//
//		System.out.println("try邨ゆｺ�");
//
//		//繝輔ぃ繧､繝ｫ蜷咲函謌�
//		filename = duplicate_avoidance + filename_def ;


        /*逕ｻ蜒上ｒS3縺ｫ菫晏ｭ倥☆繧九Γ繧ｽ繝�繝�*/

		//螟画焚螳｣險�
//        final String S3_SERVICE_END_POINT    = "http://s3-ap-northeast-1.amazonaws.com";
//        final String S3_REGION               = "ap-northeast-1";
//		final String access_key = "";
//		final String Secret_access_key = "";

		//繧ｭ繝ｼ縺ｮ逕滓��
//		 AWSCredentials credentials
//		 = new BasicAWSCredentials(access_key,Secret_access_key);
//		 System.out.println("AWS-ClientKey縺ｮ逕滓��");

		 //S3繧ｯ繝ｩ繧､繧｢繝ｳ繝医�ｮ逕滓��
//		 ClientConfiguration clientConfig = new ClientConfiguration();
//		 	clientConfig.setProtocol(Protocol.HTTP);  // 繝励Ο繝医さ繝ｫ
//		    clientConfig.setConnectionTimeout(30000);  // 謗･邯壹ち繧､繝�繧｢繧ｦ繝�(ms)
//		    System.out.println("AWS-Client縺ｮ諠�蝣ｱ險ｭ螳�");
//
//		 //繧ｨ繝ｳ繝峨�昴う繝ｳ繝郁ｨｭ螳�
//	        EndpointConfiguration endpointConfiguration =
//	        		new EndpointConfiguration(S3_SERVICE_END_POINT,  S3_REGION);
//
//	        AmazonS3 client = AmazonS3ClientBuilder
//	        		.standard()
//	        		.withCredentials(new AWSStaticCredentialsProvider(credentials))
//	                .withClientConfiguration(clientConfig)
//	                .withEndpointConfiguration(endpointConfiguration).build();
//	        System.out.println("繧ｨ繝ｳ繝峨�昴う繝ｳ繝医�ｮ險ｭ螳�");
//
//	        TransferManager manager = TransferManagerBuilder.standard()
//	        		  .withS3Client(client)
//	        		  .withMultipartUploadThreshold((long) (1024L*1024L*1024L*1024L*1024L))
//	        		  .withExecutorFactory(() -> Executors.newFixedThreadPool(1000))
//	        		  .build();
//	        System.out.println("騾∽ｿ｡譁ｹ豕輔�ｮ霑ｽ蜉�險ｭ螳�");
//
//	        // 諡｡蠑ｵ蟄舌ｒ蜿門ｾ�
//	        String extension = filename_def.substring(filename_def.lastIndexOf("."));
//
//	        //繝�繝ｼ繧ｿ繧ｹ繝医Μ繝ｼ繝�髢句ｧ�
//	        InputStream inputStream = part.getInputStream();
//	        System.out.println("DataStream縺ｮ髢区叛");
//
//	        //繝｡繧ｿ諠�蝣ｱ繧堤函謌�
//	        ObjectMetadata putMetaData = new ObjectMetadata();
//	        putMetaData.setContentLength(part.getSize());
//	        System.out.println("繝｡繧ｿ諠�蝣ｱ繧定ｿｽ蜉�");
//
//	        //upload-繝ｪ繧ｯ繧ｨ繧ｹ繝医ｒ逕滓��
//	        System.out.println("S3upload繝ｪ繧ｯ繧ｨ繧ｹ繝磯幕蟋�");
//	        Upload upload =
//        		manager.upload(
//        				"trendpasss",//Bucket蜷�
//        				filename,//菫晏ｭ倥＆繧後ｋ髫帙�ｮ蜷榊燕
//        				inputStream,//逕ｻ蜒上ョ繝ｼ繧ｿ
//        				putMetaData//expect_byte
//        				);
//
//	        System.out.println("try髢句ｧ�");
//
//	        try {
//
//	        	upload.waitForCompletion();
//	        	System.out.println("繧｢繝�繝励Ο繝ｼ繝画�仙粥");
//
//	        } catch (InterruptedException e1) {
//
//	        	new IOException(e1);
//	        	System.out.println(e1);
//	        	System.out.println("繧｢繝�繝励Ο繝ｼ繝牙､ｱ謨�");
//
//        }
//
//	        System.out.println("try邨ゆｺ�");
//	        System.out.println("S3upload繝ｪ繧ｯ繧ｨ繧ｹ繝育ｵゆｺ�");
//
//	        inputStream.close();
//	        System.out.println("datastream髢蛾事");

	        //Beans繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
	        SpotBeans spotBeans = new SpotBeans();
	        ReviewBeans reviewBeans = new ReviewBeans();

	        //繧ｹ繝昴ャ繝域兜遞ｿ繝｡繧ｽ繝�繝�
	        spotBeans.setGenreId(genreId);
	        spotBeans.setSpotName(spotName);
	        spotBeans.setLatitude(Double.parseDouble(latitude));
	        spotBeans.setLongitude(Double.parseDouble(longitude));
	        System.out.println("insertSpot繝｡繧ｽ繝�繝蛾幕蟋�");

	      //Model繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ逕滓��
	        SpotModel spotModel = new SpotModel();
	        ReviewModel reviewModel = new ReviewModel();

	        String spotId = spotModel.insertSpot(spotBeans,userId);

        	reviewBeans.setUserId(userId);
        	reviewBeans.setSpotId(spotId);
        	reviewBeans.setReviewImage(name);
        	reviewBeans.setEvaluation(rating);
        	reviewBeans.setReviewContent(spotReview);

	        boolean reviewResult = reviewModel.insertReview(reviewBeans);

	        System.out.println("insertSpot繝｡繧ｽ繝�繝臥ｵゆｺ�");


	      //繝�繧ｹ繝�
	    	Map<String, Object> resMap = new HashMap<>();
	    	resMap.put("result",reviewResult);
	        //繝�繝ｼ繧ｿ騾∽ｿ｡
	        String resJson = mapper.writeValueAsString(resMap);

	        PrintWriter out = response.getWriter();
	        out.print(resJson);

	}

		/**
	     * 繧｢繝�繝励Ο繝ｼ繝峨＆繧後◆繝輔ぃ繧､繝ｫ蜷阪ｒ繝倥ャ繝�諠�蝣ｱ繧医ｊ蜿門ｾ励☆繧�
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
	        System.out.println("繧｢繝�繝励Ο繝ｼ繝峨＆繧後◆繝輔ぃ繧､繝ｫ蜷阪ｒ繝倥ャ繝�諠�蝣ｱ繧医ｊ蜿門ｾ励☆繧�");
	        return name;
	    }

	}
