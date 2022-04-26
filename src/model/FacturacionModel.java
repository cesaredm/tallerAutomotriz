package model;

import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 000
 */
public class FacturacionModel extends Conexion {

	private int id, proforma;
	private String autoFactura;
	private Timestamp fecha;
	private String hora;
	private float total;

	//DATOS DE AUTO
	private String placa,
		marca,
		modelo,
		color,
		chasis,
		motor,
		propietario,
		vin;

	String[] datos;
	String consulta;
	PreparedStatement pst;
	Statement st;
	ResultSet rs;
	Connection cn;
	DefaultComboBoxModel modeloCmb;
	public DefaultTableModel tableModel,
		tableModelOtros;
	String[] titulos = {"ID", "Nombre", "Cantidad", "Precio", "Importe"};

	public FacturacionModel() {
		this.tableModel = new DefaultTableModel(null, this.titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				if (col == 0 || col == 4 || col == 1) {
					return false;
				}
				return true;
			}
		};
	}

	//GETTERS AND SETTERS DE FACTURA
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAutoFactura() {
		return this.autoFactura;
	}

	public void setAutoFactura(String auto) {
		this.autoFactura = auto;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public float getTotao() {
		return this.total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	//GETTERS AND SETTERS DE INFORMACION DE AUTOS
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getChasis() {
		return chasis;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getProforma() {
		return proforma;
	}

	public void setProforma(int proforma) {
		this.proforma = proforma;
	}

	public void guardar() {
		this.cn = conexion();
		this.consulta = "INSERT INTO facturacion(auto,fecha,total) VALUES(?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.autoFactura);
			this.pst.setTimestamp(2, this.fecha);
			this.pst.setFloat(3, this.total);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : en el metodo guardar en el modelo facturacion.");
		}
	}

	public void guardarProforma() {
		this.cn = conexion();
		this.consulta = "INSERT INTO proformas(auto,fecha,total) VALUES(?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.autoFactura);
			this.pst.setTimestamp(2, this.fecha);
			this.pst.setFloat(3, this.total);
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : en el metodo guardar en el modelo facturacion.");
		}
	}

	public void mostrarProformas(int proforma) {
		this.cn = conexion();
		this.consulta = "SELECT id,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,auto,total FROM proformas WHERE id = ? ORDER BY id DESC LIMIT 30";
		String[] titulos = {"N. Proforma", "Fecha y hora", "Placa", "Total"};
		this.datos = new String[4];
		this.tableModelOtros = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.st = this.cn.createStatement();
			this.st.execute("SET lc_time_names = 'es_ES'");
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, proforma);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("fecha");
				this.datos[2] = this.rs.getString("auto");
				this.datos[3] = this.rs.getString("total");
				this.tableModelOtros.addRow(datos);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo mostrarProformas en el modelo facturacion");
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void mostrarProformaPorPlaca(String placa) {
		this.cn = conexion();
		this.consulta = "SELECT id,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,auto,total FROM proformas WHERE auto LIKE ? ORDER BY id DESC"
			+ " LIMIT 30";
		String[] titulos = {"N. Proforma", "Fecha y hora", "Placa", "Total"};
		this.datos = new String[4];
		this.tableModelOtros = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.st = this.cn.createStatement();
			this.st.execute("SET lc_time_names = 'es_ES'");
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, "%"+placa+"%");
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("fecha");
				this.datos[2] = this.rs.getString("auto");
				this.datos[3] = this.rs.getString("total");
				this.tableModelOtros.addRow(datos);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo mostrarProformas en el modelo facturacion");
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void getDetallesProforma(int id){
		this.cn = conexion();
		this.consulta = "SELECT c.id,c.nombre,d.precio,d.cantidad,d.importe FROM detalles AS d INNER JOIN componentes AS c "
			+ "ON(d.componente=c.id) INNER JOIN proformas AS p ON(d.proforma=p.id) WHERE p.id = ?";
		this.datos = new String[5];
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			this.rs = this.pst.executeQuery();
			while(this.rs.next()){
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("nombre");
				this.datos[2] = this.rs.getString("precio");
				this.datos[3] = this.rs.getString("cantidad");
				this.datos[4] = this.rs.getString("importe");
				if (this.tableModel.getRowCount() < 12) {
					this.tableModel.addRow(datos);
				} else {
					JOptionPane.showMessageDialog(null, "Factura llego a su limite de items.");
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}finally{
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
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
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.modeloCmb;
	}

	public DefaultTableModel getComponentes(int servicio) {
		this.cn = conexion();
		this.consulta = "SELECT c.id,c.nombre FROM componentes AS c INNER JOIN servicios AS s ON(c.servicio=s.id) WHERE s.id = ? AND c.estado=1";
		this.datos = new String[5];
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, servicio);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("nombre");
				this.datos[2] = "1";
				this.datos[3] = "0";
				this.datos[4] = "0";
				if (this.tableModel.getRowCount() < 12) {
					this.tableModel.addRow(datos);
				} else {
					JOptionPane.showMessageDialog(null, "Factura llego a su limite de items.");
					break;
				}
			}
			this.cn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return this.tableModel;
	}

	public DefaultTableModel getAutos(String placa) {
		this.cn = conexion();
		this.consulta = "SELECT a.*,p.nombres,apellidos FROM autos AS a INNER JOIN propietario AS p ON(a.propietario=p.id) "
			+ "WHERE CONCAT(a.placa,p.nombres,apellidos) LIKE '%" + placa + "%'";
		String[] titulosTableAutos = {"Placa", "Marca", "Modelo", "Motor", "Chasis", "Vin", "Color", "Propietario"};
		this.datos = new String[8];
		this.tableModelOtros = new DefaultTableModel(null, titulosTableAutos) {
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
				this.datos[1] = this.rs.getString("Marca");
				this.datos[2] = this.rs.getString("Modelo");
				this.datos[3] = this.rs.getString("Motor");
				this.datos[4] = this.rs.getString("chasis");
				this.datos[5] = this.rs.getString("vin");
				this.datos[6] = this.rs.getString("color");
				this.datos[7] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.tableModelOtros.addRow(datos);
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return this.tableModelOtros;
	}

	public void getAuto() {
		this.cn = conexion();
		this.consulta = "SELECT a.*,p.nombres,apellidos FROM autos AS a INNER JOIN propietario AS p ON(a.propietario=p.id) "
			+ "WHERE a.placa = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setString(1, this.placa);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.placa = this.rs.getString("placa");
				this.marca = this.rs.getString("Marca");
				this.modelo = this.rs.getString("Modelo");
				this.motor = this.rs.getString("Motor");
				this.chasis = this.rs.getString("chasis");
				this.color = this.rs.getString("color");
				this.vin = this.rs.getString("vin");
				this.propietario = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
			}
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public int numeroFactura() {
		int numeroFactura = 0;
		this.cn = conexion();
		this.consulta = "SELECT MAX(id) AS numeroFactura FROM facturacion";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				numeroFactura = this.rs.getInt("numeroFactura") + 1;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, fecha);
		}
		return numeroFactura;
	}

	public void numeroProforma() {
		this.cn = conexion();
		this.consulta = "SELECT MAX(id) AS numeroProforma FROM proformas";
		try {
			this.st = this.cn.createStatement();
			this.rs = this.st.executeQuery(this.consulta);
			while (this.rs.next()) {
				this.proforma = this.rs.getInt("numeroProforma") + 1;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
