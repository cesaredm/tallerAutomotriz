
package controller;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import model.UsuariosModel;
import javax.swing.event.CaretListener;
import view.Menu;

public class UsuariosController implements ActionListener, CaretListener {

    Menu menu;
    UsuariosModel usuarioModel;
    String usuario;
    String  password;
    int id;

    
     public UsuariosController(Menu menu, UsuariosModel usuarios) {
        this.menu = menu;
        this.usuarioModel = usuarios;
        mostrar("");
        this.menu.btnGuardarUsuario.addActionListener(this);
        this.menu.btnGuardarUsuario.setActionCommand("btnGuardarUsuario");
        
        this.menu.editarUsuarios.addActionListener(this);
        this.menu.editarUsuarios.setActionCommand("mnEditarUsuario");
        
        this.menu.eliminarUsuario.addActionListener(this);
        this.menu.eliminarUsuario.setActionCommand("mnEliminarUsuario");
        
        this.menu.txtBuscarUsuarios.addCaretListener(this);
        
        this.menu.btnActualizarUsuario.addActionListener(this);
        this.menu.btnActualizarUsuario.setActionCommand("btnActualizarUsuarios");
        
        this.menu.btnNuevoUsuario.addActionListener(this);
        this.menu.btnNuevoUsuario.setActionCommand("btnlimpiarUsuarios");
       
    }
    
       @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnGuardarUsuario": {
                guardar();
                mostrar("");
            }
            break;
             case "mnEditarUsuario": {
                editar();
            }
            break;
              
            case "btnActualizarUsuarios": {
                actualizar();
                mostrar("");
            }break;
            case "btnlimpiarUsuarios":{
                limpiar();
            }break;
            
             case "mnEliminarUsuario": {
                eliminar();
                mostrar("");
            }
            break;
        }
    }
    
      public void guardar() {
        if (validarDatos()) {
            this.usuarioModel.setUsuario(this.usuario);
            this.usuarioModel.setPassword(this.password);
            
            this.usuarioModel.guardar();
            limpiar();
        } else {

        }
    }
      
       public void limpiar() {
        this.menu.txtNombresUsuarios.setText("");
        this.menu.txtPasswordUsuarios.setText("");
        this.menu.btnGuardarUsuario.setEnabled(true);
        this.menu.btnActualizarUsuario.setEnabled(false);

    }

    public boolean validarDatos() {
        boolean bandera;
        this.password = this.menu.txtPasswordUsuarios.getText();
        this.usuario = this.menu.txtNombresUsuarios.getText();
        
        if (this.usuarioModel.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete el campo nombres.");
            bandera = false;
        } else if (this.password.equals("")) {
            JOptionPane.showMessageDialog(null, "Complete el campo password.");
            bandera = false;
        }  else {
            bandera = true;
        }
        return bandera;
    }
    
      public void mostrar(String valor) {
        menu.tblUsuarios.getTableHeader().setFont(new Font("Sugoe UI", Font.PLAIN, 14));
        menu.tblUsuarios.getTableHeader().setOpaque(false);
        menu.tblUsuarios.getTableHeader().setBackground(new Color(69, 76, 89));
        menu.tblUsuarios.getTableHeader().setForeground(new Color(255, 255, 255));
        menu.tblUsuarios.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));
        this.menu.tblUsuarios.setModel(this.usuarioModel.mostrar(valor));
                
    }
      
        public void editar() {
        int filaseleccionada = this.menu.tblUsuarios.getSelectedRow();
        try {
            if (filaseleccionada != -1) {
                this.id = Integer.parseInt(this.menu.tblUsuarios.getValueAt(filaseleccionada, 0).toString());
                this.usuarioModel.setId(this.id);
                this.usuarioModel.editar();
                this.menu.txtNombresUsuarios.setText(this.usuarioModel.getUsuario());
                this.menu.txtPasswordUsuarios.setText(this.usuarioModel.getPassword());
                
                
                this.menu.btnGuardarUsuario.setEnabled(false);
                this.menu.btnActualizarUsuario.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " En el metodo editar en el controller Usuarios");
        }
    }
        
            public void actualizar() {
            if (validarDatos()) {
            this.usuarioModel.setUsuario(this.usuario);
            this.usuarioModel.setPassword(this.password);
            

            this.usuarioModel.actualizar();
            limpiar();
            this.menu.btnGuardarUsuario.setEnabled(true);
            this.menu.btnActualizarUsuario.setEnabled(false);
        }
    }
            
                public void eliminar() {
        int filaseleccionada = this.menu.tblUsuarios.getSelectedRow();
        try {
            if (filaseleccionada != -1) {
                int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quieres eliminar este usuario.?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    this.id = Integer.parseInt(this.menu.tblUsuarios.getValueAt(filaseleccionada, 0).toString());
                    this.usuarioModel.setId(this.id);
                    this.usuarioModel.eliminar();
                }else{
                    
                }

            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error en la función elimiar UsaurioContoller");
        }
    }
                @Override
    public void caretUpdate(CaretEvent e) {
        if(e.getSource() == this.menu.txtBuscarUsuarios){
            String valor = this.menu.txtBuscarUsuarios.getText();
            mostrar(valor);
        }
    }

                
                
                
                
}
