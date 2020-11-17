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
     * �摜��Ԃ��T�[�u���b�g
     *
     * �p�����[�^name�iJSP���瑗����摜�t�@�C�����j���擾���āA�t�@�C�����擾����
     * �Ԃ��Ă���
     *
     * �����{��̃t�@�C����eclipse�ŕ\�����悤�Ƃ���ƂȂ����\���ł��Ȃ��B
     * �@���{�ꂪ�������t�@�C����FireFox�Ȃǂ̃u���E�U�Ŋm�F���Ă݂Ă�������
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //�摜������f�B���N�g�����擾
        String dir = System.getProperty("user.home")+"/output_imgfile";
        //img�^�O�Ŏw�肳�ꂽ�t�@�C�������擾���� �u?name=�v�̕���
        String fname = request.getParameter("name");

        int iData = 0;

        //Servlet��OutputStream�擾
        ServletOutputStream out = null;
        BufferedInputStream in = null;

        try {
            out = response.getOutputStream();

            //�摜�t�@�C����BufferedInputStream���g�p���ēǂݎ��
            in = new BufferedInputStream(new FileInputStream(dir+"/"+fname));

            //�摜�������o��
            while((iData = in.read()) != -1){
                out.write(iData);
            }

         //�@�w�b�_���ȂǃZ�b�g
    		response.setContentType("application/json");
    		response.setHeader("Cache-Control", "nocache");
    		response.setCharacterEncoding("utf-8");

    		ObjectMapper mapper = new ObjectMapper();

            Map<String, String> resMap = new HashMap<>();
    		resMap.put("res","true");

    		//�@�I�u�W�F�N�g��Json������ɕύX
    		String resJson = mapper.writeValueAsString(resMap);

    		PrintWriter outPW = response.getWriter();
    		outPW.print(resJson);
        }catch(Exception e) {
            //���O��f���Ȃǂ̃G���[�����A�f�t�H���g�摜��Ԃ��Ȃ�
        	e.printStackTrace();
        }finally {
            //�N���[�Y
            if( in != null )    in.close();
            if( out != null )    out.close();
        }


    }




}
