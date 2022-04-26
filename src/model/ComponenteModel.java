/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 000
 */
public class ComponenteModel extends Conexion {

	private String c, nombreServicio;
	private int servicio;
	private int id;
	Connection cn;
	PreparedStatement pst;
	Statement st;
	ResultSet rs;
	private String consulta;
	String[] datos;
	private int banderin;
	DefaultComboBoxModel modelo;
	DefaultTableModel modeloTable;
	private String nombre;

	public ComponenteModel() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getServicio() {
		return servicio;
	}

	public void setServicio(int servicio) {
		this.servicio = servicio;
	}

	public String getNombreServicio() {
		return this.nombreServicio;
	}

	public void guardar() {
		this.cn = conexion();
		this.consulta = "INSERT INTO componentes(nombre, servicio) VALUES(?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.nombre);
			this.pst.setInt(2, servicio);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Componente agregado con exito");
			} else {
				JOptionPane.showMessageDialog(null, "ERROR al intentar agregar el componente.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel showServicios(String valor) {
		this.consulta = "SELECT * FROM servicios WHERE nombre LIKE '%" + valor + "%' ORDER BY id DESC";
		this.cn = conexion();
		String[] titulos = {"ID", "Nombre de Servicio"};
		this.datos = new String[2];
		this.modeloTable = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (rs.next()) {
				this.datos[0] = rs.getString("id");
				this.datos[1] = rs.getString("nombre");
				this.modeloTable.addRow(datos);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " ERROR: en el metodo showServicios en modelo componente");
		}
		return this.modeloTable;
	}

	public void editar() {
		this.cn = conexion();
		this.consulta = "SELECT c.nombre,s.nombre AS nombreServicio, s.id AS idServicio FROM componentes AS c INNER JOIN servicios AS s ON(c.servicio=s.id) WHERE c.id =?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			this.rs = this.pst.executeQuery();
			while (rs.next()) {
				this.nombre = rs.getString("nombre");
				this.nombreServicio = rs.getString("nombreServicio");
				this.servicio = rs.getInt("idServicio");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public DefaultTableModel mostrar(String buscar) {
		this.cn = conexion();
		String[] titulos = {"Id Comp.", "Componente", "Servicio"};
		String[] datos = new String[3];
		this.consulta = "SELECT c.*, s.nombre AS nombreServicio FROM componentes AS c INNER JOIN servicios AS s ON(c.servicio=s.id)"
			+ "WHERE CONCAT(c.nombre, s.nombre) LIKE '%" + buscar + "%'  AND c.estado = 1 ORDER BY c.id DESC";
		this.modeloTable = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				datos[0] = this.rs.getString("id");
				datos[1] = this.rs.getString("nombre");
				datos[2] = this.rs.getString("nombreServicio");

				this.modeloTable.addRow(datos);
			}
			this.cn.close();
		} catch (Exception e) {

		}
		return this.modeloTable;
	}

	public void actualizar() {
		this.consulta = "UPDATE componentes SET nombre = ?, servicio = ? WHERE id = ?";
		this.cn = conexion();

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.nombre);
			this.pst.setInt(2, this.servicio);
			this.pst.setInt(3, id);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Componete actualizado con éxito..");
			} else {
				JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental actualizar el Componente..");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " En el modelo de ComponenteModel en el metodo guardar");
		}
	}
	public void eliminar(){
		this.consulta = "UPDATE componentes SET estado = ? WHERE id = ?";
		this.cn = conexion();
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1,0);
			this.pst.setInt(2, this.id);
			this.banderin = this.pst.executeUpdate();
			if(this.banderin > 0)
			{
				JOptionPane.showMessageDialog(null, "Componente eliminado con exito.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e + " en el metodo eliminar en el modelo componentes");
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(ComponenteModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public String[] getLastComponent(){
		String[] component = new String[5];
		this.cn = conexion();
		this.consulta = "SELECT * FROM componentes ORDER BY id DESC LIMIT 1";
		try {
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while(this.rs.next()){
				component[0] = this.rs.getString("id");
				component[1] = this.rs.getString("nombre");
				component[2] = "1";
				component[3] = "0";
				component[4] = "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(ComponenteModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return component;
	}
}
