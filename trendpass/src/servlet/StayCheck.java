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
			System.out.println("‘Øİî•ñ‚ğ“o˜^‚µ‚Ü‚µ‚½");
		}else {
			System.out.println("");
		}
	} catch (DBConnectException | SQLException e) {
		// TODO ©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
		e.printStackTrace();
	}
	}

}
