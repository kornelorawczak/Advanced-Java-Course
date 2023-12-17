import java.io.IOException; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class WriteData { 
	
	int wrD(DataStruct Dane) throws IOException { 
		//metoda realizujaca operacja w db 
		//zwraca wartosc 0 jezeli operacja (zapisu do bazy) 
		//powiodla sie, 1 gdy nie powiodla sie; obsluga wyjatkow
		
		try
		{
		
		Connection conn = DriverManager.getConnection("jdbc:mariadb://mysqljava-lab.ue.wroc.pl:29486?useTimezone=true&serverTimezone=UTC", "pmaadm","BeLx3HFM6EXk9gCF");
		//Tworzenie polaczenia z baza danych, jako parametry 
		//podajemy adres serwera (i strefa czasowa), 
		//nazwe uzytkownika, haslo
		//jesli beda problemy z polaczeniem 
		//z baza, to program po poleceniach w tej linii 
		//przejdzie do sekcji "catch"
		System.out.println("polaczono z baza");
		//ten komunikat pojawi sie, jesli nie bedzie problemow 
		//z polaczeniem
		Statement stmt = conn.createStatement
			(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//ustawienie parametrow polaczenia: 
		//polaczenie nie na wylacznosc oraz mozliwosc zapisu danych
		stmt.execute("USE Gielda1");
		//wybor bazy danych na serwerze (polecenie USE - jezyk SQL)
		//jesli beda problemy z dostepem do tej bazy, 
		//to program po poleceniu w tej linii przejdzie do sekcji "catch"
		System.out.println("execute");//ten komunikat pojawi sie, 
		//jesli nie bedzie problemow z dostepem do wskazanej bazy
		ResultSet rs=stmt.executeQuery("SELECT * FROM Gielda1 WHERE Id=(Select max(Id) from Gielda1)");
		//instrukcja wykona polecenie SELECT - jezyk SQL
		//zawartosci obiektu "rs" bedzie 
		//cala tabela Gielda "*" oznacza wszystkie pola, 
		//po slowie "FROM" podajemy nazwe tabeli
		
		
		
		rs.moveToInsertRow();//wstawienie nowego wiersza
		rs.updateString("Id_agenta", Dane.Id_agenta);
		//zapis danych do pola Id_agenta w bazie z pola 
		//Id_agenta w strukturze danych
		rs.updateString("Nazwa_wskaznika", Dane.Nazwa_wskaznika);
		rs.updateDouble("Wartosc_wskaznika", Dane.Wartosc_wskaznika);
		rs.updateDouble("Wartosc_notowania", Dane.Wartosc_notowania);
		rs.updateInt("Decyzja", Dane.Decyzja);
		rs.updateTimestamp("Data", Dane.Data);
		rs.updateString("Instrument_finansowy", Dane.Instrument_finansowy);
		rs.insertRow();
		rs.close();
		stmt.close();
		conn.close();
		
		return 0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 1;
		}
		
	}
}
