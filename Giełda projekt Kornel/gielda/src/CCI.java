import java.io.IOException; //import biblioteki obslugi wyjatkow
import java.sql.Timestamp;
import java.util.Timer; //import klasy timera
import java.util.Calendar; //import kalendarza
//(klasa pomocnicza do timera
import java.util.TimerTask; //klasa pomocnicza do timera
import java.math.*;

public class CCI { //klasa obslugi wskaznika RSI
    //deklaracja zmiennych glownych
    int N = 14; //liczba sesji historycznych
    int seconds = 6; //czas agregowania notowań (uruchamianie timera w zależności od przedziału czasu notowań, np. minutowe, godzinne)
    int decyzja = 0; //1 - kup; -1 - sprzedaj; 0 - nic nie rob
    double[] vecNotowan = new double[N];
    double[] vecNotowansymulacji=new double[14];
    //wektor notowan - biezace notowanie + 13 poprzednich

    void RunCCI() { //obliczanie wskaznika RSI
        Timer timer1 = new Timer();//utworzenie obiektu timer
        timer1.schedule(new TimerRun(), 0, seconds*1000);
        //konfiguracja timera
    }

    class TimerRun extends TimerTask {
        public void run()
        {
            try {//wyłapywanie wyjątków
                //kod znajdujący się pomiędzy
                //nawiasami klamrowymi try
                //jest analizowany pod kątem
                //wystąpienia wyjątków
                ReadData RD = new ReadData();

//utworzenie obiektu klasy ReadData
//(odczytujacej biezaca watrtosc notowania)
                double suma=0;
                double sumasymulacji=0;
                for (int i=N-1; i>0; i--)
                //pętla przesuwająca wartości notowań
                //tak, aby zawsze było 14 ostatnich
                //notowań w wektorze
                {
                    vecNotowan[i] = vecNotowan[i-1];
                }

                vecNotowan[0] = RD.getQuotation();
                suma+=vecNotowan[0];

                //System.out.println(RD.getQuotation());

                //przepisanie do pierwszego elementu
                //wektora aktualnej wartości notowania
                //odczytanej za pomocą metody
                //getQuotation (klasa ReadData)


					vecNotowansymulacji[0]=13.0;
					vecNotowansymulacji[1] = 10.0;
					vecNotowansymulacji[2] =	12.5;
					vecNotowansymulacji[3] = 13.5;
					vecNotowansymulacji[4] = 11.0;
					vecNotowansymulacji[5] =	10.5;
					vecNotowansymulacji[6] = 14.0;
					vecNotowansymulacji[7] = 19.0;
					vecNotowansymulacji[8] = 12.5;
					vecNotowansymulacji[9] = 10.5;
					vecNotowansymulacji[10] = 9.5;
					vecNotowansymulacji[11] = 8;
					vecNotowansymulacji[12] = 9.3;
					vecNotowansymulacji[13] = 11;

                    /*
                    for(int i =0; i<14; i++){
                        System.out.println(vecNotowansymulacji[i]);
                    }
					System.out.println("--------");
					*/

                //dla symulacji
                for(int i=0;i<14;i++){
                    sumasymulacji+=vecNotowansymulacji[i];
                }
                double UOB=0;
                double UOBsymulacji=0; //Uśrednione odchylenie bezwzględne symulacji
                double sUOBsymulacji=0;
                double sUOB=0; // suma potrzebna do obliczenia UOB
                double ct = vecNotowan[0]; //cena typowa = obecna (tutaj)
                double ctsymulacji=0.0;
                double SMA=0; //Srednia arytmetyczna ruchoma
                double SMAsymulacji=0;
                double CCIValue = 0.0; //wartość CCI
                double CCIValuesymulacji=0.0;
                double last = vecNotowansymulacji[0]; //dla symulacji
                double max=0; //dla symulacji
                double min=100000; //dla symulacji
                for(int i =0; i<14; i++) {
                    if(vecNotowansymulacji[i]>max){
                        max=vecNotowansymulacji[i];
                    }
                    if(vecNotowansymulacji[i]<min){
                        min=vecNotowansymulacji[i];
                    }
                }
                ctsymulacji=(max+min+last)/3;
                SMAsymulacji=sumasymulacji/14;
                SMA=suma/14;
                for(int i=0;i<14;i++){
                    sUOBsymulacji+=Math.abs(vecNotowansymulacji[i]-SMAsymulacji);
                }
                UOBsymulacji=sUOBsymulacji/14;
                CCIValuesymulacji=(1/0.015)*((ctsymulacji-SMAsymulacji)/UOBsymulacji);
                System.out.println("--------");
                //System.out.println(CCIValue);
                System.out.println(CCIValuesymulacji);
                decyzja = 0;
                if (CCIValue >= 100) decyzja = -1;//decyzja sprzedaży
                //(patrz dokumentacja)
                if (CCIValue <= -100) decyzja = 1;//decyzja kupna
                //(patrz dokumentacja)

                DataStruct DS = new DataStruct();
                DS.Id_agenta="CCI_KO";//podajemy Id agenta
                DS.Nazwa_wskaznika="CCI";//podajemy nazwę wskaznika
                DS.Wartosc_wskaznika=CCIValue;//wartość wskaznika to wartość zmiennej MACD - zawiera ona obliczoną, aktualną wartość wskaznika MACD
                DS.Wartosc_notowania=vecNotowan[0]; //Biezaca wartosc notowania przechowywana w tablicy vecNotowan[0]
                DS.Decyzja=decyzja;//zmienna decyzja zawiera wartoś decyzji 1 oznacza "kup", -1 oznacza "sprzedaj" a 0 oznacza "wsztrzymaj się"
                Calendar cal = Calendar.getInstance();//tworzymy obiekt obsługi kalendarza
                Timestamp tmpDate= new Timestamp(cal.getTimeInMillis());//utworzenie obiektu klasy Timestamp (znacznik czasowy - bieżąca data zapisu do bazy)
                DS.Data=tmpDate;//zapis znacznika czasu do zmiennej w strukturze DataStruct
                DS.Instrument_finansowy="CDPROJEKT";//zapis nazwy instrumentu finansowego do zmiennej w obiekcie typu DataStruct
                WriteData WD=new WriteData();//tworzymy obiekt klasy WriteData
                System.out.println("Zapisano");
                int a=WD.wrD(DS);// ponieważ metoda wrD klasy
                //WriteData zwraca wartość 0 (ok) lub 1(błąd), to przekazujemy tę wartość
                //do zmiennej. Możemy pózniej np. wyświetlić komunikat
                //użytkownikowi, że jest ok lub błąd zapisu do bazy
            }

            catch(Exception e) {} //obsluga wyjatkow
        }
    }
}
