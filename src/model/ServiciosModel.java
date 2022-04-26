package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ServiciosModel extends Conexion {

	private String nombre;
	private int id;
	DefaultComboBoxModel modeloCmb;

	String[] datos;
	PreparedStatement pst;
	Connection cn;
	String consulta;
	ResultSet rs;
	int banderin;

	DefaultTableModel modelo;

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

	public void guardar() {
		this.consulta = "INSERT INTO servicios(nombre) VALUES(?)";
		this.cn = conexion();

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.nombre);

			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Nombre guardado con éxito..");
			} else {
				JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un error al intentar guardar el nombre..");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " En el modelo de servicio, del metodo guardar");
		}
	}

	public DefaultTableModel mostrar(String valor) {
		this.consulta = "SELECT * FROM servicios WHERE CONCAT(nombre) LIKE '%" + valor + "%' ORDER BY id DESC";
		String[] titulos = {"ID", "Nombre"};
		String[] datos = new String[2];
		this.modelo = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		this.cn = conexion();
		try {
			this.pst = this.cn.prepareStatement(consulta);
			this.rs = this.pst.executeQuery();
			while (rs.next()) {
				datos[0] = rs.getString("id");
				datos[1] = rs.getString("nombre");

				this.modelo.addRow(datos);
			}
			this.cn.close();
		} catch (Exception e) {

		}
		return this.modelo;
	}

	public void actualizar() {
		this.consulta = "UPDATE servicios SET nombre = ? WHERE id = ?";
		this.cn = conexion();

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.nombre);

			this.pst.setInt(2, id);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Servicio actualizado con éxito..");
			} else {
				JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental actualizar el servicio..");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " En el modelo de ServicioModel en el metodo guardar");
		}
	}

	public void editar() {
		this.consulta = "SELECT * FROM servicios WHERE id = ?";

		this.cn = conexion();
		try {
			this.pst = this.cn.prepareStatement(consulta);
			this.pst.setInt(1, id);
			this.rs = this.pst.executeQuery();
			while (rs.next()) {
				this.nombre = this.rs.getString("nombre");

			}
			this.cn.close();
		} catch (Exception e) {

		}

	}

	public DefaultTableModel componenteServicio() {
		this.cn = conexion();
		String[] titulos = {"Id Comp.", "Nombre Comp."};
		this.consulta = "SELECT c.id,c.nombre AS componente FROM componentes AS c INNER JOIN servicios AS s ON(c.servicio = s.id)"
			+ " WHERE s.id = ? AND c.estado = 1";
		this.datos = new String[2];
		this.modelo = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("componente");
				this.modelo.addRow(datos);
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modelo;
	}

	public DefaultComboBoxModel showServiciosCmb() {
		this.cn = conexion();
		this.consulta = "SELECT * FROM servicios";
		this.modeloCmb = new DefaultComboBoxModel();
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.modeloCmb.addElement(new CmbServicios(this.rs.getInt("id"), this.rs.getString("nombre")));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(ServiciosModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return this.modeloCmb;
	}
}
