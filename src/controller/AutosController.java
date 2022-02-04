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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.JOptionPane;
import model.AutosModel;
import view.Menu;

/**
 *
 * @author 000
 */
public class AutosController implements MouseListener, ActionListener, CaretListener {

	int propietario;
	int filaseleccionada;
	AutosModel autosModel;
	String marca, placa, modelo, motor, chasis, color, idPropietario, nombrePropietario, vin;
	Menu menu;

	public AutosController(Menu menu, AutosModel autosModel) {

		this.autosModel = autosModel;
		this.menu = menu;
		this.mostrar("");
		this.mostrarPropietarios("");

		this.menu.btnGuardarAutos.addActionListener(this);
		this.menu.btnGuardarAutos.setActionCommand("btnGuardarAutos");

		this.menu.editarAuto.addActionListener(this);
		this.menu.editarAuto.setActionCommand("mnEditarAuto");

		this.menu.btnActualizarAutos.addActionListener(this);
		this.menu.btnActualizarAutos.setActionCommand("btnActulizarAuto");

		this.menu.btnLimpiarAutos.addActionListener(this);
		this.menu.btnLimpiarAutos.setActionCommand("btnlimpiarautos");

		this.menu.txtPropietarioAuto.addMouseListener(this);
		this.menu.txtBuscarPropietario.addCaretListener(this);
		this.menu.tblPropietariosAutos.addMouseListener(this);

		this.menu.txtBuscarPropietario.addCaretListener(this);
		this.menu.txtBucarAutos.addCaretListener(this);

		this.menu.btnActualizarAutos.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnGuardarAutos": {
				guardar();
				this.mostrar("");
			}
			break;
			case "btnActulizarAuto": {
				this.actualizar();
				this.mostrar("");
			}
			break;
			case "mnEditarAuto": {
				this.editar();
			}
			break;
			case "btnlimpiarautos": {
				this.limpiar();
			}
			break;

		}

	}

	public void guardar() {
		if (validarDatos()) {
			this.autosModel.setPropietario(this.propietario);
			this.autosModel.setplaca(this.placa);
			this.autosModel.setmarca(this.marca);
			this.autosModel.setmotor(this.motor);
			this.autosModel.setmodelo(this.modelo);
			this.autosModel.setchasis(this.chasis);
			this.autosModel.setcolor(this.color);
			this.autosModel.setVin(this.vin);
			this.autosModel.guardar();
			limpiar();

		} else {

		}
	}

	public void actualizar() {
		if (this.validarDatos()) {
			this.autosModel.setPropietario(this.propietario);
			this.autosModel.setplaca(this.placa);
			this.autosModel.setmarca(this.marca);
			this.autosModel.setmotor(this.motor);
			this.autosModel.setmodelo(this.modelo);
			this.autosModel.setchasis(this.chasis);
			this.autosModel.setcolor(this.color);
			this.autosModel.setVin(this.vin);
			this.autosModel.actualizar();
			limpiar();
			this.menu.btnActualizarAutos.setEnabled(false);
			this.menu.btnGuardarAutos.setEnabled(true);
		}
	}

	public void editar() {
		this.filaseleccionada = this.menu.tblAutos.getSelectedRow();
		try {
			if (this.filaseleccionada != -1) {
				this.placa = (String) this.menu.tblAutos.getValueAt(filaseleccionada, 0);
				this.autosModel.setplaca(this.placa);
				this.autosModel.editar();
				this.menu.txtPropietarioAuto.setText("" + this.autosModel.getPropietario());
				this.menu.txtNombrePropietario.setText(this.autosModel.getNombreCompletoPropietario());
				this.menu.txtPlaca.setText(this.autosModel.getplaca());
				this.menu.txtMarca.setText(this.autosModel.getmarca());
				this.menu.txtMotor.setText(this.autosModel.getmotor());
				this.menu.txtColor.setText(this.autosModel.getcolor());
				this.menu.txtChasis.setText(this.autosModel.getchasis());
				this.menu.txtModelo.setText(this.autosModel.getmodelo());
				this.menu.txtVin.setText(this.autosModel.getVin());
				this.menu.btnActualizarAutos.setEnabled(true);
				this.menu.btnGuardarAutos.setEnabled(false);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " ERROR: en el metodo editar en el ctrl Autos");
		}
	}

	public boolean validarDatos() {
		boolean bandera;
		this.marca = this.menu.txtMarca.getText();
		this.modelo = this.menu.txtModelo.getText();
		this.motor = this.menu.txtMotor.getText();
		this.chasis = this.menu.txtChasis.getText();
		this.color = this.menu.txtColor.getText();
		this.placa = this.menu.txtPlaca.getText();
		this.vin = this.menu.txtVin.getText();
		if (this.menu.txtPropietarioAuto.getText().equals("")) {
			bandera = false;
			JOptionPane.showMessageDialog(null, "Complete el campo propietario.");
		} else if (this.marca.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo marca.");
			bandera = false;
		} else if (this.modelo.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo modelo.");
			bandera = false;
		} else if (this.motor.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo motor.");
			bandera = false;
		} else if (this.chasis.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo chasis.");
			bandera = false;
		} else if (this.color.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo color.");
			bandera = false;
		} else if (this.placa.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo placa.");
			bandera = false;
		} else {
			this.propietario = Integer.parseInt(this.menu.txtPropietarioAuto.getText());
			bandera = true;
		}
		return bandera;
	}

	public void limpiar() {
		this.menu.txtPropietarioAuto.setText("");
		this.menu.txtNombrePropietario.setText("");
		this.menu.txtPlaca.setText("");
		this.menu.txtMarca.setText("");
		this.menu.txtModelo.setText("");
		this.menu.txtMotor.setText("");
		this.menu.txtChasis.setText("");
		this.menu.txtColor.setText("");
		this.menu.txtVin.setText("");
		this.menu.btnGuardarAutos.setEnabled(true);
		this.menu.btnActualizarAutos.setEnabled(false);
	}

	public void mostrar(String valor) {
		this.menu.tblAutos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		this.menu.tblAutos.getTableHeader().setOpaque(false);
		this.menu.tblAutos.getTableHeader().setBackground(new Color(69, 76, 89));
		this.menu.tblAutos.getTableHeader().setForeground(new Color(255, 255, 255));
		this.menu.tblAutos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		this.menu.tblAutos.setModel(this.autosModel.mostrarAutos(valor));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.txtPropietarioAuto) {
			this.showWindowPropietarios();
		} else if (e.getSource() == this.menu.tblPropietariosAutos) {
			if (e.getClickCount() == 2) {
				this.addPropietarioAuto();
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

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBucarAutos) {
			this.mostrar(this.menu.txtBucarAutos.getText());
		} else if (e.getSource() == this.menu.txtBuscarPropietario) {
			this.mostrarPropietarios(this.menu.txtBuscarPropietario.getText());
		}
	}

	public void mostrarPropietarios(String valor) {
		menu.tblPropietariosAutos.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblPropietariosAutos.getTableHeader().setOpaque(false);
		menu.tblPropietariosAutos.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblPropietariosAutos.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblPropietariosAutos.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		this.menu.tblPropietariosAutos.setModel(this.autosModel.mostrarPropietarios(valor));
	}

	public void addPropietarioAuto() {
		this.filaseleccionada = this.menu.tblPropietariosAutos.getSelectedRow();
		this.idPropietario = (String) this.menu.tblPropietariosAutos.getValueAt(this.filaseleccionada, 0);
		this.nombrePropietario = (String) this.menu.tblPropietariosAutos.getValueAt(filaseleccionada, 1);
		this.menu.txtPropietarioAuto.setText(this.idPropietario);
		this.menu.txtNombrePropietario.setText(this.nombrePropietario);
		this.menu.jdPropietarios.setVisible(false);
	}

	public void showWindowPropietarios() {
		this.menu.jdPropietarios.setSize(400, 286);
		this.menu.jdPropietarios.setLocationRelativeTo(null);
		this.menu.jdPropietarios.setVisible(true);
	}

}
