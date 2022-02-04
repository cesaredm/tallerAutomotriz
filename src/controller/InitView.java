/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import view.Menu;
import model.*;

/**
 *
 * @author 000
 */
public class InitView implements MouseListener {

	Menu menu;
	PropietariosModel propietariosModel;
	PropietariosController propietariosController;

	UsuariosModel usuariosModel;
	UsuariosController usuariosController;

	ServiciosModel serviciosModel;
	ServiciosController serviciosController;

	ComponenteModel componenteModel;
	ComponenteController componenteController;

	AutosModel autosModel;
	AutosController autosController;

	FacturacionModel facturacionModel;
	FacturacionController facturacionController;

	IngresoAutosModel ingresoAutosModel;
	IngresoAutosController ingresoAutosController;

	ReportesModel reportesModel;
	ReportesController reportesController;

	TransaccionModel transaccionModel;
	TransaccionController transaccionController;

	public InitView(Menu menu,String usuario) {
		this.menu = menu;
		this.menu.lblUsuarioSistema.setText(usuario);
		this.propietariosModel = new PropietariosModel();
		this.propietariosController = new PropietariosController(menu, propietariosModel);

		this.usuariosModel = new UsuariosModel();
		this.usuariosController = new UsuariosController(menu, usuariosModel);

		this.serviciosModel = new ServiciosModel();
		this.serviciosController = new ServiciosController(menu, serviciosModel);

		this.componenteModel = new ComponenteModel();
		this.componenteController = new ComponenteController(menu, componenteModel);

		this.autosModel = new AutosModel();
		this.autosController = new AutosController(menu, autosModel);

		this.facturacionModel = new FacturacionModel();
		this.facturacionController = new FacturacionController(menu, facturacionModel);

		this.ingresoAutosModel = new IngresoAutosModel();
		this.ingresoAutosController = new IngresoAutosController(this.ingresoAutosModel, this.menu);

		this.reportesModel = new ReportesModel();
		this.reportesController = new ReportesController(menu, reportesModel);

		this.transaccionModel = new TransaccionModel();
		this.transaccionController = new TransaccionController(menu, transaccionModel);

		this.menu.pnlOpcionFacturacion.addMouseListener(this);
		this.menu.pnlOpcionReportes.addMouseListener(this);
		this.menu.pnlOpcionClientes.addMouseListener(this);
		this.menu.pnlOpcionUsuarios.addMouseListener(this);
		this.menu.pnlOpcionServicios.addMouseListener(this);
		this.menu.pnlOpcionTransaccion.addMouseListener(this);
	}

	public void Start() {
		this.menu.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == this.menu.pnlOpcionFacturacion) {

			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(56, 56, 56));

			this.menu.pnlFacturacion.setVisible(true);
			this.menu.pnlClientes.setVisible(false);
			this.menu.pnlServiciosComp.setVisible(false);
			this.menu.pnlReportes.setVisible(false);
			this.menu.pnlUsuarios.setVisible(false);
			this.menu.pnlTransacciones.setVisible(false);
		} else if (e.getSource() == this.menu.pnlOpcionReportes) {

			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(56, 56, 56));

			this.menu.pnlReportes.setVisible(true);
			this.menu.pnlFacturacion.setVisible(false);
			this.menu.pnlClientes.setVisible(false);
			this.menu.pnlServiciosComp.setVisible(false);
			this.menu.pnlUsuarios.setVisible(false);
			this.menu.pnlTransacciones.setVisible(false);
		} else if (e.getSource() == this.menu.pnlOpcionServicios) {

			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(56, 56, 56));

			this.menu.pnlServiciosComp.setVisible(true);
			this.menu.pnlReportes.setVisible(false);
			this.menu.pnlFacturacion.setVisible(false);
			this.menu.pnlClientes.setVisible(false);
			this.menu.pnlUsuarios.setVisible(false);
			this.menu.pnlTransacciones.setVisible(false);
		} else if (e.getSource() == this.menu.pnlOpcionClientes) {

			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(56, 56, 56));

			this.menu.pnlClientes.setVisible(true);
			this.menu.pnlFacturacion.setVisible(false);
			this.menu.pnlServiciosComp.setVisible(false);
			this.menu.pnlReportes.setVisible(false);
			this.menu.pnlUsuarios.setVisible(false);
			this.menu.pnlTransacciones.setVisible(false);
		} else if (e.getSource() == this.menu.pnlOpcionUsuarios) {

			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(56, 56, 56));

			this.menu.pnlUsuarios.setVisible(true);
			this.menu.pnlClientes.setVisible(false);
			this.menu.pnlFacturacion.setVisible(false);
			this.menu.pnlServiciosComp.setVisible(false);
			this.menu.pnlReportes.setVisible(false);
			this.menu.pnlTransacciones.setVisible(false);

		} else if (e.getSource() == this.menu.pnlOpcionTransaccion) {
			this.menu.pnlOpcionTransaccion.setBackground(new java.awt.Color(72, 72, 72));
			this.menu.pnlOpcionClientes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionServicios.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionReportes.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionFacturacion.setBackground(new java.awt.Color(56, 56, 56));
			this.menu.pnlOpcionUsuarios.setBackground(new java.awt.Color(56,56,56));

			this.menu.pnlTransacciones.setVisible(true);
			this.menu.pnlUsuarios.setVisible(false);
			this.menu.pnlClientes.setVisible(false);
			this.menu.pnlFacturacion.setVisible(false);
			this.menu.pnlServiciosComp.setVisible(false);
			this.menu.pnlReportes.setVisible(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
