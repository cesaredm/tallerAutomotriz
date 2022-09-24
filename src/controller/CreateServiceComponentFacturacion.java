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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CmbServicios;
import model.ComponenteModel;
import model.ServiciosModel;
import view.Menu;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class CreateServiceComponentFacturacion implements ActionListener, KeyListener {

	Menu menu;
	ServiciosModel serviciosModel;
	ComponenteModel componentesModel;
	CmbServicios cmbServicios;
	DefaultTableModel tableModel;

	String nameService;
	String nameComponent;
	int idService, filas;

	public CreateServiceComponentFacturacion(Menu menu, ServiciosModel serviciosModel, ComponenteModel componentesModel) {
		this.menu = menu;
		this.componentesModel = componentesModel;
		this.serviciosModel = serviciosModel;
		this.menu.btnCrearServicio.setEnabled(false);
		this.menu.txtNombreServicioFacturacion.setEnabled(false);
		this.menu.btnCrearMas.addActionListener(this);
		this.menu.btnCrearMas.setActionCommand("btnCrearMas");
		this.menu.btnAgregarComponenteFacturacion.addActionListener(this);
		this.menu.btnAgregarComponenteFacturacion.setActionCommand("btnAgregarComponenteFacturacion");
		this.menu.btnCrearServicioFacturacion.addActionListener(this);
		this.menu.btnCrearServicioFacturacion.setActionCommand("btnCrearServicioFacturacion");
		this.menu.btnCrearServicio.addActionListener(this);
		this.menu.btnCrearServicio.setActionCommand("btnCrearServicio");
		this.menu.txtNombreComponenteFacturacion.addKeyListener(this);
		this.menu.txtNombreServicioFacturacion.addKeyListener(this);
	}

	public void mostrarVentanaCrearMas() {
		this.menu.jdCrearMas.setSize(703, 172);
		this.menu.jdCrearMas.setVisible(true);
	}

	public void agregarServicio() {
		if (this.validarServicio()) {
			this.serviciosModel.setNombre(this.nameService);
			this.serviciosModel.guardar();
			this.menu.btnCrearServicio.setEnabled(false);
			this.menu.txtNombreServicioFacturacion.setEnabled(false);
			DefaultComboBoxModel cmbModel = this.serviciosModel.showServiciosCmb();
			this.menu.cmbServiciosFacturacion.setModel(cmbModel);
			this.menu.cmbElegirServicio.setModel(cmbModel);
		}
	}

	public void agregarComponente() {
		if (this.validarComponente()) {
			this.filas = this.menu.tblComponentesFacturacion.getRowCount();
			this.componentesModel.setNombre(nameComponent);
			this.componentesModel.setServicio(idService);
			this.componentesModel.guardar();
			this.menu.txtNombreComponenteFacturacion.setText("");
			int confirmar = JOptionPane.showConfirmDialog(
				null,
				"Quieres agregar este componente a la tabla de factura",
				"Informaciòn",
				JOptionPane.YES_NO_OPTION
			);
			if (confirmar == JOptionPane.YES_OPTION) {
				if (this.filas < 12) {
					this.tableModel = (DefaultTableModel) this.menu.tblComponentesFacturacion.getModel();
					this.tableModel.addRow(this.componentesModel.getLastComponent());
				}else{
					JOptionPane.showMessageDialog(null,"Factura llego a su limite de items.");
				}
			}
		}
	}

	public boolean validarServicio() {
		boolean validar = true;
		this.nameService = this.menu.txtNombreServicioFacturacion.getText();
		if (this.nameService.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	public boolean validarComponente() {
		this.cmbServicios = (CmbServicios) this.menu.cmbElegirServicio.getSelectedItem();
		boolean validar = true;
		this.nameComponent = this.menu.txtNombreComponenteFacturacion.getText();
		this.idService = this.cmbServicios.getId();
		if (this.nameComponent.equals("")) {
			validar = false;
		} else {
			validar = true;
		}
		return validar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnCrearMas": {
				this.mostrarVentanaCrearMas();
			}
			break;
			case "btnAgregarComponenteFacturacion": {
				this.agregarComponente();
			}
			break;
			case "btnCrearServicioFacturacion": {
				this.menu.txtNombreServicioFacturacion.setEnabled(true);
				this.menu.btnCrearServicio.setEnabled(true);
			}
			break;
			case "btnCrearServicio": {
				this.agregarServicio();
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.menu.txtNombreComponenteFacturacion && e.getKeyCode() == e.VK_ENTER) {
			this.agregarComponente();
		} else if (e.getSource() == this.menu.txtNombreServicioFacturacion && e.getKeyCode() == e.VK_ENTER) {
			this.agregarServicio();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
