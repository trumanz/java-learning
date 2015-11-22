package trumanz.H2Learn;

import org.junit.Test;

import org.junit.Assert;

import org.junit.BeforeClass;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import org.h2.tools.DeleteDbFiles;

public class H2Test {
	
	@BeforeClass
	public static void loadH2Driver() throws ClassNotFoundException{

		Class.forName("org.h2.Driver");
	}
	

	@Test
	public void fileDb() throws SQLException {
		DeleteDbFiles.execute("~", "test", true);


		Connection conn = DriverManager.getConnection("jdbc:h2:file:~/test");

		Statement stat = conn.createStatement();

		stat.execute("create table test(id int primary key, name varchar(255))");
		stat.execute("insert into test values(1, 'Hello')");
		ResultSet rs;
		rs = stat.executeQuery("select * from test");
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
		stat.close();
		conn.close();
		
		DeleteDbFiles.execute("~", "test", true);
	}
	

	@Test
	public void memDb() throws SQLException  {
		
		
		String url_db_close_delay = "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1";
		String url_new_db = "jdbc:h2:mem:db1";
		String url_ifexist = "jdbc:h2:mem:db1;IFEXISTS=TRUE";
		Connection conn = DriverManager.getConnection(url_new_db);

		Statement stat = conn.createStatement();

		stat.execute("create table test(id int primary key, name varchar(255))");
		stat.execute("insert into test values(1, 'Hello')");
		ResultSet rs;
		rs = stat.executeQuery("select * from test");
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
		stat.close();
		conn.close();
		
		
		boolean catched = false;
		try {
			conn = DriverManager.getConnection(url_ifexist);
		} catch (SQLException e) {
		
			catched  =true;
			//System.out.print(e.getErrorCode() + ":"+ e.getSQLState());
		}
		
		Assert.assertEquals(catched, true);
		
	}
		
}
