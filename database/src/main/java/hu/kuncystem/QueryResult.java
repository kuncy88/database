package hu.kuncystem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QueryResult {
	//source data
	private final List<Map<String, Object>> lCursor;
	
	//this is contained all rows
	private ArrayList<QueryRow> allRow = null;
	
	public QueryResult(List<Map<String, Object>> c){
		if(c == null){
			 throw new IllegalStateException(QueryResult.class.getSimpleName() +
	                    " - Not found result set!");
		}
		this.lCursor = c;
	}
	
	/**
	 * Get all rows count from result set.
	 * */
	public int getCount(){
		int count = 0;
		if(lCursor != null){
			count = lCursor.size();
		}
		return count;
	}
	
	/**
	 * Get one row from result set
	 * 
	 * @param pos Row index which we are need. The index start from 0 and max index is all row-1.
	 * */
	public QueryRow getRow(int pos){
		if(pos >= 0 && lCursor.size() <= pos){			//index out of range(is to large or to small)
			return new QueryRow(new LinkedHashMap<String, Object>());
		}
		return new QueryRow(lCursor.get(pos));
	}
	
	/**
	 * Get all row from result set.
	 * */
	public ArrayList<QueryRow> getAllRow(){
		if(this.allRow == null){
			this.allRow = new ArrayList<QueryResult.QueryRow>();
			if(this.getCount() > 0){
				for(int i = 0; i < this.getCount(); i++){
					this.allRow.add(getRow(i));
				}
			}
		}
		return this.allRow;
	}
	
	/**
	 * Get first row from result set.
	 * */
	public QueryRow getFirstRow(){
		return getRow(0);
	}
	
	/**
	 * Get last row from result set.
	 * 
	 * @return QuerRow object. If the row is not found, because the result set don't contain rows, then empty object and never null value.
	 * */
	public QueryRow getLastRow(){
		return getRow(getCount()-1);
	}
	
	/**
	 * Get all column names from result set.
	 * */
	public ArrayList<String> getColumnNames(){
		return getFirstRow().getColumnNames();
	}
	
	public static class QueryRow{
		private final Map<String, Object> mRow;
		
		public QueryRow(Map<String, Object> row){
			this.mRow = row;
		}
		
		/**
		 * Get field value by sql name
		 * 
		 * @param columnName SQL field name
		 * 
		 * */
		public Object get(String columnName){
			return mRow.get(columnName);
		}
		
		/**
		 * Get columns count
		 * */
		public int getCount(){
			if(mRow == null){
				return 0;
			}else{
				return mRow.size();
			}
		}
		
		/**
		 * Get all sql field name form SQL result set. If the result set aren't rows, then the return value is going to be empty ArrayList object.
		 * 
		 * @return SQL field names list or empty list.
		 * */
		public ArrayList<String> getColumnNames(){
			ArrayList<String> columns = new ArrayList<String>();
			if(mRow != null){
				for(Entry<String, Object> entry: mRow.entrySet()){
					columns.add(entry.getKey());
				}
			}
			return columns;
		}
	}
}
