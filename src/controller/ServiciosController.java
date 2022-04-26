package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.ServiciosModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import model.ComponenteModel;
import view.Menu;

public class ServiciosController implements ActionListener, CaretListener, KeyListener, MouseListener {

	Menu menu;
	ServiciosModel ServiciosModel;
	ComponenteModel componenteModel;
	String nombre;
	int id;

	public ServiciosController(Menu menu, ServiciosModel serviciosModel) {
		this.menu = menu;
		this.ServiciosModel = serviciosModel;
		this.componenteModel = new ComponenteModel();
		mostrar("");
		this.showServiciosCmb();

		this.menu.btnGuardarServicios.addActionListener(this);
		this.menu.btnGuardarServicios.setActionCommand("btnGuardarServicios");

		this.menu.EditarServicios.addActionListener(this);
		this.menu.EditarServicios.setActionCommand("mnEditarServicios");

		this.menu.btnActualizarServicios.addActionListener(this);
		this.menu.btnActualizarServicios.setActionCommand("btnActualizarServicios");

		this.menu.txtNombresServicios.addKeyListener(this);

		this.menu.tblServicios.addMouseListener(this);
		this.menu.txtBuscarServicios.addCaretListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "btnGuardarServicios": {
				guardar();
				mostrar("");
				showServiciosToComponentes("");
				this.showServiciosCmb();
			}
			break;
			case "mnEditarServicios": {
				editar();
			}
			break;
			case "btnActualizarServicios": {
				actualizar();
				mostrar("");
				showServiciosToComponentes("");
				this.showServiciosCmb();
			}
			break;

		}
	}

	public void guardar() {
		if (validarDatos()) {
			this.ServiciosModel.setNombre(this.nombre);
			this.ServiciosModel.guardar();
			limpiar();
		} else {

		}
	}

	public void limpiar() {
		this.menu.txtNombresServicios.setText("");

	}

	public boolean validarDatos() {
		boolean bandera;
		this.nombre = this.menu.txtNombresServicios.getText();

		if (this.nombre.equals("")) {
			//JOptionPane.showMessageDialog(null, "Complete el campo Nombre.");
			bandera = false;
		} else {
			bandera = true;
		}
		return bandera;
	}

	public void mostrar(String valor) {
		menu.tblServicios.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
		menu.tblServicios.getTableHeader().setOpaque(false);
		menu.tblServicios.getTableHeader().setBackground(new Color(69, 76, 89));
		menu.tblServicios.getTableHeader().setForeground(new Color(255, 255, 255));
		menu.tblServicios.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
		this.menu.tblServicios.setModel(this.ServiciosModel.mostrar(valor));

	}

	public void editar() {
		int filaseleccionada = this.menu.tblServicios.getSelectedRow();
		try {
			if (filaseleccionada != -1) {
				this.id = Integer.parseInt(this.menu.tblServicios.getValueAt(filaseleccionada, 0).toString());
				this.ServiciosModel.setId(this.id);
				this.ServiciosModel.editar();
				this.menu.txtNombresServicios.setText(this.ServiciosModel.getNombre());

				this.menu.btnGuardarServicios.setEnabled(false);
				this.menu.btnActualizarServicios.setEnabled(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " En el metodo editar en el controller Usuarios");
		}
	}

	public void actualizar() {
		if (validarDatos()) {
			this.ServiciosModel.setNombre(this.nombre);

			this.ServiciosModel.actualizar();
			limpiar();
			this.menu.btnGuardarServicios.setEnabled(true);

			this.menu.btnActualizarServicios.setEnabled(false);
		}
	}

	public void showComponentService() {
		try {
			int fila = this.menu.tblServicios.getSelectedRow();
			this.id = Integer.parseInt(this.menu.tblServicios.getValueAt(fila, 0).toString());
			this.ServiciosModel.setId(id);
			menu.tblComponenteServicio.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
			menu.tblComponenteServicio.getTableHeader().setOpaque(false);
			menu.tblComponenteServicio.getTableHeader().setBackground(new Color(69, 76, 89));
			menu.tblComponenteServicio.getTableHeader().setForeground(new Color(255, 255, 255));
			menu.tblComponenteServicio.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
			this.menu.tblComponenteServicio.setModel(this.ServiciosModel.componenteServicio());
			this.menu.jdComponentesServicio.setSize(537, 245);
			this.menu.jdComponentesServicio.setLocationRelativeTo(null);
			this.menu.jdComponentesServicio.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " ERROR: en el metodo showComponentService en el ctrl servicios.");
		}

	}

	public void showServiciosCmb(){
		//DefaultComboBoxModel cmbModel = this.ServiciosModel.showServiciosCmb();
		this.menu.cmbServiciosFacturacion.setModel(this.ServiciosModel.showServiciosCmb());
		this.menu.cmbElegirServicio.setModel(this.ServiciosModel.showServiciosCmb());
	}

	public void caretUpdate(CaretEvent e) {
		if (e.getSource() == this.menu.txtBuscarServicios) {
			mostrar(this.menu.txtBuscarServicios.getText());
		}
	}

	public void showServiciosToComponentes(String valor) {
		this.menu.tblAddServicioComponete.setModel(this.componenteModel.showServicios(valor));
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getSource() == menu.txtNombresServicios && e.getKeyCode() == e.VK_ENTER) {
			guardar();
			mostrar("");
			showServiciosToComponentes("");
			this.menu.cmbServiciosFacturacion.setModel(this.ServiciosModel.showServiciosCmb());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.menu.tblServicios) {
			if (e.getClickCount() == 2) {
				this.showComponentService();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

};
