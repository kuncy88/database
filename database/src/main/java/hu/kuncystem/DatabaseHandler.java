package hu.kuncystem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract class DatabaseHandler {
	
	private QueryHandler queryHandler = null;
	
	protected void setQueryHandler(QueryHandler qr){
		this.queryHandler = qr;
	}
	
	protected QueryHandler getQueryHandler(){
		return this.queryHandler;
	}

	public void dbClose(){
		if(queryHandler != null){
			try {
				queryHandler.getDataSource().getConnection().close();
				queryHandler = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				throw new IllegalStateException("QueryHandler is not found! Perhaps, you may run dbOpen method!");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	public abstract QueryHandler dbOpen(String driverClassName, String url, String name, String password);

	public abstract static class QueryHandler {
		protected JdbcTemplate jdbcTemplateObject;
		private DriverManagerDataSource dataSource;
		
		public QueryHandler(DriverManagerDataSource dataSource){
			this.dataSource = dataSource;
			this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		}
		
		public DriverManagerDataSource getDataSource(){
			return this.dataSource;
		}

		public boolean dbQuery(String sql) {
			try{
				jdbcTemplateObject.execute(sql);
				return true;
			}catch(BadSqlGrammarException e){
				e.printStackTrace();
				return false;
			}
			
		}

		public QueryResult dbSelect(String sql, Object[] args) {
			QueryResult result = null;
			try{
				result = new QueryResult(jdbcTemplateObject.queryForList(sql, args));
			}catch(BadSqlGrammarException e){
				result = new QueryResult(new ArrayList<Map<String, Object>>());
				e.printStackTrace();
			}
			return result;
		}

		public QueryResult dbSelect(String table, String[] fields) {
			return this.dbSelect(table, fields, null, null, null, null);
		}

		public QueryResult dbSelect(String table, String[] fields, Map<String, Object> where) {
			return this.dbSelect(table, fields, where, null, null, null);
		}

		public abstract QueryResult dbSelect(String table, String[] fields, Map<String, Object> where, String order,
				String group, String having);

		public abstract int dbInsert(String table, Map<String, Object> values);

		public abstract int dbInsert(String table, List<String> fields, String sql);

		public abstract int dbUpdate(String table, Map<String, Object> values, String from, Map<String, Object> where);

		public abstract int dbDelete(String table, Map<String, Object> where);
	}

}
