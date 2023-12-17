import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza 
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class RSI { //klasa obslugi wskaznika RSI
	//deklaracja zmiennych glownych
	int N = 14; //liczba sesji historycznych
	int seconds = 6; //czas agregowania notowañ (uruchamianie timera w zale¿noœci od przedzia³u czasu notowañ, np. minutowe, godzinne dzienne)
	int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
	double[] vecNotowan = new double[N]; 
	//wektor notowan: biezace notowanie + 13 poprzednich
	
	void RunRSI() { //obliczanie wskaznika RSI
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

					//przepisanie do pierwszego elementu
					//wektora aktualnej wartoœci notowania
					//odczytanej za pomoc¹ metody 
					//getQuotation (klasa ReadData)
					//System.out.println(RD.getQuotation());
					/*vecNotowan[0] = 9.0;
					vecNotowan[1] = 5.0;
					vecNotowan[2] =	9.5;
					vecNotowan[3] = 8.5;
					vecNotowan[4] = 8.0;
					vecNotowan[5] =	10.5;
					vecNotowan[6] = 9.0;
					vecNotowan[7] = 10.0;
					vecNotowan[8] = 8.0;*/
					
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
					
					double wzrosty = 0.0; //srednia wartosc wzrostu z N sesji
					double spadki = 0.0; //srednia wartosc spadku z N sesji
					double RS = 0.0;//wartoœæ RS ze wzoru (patrz dokumentacja)
					double RSIValue = 0.0;//w tej zmiennej przechowywana
					//jest wartoœæ wskaznika RSI (patrz dokumentacja) .
					int licz_wzrosty = 0;//licznik wzrostów w celu 
					//obliczenia œredniej wzrostów
					int licz_spadki = 0;//licznik spadków w celu 
					//obliczenia œredniej spadków
					
					for (int i=0; i<N-1; i++) {//obliczanie wzrostów 
						if (vecNotowan[i] > vecNotowan[i+1]) {
							//jeœli wartoœæ nastêpna jest wiêksza od poprzedniej
							//to jest wzrost
							
							wzrosty = wzrosty + (vecNotowan[i] - vecNotowan[i+1]);
							//do aktualnej wartoœci wzrostów dodajemy
							//ró¿nicê pomiêdzy wartoœci¹ nastêpn¹, a poprzedni¹
							licz_wzrosty++;//zwiêkszenie wartoœci licznika 
							//wzrostów o 1
						
						}
						//obliczanie spadków
						if ((vecNotowan[i] < vecNotowan[i+1])) {
							//jeœli wartoœæ nastêpna jest mniejsza od poprzedniej
							//to jest spadek
							spadki = spadki - (vecNotowan[i] - vecNotowan[i+1]);
							//od aktualnej wartoœci spadków odejmujemy
							//ró¿nicê pomiêdzy wartoœci¹ nastêpn¹, a poprzedni¹
							licz_spadki++;//zwiêkszenie wartoœci licznika 
							//spadków o 1
						
						}
					}
					

					//System.out.println(wzrosty);
					//System.out.println(spadki);
					
					if (licz_wzrosty > 0)//sprawdzanie, czy
						//nie bêdzie dzielenia przez 0
					{
					
						wzrosty = wzrosty/licz_wzrosty;
						//obliczamy œredni¹ wzrostów
						
					}
					if (licz_spadki > 0) //sprawdzanie, czy
							//nie bêdzie dzielenia przez 0
							{
	
						spadki = spadki/licz_spadki;
						//obliczamy œredni¹ spadków
					}
					
					if (spadki > 0) RS = wzrosty/spadki;//sprawdzenie,
					//czy nie bêdzie dzielenia przez 0
					RSIValue = 100 - (100/(1+RS));//obliczanie wskaznika RSI
					
					//System.out.println("--------");
					System.out.println(RSIValue);
					
					decyzja = 0;
					if (RSIValue >= 70) decyzja = -1;//decyzja sprzeda¿y
					//(patrz dokumentacja)
					if (RSIValue <= 30) decyzja = 1;//decyzja kupna
					//(patrz dokumentacja)

					DataStruct DS = new DataStruct();
					DS.Id_agenta="RSI_KO";//podajemy Id agenta
					DS.Nazwa_wskaznika="RSI";//podajemy nazwê wskaznika
					DS.Wartosc_wskaznika=RSIValue;//wartoœæ wskaznika to wartoœæ zmiennej MACD - zawiera ona obliczon¹, aktualn¹ wartoœæ wskaznika MACD
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
