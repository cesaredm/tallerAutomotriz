package model;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class ReportesModel extends Conexion {

	private Date fecha1,
		fecha2,
		fechaApertura;
	private int factura;
	private float totalFacturadoRango,
		totalFacturadoDiario,
		apertura,
		efectivoCaja,
		montoApertura,
		ingresosEfectivo,
		egresoEfectivo;
	String[] datos;
	DecimalFormat formato;

	/* variables de conexion */
	String consulta;
	PreparedStatement pst;
	Statement st;
	ResultSet rs;
	Connection cn;
	DefaultTableModel tableModel;

	public ReportesModel() {
		this.formato = new DecimalFormat("###,###,###,#00.00");

	}

	public Date getFecha1() {
		return fecha1;
	}

	public void setFecha1(Date fecha1) {
		this.fecha1 = fecha1;
	}

	public Date getFecha2() {
		return fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public int getFactura() {
		return factura;
	}

	public void setFactura(int factura) {
		this.factura = factura;
	}

	public float getTotalFacturadoRango() {
		return totalFacturadoRango;
	}

	public void setTotalFacturadoRango(float totalFacturado) {
		this.totalFacturadoRango = totalFacturado;
	}

	public float getTotalFacturadoDiario() {
		return totalFacturadoDiario;
	}

	public void setTotalFacturadoDiario(float totalFacturadoDiario) {
		this.totalFacturadoDiario = totalFacturadoDiario;
	}

	public float getApertura() {
		return apertura;
	}

	public void setApertura(float apertura) {
		this.apertura = apertura;
	}

	public float getMontoApertura() {
		return montoApertura;
	}

	public void setMontoApertura(float montoApertura) {
		this.montoApertura = montoApertura;
	}

	public float getEfectivoCaja() {
		return efectivoCaja;
	}

	public void setEfectivoCaja(float efectivoCaja) {
		this.efectivoCaja = efectivoCaja;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public float getIngresosEfectivo() {
		return ingresosEfectivo;
	}

	public void setIngresosEfectivo(float ingresosEfectivo) {
		this.ingresosEfectivo = ingresosEfectivo;
	}

	public float getEgresoEfectivo() {
		return egresoEfectivo;
	}

	public void setEgresoEfectivo(float egresoEfectivo) {
		this.egresoEfectivo = egresoEfectivo;
	}

	public void close() {
		try {
			this.cn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex + "ERROR: en el metodo close en el modelo reportes");
		}
	}

	public DefaultTableModel getFacturasRango() {
		this.cn = conexion();
		this.consulta = "SELECT f.id,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,total,a.placa, marca,p.nombres,apellidos FROM"
			+ " facturacion AS f INNER JOIN autos AS a ON(f.auto=a.placa) INNER JOIN propietario AS p ON(a.propietario=p.id) "
			+ "WHERE DATE(f.fecha) BETWEEN ? AND ? ORDER BY f.id DESC";
		String[] titulos = {"N° Fact.", "Fecha y hora de Emisión", "Total", "Auto", "Marca", "Propietario"};
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
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.pst.setDate(2, this.fecha2);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("fecha");
				this.datos[2] = this.formato.format(this.rs.getFloat("total"));
				this.datos[3] = this.rs.getString("placa");
				this.datos[4] = this.rs.getString("marca");
				this.datos[5] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.tableModel.addRow(this.datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		} finally {
			this.close();
		}
		return this.tableModel;
	}

	public DefaultTableModel getFacturasDiarias() {
		this.cn = conexion();
		this.consulta = "SELECT f.id,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,total,a.placa, marca,p.nombres,apellidos FROM facturacion"
			+ " AS f INNER JOIN autos AS a ON(f.auto=a.placa) INNER JOIN propietario AS p ON(a.propietario=p.id) WHERE "
			+ "DATE(f.fecha) = ? ORDER BY f.id DESC";
		String[] titulos = {"N° Fact.", "Fecha y hora de Emisión", "Total", "Auto", "Marca", "Propietario"};
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
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("fecha");
				this.datos[2] = this.formato.format(this.rs.getFloat("total"));
				this.datos[3] = this.rs.getString("placa");
				this.datos[4] = this.rs.getString("marca");
				this.datos[5] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.tableModel.addRow(this.datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
		return this.tableModel;
	}

	public DefaultTableModel getFacturaId() {
		this.cn = conexion();
		this.consulta = "SELECT f.id,DATE_FORMAT(fecha,'%d - %M - %y %r') AS fecha,total,a.placa, marca,p.nombres,apellidos FROM facturacion"
			+ " AS f INNER JOIN autos AS a ON(f.auto=a.placa) INNER JOIN propietario AS p ON(a.propietario=p.id) WHERE f.id = ?";
		String[] titulos = {"N° Fact.", "Fecha y hora de Emisión", "Total", "Auto", "Marca", "Propietario"};
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
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.factura);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("fecha");
				this.datos[2] = this.formato.format(this.rs.getFloat("total"));
				this.datos[3] = this.rs.getString("placa");
				this.datos[4] = this.rs.getString("marca");
				this.datos[5] = this.rs.getString("nombres") + " " + this.rs.getString("apellidos");
				this.tableModel.addRow(this.datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
		return this.tableModel;
	}

	public void apertura() {
		this.cn = conexion();
		this.consulta = "SELECT monto FROM apertura WHERE fecha = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fechaApertura);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.apertura = this.rs.getFloat("monto");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
	}

	public void getFacturadoRango() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(f.total) AS total FROM facturacion AS f WHERE DATE(f.fecha) BETWEEN ? AND ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha1);
			this.pst.setDate(2, fecha2);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.totalFacturadoRango = this.rs.getFloat("total");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "ERROR: en el metodo getFacturado en el modelo reportes");
		} finally {
			this.close();
		}
	}

	public void getFacturadoDiario() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(total) AS total FROM facturacion AS f WHERE DATE(f.fecha) = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fecha1);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.totalFacturadoDiario = this.rs.getFloat("total");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
	}

	public void guardarApertura() {
		this.cn = conexion();
		this.consulta = "INSERT INTO apertura(fecha,monto) VALUES(?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, fechaApertura);
			this.pst.setFloat(2, montoApertura);
			int banderin = this.pst.executeUpdate();
			if (banderin > 0) {
				JOptionPane.showMessageDialog(null, "Apertura realizada con exito..");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
	}

	public void ingresos() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Ingreso' AND DATE(fecha) = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.ingresosEfectivo = this.rs.getFloat("total");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo ingresos en modelo reportes");
		} finally {
			this.close();
		}
	}

	public void egresos() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Egreso' AND DATE(fecha) = ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.egresoEfectivo = this.rs.getFloat("total");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo egresos en modelo reportes");
		} finally {
			this.close();
		}
	}

	public void egresosRango() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Egreso' AND DATE(fecha) BETWEEN ? AND ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.pst.setDate(2, this.fecha2);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.egresoEfectivo = this.rs.getFloat("total");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo egresos en modelo reportes");
		} finally {
			this.close();
		}
	}

	public void ingresosRango() {
		this.cn = conexion();
		this.consulta = "SELECT SUM(monto) AS total FROM transacciones WHERE tipo = 'Ingreso' AND DATE(fecha) BETWEEN ? AND ?";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setDate(1, this.fecha1);
			this.pst.setDate(2, this.fecha2);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.ingresosEfectivo = this.rs.getFloat("total");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " en el metodo ingresos en modelo reportes");
		} finally {
			this.close();
		}
	}
	
	public DefaultTableModel DetallesFactura() {
		this.cn = conexion();
		this.consulta = "SELECT d.id,c.nombre,d.cantidad,precio,importe FROM detalles AS d INNER JOIN componentes AS c"
			+ " ON(d.componente = c.id) INNER JOIN facturacion AS f ON(d.factura = f.id) WHERE  f.id = ?";
		String[] titulos = {"Id", "Nombre", "Cantidad", "Precio", "Importe"};
		this.datos = new String[5];
		this.tableModel = new DefaultTableModel(null, titulos) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, this.factura);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("nombre");
				this.datos[2] = this.rs.getString("cantidad");
				this.datos[3] = this.rs.getString("precio");
				this.datos[4] = this.rs.getString("importe");
				this.tableModel.addRow(datos);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			this.close();
		}
		return this.tableModel;
	}

}
