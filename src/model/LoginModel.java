
package model;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author 000
 */
public class LoginModel extends Conexion {

    private String usuario;
    private String password;
    Connection cn;
    PreparedStatement pst;
    String consulta;

    public LoginModel() {

    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean Validacion() {
        this.consulta = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        int cont = 0;
        String nombre = null;
        this.cn = conexion();
        ResultSet rs;
        boolean validacion = true;
        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, usuario);
            this.pst.setString(2, password);
            rs = this.pst.executeQuery();
            while (rs.next()) {
                cont++;
                nombre = rs.getString("usuario");
            }
            if (cont > 0 && nombre.equals(usuario)) {
                validacion = true;
            } else {
                validacion = false;
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return validacion;
    }
}
