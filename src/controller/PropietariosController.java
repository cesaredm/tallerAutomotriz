package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import model.PropietariosModel;
import view.Menu;

/**
 *
 * @author 000
 */
public class PropietariosController implements ActionListener, CaretListener {

    Menu menu;
    PropietariosModel propietarios;
    String nombres, apellidos, telefono, direccion, dni;
    int id;

    public PropietariosController(Menu menu, PropietariosModel propietarios) {
        this.menu = menu;
        this.propietarios = propietarios;
        mostrar("");
       
        this.menu.btnGuardarClientes.addActionListener(this);
        this.menu.btnGuardarClientes.setActionCommand("btnGuardarClientes");
        
        this.menu.btnActualizarClientes.setEnabled(false);
        this.menu.btnActualizarClientes.addActionListener(this);
        this.menu.btnActualizarClientes.setActionCommand("btnActualizarClientes");
        
        this.menu.editarCliente.addActionListener(this);
        this.menu.editarCliente.setActionCommand("mnEditarCliente");
        
        this.menu.eliminarCliente.addActionListener(this);
        this.menu.eliminarCliente.setActionCommand("mnEliminarCliente");

	this.menu.btnLimpiarClientes.addActionListener(this);
	this.menu.btnLimpiarClientes.setActionCommand("btnlimpiarclientes");
        
        this.menu.txtBuscarClientes.addCaretListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnGuardarClientes": {
                guardar();
                mostrar("");
            }
            break;
            case "mnEditarCliente": {
                editar();
            }
            break;
            case "btnActualizarClientes": {
                actualizar();
                mostrar("");
            }
            break;
            case "mnEliminarCliente": {
                eliminar();
                mostrar("");
            }
            break;
	    case "btnlimpiarclientes":{
		    this.limpiar();
	    }break;
        }
    }

    public void guardar() {
        if (validarDatos()) {
            this.propietarios.setNombre(this.nombres);
            this.propietarios.setApellidos(this.apellidos);
            this.propietarios.setTelefono(this.telefono);
            this.propietarios.setDni(this.dni);
            this.propietarios.setDireccion(this.direccion);

            this.propietarios.guardar();
            limpiar();
        } else {

        }
    }

    public void editar() {
        int filaseleccionada = this.menu.tblClientes.getSelectedRow();
        try {
            if (filaseleccionada != -1) {
                this.id = Integer.parseInt(this.menu.tblClientes.getValueAt(filaseleccionada, 0).toString());
                this.propietarios.setId(this.id);
                this.propietarios.editar();
                this.menu.txtNombresClientes.setText(this.propietarios.getNombre());
                this.menu.txtApellidosClientes.setText(this.propietarios.getApellidos());
                this.menu.txtTelefonoClientes.setText(this.propietarios.getTelefono());
                this.menu.txtDireccionClientes.setText(this.propietarios.getDireccion());
                this.menu.txtDniClientes.setText(this.propietarios.getDni());
                this.menu.btnGuardarClientes.setEnabled(false);
                this.menu.btnActualizarClientes.setEnabled(true);
            }
        } catch (Exception e) {
        }
    }

    public void actualizar() {
        if (validarDatos()) {
            this.propietarios.setNombre(this.nombres);
            this.propietarios.setApellidos(this.apellidos);
            this.propietarios.setTelefono(this.telefono);
            this.propietarios.setDireccion(this.direccion);
            this.propietarios.setDni(this.dni);

            this.propietarios.actualizar();
            limpiar();
            this.menu.btnGuardarClientes.setEnabled(true);

            this.menu.btnActualizarClientes.setEnabled(false);
        }
    }

    public void eliminar() {
        int filaseleccionada = this.menu.tblClientes.getSelectedRow();
        try {
            if (filaseleccionada != -1) {
                int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quieres eliminar este cliente.?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    this.id = Integer.parseInt(this.menu.tblClientes.getValueAt(filaseleccionada, 0).toString());
                    this.propietarios.setId(this.id);
                    this.propietarios.eliminar();
                }else{
                    
                }

            }
        } catch (Exception e) {
            
        }
    }

// aqui no  retorna nada 
    public void mostrar(String valor) {
        menu.tblClientes.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblClientes.getTableHeader().setOpaque(false);
        menu.tblClientes.getTableHeader().setBackground(new Color(69, 76, 89));
        menu.tblClientes.getTableHeader().setForeground(new Color(255, 255, 255));
        menu.tblClientes.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
        this.menu.tblClientes.setModel(this.propietarios.mostrar(valor));
    }

    public void limpiar() {
        this.menu.txtNombresClientes.setText("");
        this.menu.txtApellidosClientes.setText("");
        this.menu.txtTelefonoClientes.setText("");
        this.menu.txtDniClientes.setText("");
        this.menu.txtDireccionClientes.setText("");
    }

    public boolean validarDatos() {
        boolean bandera;
        this.nombres = this.menu.txtNombresClientes.getText();
        this.apellidos = this.menu.txtApellidosClientes.getText();
        this.telefono = this.menu.txtTelefonoClientes.getText();
        this.direccion = this.menu.txtDireccionClientes.getText();
        this.dni = this.menu.txtDniClientes.getText();
        if (this.nombres.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete el campo nombres.");
            bandera = false;
        } else if (this.apellidos.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete el campo apellidos.");
            bandera = false;
        } else if (this.dni.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete el campo DNI.");
            bandera = false;
        } else {
            bandera = true;
        }
        return bandera;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        if(e.getSource() == this.menu.txtBuscarClientes){
            String valor = this.menu.txtBuscarClientes.getText();
            mostrar(valor);
        }
    }

}
