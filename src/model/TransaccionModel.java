/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class TransaccionModel extends Conexion {

	private Timestamp fecha;
	private String tipo,
		anotaciones,
		usuario;
	private float monto;
	private int id;

	String[] datos;
	Connection cn;
	PreparedStatement pst;
	ResultSet rs;
	Statement st;
	public DefaultTableModel tableModel;
	String consulta;
	private int banderin;

	public TransaccionModel() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void guardar() {
		this.cn = conexion();
		this.consulta = "INSERT INTO transacciones(fecha,tipo,anotacion,monto,usuario) VALUES(?,?,?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setTimestamp(1, this.fecha);
			this.pst.setString(2, this.tipo);
			this.pst.setString(3, this.anotaciones);
			this.pst.setFloat(4, this.monto);
			this.pst.setString(5, this.usuario);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Transaccion guardada con exito.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(TransaccionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void editar() {
		this.cn = conexion();
		this.consulta = "SELECT * FROM transacciones WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.tipo = this.rs.getString("tipo");
				this.monto = this.rs.getFloat("monto");
				this.fecha = this.rs.getTimestamp("fecha");
				this.anotaciones = this.rs.getString("anotacion");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(TransaccionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void actualizar() {
		this.cn = conexion();
		this.consulta = "UPDATE transacciones SET fecha=?,tipo=?,monto=?,anotacion=? WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setTimestamp(1, this.fecha);
			this.pst.setString(2, this.tipo);
			this.pst.setFloat(3, this.monto);
			this.pst.setString(4, this.anotaciones);
			this.pst.setInt(5, this.id);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Transaccion actualizada con exito.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(TransaccionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void eliminar() {
		this.cn = conexion();
		this.consulta = "DELETE FROM transacciones WHERE id = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1,this.id);
			this.banderin = this.pst.executeUpdate();
			if(this.banderin>0){
				JOptionPane.showMessageDialog(null, "Transacción eliminada con exito.");
			}
		} catch (Exception e) {
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(TransaccionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void mostrar(String valor) {
		this.cn = conexion();
		this.consulta = "SELECT id,tipo,monto,anotacion,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,usuario FROM transacciones"
			+ " WHERE CONCAT(tipo,usuario,fecha) LIKE '%"+valor+"%'";
		String[] titulos = {"N°", "Tipo", "Monto", "Nota", "Fecha", "Usuario"};
		this.datos = new String[6];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.st = this.cn.createStatement();
			this.st.execute("SET lc_time_names = 'es_ES'");
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("tipo");
				this.datos[2] = this.rs.getString("monto");
				this.datos[3] = this.rs.getString("anotacion");
				this.datos[4] = this.rs.getString("fecha");
				this.datos[5] = this.rs.getString("usuario");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(TransaccionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
