/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class IngresoAutosModel extends Conexion {

	private int id;
	private Date fecha;
	private String placa,
		anotaciones;

	/* variables de conexion */
	Connection cn;
	PreparedStatement pst;
	ResultSet rs;
	String consulta;
	String[] datos;
	DefaultTableModel tableModel;

	public IngresoAutosModel() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public void guardar() {
		this.cn = conexion();
		this.consulta = "INSERT INTO registro_autos(auto,fecha,anotacion) VALUES(?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.placa);
			this.pst.setDate(2, this.fecha);
			this.pst.setString(3, this.anotaciones);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void editar(){
		this.cn = conexion();
		this.consulta = "SELECT * FROM registro_autos WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.rs = this.pst.executeQuery();
			while(this.rs.next()){
				this.placa = this.rs.getString("auto");
				this.fecha = this.rs.getDate("fecha");
				this.anotaciones = rs.getString("anotacion");
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "ERROR: en el metodo editar en modelo IngresoAuto");
		}
	}

	public void actualizar(){
		this.cn = conexion();
		this.consulta = "UPDATE registro_autos SET auto = ?, fecha = ?, anotacion = ? WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.placa);
			this.pst.setDate(2,this.fecha);
			this.pst.setString(3, this.anotaciones);
			this.pst.setInt(4, this.id);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel mostrar(String valor) {
		this.cn = conexion();
		this.consulta = "SELECT * FROM registro_autos WHERE CONCAT(auto) LIKE '%" + valor + "%' ORDER BY id DESC";
		String[] titulos = {"ID", "Placa", "Fecha", "Anotaciones"};
		this.datos = new String[4];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("auto");
				this.datos[2] = this.rs.getString("fecha");
				this.datos[3] = this.rs.getString("anotacion");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : metodo mostrar en el modelo ingresoAutos");
		}
		return this.tableModel;
	}

	public DefaultTableModel mostrarPorFecha(Date fecha) {
		this.cn = conexion();
		this.consulta = "SELECT * FROM registro_autos WHERE fecha = ? ORDER BY id DESC";
		String[] titulos = {"ID", "Placa", "Fecha", "Anotaciones"};
		this.datos = new String[4];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1,fecha);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("auto");
				this.datos[2] = this.rs.getString("fecha");
				this.datos[3] = this.rs.getString("anotacion");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : metodo mostrar en el modelo ingresoAutos");
		}
		return this.tableModel;
	}

	public DefaultTableModel getAutosRA(String valor) {
		this.cn = conexion();
		this.consulta = "SELECT a.placa,marca,modelo,p.nombres,apellidos FROM autos AS a INNER JOIN propietario AS p "
			+ "ON(a.propietario = p.id) WHERE CONCAT(a.placa,p.nombres,p.apellidos) LIKE '%" + valor + "%'";
		String[] titulos = {"Placa", "Marca", "Modelo","Propietario"};
		this.datos = new String[4];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("placa");
				this.datos[1] = this.rs.getString("marca");
				this.datos[2] = this.rs.getString("modelo");
				this.datos[3] = this.rs.getString("nombres") + " " + rs.getString("apellidos");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR: en el metodo getAutosRA en el modelo ingresoAutos");
		}
		return this.tableModel;
	}
}
