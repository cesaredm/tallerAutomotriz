/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Reportes extends Conexion {
	PreparedStatement pst;
	ResultSet rs;
	Connection cn;
	SimpleDateFormat sdf;
	private int idFactura;
	private String cliente,
		direccion,
		marca,
		modelo,
		color,
		placa,
		chasis,
		motor,
		vin;
	private java.util.Date fecha;
	private float totalFactura;

	public Reportes(){
		this.sdf = new SimpleDateFormat("EEEEEEEEE dd 'de' MMMMM 'de' yyyy HH:mm:ss z");
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public void setTotalFactura(float totalFactura) {
		this.totalFactura = totalFactura;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	

	
	
	public void generarFactura() throws SQLException {
		try {
			this.cn = conexion();
			JasperReport Reporte = null;
			Map parametros = new HashMap();
	                parametros.put("idFactura", this.idFactura);
			parametros.put("fecha", this.sdf.format(this.fecha));
	                parametros.put("cliente", this.cliente);
	                parametros.put("marca", this.marca);
			parametros.put("modelo",this.modelo);
			parametros.put("placa",this.placa);
			parametros.put("color",this.color);
			parametros.put("chasis",this.chasis);
			parametros.put("vin", this.vin);
			parametros.put("motor",this.motor);
			parametros.put("totalFactura",this.totalFactura);
			//Reporte = (JasperReport) JRLoader.loadObject(path);
			Reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/FacturaPDF.jasper"));
			JasperPrint jprint = JasperFillManager.fillReport(Reporte, parametros, cn);
			JasperViewer vista = new JasperViewer(jprint, false);
			vista.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			vista.setVisible(true);
			cn.close();
		} catch (JRException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	public void infoFactura(){
		this.cn = conexion();
		try {
			this.pst = this.cn.prepareStatement(
				"SELECT f.*,a.*,p.nombres,apellidos FROM facturacion AS f INNER JOIN autos AS a ON(f.auto=a.placa)"
					+ "INNER JOIN propietario AS p ON(a.propietario=p.id) WHERE f.id = ?"
			);
			this.pst.setInt(1, this.idFactura);
			this.rs = this.pst.executeQuery();
			while(this.rs.next()){
				this.fecha = this.rs.getTimestamp("fecha");	
				this.cliente = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.marca = this.rs.getString("marca");
				this.modelo = this.rs.getString("modelo");
				this.placa = this.rs.getString("placa");
				this.color = this.rs.getString("color");
				this.chasis = this.rs.getString("chasis");
				this.vin = this.rs.getString("vin");
				this.motor = this.rs.getString("motor");
				this.totalFactura = this.rs.getFloat("total");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
