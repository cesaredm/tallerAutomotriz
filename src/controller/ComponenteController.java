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
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import model.ComponenteModel;
import view.Menu;

/**
 *
 * @author 000
 */
public class ComponenteController implements ActionListener, MouseListener, KeyListener, CaretListener {

	String nombre;
	int id, servicio;

	ComponenteModel componenteModel;
	Menu menu;

	public ComponenteController(Menu menu, ComponenteModel componenteModel) {
		this.componenteModel = componenteModel;
		this.menu = menu;
		this.showServicios("");
		this.mostrar("");
		this.menu.btnGuardarComponete.addActionListener(this);
		this.menu.btnGuardarComponete.setActionCommand("btnGuardarComponente");

		this.menu.EditarComponente.addActionListener(this);
		this.menu.EditarComponente.setActionCommand("mnEditarComponente");

		this.menu.EliminarComponente.addActionListener(this);
		this.menu.EliminarComponente.setActionCommand("mnEliminarComponente");

		this.menu.txtServicioComponente.addMouseListener(this);
		this.menu.tblAddServicioComponete.addMouseListener(this);
		this.menu.txtNombreComponente.addKeyListener(this);
		this.menu.tblAddServicioComponete.addKeyListener(this);
		this.menu.txtNombreServicioCOmponente.addMouseListener(this);

		this.menu.tblComponentes.addMouseListener(this);

		this.menu.txtBuscarComponenetes.addCaretListener(this);
		this.menu.txtBuscarServicioComponete.addCaretListener(this);

		this.menu.btnActualizarComponete.addActionListener(this);
		this.menu.btnActualizarComponete.setActionCommand("btnActualizarComponete");

	}

	public void guardar() {
		if (validarDatos()) {
			this.componenteModel.setNombre(this.nombre);
			this.componenteModel.setServicio(this.servicio);
			this.componenteModel.guardar();
			this.mostrar("");
			this.limpiar();
		} else {

		}
	}

	public void editar() {
		int filaseleccionada = this.menu.tblComponentes.getSelectedRow();
		try {
			if (filaseleccionada != -1) {
				this.id = Integer.parseInt(this.menu.tblComponentes.getValueAt(filaseleccionada, 0).toString());
				this.componenteModel.setId(id);
				this.componenteModel.editar();
				this.menu.txtNombreComponente.setText(this.componenteModel.getNombre());

				this.menu.btnGuardarComponete.setEnabled(false);
				this.menu.btnActualizarComponete.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "Elija una fila.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "ERROR : en el metodo editar en el ctrl componente");
		}
	}

	public void showServicios(String valor) {
		this.menu.tblAddServicioComponete.setModel(this.componenteModel.showServicios(valor));
	}

	public boolean validarDatos() {
		boolean bandera;
		this.servicio = Integer.parseInt(this.menu.txtServicioComponente.getText());
		this.nombre = this.menu.txtNombreComponente.getText();
		if (this.nombre.equals("")) {
			JOptionPane.showMessageDialog(null, "Complete el campo Nombre.");
			bandera = false;
		} else {
			bandera = true;
		}
		return bandera;
	}

	public void mostrar(String valor) {
		menu.tblComponentes.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblComponentes.getTableHeader().setOpaque(false);
		menu.tblComponentes.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblComponentes.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblComponentes.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		this.menu.tblComponentes.setModel(this.componenteModel.mostrar(valor));
	}

	public void agregarServicio() {
		int filaseleccionada = this.menu.tblAddServicioComponete.getSelectedRow();
		if (filaseleccionada != -1) {
			String idServicio = (String) this.menu.tblAddServicioComponete.getValueAt(filaseleccionada, 0);
			String nombreServicio = (String) this.menu.tblAddServicioComponete.getValueAt(filaseleccionada, 1);
			this.menu.txtServicioComponente.setText(idServicio);
			this.menu.txtNombreServicioCOmponente.setText(nombreServicio);
			this.menu.jdAddServicioComponente.setVisible(false);
		}
	}

	public void showWindowsAddServicio() {
		this.menu.jdAddServicioComponente.setSize(508, 279);
		this.menu.jdAddServicioComponente.setLocationRelativeTo(null);
		this.menu.jdAddServicioComponente.setVisible(true);
		this.menu.txtBuscarServicioComponete.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnGuardarComponente": {
				this.guardar();
			}
			break;
			case "mnEditarComponente": {
				this.editar();
				mostrar("");
			}
			break;
			case "btnActualizarComponete": {
				this.actualizar();
				mostrar("");
			}
			break;
			case "mnEliminarComponente": {
				this.eliminar();
				this.mostrar("");
			}
			break;
		}
	}

	public void eliminar() {
		int filaSeleccionada = this.menu.tblComponentes.getSelectedRow();
		if (filaSeleccionada != -1) {
			int confirm = JOptionPane.showConfirmDialog(
				null,
				"Seguro quieres eliminar este componente.?",
				"Advertencia.",
				JOptionPane.OK_CANCEL_OPTION
			);
			if (confirm == JOptionPane.YES_OPTION) {
				this.id = Integer.parseInt(this.menu.tblComponentes.getValueAt(filaSeleccionada, 0).toString());
				this.componenteModel.setId(this.id);
				this.componenteModel.eliminar();
				this.mostrar("");
			}
		}
	}

	public void actualizar() {
		if (validarDatos()) {
			this.componenteModel.setNombre(this.nombre);
			this.componenteModel.setServicio(this.servicio);
			this.componenteModel.actualizar();
			limpiar();
			this.menu.btnGuardarComponete.setEnabled(false);
			this.menu.btnActualizarComponete.setEnabled(true);
		}
	}

	public void limpiar() {
		this.menu.txtNombreComponente.setText("");
		this.menu.btnGuardarComponete.setEnabled(true);
		this.menu.btnActualizarComponete.setEnabled(false);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.txtServicioComponente) {
			this.showWindowsAddServicio();
		} else if (e.getSource() == this.menu.tblAddServicioComponete) {
			this.agregarServicio();
		} else if (e.getSource() == this.menu.txtNombreServicioCOmponente) {
			this.showWindowsAddServicio();
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
		if (e.getSource() == this.menu.txtNombreComponente && e.getKeyCode() == e.VK_DOWN) {
			this.showWindowsAddServicio();
		} else if (e.getSource() == this.menu.txtNombreComponente && e.getKeyCode() == e.VK_ENTER && this.menu.btnGuardarComponete.isEnabled()) {
			this.guardar();
		} else if (e.getSource() == this.menu.tblAddServicioComponete && e.getKeyCode() == e.VK_ENTER) {
			this.agregarServicio();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBuscarServicioComponete) {
			this.showServicios(this.menu.txtBuscarServicioComponete.getText());
		} else if (e.getSource() == this.menu.txtBuscarComponenetes) {
			this.mostrar(this.menu.txtBuscarComponenetes.getText());
		}
	}
}
