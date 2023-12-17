import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza 
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class RSI { //klasa obslugi wskaznika RSI
	//deklaracja zmiennych glownych
	int N = 14; //liczba sesji historycznych
	int seconds = 6; //czas agregowania notowa� (uruchamianie timera w zale�no�ci od przedzia�u czasu notowa�, np. minutowe, godzinne dzienne)
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
				try {//wy�apywanie wyj�tk�w
					//kod znajduj�cy si� pomi�dzy
					//nawiasami klamrowymi try
					//jest analizowany pod k�tem
					//wyst�pienia wyj�tk�w
					ReadData RD = new ReadData(); 
					
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

					//przepisanie do pierwszego elementu
					//wektora aktualnej warto�ci notowania
					//odczytanej za pomoc� metody 
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
					double RS = 0.0;//warto�� RS ze wzoru (patrz dokumentacja)
					double RSIValue = 0.0;//w tej zmiennej przechowywana
					//jest warto�� wskaznika RSI (patrz dokumentacja) .
					int licz_wzrosty = 0;//licznik wzrost�w w celu 
					//obliczenia �redniej wzrost�w
					int licz_spadki = 0;//licznik spadk�w w celu 
					//obliczenia �redniej spadk�w
					
					for (int i=0; i<N-1; i++) {//obliczanie wzrost�w 
						if (vecNotowan[i] > vecNotowan[i+1]) {
							//je�li warto�� nast�pna jest wi�ksza od poprzedniej
							//to jest wzrost
							
							wzrosty = wzrosty + (vecNotowan[i] - vecNotowan[i+1]);
							//do aktualnej warto�ci wzrost�w dodajemy
							//r�nic� pomi�dzy warto�ci� nast�pn�, a poprzedni�
							licz_wzrosty++;//zwi�kszenie warto�ci licznika 
							//wzrost�w o 1
						
						}
						//obliczanie spadk�w
						if ((vecNotowan[i] < vecNotowan[i+1])) {
							//je�li warto�� nast�pna jest mniejsza od poprzedniej
							//to jest spadek
							spadki = spadki - (vecNotowan[i] - vecNotowan[i+1]);
							//od aktualnej warto�ci spadk�w odejmujemy
							//r�nic� pomi�dzy warto�ci� nast�pn�, a poprzedni�
							licz_spadki++;//zwi�kszenie warto�ci licznika 
							//spadk�w o 1
						
						}
					}
					

					//System.out.println(wzrosty);
					//System.out.println(spadki);
					
					if (licz_wzrosty > 0)//sprawdzanie, czy
						//nie b�dzie dzielenia przez 0
					{
					
						wzrosty = wzrosty/licz_wzrosty;
						//obliczamy �redni� wzrost�w
						
					}
					if (licz_spadki > 0) //sprawdzanie, czy
							//nie b�dzie dzielenia przez 0
							{
	
						spadki = spadki/licz_spadki;
						//obliczamy �redni� spadk�w
					}
					
					if (spadki > 0) RS = wzrosty/spadki;//sprawdzenie,
					//czy nie b�dzie dzielenia przez 0
					RSIValue = 100 - (100/(1+RS));//obliczanie wskaznika RSI
					
					//System.out.println("--------");
					System.out.println(RSIValue);
					
					decyzja = 0;
					if (RSIValue >= 70) decyzja = -1;//decyzja sprzeda�y
					//(patrz dokumentacja)
					if (RSIValue <= 30) decyzja = 1;//decyzja kupna
					//(patrz dokumentacja)

					DataStruct DS = new DataStruct();
					DS.Id_agenta="RSI_KO";//podajemy Id agenta
					DS.Nazwa_wskaznika="RSI";//podajemy nazw� wskaznika
					DS.Wartosc_wskaznika=RSIValue;//warto�� wskaznika to warto�� zmiennej MACD - zawiera ona obliczon�, aktualn� warto�� wskaznika MACD
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
