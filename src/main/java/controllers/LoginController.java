/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.AppController;
import javax.swing.JOptionPane;
import luisalejos.reporteincidente.AppModel;
import vistas.VistaLogin;

/**
 *
 * @author alexis
 */
public class LoginController {
    private AppModel modelo;
    private VistaLogin vista;
    private AppController appController; 

    public LoginController(AppModel modelo, VistaLogin vista, AppController appController) {
        this.modelo = modelo;
        this.vista = vista;
        this.appController = appController;

        
        this.vista.addLoginListener(e -> autenticarUsuario() );
    }

    
    private void autenticarUsuario() {
      
        
        String user = vista.getUsuario();
        String pass = vista.getPassword();
       
        
        if (modelo.autenticar(user, pass)) {
            
            appController.mostrarInicioOperativo();
            vista.resetPassword();
            
        } else {
            // Manejar error (en una app real)
            JOptionPane.showMessageDialog(null, "DNI o Contraseña incorrectos");
        }
        
    }

}
