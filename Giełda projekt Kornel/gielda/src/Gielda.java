
public class Gielda {

	public static void main(String[] args) {
		//System.out.println("Gielda 03. 10.2020");
		RSI RSIWsk= new RSI();//utworzenie nowego obiektu klasy RSI
		MACD MACDWsk= new MACD();//utworzenie nowego obiektu klasy RSI
		CCI CCIWsk=new CCI();
		ROC ROCWSK=new ROC();
		Williams WWsk=new Williams();
		//obiekt, to "¿yj¹ce" wyst¹pienie klasy.
		//Klasa jest tylko bytem abstrakcyjnym - nie mo¿e byæ uruchomiona
		//tylko obiekty mog¹ dzia³aæ jako uruchomione procesy
		//proces to uruchomiony program
		RSIWsk.RunRSI();//Wywo³anie metody RunRSI klasy RSI
		MACDWsk.RunMACD();//Wywo³anie metody RunRSI klasy RSI
		CCIWsk.RunCCI();
		ROCWSK.RunROC();
		WWsk.RunWilliams();
		//System.out.println(RSIWsk.RunRSI());
		
	}

}
