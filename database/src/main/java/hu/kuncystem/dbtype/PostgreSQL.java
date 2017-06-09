package hu.kuncystem.dbtype;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import hu.kuncystem.DatabaseHandler;
import hu.kuncystem.QueryResult;

public class PostgreSQL extends DatabaseHandler{
	public final static String NAME = "postgreSql";
	
	public PostgreSQL() {}

	@Override
	public QueryHandler dbOpen(String driverClassName, String url, String name, String password) {
		QueryHandler queryHandler = this.getQueryHandler();
		
		if(queryHandler == null){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUrl(url);
			dataSource.setUsername(name);
			dataSource.setPassword(password);
			
			queryHandler = new SelfQueryHandler(dataSource);
			this.setQueryHandler(queryHandler);
		}
		
		return queryHandler;
	}
	
	private class SelfQueryHandler extends QueryHandler{
		
		public SelfQueryHandler(DriverManagerDataSource dataSource){
			super(dataSource);
		}

		@Override
		public QueryResult dbSelect(String table, String[] fields, Map<String, Object> where, String order,
				String group, String having) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int dbInsert(String table, Map<String, Object> values) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int dbInsert(String table, List<String> fields, String sql) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int dbUpdate(String table, Map<String, Object> values, String from, Map<String, Object> where) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int dbDelete(String table, Map<String, Object> where) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
