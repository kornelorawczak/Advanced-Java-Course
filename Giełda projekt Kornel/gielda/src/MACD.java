import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza 
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class MACD { //klasa obslugi wskaznika MACD
	//deklaracja zmiennych glownych
	int N = 26; //liczba sesji historycznych
	int seconds = 6;//czas timera
	int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
	double[] vecNotowan = new double[N]; 
	double MACDValue = 0;
	double SMA26 = 0;
	double SMA12 = 0;
	//wektor notowan - biezace notowanie + 13 poprzednich
	
	void RunMACD() { //obliczanie wskaznika MACD
		Timer timer1 = new Timer();//utworzenie obiektu timer
		timer1.schedule(new TimerRun(), 0, seconds*1000);
		//konfiguracja timera
	} 
	
	class TimerRun extends TimerTask {
			public void run()
			{
				try {//wy³apywanie wyj¹tków
					//kod znajduj¹cy siê pomiêdzy
					//nawiasami klamrowymi try
					//jest analizowany pod k¹tem
					//wyst¹pienia wyj¹tków
					ReadData RD = new ReadData(); 
					DataStruct DS = new DataStruct();//tworzenie obiektu klasy DataStruct
					
					
//utworzenie obiektu klasy ReadData 
//(odczytujacej biezaca watrtosc notowania)
					
					for (int i=N-1; i>0; i--)
						//pêtla przesuwaj¹ca wartoœci notowañ
						//tak, aby zawsze by³o 14 ostatnich
						//notowañ w wektorze
					{
						
						vecNotowan[i] = vecNotowan[i-1];
					}
					
					vecNotowan[0] = RD.getQuotation(); 
					//System.out.println(vecNotowan[0]);
					//przepisanie do pierwszego elementu
					//wektora aktualnej wartoœci notowania
					//odczytanej za pomoc¹ metody 
					//getQuotation (klasa ReadData)
					
					/*vecNotowan[1] = 30.0;
					vecNotowan[2] =	10.5;
					vecNotowan[3] = 18.5;
					vecNotowan[4] = 9.0;
					vecNotowan[5] =	3.5;
					vecNotowan[6] = 8.0;
					vecNotowan[7] = 9.0;
					vecNotowan[8] = 15.5;
					vecNotowan[9] = 19.5;
					vecNotowan[10] = 22.5;
					vecNotowan[11] = 24.5;
					vecNotowan[12] = 19.5;
					vecNotowan[13] = 21.5;
					vecNotowan[14] = 15.5;
					vecNotowan[15] = 16.5;
					vecNotowan[16] = 19.5;
					vecNotowan[17] = 23.5;
					vecNotowan[18] = 25.5;
					vecNotowan[19] = 24.5;
					vecNotowan[20] = 24.5;
					vecNotowan[21] = 24.5;
					vecNotowan[22] = 21.5;
					vecNotowan[23] = 20.5;
					vecNotowan[24] = 19.5;
					vecNotowan[25] = 21.5;*/


					
					/*System.out.println(vecNotowan[0]);
					System.out.println(vecNotowan[1]);
					System.out.println(vecNotowan[2]);
					System.out.println(vecNotowan[3]);
					System.out.println(vecNotowan[4]);
					System.out.println(vecNotowan[5]);
					System.out.println(vecNotowan[6]);
					System.out.println(vecNotowan[7]);
					System.out.println(vecNotowan[8]);
					System.out.println(vecNotowan[9]);
					System.out.println("--------");*/
					SMA12 = 0;
					SMA26 = 0;
						for (int a = 0; a<N; a++)
					{
						SMA26 = SMA26+vecNotowan[a];
					}
						
					SMA26 = SMA26/N;
					
					for (int a = 0; a<12;a++)
					{
						SMA12 = SMA12+vecNotowan[a];
					}
						
					SMA12 = SMA12/12;
					System.out.println(SMA26);
					System.out.println(SMA12);
					MACDValue = SMA12 - SMA26;
					System.out.println(MACDValue);
					
						if (vecNotowan[N-1] > 0) {			
						decyzja = 0;
						if (MACDValue<0.0) decyzja = -1;//decyzja sprzeda¿y
						//(patrz dokumentacja)
						if (MACDValue>0.0) decyzja = 1;//decyzja kupna
						//(patrz dokumentacja)
						System.out.println(decyzja);

					}
						DS.Id_agenta="MACD_KO";//podajemy Id agenta
						DS.Nazwa_wskaznika="MACD";//podajemy nazwê wskaznika
						DS.Wartosc_wskaznika=MACDValue;//wartoœæ wskaznika to wartoœæ zmiennej MACD - zawiera ona obliczon¹, aktualn¹ wartoœæ wskaznika MACD
						DS.Wartosc_notowania=vecNotowan[0]; //Biezaca wartosc notowania przechowywana w tablicy vecNotowan[0]
						DS.Decyzja=decyzja;//zmienna decyzja zawiera wartoœ decyzji 1 oznacza "kup", -1 oznacza "sprzedaj" a 0 oznacza "wsztrzymaj siê"
						Calendar cal = Calendar.getInstance();//tworzymy obiekt obs³ugi kalendarza
						Timestamp tmpDate= new Timestamp(cal.getTimeInMillis());//utworzenie obiektu klasy Timestamp (znacznik czasowy - bie¿¹ca data zapisu do bazy)
						DS.Data=tmpDate;//zapis znacznika czasu do zmiennej w strukturze DataStruct
						DS.Instrument_finansowy="CDPROJEKT";//zapis nazwy instrumentu finansowego do zmiennej w obiekcie typu DataStruct
						WriteData WD=new WriteData();//tworzymy obiekt klasy WriteData
						System.out.println("Zapisano");
						int a=WD.wrD(DS);// poniewa¿ metoda wrD klasy
						//WriteData zwraca wartoœæ 0 (ok) lub 1(b³¹d), to przekazujemy tê wartoœæ
						//do zmiennej. Mo¿emy pózniej np. wyœwietliæ komunikat
						//u¿ytkownikowi, ¿e jest ok lub b³¹d zapisu do bazy	
				}
				
			catch(Exception e) {} //obsluga wyjatkow
			}
	}
}
