/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package luisalejos.reporteincidente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author alexis
 */
public class TipoIncidenteSelector extends JPanel {
    
    private String tipo;
    private final JPanel iconsContainer;

    public TipoIncidenteSelector() {
    
        setLayout(new BorderLayout());
        
        JPanel tituloContainer = new JPanel();
        JLabel tituloLabel = new JLabel("Tipo de incidente");
        JPanel tiposContainer = new JPanel();
        
        JPanel tecnicoContainer = new JPanel(new BorderLayout());
        JPanel seguridadContainer = new JPanel(new BorderLayout());
        JPanel instalacionContainer = new JPanel(new BorderLayout());
        
        
        
        
        JLabel tecnicoLabel = new JLabel("Tecnico");
        JLabel instalacionLabel = new JLabel("Instalacion");
        JLabel seguridadLabel = new JLabel("Seguridad");
       
        tecnicoLabel.setHorizontalAlignment(JLabel.CENTER);
        instalacionLabel.setHorizontalAlignment(JLabel.CENTER);
        seguridadLabel.setHorizontalAlignment(JLabel.CENTER);
        
        tituloContainer.add(tituloLabel);
        
        FlowLayout layout = new FlowLayout();
        
        
                
        layout.setHgap(60);
        layout.setVgap(10);
       
        iconsContainer = new JPanel();
        iconsContainer.setLayout(layout);
        
        
        
        
        
        ButtonGroup tipoIncidente = new ButtonGroup();
        JRadioButton incidenteTecnico = new JRadioButton("");
        JRadioButton incidenteSeguridad = new JRadioButton("");
        JRadioButton incidenteInstalacion = new JRadioButton("");
        
        incidenteSeguridad.setActionCommand("seguridad");
        incidenteInstalacion.setActionCommand("instalacion");
        incidenteTecnico.setActionCommand("tecnico");
        
        Border bordeSeleccionado = BorderFactory.createDashedBorder(Color.BLUE, 5,3,3,true);
        Border bordeVacio = BorderFactory.createEmptyBorder(5, 5, 5, 5); // 
       
        
        ImageIcon iconoSeguridad = new ImageIcon("iconos/seguridad.png", "seguridad");
        ImageIcon iconoInstalacion = new ImageIcon("iconos/instalacion.png", "instalacion");
        ImageIcon iconoTecnico = new ImageIcon("iconos/tecnico.png", "tecnico");
        
        
        incidenteSeguridad.setBorder(bordeVacio);
        incidenteSeguridad.setBorderPainted(true);
        incidenteTecnico.setBorder(bordeVacio);
        incidenteTecnico.setBorderPainted(true);
        incidenteInstalacion.setBorder(bordeVacio);
        incidenteInstalacion.setBorderPainted(true);
        
        
        System.out.println(TipoIncidenteSelector.class.getResource("src/iconos/seguridad.png"));
        
        incidenteSeguridad.setIcon(iconoSeguridad);
        incidenteInstalacion.setIcon(iconoInstalacion);
        incidenteTecnico.setIcon(iconoTecnico);
             
        JButton test = new JButton("test");
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton boton = (JRadioButton) e.getSource();
              
                tipo = boton.getActionCommand();
                System.out.println("Opción seleccionada: " + tipo);
            }
        };

        ItemListener listener2 = e -> {
                JRadioButton boton = (JRadioButton) e.getSource();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // 3. Aplicar el borde seleccionado
                    
                    boton.setBorder(bordeSeleccionado);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    // 3. Quitar el borde (volver al vacío)
                    boton.setBorder(bordeVacio);
                }
            };
              
        tipoIncidente.add(incidenteTecnico);
        tipoIncidente.add(incidenteSeguridad);
        tipoIncidente.add(incidenteInstalacion);
        
        incidenteInstalacion.addActionListener(listener);
        incidenteTecnico.addActionListener(listener);
        incidenteSeguridad.addActionListener(listener);
        
        incidenteSeguridad.addItemListener(listener2);
        incidenteInstalacion.addItemListener(listener2);
        incidenteTecnico.addItemListener(listener2);
        
        seguridadContainer.add(seguridadLabel, BorderLayout.NORTH);
        instalacionContainer.add(instalacionLabel, BorderLayout.NORTH);
        tecnicoContainer.add(tecnicoLabel, BorderLayout.NORTH);
        
        seguridadContainer.add(incidenteSeguridad, BorderLayout.SOUTH);
        instalacionContainer.add(incidenteInstalacion, BorderLayout.SOUTH);
        tecnicoContainer.add(incidenteTecnico, BorderLayout.SOUTH);
        
        iconsContainer.add(seguridadContainer);
        iconsContainer.add(instalacionContainer);
        iconsContainer.add(tecnicoContainer);
                
        
        
        add(tituloContainer, BorderLayout.NORTH);
        add(iconsContainer);
         
    }
    
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            JFrame ventana = new JFrame();
            
            
            
            
            public void run() {
                ventana.setDefaultCloseOperation(3);
                ventana.setLocationRelativeTo(null);
                
                
                
                ventana.add(new TipoIncidenteSelector());
                
                ventana.pack();
                ventana.setVisible(true);
            }
        });
        
    }
    
    
    
}
