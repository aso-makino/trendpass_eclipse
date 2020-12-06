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
			System.out.println("滞在情報を登録しました");
		}else {
			System.out.println("");
		}
	} catch (DBConnectException | SQLException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
	}

}
