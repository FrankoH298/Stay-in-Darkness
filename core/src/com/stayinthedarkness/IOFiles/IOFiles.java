package com.stayinthedarkness.IOFiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOFiles {

    public void writeVarBinary(String fileName, String text) {
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

    public String getVarBinary(String fileName) {
        FileHandle file = Gdx.files.local("config/" + fileName + ".bin");
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

    public void writeVar(String fileName, String index, String var, String value) {
        FileHandle file;
        Json json = new Json();
        fileJson filejson;

        // Si el archivo existe.
        if (Gdx.files.local("config/" + fileName + ".ini").exists()) {
            file = Gdx.files.local("config/" + fileName + ".ini");

            // Cargamos el archivo Json.
            filejson = getJson(file, json);
        } else {
            file = Gdx.files.local("config/" + fileName + ".ini");

            // Creamos un nuevo archivo Json.
            filejson = newJson(file, json);
        }

        // Escribe el valor de la variable.
        filejson.writeVar(index, var, value);

        // Lo guardamos como prettyPrint en el archivo.
        file.writeString(json.prettyPrint(filejson), false);
    }

    public String getVar(String fileName, String index, String var) {
        FileHandle file;
        Json json = new Json();
        fileJson filejson;

        // Si el archivo existe.
        if (Gdx.files.local("config/" + fileName + ".ini").exists()) {
            file = Gdx.files.local("config/" + fileName + ".ini");

            // Cargamos el json.
            filejson = getJson(file, json);

            // Retornamos el json.
            return filejson.getVar(index, var);

        } else {
            System.out.println("Error, archivo no existente.");
            return null;

        }
    }

    public fileJson getJson(FileHandle file, Json json) {
        fileJson filejson = json.fromJson(fileJson.class, file.readString());
        return filejson;
    }

    public fileJson newJson(FileHandle file, Json json) {
        fileJson filejson = new fileJson();
        return filejson;
    }
}
