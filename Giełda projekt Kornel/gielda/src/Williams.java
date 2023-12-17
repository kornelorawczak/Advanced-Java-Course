import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera

public class Williams { //klasa obslugi wskaznika RSI
    //deklaracja zmiennych glownych
    int N = 14; //liczba sesji historycznych
    static int seconds = 5; //czas agregowania notowan (uruchamianie timera w zaleznosci od przedzialu czasu notowan, np. minutowych - 60 sec, godzinowy - 360 sec, dzienny)
    int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
    double[] vecNotowan = new double[N];
    //wektor notowan - biezace notowanie + 13 poprzednich

    void RunWilliams() { //obliczanie wskaznika RSI
        Timer timer1 = new Timer();//utworzenie obiektu timer
        timer1.schedule(new TimerRun(), 0, seconds*1000);
        //konfiguracja timera
    }

    class TimerRun extends TimerTask {
        public void run()
        {
            try {
                ReadData RD = new ReadData();

//utworzenie obiektu klasy ReadData
//(odczytujacej biezaca watrtosc notowania)

                for (int i=N-1; i>0; i--)
                
                {
                    vecNotowan[i] = vecNotowan[i-1];
                }

                vecNotowan[0] = RD.getQuotation();
                //przepisanie do pierwszego elementu
                //wektora aktualnej warto?ci notowania
                //odczytanej za pomoca metody
                //getQuotation (klasa ReadData)
                //System.out.println(RD.getQuotation());

                
                double maxN = 0.0; //srednia wartosc wzrostu z N sesji
                double minN = 0.0; //srednia wartosc spadku z N sesji
                double WilliamsValue = 0.0;//w tej zmiennej przechowywana wartosc %R (patrz dokument)

                for (int i=0; i<N-1; i++) {//obliczanie maksimum
                    if (vecNotowan[i] > vecNotowan[i+1]) {
                        //jezli wartosc nastepna jest wieksza od poprzedniej
                        //to ustawiana jako maksymalna i tak wertujemy wszystkie liczby az znajdziemy wartosc maksymalna
                        maxN = vecNotowan[i];
                    }
                    //obliczanie minimum
                    if ((vecNotowan[i] < vecNotowan[i+1])) {
                        //analogicznie jak w przypadku max tylko ze dla wartosci minimalnej
                        minN = vecNotowan[i];
                    }
                }
                if (maxN == minN)
                {
                    WilliamsValue = 0;
                    System.out.println("----Williams----");
                    System.out.println("Max jest r�wny min, czyli zero zmian przez ca�y przedzia�!");
                }
                else
                {
                    WilliamsValue = (vecNotowan[0]-maxN)/(maxN-minN)*100;
                    System.out.println("----Williams----");
                    System.out.println(WilliamsValue);
                }


                decyzja = 0;
                if (WilliamsValue <= -80) decyzja = -1;//decyzja sprzedazy
                //(patrz dokumentacja)
                if (WilliamsValue >= -20) decyzja = 1;//decyzja kupna
                //(patrz dokumentacja)

                DataStruct DS = new DataStruct();
                DS.Id_agenta="WILLIAMS_KO";//podajemy Id agenta
                DS.Nazwa_wskaznika="WILLIAMS";//podajemy nazwe wskaznika
                DS.Wartosc_wskaznika=WilliamsValue;//wartosd wskaznika to wartosc zmiennej MACD - zawiera ona obliczona, aktualna wartosc wskaznika MACD
                DS.Wartosc_notowania=vecNotowan[0]; //Biezaca wartosc notowania przechowywana w tablicy vecNotowan[0]
                DS.Decyzja=decyzja;//zmienna decyzja zawiera wartosc decyzji 1 oznacza "kup", -1 oznacza "sprzedaj" a 0 oznacza "wsztrzymaj si�"
                Calendar cal = Calendar.getInstance();//tworzymy obiekt obslugi kalendarza
                Timestamp tmpDate= new Timestamp(cal.getTimeInMillis());//utworzenie obiektu klasy Timestamp (znacznik czasowy - biezaca data zapisu do bazy)
                DS.Data=tmpDate;//zapis znacznika czasu do zmiennej w strukturze DataStruct
                DS.Instrument_finansowy="CDPROJEKT";//zapis nazwy instrumentu finansowego do zmiennej w obiekcie typu DataStruct
                WriteData WD=new WriteData();//tworzymy obiekt klasy WriteData
                System.out.println("Zapisano");
                int a=WD.wrD(DS);// poniewaz metoda wrD klasy
                //WriteData zwraca wartosc 0 (ok) lub 1(blad), to przekazujemy te wartosc
                //do zmiennej. Mozemy pozniej np. wyswietlic komunikat
                //uzytkownikowi, ze jest ok lub blad zapisu do bazy

            }
            catch(Exception e) {} //obsluga wyjatkow
        }
    }
}
