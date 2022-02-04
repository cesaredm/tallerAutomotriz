/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import model.IngresoAutosModel;
import view.Menu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class IngresoAutosController implements ActionListener, MouseListener, KeyListener, CaretListener {

	IngresoAutosModel ingresoAutosModel;
	Menu menu;
	private int id,
		fila;
	private java.sql.Date fechaSQL;
	Date fecha;
	private String placa,
		anotaciones;

	public IngresoAutosController(IngresoAutosModel registroAutosModel, Menu menu) {
		this.menu = menu;
		this.ingresoAutosModel = registroAutosModel;
		this.fecha = new Date();
		this.buscarAutos("");
		this.menu.jcFechaRA.setDate(fecha);
		this.menu.jcFechaBuscarRA.setDate(fecha);
		this.mostrarRegistrosPorFecha(this.menu.jcFechaBuscarRA.getDate());
		this.menu.btnActualizarRA.setEnabled(false);
		this.menu.btnGuardarRA.addActionListener(this);
		this.menu.btnGuardarRA.setActionCommand("btnGuardarRA");
		this.menu.btnActualizarRA.addActionListener(this);
		this.menu.btnActualizarRA.setActionCommand("btnActualizarRA");
		this.menu.btnLimpiarRA.addActionListener(this);
		this.menu.btnLimpiarRA.setActionCommand("btnLimpiarRA");
		this.menu.editarRA.addActionListener(this);
		this.menu.editarRA.setActionCommand("mnEditarRA");
		this.menu.txtBuscarAutoRA.addCaretListener(this);
		this.menu.txtBuscarRA.addCaretListener(this);
		this.menu.txtPlacaRA.addMouseListener(this);
		this.menu.tblAutosRA.addMouseListener(this);
		this.menu.tblAutosRA.addKeyListener(this);
		this.menu.txtAreaAnotacionesRA.addKeyListener(this);
		this.menu.jcFechaRA.addKeyListener(this);
		this.menu.btnBuscarRAporFecha.addActionListener(this);
		this.menu.btnBuscarRAporFecha.setActionCommand("btnBUscarRAporFecha");
	}

	public boolean validar() {
		boolean validar = true;
		this.placa = this.menu.txtPlacaRA.getText();
		this.fechaSQL = new java.sql.Date(this.menu.jcFechaRA.getDate().getTime());
		this.anotaciones = this.menu.txtAreaAnotacionesRA.getText();
		if (this.placa.equals("")) {
			validar = false;
		} else if (this.anotaciones.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public void guardar() {
		if (this.validar()) {
			this.ingresoAutosModel.setPlaca(this.placa);
			this.ingresoAutosModel.setFecha(fechaSQL);
			this.ingresoAutosModel.setAnotaciones(anotaciones);
			this.ingresoAutosModel.guardar();
			this.limpiar();
			this.mostrarRegistrosPorFecha(this.menu.jcFechaBuscarRA.getDate());
		}
	}

	public void editar() {
		this.fila = this.menu.tblRA.getSelectedRow();
		if (this.fila != -1) {
			this.id = Integer.parseInt(this.menu.tblRA.getValueAt(this.fila, 0).toString());
			this.ingresoAutosModel.setId(this.id);
			this.ingresoAutosModel.editar();
			this.menu.txtPlacaRA.setText(this.ingresoAutosModel.getPlaca());
			this.menu.jcFechaRA.setDate(this.ingresoAutosModel.getFecha());
			this.menu.txtAreaAnotacionesRA.setText(this.ingresoAutosModel.getAnotaciones());
			this.menu.btnActualizarRA.setEnabled(true);
			this.menu.btnGuardarRA.setEnabled(false);
		}
	}

	public void actualiar() {
		if (this.validar()) {
			this.ingresoAutosModel.setPlaca(this.placa);
			this.ingresoAutosModel.setFecha(fechaSQL);
			this.ingresoAutosModel.setAnotaciones(anotaciones);
			this.ingresoAutosModel.actualizar();
			this.menu.btnGuardarRA.setEnabled(true);
			this.menu.btnActualizarRA.setEnabled(false);
			this.limpiar();
			this.mostrarRegistrosPorFecha(this.menu.jcFechaBuscarRA.getDate());
		}
	}

	public void showWindowAutos() {
		this.menu.jdAutosRA.setSize(714, 300);
		this.menu.jdAutosRA.setLocationRelativeTo(null);
		this.menu.jdAutosRA.setVisible(true);
	}

	public void seleccionarAuto() {
		this.fila = this.menu.tblAutosRA.getSelectedRow();
		try {
			this.placa = (String) this.menu.tblAutosRA.getValueAt(this.fila, 0);
			this.menu.txtPlacaRA.setText(this.placa);
			this.menu.jdAutosRA.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Oops.. Ocurrio un error al intentar agregar el auto.\nERROR:"
				+ " en el metodo seleccionarAuto en ctrl IngresoAutos");
		}
	}

	public void buscarAutos(String valor) {
		this.menu.tblAutosRA.setModel(this.ingresoAutosModel.getAutosRA(valor));
	}

	public void mostrarRegistros(String valor) {
		this.menu.tblRA.setModel(this.ingresoAutosModel.mostrar(valor));
	}

	public void mostrarRegistrosPorFecha(Date fecha){
		this.menu.tblRA.setModel(this.ingresoAutosModel.mostrarPorFecha(new java.sql.Date(fecha.getTime())));
	}

	public void limpiar() {
		this.menu.txtPlacaRA.setText("");
		this.menu.jcFechaRA.setDate(this.fecha);
		this.menu.txtAreaAnotacionesRA.setText("");
		this.menu.btnGuardarRA.setEnabled(true);
		this.menu.btnActualizarRA.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnGuardarRA": {
				this.guardar();
			}
			break;
			case "mnEditarRA": {
				this.editar();
			}
			break;
			case "btnActualizarRA": {
				this.actualiar();
			}
			break;
			case "btnLimpiarRA": {
				this.limpiar();
			}break;
			case "btnBUscarRAporFecha":{
				this.mostrarRegistrosPorFecha(this.menu.jcFechaBuscarRA.getDate());
			}break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.txtPlacaRA) {
			this.showWindowAutos();
		} else if (e.getSource() == this.menu.tblAutosRA) {
			this.seleccionarAuto();
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.menu.tblAutosRA && e.getKeyCode() == e.VK_ENTER) {
			this.seleccionarAuto();
		} else if (e.getSource() == this.menu.txtAreaAnotacionesRA && e.getKeyCode() == e.VK_ENTER) {
			this.guardar();
		} else if (e.getSource() == this.menu.jcFechaRA && e.getKeyCode() == e.VK_ENTER) {
			this.guardar();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBuscarAutoRA) {
			this.buscarAutos(this.menu.txtBuscarAutoRA.getText());
		} else if (e.getSource() == this.menu.txtBuscarRA) {
			this.mostrarRegistros(this.menu.txtBuscarRA.getText());
		}
	}

}
