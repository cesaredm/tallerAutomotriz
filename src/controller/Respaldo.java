/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Respaldo {
	public static void backUp() {
		try {
			int processCompleto;
			Process p = null;
			/* SI EL EL ARCHIVO NO ESTA EN program Files(x86) ENTONCES BUSCARA EN Progrma Files */
			try {
				p = Runtime.getRuntime().exec("C:/Program Files (x86)/MariaDB 10.5/bin/mysqldump.exe -u root -p19199697tsoCD"
					+ " --single-transaction --skip-lock-tables -R taller");
			} catch (Exception e) {
				p = Runtime.getRuntime().exec("C:/Program Files/MariaDB 10.5/bin/mysqldump.exe -u root -p19199697tsoCD"
					+ " --single-transaction --skip-lock-tables -R taller");
			}
			InputStream is = p.getInputStream();
			FileOutputStream fos = new FileOutputStream("C:\\RESPALDOTALLER\\BackUp_taller.sql");

			byte[] buffer = new byte[1000];
			int leido = is.read(buffer);

			while (leido > 0) {
				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}
			fos.close();
			processCompleto = p.waitFor();
			if (processCompleto == 0) {
				JOptionPane.showMessageDialog(null, "Respaldo realizado con exito");
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el respaldo.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + "Al intentar hacer el respaldo.");
		}
	}
}
