package model;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 000
 */
public class DetallesModel extends Conexion {

	private int id;
	private int factura,
		proforma;
	private int componente;
	private float cantidad;
	private float precio;
	private float importe;

	/* Variables de connexion */
	String consulta;
	Connection cn;
	PreparedStatement pst;
	ResultSet rs;
	String[] datos;
	public DefaultTableModel tableModel;

	public DetallesModel() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFactura() {
		return factura;
	}

	public void setFactura(int factura) {
		this.factura = factura;
	}

	public int getComponente() {
		return componente;
	}

	public void setComponente(int componente) {
		this.componente = componente;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public int getProforma() {
		return proforma;
	}

	public void setProforma(int proforma) {
		this.proforma = proforma;
	}

	public void guardar() {
		this.cn = conexion();
		this.consulta = "INSERT INTO detalles(factura,proforma,componente,cantidad,precio,importe) VALUES(?,?,?,?,?,?)";
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			if (this.proforma == 0) {
				this.pst.setInt(1, this.factura);
				this.pst.setNull(2, java.sql.Types.NULL);
				this.pst.setInt(3, this.componente);
				this.pst.setFloat(4, this.cantidad);
				this.pst.setFloat(5, this.precio);
				this.pst.setFloat(6, this.importe);
			} else if (this.factura == 0) {
				this.pst.setNull(1, java.sql.Types.NULL);
				this.pst.setInt(2, this.proforma);
				this.pst.setInt(3, this.componente);
				this.pst.setFloat(4, this.cantidad);
				this.pst.setFloat(5, this.precio);
				this.pst.setFloat(6, this.importe);
			}
			this.pst.executeUpdate();
			this.cn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + "ERROR: en el metodo guardar en el modelo detalles");
		}
	}

	public void mostrar() {
		this.cn = conexion();
		this.consulta = "SELECT c.id,c.nombre,d.precio,d.cantidad,d.importe FROM detalles AS d INNER JOIN componentes AS c "
			+ "ON(d.componente=c.id) INNER JOIN proformas AS p ON(d.proforma=p.id) WHERE p.id = ?";
		this.datos = new String[5];
		String[] titulos = {"ID", "Cantidad", "Componente", "Precio", "Importe"};
		this.tableModel = new DefaultTableModel(null,titulos){
			@Override
			public boolean isCellEditable(int row,int col){
				return false;	
			}
		};
		try {
			this.pst = this.cn.prepareStatement(this.consulta);
			this.pst.setInt(1, id);
			this.rs = this.pst.executeQuery();
			while (this.rs.next()) {
				this.datos[0] = this.rs.getString("id");
				this.datos[1] = this.rs.getString("cantidad");
				this.datos[2] = this.rs.getString("nombre");
				this.datos[3] = this.rs.getString("precio");
				this.datos[4] = this.rs.getString("importe");
				this.tableModel.addRow(datos);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				this.cn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void eliminar() {
	}

	public void editar() {
	}

	public void actualizar() {
	}

}
