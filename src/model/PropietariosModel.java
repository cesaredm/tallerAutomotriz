package model;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class PropietariosModel extends Conexion {

    private int id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String dni;

    //herramientas para conectarno a db
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void guardar() {
        this.consulta = "INSERT INTO propietario(nombres,apellidos,telefono,direccion,dni) VALUES(?,?,?,?,?)";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, this.nombre);
            this.pst.setString(2, this.apellidos);
            this.pst.setString(3, this.telefono);
            this.pst.setString(4, this.direccion);
            this.pst.setString(5, this.dni);
            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Propietario guardado con exito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental guardar el propietario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de propietarios en el metodo guardar");
        }
    }

    public DefaultTableModel mostrar(String valor) {
        this.consulta = "SELECT * FROM propietario WHERE CONCAT(nombres, apellidos, dni) LIKE '%"+valor+"%' ORDER BY nombres ASC";
        String[] titulos = {"ID", "Nombres", "Apeliidos", "Telefono", "DNI", "Direccion"};
        String[] datos = new String[6];
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
                datos[1] = rs.getString("nombres");
                datos[2] = rs.getString("apellidos");
                datos[3] = rs.getString("telefono");
                datos[4] = rs.getString("dni");
                datos[5] = rs.getString("direccion");
                this.modelo.addRow(datos);
            }
            this.cn.close();
        } catch (Exception e) {

        }
        return this.modelo;
    }

    public void eliminar() {
        this.consulta = "DELETE FROM propietario WHERE id = ?";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setInt(1, this.id);
            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Propietario eliminado con exito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental eliminar el propietario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de propietarios en el metodo eliminar");
        }
    }

    public void editar() {
        this.consulta = "SELECT * FROM propietario WHERE id = ?";

        this.cn = conexion();
        try {
            this.pst = this.cn.prepareStatement(consulta);
            this.pst.setInt(1, id);
            this.rs = this.pst.executeQuery();
            while (rs.next()) {
                this.nombre = this.rs.getString("nombres");
                this.apellidos = this.rs.getString("apellidos");
                this.telefono = this.rs.getString("telefono");
                this.direccion = this.rs.getString("direccion");
                this.dni = this.rs.getString("dni");
            }
            this.cn.close();
        } catch (Exception e) {

        }
    }

    public void actualizar() {
        this.consulta = "UPDATE propietario SET nombres = ?, apellidos = ?, telefono = ?, direccion = ?, dni = ? WHERE id = ?";
        this.cn = conexion();

        try {
            this.pst = this.cn.prepareStatement(this.consulta);
            this.pst.setString(1, this.nombre);
            this.pst.setString(2, this.apellidos);
            this.pst.setString(3, this.telefono);
            this.pst.setString(4, this.direccion);
            this.pst.setString(5, this.dni);
            this.pst.setInt(6, id);
            this.banderin = this.pst.executeUpdate();
            if (this.banderin > 0) {
                JOptionPane.showMessageDialog(null, "Propietario actualizado con exito..");
            } else {
                JOptionPane.showMessageDialog(null, "Ups.. Ocurrio un erro al intental actualizar el propietario..");
            }
            this.cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " En el modelo de propietarios en el metodo guardar");
        }
    }
}
