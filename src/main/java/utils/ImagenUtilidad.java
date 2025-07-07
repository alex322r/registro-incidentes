/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author alexis
 */
public class ImagenUtilidad {
    
    public static File seleccionarImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona una imagen para el incidente");

    // Filtrar para que solo se muestren archivos de imagen
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Archivos de Imagen (jpg, png, gif)", "jpg", "png", "gif");
        selector.setFileFilter(filtroImagen);

        int resultado = selector.showOpenDialog(null); // 'null' para centrar en la pantalla

        if (resultado == JFileChooser.APPROVE_OPTION) {
       
            return selector.getSelectedFile();
        } else {
        // El usuario canceló
            return null;
        }
    }
    
    public static String guardarImagen(File archivoOriginal) throws IOException {
    // 1. Define la carpeta de destino
    String carpetaDestino = "incident_images/";
    File directorio = new File(carpetaDestino);
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea la carpeta si no existe
        }

    // 2. Crea un nombre de archivo único para evitar colisiones
        String nombreOriginal = archivoOriginal.getName();
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        String nuevoNombre = "incidente_" + System.currentTimeMillis() + extension;

    // 3. Define la ruta de destino completa
        Path destino = Paths.get(carpetaDestino + nuevoNombre);
        Path origen = archivoOriginal.toPath();

    // 4. Copia el archivo
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

    // 5. Devuelve la ruta relativa que se guardará en la BD
        return destino.toString();
    }
}
