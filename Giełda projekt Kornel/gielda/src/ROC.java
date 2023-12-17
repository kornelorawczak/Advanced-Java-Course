import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza 
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class ROC { //klasa obslugi wskaznika ROC
	//deklaracja zmiennych glownych
	int N = 14; //liczba sesji historycznych
	int seconds = 6;//czas timera
	int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
	double[] vecNotowan = new double[N]; 
	double ROCValue = 0.0;//w tej zmiennej przechowywana
	double prevROCValue = 0.0;//warto�� wskaznika z poprzedniej iteracji (poprzedniego notowania)

	//wektor notowan - biezace notowanie + 13 poprzednich
	
	void RunROC() { //obliczanie wskaznika ROC
		Timer timer1 = new Timer();//utworzenie obiektu timer
		timer1.schedule(new TimerRun(), 0, seconds*1000);
		//konfiguracja timera
	} 
	
	class TimerRun extends TimerTask {
			public void run()
			{
				try {//wy�apywanie wyj�tk�w
					//kod znajduj�cy si� pomi�dzy
					//nawiasami klamrowymi try
					//jest analizowany pod k�tem
					//wyst�pienia wyj�tk�w
					ReadData RD = new ReadData(); 
					DataStruct DS = new DataStruct();//tworzenie obiektu klasy DataStruct
					
//utworzenie obiektu klasy ReadData 
//(odczytujacej biezaca watrtosc notowania)
					
					for (int i=N-1; i>0; i--)
						//p�tla przesuwaj�ca warto�ci notowa�
						//tak, aby zawsze by�o 14 ostatnich
						//notowa� w wektorze
					{
						
						vecNotowan[i] = vecNotowan[i-1];
					}
					
					vecNotowan[0] = RD.getQuotation(); 
					//System.out.println(vecNotowan[0]);
					//przepisanie do pierwszego elementu
					//wektora aktualnej warto�ci notowania
					//odczytanej za pomoc� metody 
					//getQuotation (klasa ReadData)
					/*vecNotowan[0] = 11.0;
					vecNotowan[1] = 19.0;
					vecNotowan[2] =	34.5;
					vecNotowan[3] = 22.5;
					vecNotowan[4] = 19.0;
					vecNotowan[5] =	23.5;
					vecNotowan[6] = 38.0;
					vecNotowan[7] = 29.0;
					vecNotowan[8] = 30.5;
					vecNotowan[9] =	31.5;
					vecNotowan[10] = 28.0;
					vecNotowan[11] = 29.0;
					vecNotowan[12] = 30.5;
					vecNotowan[13] = 12.5;*/
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
					
					
					//jest warto�� wskaznika ROC (patrz dokumentacja) .
					
					ROCValue=0.0;
					decyzja = 0;
					//prevROCValue=-3.0;
					if(vecNotowan[N-1]>0)
					{
					
						ROCValue = (vecNotowan[0]-vecNotowan[N-1])/vecNotowan[N-1];//obliczanie wskaznika ROC
					
						System.out.println(ROCValue);
						//System.out.println(vecNotowan[N-1]);
					
						
						if ((prevROCValue>0.0)&& (ROCValue <0.0)) decyzja = -1;//decyzja sprzeda�y
						//(patrz dokumentacja)
						if ((prevROCValue<0.0)&& (ROCValue >0.0)) decyzja = 1;//decyzja kupna
						//(patrz dokumentacja)
					System.out.println(decyzja);
					}
					
					prevROCValue=ROCValue;
					
					DS.Id_agenta="ROC_KO";//podajemy Id agenta
					DS.Nazwa_wskaznika="ROC";//podajemy nazw� wskaznika
					DS.Wartosc_wskaznika=ROCValue;//warto�� wskaznika to warto�� zmiennej MACD - zawiera ona obliczon�, aktualn� warto�� wskaznika MACD
					DS.Wartosc_notowania=vecNotowan[0]; //Biezaca wartosc notowania przechowywana w tablicy vecNotowan[0]
					DS.Decyzja=decyzja;//zmienna decyzja zawiera warto� decyzji 1 oznacza "kup", -1 oznacza "sprzedaj" a 0 oznacza "wsztrzymaj si�"
					Calendar cal = Calendar.getInstance();//tworzymy obiekt obs�ugi kalendarza
					Timestamp tmpDate= new Timestamp(cal.getTimeInMillis());//utworzenie obiektu klasy Timestamp (znacznik czasowy - bie��ca data zapisu do bazy)
					DS.Data=tmpDate;//zapis znacznika czasu do zmiennej w strukturze DataStruct
					DS.Instrument_finansowy="CDPROJEKT";//zapis nazwy instrumentu finansowego do zmiennej w obiekcie typu DataStruct
					WriteData WD=new WriteData();//tworzymy obiekt klasy WriteData
					System.out.println("Zapisano");
					int a=WD.wrD(DS);// poniewa� metoda wrD klasy
					//WriteData zwraca warto�� 0 (ok) lub 1(b��d), to przekazujemy t� warto��
					//do zmiennej. Mo�emy p�zniej np. wy�wietli� komunikat
					//u�ytkownikowi, �e jest ok lub b��d zapisu do bazy
				}
				
			catch(Exception e) {} //obsluga wyjatkow
			}
	}
}
