package model;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuariosModel extends Conexion {

    private int id;
    private String usuario;
    private String password;

    PreparedStatement pst;
    Connection cn;
    String consulta;
    ResultSet rs;
    int banderin;

    DefaultTableModel modelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void guardar() {
        this.consulta = "INSERT INTO usuarios(usuario,password) VALUES(?,?)";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, this.usuario);
            this.pst.setString(2, this.password);

            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Usuario guardado con éxito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental guardar el usuario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de usuario, del metodo guardar");
        }
    }
    
       public DefaultTableModel mostrar(String valor) {
        this.consulta = "SELECT * FROM usuarios WHERE CONCAT(usuario) LIKE '%"+valor+"%'";
        String[] titulos = {"ID", "Usuario", "Password"};
        String[] datos = new String[3];
        this.modelo = new DefaultTableModel(null, titulos) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        this.cn = conexion();
        try {
            this.pst = this.cn.prepareStatement(consulta);
            this.rs = this.pst.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("id");
                datos[1] = rs.getString("usuario");
                datos[2] = rs.getString("password");
                
                this.modelo.addRow(datos);
            }
            this.cn.close();
        } catch (Exception e) {

        }
        return this.modelo;
    }

    public void actualizar() {
        this.consulta = "UPDATE usuarios SET usuario = ?, password = ? WHERE id = ?";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, this.usuario);
            this.pst.setString(2, this.password);
            this.pst.setInt(3, id);
            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Propietario actualizado con éxito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental actualizar el usuario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de UsarioModel en el metodo guardar");
        }
    }

        public void editar() {
        this.consulta = "SELECT * FROM usuarios WHERE id = ?";

        this.cn = conexion();
        try {
            this.pst = this.cn.prepareStatement(consulta);
            this.pst.setInt(1, id);
            this.rs = this.pst.executeQuery();
            while (rs.next()) {
                this.usuario = this.rs.getString("usuario");
                this.password = this.rs.getString("password");
            }
            this.cn.close();
        } catch (Exception e) {

        }
    }
    

    public void eliminar() {
        this.consulta = "DELETE FROM usuarios WHERE id = ?";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setInt(1, this.id);
            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Usuari eliminado con éxito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intentar eliminar el usuario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de UsuariosModel en el metodo eliminar");
        }

   

}
}
