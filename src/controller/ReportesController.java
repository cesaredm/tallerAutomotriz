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
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import model.Reportes;
import model.ReportesModel;
import view.Menu;
import controller.Respaldo;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class ReportesController implements ActionListener, KeyListener, MouseListener {

	Menu menu;
	ReportesModel reportesModel;
	Reportes reportesPDF;
	Date fecha;
	java.sql.Date fechaSQL1, fechaSQL2, fechaAperturaSQL;
	int idFactura, fila;
	float montoApertura,
		totalEfectivoCaja;
	DecimalFormat formato;
	DefaultTableCellRenderer formatoColum;

	public ReportesController(Menu menu, ReportesModel reportesModel) {
		this.menu = menu;
		this.reportesModel = reportesModel;
		this.createRadioGroup();
		this.reportesPDF = new Reportes();
		this.fecha = new Date();
		this.menu.jcFechaInicioReporte.setDate(this.fecha);
		this.menu.jcFechaFinalReporte.setDate(this.fecha);
		this.menu.jcFechaApertura.setDate(this.fecha);
		this.formato = new DecimalFormat("#,###,###,###,#00.00");
		this.formatoColum = new DefaultTableCellRenderer();
		this.styleTable();
		this.mostrarFacturasDiarias();
		this.reporteDiario();
		this.alinearTextoTabla();
		this.menu.rbDiario.setSelected(true);
		this.filtros();
		this.menu.btnBuscarFacturasReporte.addActionListener(this);
		this.menu.btnBuscarFacturasReporte.setActionCommand("btnBuscarFacturasReportes");
		this.menu.btnGuardarApertura.addActionListener(this);
		this.menu.btnGuardarApertura.setActionCommand("btnGuardarApertura");
		this.menu.btnCrearApertura.addActionListener(this);
		this.menu.btnCrearApertura.setActionCommand("btnCrearApertura");
		this.menu.btnCrearRespaldo.addActionListener(this);
		this.menu.txtBuscarFacturaId.addKeyListener(this);
		this.menu.rbDiario.addActionListener(this);
		this.menu.rbDiario.setActionCommand("rbDiario");
		this.menu.rbRango.addActionListener(this);
		this.menu.rbRango.setActionCommand("rbRango");
		this.menu.ImprimirFactura.addActionListener(this);
		this.menu.ImprimirFactura.setActionCommand("mnReimprimirFactura");
		this.menu.tblFacturasReporte.addMouseListener(this);
	}

	public void createRadioGroup() {
		this.menu.groupReportes.add(this.menu.rbDiario);
		this.menu.groupReportes.add(this.menu.rbRango);
	}

	public void getFechas() {
		this.fechaSQL1 = new java.sql.Date(
			this.menu.jcFechaInicioReporte.getDate()
				.getTime()
		);
		this.fechaSQL2 = new java.sql.Date(
			this.menu.jcFechaFinalReporte.getDate()
				.getTime()
		);
	}

	public void reporteDiario() {
		this.getFechas();
		this.reportesModel.setFecha1(fechaSQL1);
		this.reportesModel.setFechaApertura(fechaSQL1);
		this.reportesModel.getFacturadoDiario();
		this.reportesModel.ingresos();
		this.reportesModel.egresos();
		this.reportesModel.apertura();
		this.totalEfectivoCaja = (this.reportesModel.getTotalFacturadoDiario()
			+ this.reportesModel.getApertura()
			+ this.reportesModel.getIngresosEfectivo()) - this.reportesModel.getEgresoEfectivo();
		/* set datos */

		this.menu.lblAperturaReporte.setText(this.formato.format(this.reportesModel.getApertura()));
		this.menu.lblTotalFacturado.setText(this.formato.format(this.reportesModel.getTotalFacturadoDiario()));
		this.menu.lblEgresos.setText(this.formato.format(this.reportesModel.getEgresoEfectivo()));
		this.menu.lblIngresos.setText(this.formato.format(this.reportesModel.getIngresosEfectivo()));
		this.menu.lblTotalEfectivoCaja.setText(this.formato.format(this.totalEfectivoCaja));
		this.reportesModel.setApertura(0);
	}

	public void reporteRango() {
		this.getFechas();
		this.reportesModel.setFecha1(fechaSQL1);
		this.reportesModel.setFecha2(fechaSQL2);
		this.reportesModel.getFacturadoRango();
		this.reportesModel.ingresosRango();
		this.reportesModel.egresosRango();
		this.totalEfectivoCaja = (this.reportesModel.getTotalFacturadoRango() + this.reportesModel.getIngresosEfectivo())
			- this.reportesModel.getEgresoEfectivo();
		/* set datos */

		this.menu.lblAperturaReporte.setText(this.formato.format(00.00));
		this.menu.lblIngresos.setText(this.formato.format(this.reportesModel.getIngresosEfectivo()));
		this.menu.lblEgresos.setText(this.formato.format(this.reportesModel.getEgresoEfectivo()));
		this.menu.lblTotalFacturado.setText(this.formato.format(this.reportesModel.getTotalFacturadoRango()));
		this.menu.lblTotalEfectivoCaja.setText(this.formato.format(this.totalEfectivoCaja));
	}

	public void alinearTextoTabla() {
		this.formatoColum.setHorizontalAlignment(SwingConstants.RIGHT);
		menu.tblFacturasReporte.getColumnModel().getColumn(2).setCellRenderer(this.formatoColum);
	}

	public void styleTable() {
		menu.tblFacturasReporte.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblFacturasReporte.getTableHeader().setOpaque(false);
		menu.tblFacturasReporte.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblFacturasReporte.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblFacturasReporte.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));

		menu.tblDetallesFactura.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblDetallesFactura.getTableHeader().setOpaque(false);
		menu.tblDetallesFactura.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblDetallesFactura.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblDetallesFactura.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
	}

	public void mostrarFacturasDiarias() {
		this.getFechas();
		this.reportesModel.setFecha1(fechaSQL1);
		this.menu.tblFacturasReporte.setModel(this.reportesModel.getFacturasDiarias());
		this.alinearTextoTabla();
	}

	public void mostrarFacturasRango() {
		this.getFechas();
		this.reportesModel.setFecha1(fechaSQL1);
		this.reportesModel.setFecha2(fechaSQL2);
		this.menu.tblFacturasReporte.setModel(this.reportesModel.getFacturasRango());
		this.alinearTextoTabla();
	}

	public void mostrarFacturaId() {
		this.idFactura = (!isNumeric(this.menu.txtBuscarFacturaId.getText()))
			? 1 : Integer.parseInt(this.menu.txtBuscarFacturaId.getText());
		this.reportesModel.setFactura(
			this.idFactura
		);
		this.menu.tblFacturasReporte.setModel(this.reportesModel.getFacturaId());
		this.alinearTextoTabla();
	}

	public boolean isNumeric(String valor) {
		try {
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void guardarApertura() {
		this.fechaAperturaSQL = new java.sql.Date(
			this.menu.jcFechaApertura.getDate().getTime()
		);

		this.montoApertura = Float.parseFloat(this.menu.jsMontoApertura.getValue().toString());
		this.reportesModel.setFechaApertura(fechaAperturaSQL);
		this.reportesModel.setMontoApertura(montoApertura);
		this.reportesModel.guardarApertura();
		this.menu.jsMontoApertura.setValue(0);
		this.menu.jdAperturaCaja.setVisible(false);
		this.reporteDiario();
	}

	public void isExiste() {

	}

	public void filtros() {
		if (this.menu.rbDiario.isSelected()) {
			this.menu.jcFechaInicioReporte.setEnabled(true);
			this.menu.jcFechaFinalReporte.setEnabled(false);
		} else if (this.menu.rbRango.isSelected()) {
			this.menu.jcFechaInicioReporte.setEnabled(true);
			this.menu.jcFechaFinalReporte.setEnabled(true);
		}
	}

	public void detallesFacturas() {
		this.fila = this.menu.tblFacturasReporte.getSelectedRow();
		this.idFactura = Integer.parseInt(this.menu.tblFacturasReporte.getValueAt(fila, 0).toString());
		this.reportesModel.setFactura(idFactura);
		this.menu.tblDetallesFactura.setModel(this.reportesModel.DetallesFactura());
		this.menu.jdDetallesFacturas.setSize(989, 330);
		this.menu.jdDetallesFacturas.setVisible(true);
	}

	public void ReimprimirFactura() {
		this.fila = this.menu.tblFacturasReporte.getSelectedRow();
		if (this.fila != -1) {
			try {
				this.idFactura = Integer.parseInt(this.menu.tblFacturasReporte.getValueAt(this.fila, 0).toString());
				this.reportesPDF.setIdFactura(this.idFactura);
				this.reportesPDF.infoFactura();
				this.reportesPDF.generarFactura();
			} catch (SQLException ex) {
				//Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnBuscarFacturasReportes": {
				if (this.menu.rbDiario.isSelected()) {
					this.mostrarFacturasDiarias();
					this.reporteDiario();
				} else if (this.menu.rbRango.isSelected()) {
					this.reporteRango();
					this.mostrarFacturasRango();
				}

			}
			break;
			case "btnGuardarApertura": {
				this.guardarApertura();
			}
			break;
			case "btnCrearApertura": {
				this.menu.jdAperturaCaja.setSize(400, 150);
				this.menu.jdAperturaCaja.setLocationRelativeTo(null);
				this.menu.jdAperturaCaja.setVisible(true);
			}
			break;
			case "rbDiario": {
				this.filtros();
			}
			break;
			case "rbRango": {
				this.filtros();
			}
			break;
			case "mnReimprimirFactura": {
				this.ReimprimirFactura();
			}
			break;
			case "btnCrearRespaldo": {
				Respaldo.backUp();
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.menu.txtBuscarFacturaId && e.getKeyCode() == e.VK_ENTER) {
			this.mostrarFacturaId();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.tblFacturasReporte) {
			if (e.getClickCount() == 2) {
				this.detallesFacturas();
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
