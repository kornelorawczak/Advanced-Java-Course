//import klas biblioteki Jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import klas obs�ugi wyj�tk�w wej�cia/wyj�cia.
import java.io.IOException;

public class ReadData {//klasa obs�  uguj�ca odczyt danych 
	//notowania ze strony internetowej
double getQuotation() throws IOException //metoda pobieraj�ca warto�� notowania 
//ze strony internetowej z obs�ug� wyj�tk�w - ta obs�uga musi by� w ka�dej klasie, kt�ra korzysta z tej metody 
{
	double QuotationValue;//warto�� notowania
	String tmpQuotation="";//tymczasowa zmienna typu string, poniewa� na stronie internetowej mamy ci�g znak�w -
	//patrz dokumentacja
	
	//utworzenie dokumentu i po��czenie ze stron� internetow� i pobranie jej zawarto�ci
	Document doc =Jsoup.connect("https://www.bankier.pl/inwestowanie/profile/quote.html?symbol=CDPROJEKT").get();
	
	//dokonanie selekcji znacznik�w div, poniewa� notowanie na stronie internetowej zawiera si� 
	//w znaczniku div - patrz dokumentacja
	Elements Quotation = doc.select("div");
	
	//znajdowanie klasy profilLast, kt�ra zawiera warto�� notowania na stronie www.bankier.pl - patrz dokumentacja
	// wykorzystana zostanie p�tla
	for (Element src :Quotation) // sprawdzony zostanie ka�dy element div
	{
		if(src.attr("class").equals("profilLast"))//je�eli atrybut class = profilLast, to nast�puje zapis warto�ci 
			//znajduj�cej si� w znaczniku div i klasie profilLast do zmiennej tmpQuotation
		{
			tmpQuotation=src.text();
		}
		//odczytana warto� posiada przecinek  jako separator dziesi�tny, a separatorem dziesi�tnym zmiennej typu
		//double jest kropka - trzeba wi�c zamieni� przecinek na kropk�
		//odczytana warto�� tekstowa zawira r�wnie� spacj� i tekst z� - nale�y je usun��
		tmpQuotation = tmpQuotation.replace(",", ".");//zamiana przecinka na kropk� 
		//(w odczytanym notowaniu by� przecinek)
		
		tmpQuotation = tmpQuotation.replace(" ", "");//usuni�cie spacji
		tmpQuotation = tmpQuotation.replace("z�", ""); //usuni�cie tekstu z� - otrzymujemy 
		//tekst zawieraj�cy tylko znaki liczbowe
		
	}
		
		
	
	//System.out.println(tmpQuotation);
	QuotationValue=Double.parseDouble(tmpQuotation);//warto�� typu tekstowego 
	//zostanie skonwertowana na warto�� typu double 
	return QuotationValue;
	
}
	

}
