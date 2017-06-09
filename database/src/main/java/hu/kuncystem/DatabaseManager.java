package hu.kuncystem;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DatabaseManager {
	private final static Map<String, DatabaseHandler> mDatabaseList = new HashMap<String, DatabaseHandler>();
			
	public static void init(String... list){
		if(list != null){
			for(int i = 0; i < list.length; i++){
				DatabaseManager.initDatabase(list[i]);
			}
		}
	}

	public static void initDatabase(String type){
		ApplicationContext ctx = null;
		DatabaseHandler dh = null;
		
		try{
			ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
			dh = (DatabaseHandler) ctx.getBean(type);
			
			if(dh == null){
				try {
					throw new Exception("Not found DatabaseType: " + type);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				mDatabaseList.put(type, dh);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(ctx != null){
				((ConfigurableApplicationContext) ctx).close();
			}
		}
	}
	
	public static DatabaseHandler getDatabase(String type){
		DatabaseHandler dh = mDatabaseList.get(type);
		if(dh == null){
			try {
				throw new Exception("Not found DatabaseType: " + type + ". First run init or initDatabase method!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dh;
	}
}
