package hu.kuncystem;

import hu.kuncystem.DatabaseHandler.QueryHandler;
import hu.kuncystem.dbtype.PostgreSQL;

public class App {
	public static void main(String[] args){
		DatabaseManager.init(PostgreSQL.NAME);
		
		QueryHandler db = DatabaseManager.getDatabase(PostgreSQL.NAME).dbOpen(
				"org.postgresql.Driver", 
				"jdbc:postgresql://localhost:5432/test", 
				"kuncy", 
				"12345678"
		);
		
		//db.dbQuery("abcd");
		QueryResult result = db.dbSelect("SELECT * FROM tabedef ORDER BY id DESC", new Object[]{});
		if(result == null){
			System.out.println("jajj ez null");
		}
		System.out.println("row count: " + result.getCount());
		for(QueryResult.QueryRow row: result.getAllRow()){
			System.out.println(row.get("id") + " - " + row.get("name"));
			System.out.println("column count: " + row.getCount());
		}
		
		for(String name: result.getColumnNames()){
			System.out.println("column name: " + name);
		}
		
		DatabaseManager.getDatabase(PostgreSQL.NAME).dbClose();
	}
}
