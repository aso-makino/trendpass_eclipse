package servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/DisplayImage")
public class DisplayImageServlet extends HttpServlet {




    /*
     * 画像を返すサーブレット
     *
     * パラメータname（JSPから送られる画像ファイル名）を取得して、ファイルを取得して
     * 返している
     *
     * ※日本語のファイルをeclipseで表示しようとするとなぜか表示できない。
     * 　日本語が入ったファイルはFireFoxなどのブラウザで確認してみてください
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //画像があるディレクトリを取得
        String dir = System.getProperty("user.home")+"/output_imgfile";
        //imgタグで指定されたファイル名を取得する 「?name=」の部分
        String fname = request.getParameter("name");

        int iData = 0;

        //ServletのOutputStream取得
        ServletOutputStream out = null;
        BufferedInputStream in = null;

        try {
            out = response.getOutputStream();

            //画像ファイルをBufferedInputStreamを使用して読み取る
            in = new BufferedInputStream(new FileInputStream(dir+"/"+fname));

            //画像を書き出す
            while((iData = in.read()) != -1){
                out.write(iData);
            }

         //　ヘッダ情報などセット
    		response.setContentType("application/json");
    		response.setHeader("Cache-Control", "nocache");
    		response.setCharacterEncoding("utf-8");

    		ObjectMapper mapper = new ObjectMapper();

            Map<String, String> resMap = new HashMap<>();
    		resMap.put("res","true");

    		//　オブジェクトをJson文字列に変更
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);
        }catch(Exception e) {
            //ログを吐くなどのエラー処理、デフォルト画像を返すなど
        	e.printStackTrace();
        }finally {
            //クローズ
            if( in != null )    in.close();
            if( out != null )    out.close();
        }


    }




}
