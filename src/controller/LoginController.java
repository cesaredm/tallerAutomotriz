package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.LoginModel;
import view.Login;
import view.Menu;

/**
 *
 * @author 000
 */
public class LoginController implements ActionListener {

	Login login;
	Menu menu;
	LoginModel model;
	InitView menuOpciones;
	String usuario;

	public LoginController(Login login, LoginModel model) {
		this.login = login;
		this.model = model;
		this.menu = new Menu();
		this.login.btnAccederLogin.addActionListener(this);
	}

	public void start() {
		this.login.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login.btnAccederLogin) {
			this.model.setUsuario(this.login.txtUsuarioLogin.getText());
			this.model.setPassword(this.login.txtPasswordLogin.getText());
			this.usuario = this.login.txtUsuarioLogin.getText();
			if (this.model.Validacion()) {
				this.login.dispose();
				this.menuOpciones = new InitView(menu,this.usuario);
				this.menuOpciones.Start();
			} else {
				JOptionPane.showMessageDialog(null, "Usuario no existe...");
			}
		}
	}

}
