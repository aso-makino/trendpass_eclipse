package servlet;

import java.sql.SQLException;

import exception.DBConnectException;
import model.StayModel;

public class StayCheck{

	public void stayCheck() {

	StayModel stayModel = new StayModel();
	try {
		boolean result = stayModel.stayCheck();

		if(result) {
			System.out.println("�؍ݏ���o�^���܂���");
		}else {
			System.out.println("");
		}
	} catch (DBConnectException | SQLException e) {
		// TODO �����������ꂽ catch �u���b�N
		e.printStackTrace();
	}
	}

}
