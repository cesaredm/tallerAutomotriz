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
import java.util.Date;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import model.TransaccionModel;
import view.Menu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class TransaccionController implements ActionListener, CaretListener {

	Menu menu;
	TransaccionModel transaccionModel;
	boolean validar;

	String tipo, anotacion, usuario;
	float monto;
	int id, filaseleccionada;
	java.sql.Timestamp fechaSql;
	Date fecha;

	public TransaccionController(Menu menu, TransaccionModel transaccionModel) {
		this.menu = menu;
		this.transaccionModel = transaccionModel;
		this.fecha = new Date();
		this.menu.jcFechaTransaccion.setDate(this.fecha);
		this.styleTable();
		this.mostrar("");
		this.menu.btnActualizarTransaccion.setEnabled(false);
		this.menu.btnGuardarTransaccion.addActionListener(this);
		this.menu.btnActualizarTransaccion.addActionListener(this);
		this.menu.eliminarTransaccion.addActionListener(this);
		this.menu.editarTransaccion.addActionListener(this);
		this.menu.btnLimpiarTransaccion.addActionListener(this);
		this.menu.txtBuscarTransaccion.addCaretListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnGuardarTransaccion": {
				this.guardar();
			}
			break;
			case "btnActualizarTransaccion": {
				this.actualizar();
			}
			break;
			case "mnEliminarTransaccion": {
				this.eliminar();
			}
			break;
			case "mnEditarTransaccion": {
				this.editar();
			}
			break;
			case "btnLimpiarTransaccion": {
				this.limpiar();
			}
			break;
		}
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBuscarTransaccion) {
			this.mostrar(this.menu.txtBuscarTransaccion.getText());
		}
	}

	/*------------------------------ METODOS --------------------------------*/
	public void styleTable() {
		menu.tblTransacciones.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblTransacciones.getTableHeader().setOpaque(false);
		menu.tblTransacciones.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblTransacciones.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblTransacciones.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
	}

	public void validar() {
		this.tipo = this.menu.cmbTipoTransacion.getSelectedItem().toString();
		this.anotacion = this.menu.txtAreaAnotacionTransaccion.getText();
		this.monto = Float.parseFloat(this.menu.jsMontoTransaccion.getValue().toString());
		this.fechaSql = new java.sql.Timestamp(this.menu.jcFechaApertura.getDate().getTime());
		this.usuario = this.menu.lblUsuarioSistema.getText();
		if (tipo.equals("")) {
			this.validar = false;
		} else {
			this.validar = true;
		}
	}

	public void guardar() {
		this.validar();
		if (this.validar) {
			this.transaccionModel.setTipo(tipo);
			this.transaccionModel.setFecha(fechaSql);
			this.transaccionModel.setAnotaciones(anotacion);
			this.transaccionModel.setMonto(monto);
			this.transaccionModel.setUsuario(this.usuario);
			this.transaccionModel.guardar();
			this.mostrar("");
			this.limpiar();
		}
	}

	public void mostrar(String valor) {
		this.transaccionModel.mostrar(valor);
		this.menu.tblTransacciones.setModel(this.transaccionModel.tableModel);
	}

	public void eliminar() {
		this.filaseleccionada = this.menu.tblTransacciones.getSelectedRow();
		if (this.filaseleccionada != -1) {
			this.id = Integer.parseInt(this.menu.tblTransacciones.getValueAt(this.filaseleccionada, 0).toString());
			this.transaccionModel.setId(this.id);
			this.transaccionModel.eliminar();
		}
	}

	public void editar() {
		this.filaseleccionada = this.menu.tblTransacciones.getSelectedRow();
		if (this.filaseleccionada != -1) {
			this.id = Integer.parseInt(this.menu.tblTransacciones.getValueAt(this.filaseleccionada, 0).toString());
			this.transaccionModel.setId(this.id);
			this.transaccionModel.editar();
			this.menu.cmbTipoTransacion.setSelectedItem(this.transaccionModel.getTipo());
			this.menu.jcFechaTransaccion.setDate(this.transaccionModel.getFecha());
			this.menu.jsMontoTransaccion.setValue(this.transaccionModel.getMonto());
			this.menu.txtAreaAnotacionTransaccion.setText(this.transaccionModel.getAnotaciones());
			this.menu.btnGuardarTransaccion.setEnabled(false);
			this.menu.btnActualizarTransaccion.setEnabled(true);
		}
	}

	public void actualizar() {
		this.validar();
		if (this.validar) {
			this.transaccionModel.setTipo(tipo);
			this.transaccionModel.setFecha(fechaSql);
			this.transaccionModel.setAnotaciones(anotacion);
			this.transaccionModel.setMonto(monto);
			this.transaccionModel.actualizar();
			this.mostrar("");
			this.limpiar();
			this.menu.btnGuardarTransaccion.setEnabled(true);
			this.menu.btnActualizarTransaccion.setEnabled(false);
		}
	}

	public void limpiar() {
		this.menu.jcFechaTransaccion.setDate(this.fecha);
		this.menu.cmbTipoTransacion.setSelectedItem("Ingreso");
		this.menu.jsMontoTransaccion.setValue(0);
		this.menu.txtAreaAnotacionTransaccion.setText("");
		this.menu.btnGuardarTransaccion.setEnabled(true);
		this.menu.btnActualizarTransaccion.setEnabled(false);
	}

}
