package exception;

public class SystemErrException extends Exception{


		public SystemErrException(String errMsg){
			System.out.println(errMsg);
		}
		public SystemErrException(Exception e) {
			e.printStackTrace();
		}

	}

