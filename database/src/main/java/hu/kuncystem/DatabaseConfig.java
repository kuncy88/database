package hu.kuncystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import hu.kuncystem.dbtype.PostgreSQL;

@Configuration
public class DatabaseConfig {
	
	@Bean(name=PostgreSQL.NAME)
	public PostgreSQL postgreSQL(){
		return new PostgreSQL();
	}
}
