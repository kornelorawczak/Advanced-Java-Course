import java.sql.Timestamp;//importowanie klasy znacznika czasowego
							//data/czas

public class DataStruct {
String Id_agenta;//Identyfikator agenta (patrz dokumentacja)
String Nazwa_wskaznika;// nazwa wskaznika obliczanego
//przez agenta(patrz dokumentacja)
String Instrument_finansowy;// nazwa instrumentu finansowego
//wykorzystywana przez agenta(patrz dokumentacja)
double Wartosc_notowania;// wartosc notowania
//wykorzystywana przez agenta(patrz dokumentacja)
double Wartosc_wskaznika;// wartosc wskaznika obliczanego
//przez agenta(patrz dokumentacja)
int Decyzja;// wartosc decyzji wyznaczanej
//przez agenta(patrz dokumentacja)
Timestamp Data;//znacznik czasowy (data/czas)
}
