package com.stayinthedarkness.IOFiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author franc
 */
public class IOFiles {

    public void writeVar(String fileName, String index, String var, String text) {
        FileHandle file = Gdx.files.local("config/" + fileName + ".bin");
        ByteArrayOutputStream out;
        ObjectOutputStream oos;
        try {
            out = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(out);

            oos.writeBytes(text);
            oos.flush();
            byte[] datos = out.toByteArray(); // String a array de bytes.
            file.writeBytes(datos, false); // Escribimos el array de bytes en el archivo.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVar(String fileName, String index, String var) {
        FileHandle file = Gdx.files.local("config/" + fileName + ".bin"); // Abrimos el archivo.
        ByteArrayInputStream in;
        ObjectInputStream ois;
        String text = "";
        try {
            in = new ByteArrayInputStream(file.readBytes()); // Lector de array de bytes.
            ois = new ObjectInputStream(in);

            text = ois.readLine(); // Metodo deprecado, hay que cambiar esto.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
