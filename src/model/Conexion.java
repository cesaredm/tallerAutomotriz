
package model;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Conexion {
	String user = "root";
	String password = "19199697tsoCD";
	String db = "taller";
	String host = "jdbc:mysql://localhost/"+db;	

	public Conexion(){

	}
	
	public Connection conexion(){
		Connection link = null;
		try {
				//cargamos el driver
				Class.forName("org.gjt.mm.mysql.Driver");
			link = DriverManager.getConnection(host,user,password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "ERROR AL INTENTENTAR CONECTARCE CON LA BASE DE DATOS \n "
				+ "ERROR : "+ ex);
			Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
		}

		return link;
	}
}
