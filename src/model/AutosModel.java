package model;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class AutosModel extends Conexion {

	private String placa;
	private String marca;
	private String modelo;
	private String motor;
	private String chasis;
	private String color;
	private String vin;
	private int propietario;
	private String nombreCompletoPropietario;

	String[] datos;
	String consulta;
	DefaultTableModel modeloTable;
	ResultSet rs;
	Connection cn;
	PreparedStatement pst;
	private int banderin;
	private String filaseleccionada;

	public int getPropietario() {
		return propietario;
	}

	public void setPropietario(int propietario) {
		this.propietario = propietario;
	}

	public String getplaca() {
		return placa;
	}

	public void setplaca(String placa) {
		this.placa = placa;
	}

	public String getmarca() {
		return marca;
	}

	public void setmarca(String marca) {
		this.marca = marca;
	}

	public String getmodelo() {
		return modelo;
	}

	public void setmodelo(String modelo) {
		this.modelo = modelo;
	}

	public String getmotor() {
		return motor;
	}

	public void setmotor(String motor) {
		this.motor = motor;
	}

	public String getchasis() {
		return chasis;
	}

	public void setchasis(String chasis) {
		this.chasis = chasis;
	}

	public String getcolor() {
		return color;
	}

	public void setcolor(String color) {
		this.color = color;
	}

	public String getNombreCompletoPropietario() {
		return nombreCompletoPropietario;
	}

	public void setNombreCompletoPropietario(String nombreCompletoPropietario) {
		this.nombreCompletoPropietario = nombreCompletoPropietario;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public void guardar() {
		this.consulta = "INSERT INTO autos(propietario,placa,marca,modelo,motor,chasis,color,vin) VALUES(?,?,?,?,?,?,?,?)";
		this.cn = conexion();

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.propietario);
			this.pst.setString(2, this.placa);
			this.pst.setString(3, this.marca);
			this.pst.setString(4, this.modelo);
			this.pst.setString(5, this.motor);
			this.pst.setString(6, this.chasis);
			this.pst.setString(7, this.color);
			this.pst.setString(8, this.vin);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "Auto guardado con éxito..");
			} else {
				JOptionPane.showMessageDialog(null, "Ups.. Ocurrió un error al intentar guardar el auto..");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " En el modelo de AutosModel en el metodo guardar");
		}
	}

	public DefaultTableModel mostrarPropietarios(String valor) {
		this.cn = conexion();
		this.consulta = "SELECT p.id,nombres,apellidos FROM propietario AS p WHERE CONCAT(p.nombres,p.apellidos) LIKE '%" + valor + "%'"
			+ " ORDER BY p.id DESC";
		String[] titulos = {"Id", "Nombre"};
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
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.modeloTable.addRow(datos);
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "");
		}
		return this.modeloTable;
	}

	public void eliminar() {
	}

	public void editar() {
		this.cn = conexion();
		this.consulta = "select a.placa, p.id,nombres,apellidos, a.marca,modelo,motor,chasis,color,vin"
			+ " from autos AS a inner join propietario AS p on(a.propietario=p.id) WHERE a.placa = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.placa);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.propietario = this.rs.getInt("id");
				this.nombreCompletoPropietario = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.marca = this.rs.getString("marca");
				this.modelo = this.rs.getString("modelo");
				this.motor = this.rs.getString("motor");
				this.placa = this.rs.getString("placa");
				this.chasis = this.rs.getString("chasis");
				this.color = this.rs.getString("color");
				this.vin = this.rs.getString("vin");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: en el metodo editar en el modelo AutosModel" + e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(AutosModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public DefaultTableModel mostrarAutos(String buscar) {
		this.cn = conexion();
		String[] titulos = {"Placa", "Propietario", "Teléfono", "Marca", "Modelo", "Motor", "Chasis", "Vin", "Color",};
		String[] datos = new String[9];
		this.consulta = "select a.placa, p.nombres,apellidos,telefono, a.marca,modelo,motor,chasis,color,vin"
			+ " from autos AS a inner join propietario AS p on(a.propietario=p.id) WHERE CONCAT(a.placa,p.nombres,p.apellidos)"
			+ "LIKE '%" + buscar + "%'";
		this.modeloTable = new DefaultTableModel(null, titulos) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				datos[0] = this.rs.getString("placa");
				datos[1] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				datos[2] = this.rs.getString("telefono");
				datos[3] = this.rs.getString("marca");
				datos[4] = this.rs.getString("modelo");
				datos[5] = this.rs.getString("motor");
				datos[6] = this.rs.getString("chasis");
				datos[7] = this.rs.getString("vin");
				datos[8] = this.rs.getString("color");
				this.modeloTable.addRow(datos);
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modeloTable;
	}

	public void actualizar() {
		this.consulta = "UPDATE autos SET propietario = ?, placa = ?, marca = ?, modelo = ?, motor = ?, chasis = ?, color = ?, vin = ? WHERE placa = ?";
		this.cn = conexion();

		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.propietario);
			this.pst.setString(2, this.placa);
			this.pst.setString(3, this.marca);
			this.pst.setString(4, this.modelo);
			this.pst.setString(5, this.motor);
			this.pst.setString(6, this.chasis);
			this.pst.setString(7, this.color);
			this.pst.setString(8,this.vin);
			this.pst.setString(9, this.placa);
			this.banderin = this.pst.executeUpdate();
			if (this.banderin > 0) {
				JOptionPane.showMessageDialog(null, "auto actualizado con éxito..");
			} else {
				JOptionPane.showMessageDialog(null, "Ups.. Ocurrió un error al intentar actualizar el auto..");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " En el modelo de AutosModel en el metodo guardar");
		}
	}
}
