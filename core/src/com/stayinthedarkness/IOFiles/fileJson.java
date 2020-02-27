package com.stayinthedarkness.IOFiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;

public class fileJson {

    public ArrayList<index> indexs;

    public fileJson() {
        indexs = new ArrayList<index>();
    }

    private static class index {

        public String name;
        public ArrayList<var> vars;

        public index() {
            vars = new ArrayList<var>();
        }

        public index(String name) {
            this.name = name;
            vars = new ArrayList<var>();
        }
    }

    private static class var {

        public String name;
        public String value;

        public var(String name) {
            this.name = name;
        }

        public var() {
        }

    }

    public String getVar(String index, String var) {
        int a;
        int b;
        String value = null;
        for (a = 0; a < indexs.size(); a++) { // Recorre los indices.
            if (indexs.get(a).name.equals(index)) { // Si es igual al indice buscado.
                ArrayList<var> vars = indexs.get(a).vars;
                for (b = 0; b < vars.size(); b++) { // Recorre las variables.
                    if (vars.get(b).name.equals(var)) { // Si es igual a la variable buscada.
                        value = vars.get(b).value; // Nos retorna el valor de la variable.
                    }
                }
            }
        }
        return value;
    }

    public void writeVar(String index, String var, String value) {
        int a;
        int b;

        // Recorre los indices.
        for (a = 0; a < indexs.size(); a++) {

            // Si es igual al indice buscado.
            if (indexs.get(a).name.equals(index)) {
                ArrayList<var> vars = indexs.get(a).vars;

                // Recorre las variables.
                for (b = 0; b < vars.size(); b++) {

                    // Si es igual a la variable buscada.
                    if (vars.get(b).name.equals(var)) {

                        // Setea el valor a la variable.
                        vars.get(b).value = value;

                        break; // Rompe el bucle.
                    }

                }

                // Si es la ultima variable y todavia no la encuentra.
                if (b == vars.size()) {

                    // Agrega una variable.
                    vars.add(new var(var));

                    // Le setea el valor a la nueva variable.
                    vars.get(vars.size() - 1).value = value;
                }
                break; // Rompe el bucle.
            }
        }

        // Si es el ultimo indice y todavia no lo encuentra.
        if (a == indexs.size()) {

            // Agrega un indice.
            indexs.add(new index(index));

            // Al indice le agrega una variable
            indexs.get(indexs.size() - 1).vars.add(new var(var));
            ArrayList<var> vars = indexs.get(indexs.size() - 1).vars;

            // A la variable nueva le setea un valor.
            vars.get(vars.size() - 1).value = value;
        }
    }

}
