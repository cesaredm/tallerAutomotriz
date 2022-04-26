/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.CmbServicios;
import model.DetallesModel;
import model.FacturacionModel;
import model.Reportes;
import view.Menu;

/**
 *
 * @author 000
 */
public class FacturacionController implements ActionListener, KeyListener, CaretListener, MouseListener {

	Menu menu;
	FacturacionModel facturacionModel;
	CmbServicios cmbServicios;
	DetallesModel detallesModel;
	CreateServiceComponentFacturacion services;
	Reportes reportePDF;
	float precio,
		cantidad,
		importe,
		totalFactura,
		sumaImporte;
	String precioString,
		cantidadString,
		sumaImporteString,
		placa;
	TableColumnModel columnModel;
	DefaultTableCellRenderer formatColum;
	java.sql.Timestamp fechaSql;
	long fechaLong;
	Date fecha;
	int fila,
		factura,
		componente;
	DecimalFormat formato;
	DefaultTableModel tableModel;

	public FacturacionController(Menu menu, FacturacionModel facturacionModel) {
		this.menu = menu;
		this.facturacionModel = facturacionModel;
		this.detallesModel = new DetallesModel();
		this.reportePDF = new Reportes();
		this.actualizarNumeroFactura();
		this.stylesTables();
		this.buscarAutosFacturacion("");
		this.formato = new DecimalFormat("#,###,###,###,#00.00");
		this.fecha = new Date();
		this.formatColum = new DefaultTableCellRenderer();
		this.gruopFacturas();
		this.mostrarProformas("");
		this.menu.jcFechaFactura.setDate(this.fecha);
		this.menu.rbFactura.setSelected(true);
		//this.menu.cmbServiciosFacturacion.setModel(this.facturacionModel.showServiciosCmb());
		this.menu.btnAgregarServicioFacturacion.addActionListener(this);
		this.menu.btnAgregarServicioFacturacion.setActionCommand("btnAgregarServicioFacturacion");
		this.menu.btnCalcular.addActionListener(this);
		this.menu.btnCalcular.setActionCommand("btnCalcular");
		this.menu.tblComponentesFacturacion.addKeyListener(this);
		this.menu.eliminarComponenteFacturacion.addActionListener(this);
		this.menu.eliminarComponenteFacturacion.setActionCommand("mnEliminarComponentesFacturacion");
		this.menu.btnVentanaAutosFacturacion.addActionListener(this);
		this.menu.btnVentanaAutosFacturacion.setActionCommand("btnVentanaAutosFacturacion");
		this.menu.btnVentanaAutosFacturacion.addKeyListener(this);
		this.menu.btnLimpiarFactura.addActionListener(this);
		this.menu.btnLimpiarFactura.setActionCommand("btnLimpiarFactura");
		this.menu.btnGuardarFactura.addActionListener(this);
		this.menu.btnGuardarFactura.setActionCommand("btnGuardarFactura");
		this.menu.btnGuardarImprimirFactura.addActionListener(this);
		this.menu.btnGuardarImprimirFactura.setActionCommand("btnGuardarEimprimir");
		this.menu.btnBuscarProforma.addActionListener(this);
		this.menu.btnBuscarProforma.setActionCommand("btnBuscarProforma");
		this.menu.txtBuscarAutoFacturacion.addCaretListener(this);
		this.menu.tblAutosFacturacion.addMouseListener(this);
		this.menu.tblAutosFacturacion.addKeyListener(this);
		this.menu.rbFactura.addActionListener(this);
		this.menu.rbFactura.setActionCommand("rbFactura");
		this.menu.rbProforma.addActionListener(this);
		this.menu.rbProforma.setActionCommand("rbProforma");
		this.menu.txtBuscarProforma.addCaretListener(this);
		this.menu.AgregarFacturacion.addActionListener(this);
		this.menu.AgregarFacturacion.setActionCommand("mnAgregarFactura");
		this.menu.tblProformas.addMouseListener(this);
	}

	public void gruopFacturas() {
		this.menu.groupTipoDoc.add(this.menu.rbFactura);
		this.menu.groupTipoDoc.add(this.menu.rbProforma);
	}

	public void stylesTables() {
		menu.tblComponentesFacturacion.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblComponentesFacturacion.getTableHeader().setOpaque(false);
		menu.tblComponentesFacturacion.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblComponentesFacturacion.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblComponentesFacturacion.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));

		menu.tblAutosFacturacion.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblAutosFacturacion.getTableHeader().setOpaque(false);
		menu.tblAutosFacturacion.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblAutosFacturacion.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblAutosFacturacion.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));

		menu.tblProformas.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblProformas.getTableHeader().setOpaque(false);
		menu.tblProformas.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblProformas.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblProformas.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));

		menu.tblDetallesProformas.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblDetallesProformas.getTableHeader().setOpaque(false);
		menu.tblDetallesProformas.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblDetallesProformas.getTableHeader().setForeground(Color.WHITE);
		menu.tblDetallesProformas.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
	}

	public void alinearTextoTable() {
		this.formatColum.setHorizontalAlignment(SwingConstants.RIGHT);
		this.menu.tblComponentesFacturacion.getColumnModel().getColumn(3).setCellRenderer(formatColum);
		this.menu.tblComponentesFacturacion.getColumnModel().getColumn(4).setCellRenderer(formatColum);

		this.columnModel = this.menu.tblComponentesFacturacion.getColumnModel();
		this.columnModel.getColumn(0).setPreferredWidth(8);
		this.columnModel.getColumn(1).setPreferredWidth(400);
	}

	public boolean validar() {
		boolean validar = true;
		this.placa = this.menu.lblPlacaAuto.getText();
		this.fechaSql = new java.sql.Timestamp(this.menu.jcFechaFactura.getDate().getTime());
		this.totalFactura = (this.menu.lblTotalFactura.getText().equals(""))
			? 0 : Float.parseFloat(this.menu.lblTotalFactura.getText().replace(",", ""));
		this.fila = this.menu.tblComponentesFacturacion.getRowCount();
		if (this.placa.equals("")) {
			validar = false;
			JOptionPane.showMessageDialog(null, "Elija un auto!!", "Advertencia", JOptionPane.WARNING_MESSAGE);
		} else if (this.fila == 0) {
			JOptionPane.showMessageDialog(null, "Oops.. la factura esta vacia!!", "Advertencia", JOptionPane.WARNING_MESSAGE);
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public boolean isNumeric(String valor) {
		try {
			Float.parseFloat(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void actualizarNumeroFactura() {
		this.menu.lblNumeroFactura.setText("" + this.facturacionModel.numeroFactura());
	}

	public void actualizarNumeroProforma() {
		this.facturacionModel.numeroProforma();
		this.menu.lblNumeroFactura.setText("" + this.facturacionModel.getProforma());
	}

	public boolean validarDetalles() {
		boolean validar = true;
		this.fila = this.menu.tblComponentesFacturacion.getRowCount();
		this.factura = Integer.parseInt(this.menu.lblNumeroFactura.getText());
		if (this.fila == 0) {
			validar = false;
		}
		return validar;
	}

	public void guardarDetalle() {
		this.fila = this.menu.tblComponentesFacturacion.getRowCount();
		this.tableModel = (DefaultTableModel) this.menu.tblComponentesFacturacion.getModel();
		if (this.validarDetalles()) {
			for (int i = 0; i < this.fila; i++) {
				this.componente = Integer.parseInt(this.tableModel.getValueAt(i, 0).toString());
				this.cantidad = (!isNumeric(this.tableModel.getValueAt(i, 2).toString()))
					? 0 : Float.parseFloat(this.tableModel.getValueAt(i, 2).toString().replaceAll("\\s+", ""));
				this.precio = (!isNumeric(this.tableModel.getValueAt(i, 3).toString()))
					? 0 : Float.parseFloat(this.tableModel.getValueAt(i, 3).toString().replaceAll("\\s+", ""));
				this.importe = (!isNumeric(this.tableModel.getValueAt(i, 4).toString()))
					? 0 : Float.parseFloat(this.tableModel.getValueAt(i, 4).toString());
				if (this.menu.rbFactura.isSelected()) {
					this.detallesModel.setFactura(factura);
					this.detallesModel.setProforma(0);
				} else if (this.menu.rbProforma.isSelected()) {
					this.detallesModel.setProforma(factura);
					this.detallesModel.setFactura(0);
				}
				this.detallesModel.setComponente(componente);
				this.detallesModel.setCantidad(cantidad);
				this.detallesModel.setPrecio(precio);
				this.detallesModel.setImporte(importe);
				this.detallesModel.guardar();
			}
		}
	}

	public void guardar() {
		if (this.validar()) {
			this.facturacionModel.setAutoFactura(this.placa);
			this.facturacionModel.setFecha(this.fechaSql);
			this.facturacionModel.setTotal(this.totalFactura);
			if (this.menu.rbFactura.isSelected()) {
				this.facturacionModel.guardar();
			} else if (this.menu.rbProforma.isSelected()) {
				this.facturacionModel.guardarProforma();
			}
			this.guardarDetalle();
			this.actualizarNumeroFactura();
			this.menu.rbFactura.setSelected(true);
			limpiarFacturacion();
		}
	}

	public void guardarImprimir() {
		if (this.validar()) {
			try {
				this.facturacionModel.setAutoFactura(this.placa);
				this.facturacionModel.setFecha(this.fechaSql);
				this.facturacionModel.setTotal(this.totalFactura);
				if (this.menu.rbFactura.isSelected()) {
					this.facturacionModel.guardar();
					this.reportePDF.setTipoDoc("factura");
					this.reportePDF.setIdFactura(Integer.parseInt(menu.lblNumeroFactura.getText()));
				} else if (this.menu.rbProforma.isSelected()) {
					this.facturacionModel.guardarProforma();
					this.reportePDF.setTipoDoc("proforma");
					this.reportePDF.setIdProforma(Integer.parseInt(menu.lblNumeroFactura.getText()));
				}
				this.guardarDetalle();
				/* --------------------- SETEO DE DATOS PARA CREACION DE PDF --------------------*/
				this.reportePDF.setFecha(this.menu.jcFechaFactura.getDate());
				this.reportePDF.setMarca(this.menu.lblMarcaAuto.getText());
				this.reportePDF.setModelo(this.menu.lblModeloAuto.getText());
				this.reportePDF.setPlaca(this.menu.lblPlacaAuto.getText());
				this.reportePDF.setColor(this.menu.lblColorAuto.getText());
				this.reportePDF.setMotor(this.menu.lblMotorAuto.getText());
				this.reportePDF.setChasis(this.menu.lblChasisAuto.getText());
				this.reportePDF.setTotalFactura(this.totalFactura);
				this.reportePDF.setCliente(this.menu.lblPropietarioAuto.getText());
				this.reportePDF.setVin(this.menu.lblVin.getText());
				this.reportePDF.generarPDF();
				this.actualizarNumeroFactura();
				this.menu.rbFactura.setSelected(true);
				this.limpiarFacturacion();
			} catch (Exception ex) {
				Logger.getLogger(FacturacionController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void showComponents(int servicio) {
		this.menu.tblComponentesFacturacion.setModel(this.facturacionModel.getComponentes(servicio));
		this.alinearTextoTable();
	}

	public void sumarTotalFactura(int filas) {
		try {
			for (int i = 0; i < filas; i++) {
				this.sumaImporteString = menu.tblComponentesFacturacion.getValueAt(i, 4).toString();
				this.sumaImporte = (this.sumaImporteString == null) ? 0 : Float.parseFloat(this.sumaImporteString);
				this.totalFactura += this.sumaImporte;
			}

			this.menu.lblTotalFactura.setText(this.formato.format(this.totalFactura));
			this.totalFactura = 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void calculosFacturacion() {
		this.fila = this.menu.tblComponentesFacturacion.getRowCount();
		for (int i = 0; i < fila; i++) {
			this.cantidadString = (String) this.menu.tblComponentesFacturacion.getValueAt(i, 2);
			this.precioString = (String) this.menu.tblComponentesFacturacion.getValueAt(i, 3);
			this.cantidad = (this.cantidadString == null || this.cantidadString.equals(""))
				? 0 : Float.parseFloat(this.cantidadString.replaceAll("\\s+", ""));
			this.precio = (this.precioString == null || this.precioString.equals(""))
				? 0 : Float.parseFloat(this.precioString.replaceAll("\\s+", ""));
			this.importe = this.cantidad * precio;
			this.menu.tblComponentesFacturacion.setValueAt(this.importe, i, 4);
		}
		sumarTotalFactura(fila);
	}

	public void eliminarFilas() {
		try {
			this.tableModel = (DefaultTableModel) this.menu.tblComponentesFacturacion.getModel();
			int[] filas = this.menu.tblComponentesFacturacion.getSelectedRows();
			for (int index = 0; index < filas.length; index++) {
				this.tableModel.removeRow(filas[index] - index);

			}
			this.sumarTotalFactura(this.menu.tblComponentesFacturacion.getRowCount());
		} catch (Exception e) {

		}

	}

	public void buscarAutosFacturacion(String buscar) {
		this.menu.tblAutosFacturacion.setModel(this.facturacionModel.getAutos(buscar));
	}

	public void showWindowsAutosFacturacion() {
		this.menu.jdAutosFacturacion.setSize(577, 292);
		this.menu.jdAutosFacturacion.setLocationRelativeTo(null);
		this.menu.jdAutosFacturacion.setVisible(true);
	}

	public void seleccionarAuto() {
		this.fila = this.menu.tblAutosFacturacion.getSelectedRow();
		try {
			if (this.fila != -1) {
				this.placa = (String) this.menu.tblAutosFacturacion.getValueAt(fila, 0);
				this.facturacionModel.setPlaca(this.placa);
				this.facturacionModel.getAuto();
				this.menu.lblPlacaAuto.setText(this.facturacionModel.getPlaca());
				this.menu.lblModeloAuto.setText(this.facturacionModel.getModelo());
				this.menu.lblMarcaAuto.setText(this.facturacionModel.getMarca());
				this.menu.lblMotorAuto.setText(this.facturacionModel.getMotor());
				this.menu.lblChasisAuto.setText(this.facturacionModel.getChasis());
				this.menu.lblColorAuto.setText(this.facturacionModel.getColor());
				this.menu.lblPropietarioAuto.setText(this.facturacionModel.getPropietario());
				this.menu.lblVin.setText(this.facturacionModel.getVin());
				this.menu.jdAutosFacturacion.setVisible(false);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void limpiarFacturacion() {
		this.menu.lblPlacaAuto.setText("");
		this.menu.lblModeloAuto.setText("");
		this.menu.lblMarcaAuto.setText("");
		this.menu.lblMotorAuto.setText("");
		this.menu.lblChasisAuto.setText("");
		this.menu.lblColorAuto.setText("");
		this.menu.lblVin.setText("");
		this.menu.lblPropietarioAuto.setText("");
		this.menu.lblTotalFactura.setText("0.00");
		this.totalFactura = 0;
		try {
			this.tableModel = (DefaultTableModel) this.menu.tblComponentesFacturacion.getModel();
			this.fila = this.tableModel.getRowCount();
			for (int i = 0; i < this.fila; i++) {
				this.tableModel.removeRow(0);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				null,
				e + "ERROR: metodo limpiarFactura en el ctrl Facturacion."
			);
		}

	}

	public void mostrarProformas(String placa) {
		this.facturacionModel.mostrarProformaPorPlaca(placa);
		this.menu.tblProformas.setModel(this.facturacionModel.tableModelOtros);
	}

	public void agregarProformaAfacturacion() {
		int filaseleccionada = this.menu.tblProformas.getSelectedRow();
		int id;
		try {
			if (filaseleccionada != -1) {
				id = Integer.parseInt(this.menu.tblProformas.getValueAt(filaseleccionada, 0).toString());
				this.facturacionModel.getDetallesProforma(id);
				this.menu.tblComponentesFacturacion.setModel(this.facturacionModel.tableModel);
				this.alinearTextoTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void DetallesProformas() {
		int filaseleccionada = this.menu.tblProformas.getSelectedRow();
		try {
			this.detallesModel.setId(Integer.parseInt(this.menu.tblProformas.getValueAt(filaseleccionada, 0).toString()));
			this.detallesModel.mostrar();
			this.menu.tblDetallesProformas.setModel(this.detallesModel.tableModel);
			this.menu.jdDetallesProforma.setSize(905, 324);
			this.menu.jdDetallesProforma.setLocationRelativeTo(null);
			this.menu.jdDetallesProforma.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnAgregarServicioFacturacion": {
				this.cmbServicios = (CmbServicios) this.menu.cmbServiciosFacturacion.getSelectedItem();
				showComponents(this.cmbServicios.getId());
			}
			break;
			case "btnCalcular": {
				this.calculosFacturacion();
			}
			break;
			case "mnEliminarComponentesFacturacion": {
				this.eliminarFilas();
			}
			break;
			case "btnVentanaAutosFacturacion": {
				this.showWindowsAutosFacturacion();
			}
			break;
			case "btnLimpiarFactura": {
				this.limpiarFacturacion();
			}
			break;
			case "btnGuardarFactura": {
				this.guardar();
			}
			break;
			case "btnGuardarEimprimir": {
				this.guardarImprimir();
			}
			break;
			case "rbFactura": {
				if (this.menu.rbFactura.isSelected()) {
					this.actualizarNumeroFactura();
				}
			}
			break;
			case "rbProforma": {
				if (this.menu.rbProforma.isSelected()) {
					this.actualizarNumeroProforma();
				}
			}
			break;
			case "btnBuscarProforma": {
				this.facturacionModel.mostrarProformas(Integer.parseInt(this.menu.jsBuscarProforma.getValue().toString()));
				this.menu.tblProformas.setModel(this.facturacionModel.tableModelOtros);
			}
			break;
			case "mnAgregarFactura": {
				this.agregarProformaAfacturacion();
				this.calculosFacturacion();
			}
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.menu.tblComponentesFacturacion && e.getKeyCode() == e.VK_SPACE) {
			this.calculosFacturacion();
		} else if (e.getSource() == this.menu.tblAutosFacturacion && e.getKeyCode() == e.VK_ENTER) {
			this.seleccionarAuto();
		} else if (e.getSource() == this.menu.tblComponentesFacturacion && e.getKeyCode() == e.VK_F1) {
			this.guardar();
		} else if (e.getSource() == this.menu.tblComponentesFacturacion && e.getKeyCode() == e.VK_F2) {
			this.guardarImprimir();
		} else if (e.getSource() == this.menu.btnVentanaAutosFacturacion && e.getKeyCode() == e.VK_F1) {
			this.guardar();
		} else if (e.getSource() == this.menu.btnVentanaAutosFacturacion && e.getKeyCode() == e.VK_F2) {
			this.guardarImprimir();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBuscarAutoFacturacion) {
			this.buscarAutosFacturacion(this.menu.txtBuscarAutoFacturacion.getText());
		} else if (e.getSource() == this.menu.txtBuscarProforma) {
			this.mostrarProformas(this.menu.txtBuscarProforma.getText());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.tblAutosFacturacion) {
			if (e.getClickCount() == 1) {
				this.seleccionarAuto();
			}
		} else if (e.getSource() == this.menu.tblProformas) {
			if (e.getClickCount() == 2) {
				this.DetallesProformas();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
