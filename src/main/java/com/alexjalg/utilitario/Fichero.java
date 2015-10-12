package com.alexjalg.utilitario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fichero {

    public Fichero() {
    }

    public static void main(String[] args) {
        File file = new File("C:\\Monitor\\auxiliar949259166.txt");
        
        file.delete();
//        System.gc();
    }
    /**
     * *********************************************************
     * Modificar un fichero de texto, consiste en leer un archivo y escribir su
     * con tenido en uno nuevo llamado X, excepto la linea a modificar que se
     * remplaza con la linea nueva.Luego se borra el fichero inicial y se
     * renombra el nuevo fichero con el nombre del archivo inicial
     * ********************************************************** PARoMETROS:
     * FficheroAntiguo:Objeto File del fichero a modificar Satigualinea:Linea
     * que se busca para modificar Snuevalinea:Linea con la que se va a
     * remplazar la vieja
     * *********************************************************
     */
    public void modificarFichero(File FficheroAntiguo,
            String Satigualinea, String Snuevalinea) {
        /* Obtengo un numero aleatorio */
        Random numaleatorio = new Random(3816L);
        /*
         * Creo un nombre para el nuevo fichero apartir delnumero aleatorio
         */
        String SnombFichNuev = FficheroAntiguo.getParent() + "/auxiliar"
                + String.valueOf(Math.abs(numaleatorio.nextInt())) + ".txt";
        /* Crea un objeto File para el fichero nuevo */
        File FficheroNuevo = new File(SnombFichNuev);
        try {
            /* Si existe el fichero inical */
            if (FficheroAntiguo.exists()) {
                /* Abro un flujo de lectura */
                BufferedReader Flee = new BufferedReader(new FileReader(
                        FficheroAntiguo));
                String Slinea;
                /* Recorro el fichero de texto linea a linea */
                while ((Slinea = Flee.readLine()) != null) {
                    /*
                     * Si la lia obtenida es igual al la bucadapara modificar
                     */
                    if (Slinea.toUpperCase().trim()
                            .equals(Satigualinea.toUpperCase().trim())) {
                        /* Escribo la nueva linea en vez de la que tenia */
                        escribirFichero(FficheroNuevo, Snuevalinea);
                    } else {
                        /* Escribo la linea antigua */
                        escribirFichero(FficheroNuevo, Slinea);
                    }
                }
                /* Obtengo el nombre del fichero inicial */
                String SnomAntiguo = FficheroAntiguo.getName();
                /* Borro el fichero inicial */
                borrarFichero(FficheroAntiguo);
                /*
                 * renombro el nuevo fichero con el nombre delfichero inicial
                 */
                FficheroNuevo.renameTo(FficheroAntiguo);
                /* Cierro el flujo de lectura */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }
    }

    public void escribirFichero(File Ffichero, String SCadena) {
        try {
            // Si no Existe el fichero lo crea
            if (!Ffichero.exists()) {
                Ffichero.createNewFile();
            }
            /*
             * Abre un Flujo de escritura,sobre el fichero con codificacion
             * utf-8.Ademos en el pedazo de sentencia
             * "FileOutputStream(Ffichero,true)",true es por si existe el
             * fichero seguir aoadiendo texto y no borrar lo que tenia
             */
            BufferedWriter Fescribe = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(Ffichero, true), "utf-8"));
            /*
             * Escribe en el fichero la cadena que recibe la funcion.el string
             * "\r\n" significa salto de linea
             */
            Fescribe.write(SCadena + "\r\n");
            // Cierra el flujo de escritura
            Fescribe.close();
        } catch (Exception ex) {
            // Captura un posible error le imprime en pantalla
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Funcion que lee el contenido de un fichero de textoParometro:Ffichero.
     * Objeto de la clase file donde se va a leer
     */
    public void leerFichero(File Ffichero) {
        try {
            /* Si existe el fichero */
            if (Ffichero.exists()) {
                /* Abre un flujo de lectura a el fichero */
                BufferedReader Flee = new BufferedReader(new FileReader(
                        Ffichero));
                String Slinea;
                System.out.println("**********Leyendo Fichero***********");
                /* Lee el fichero linea a linea hasta llegar a la ultima */
                while ((Slinea = Flee.readLine()) != null) {
                    /* Imprime la linea leida */
                    System.out.println(Slinea);
                }
                System.out.println("*********Fin Leer Fichero**********");
                /* Cierra el flujo */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }
    }

    public List<String> consultarRegistros(File Ffichero) {
        List<String> list = new ArrayList<String>();
        try {
            /* Si existe el fichero */
            if (Ffichero.exists()) {
                /* Abre un flujo de lectura a el fichero */
                BufferedReader Flee = new BufferedReader(new FileReader(
                        Ffichero));
                String Slinea;
                System.out.println("**********Leyendo Fichero***********");
                /* Lee el fichero linea a linea hasta llegar a la ultima */
                while ((Slinea = Flee.readLine()) != null) {
                    list.add(Slinea);
                }
                System.out.println("*********Fin Leer Fichero**********");
                /* Cierra el flujo */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /*
     * Motodo que borra un fichero si existeParometro:Ffichero. Objeto de la
     * clase file donde se va a borrar
     */
    public static void borrarFichero(File Ffichero) {
        try {
            /* Si existe el fichero */
            if (Ffichero.exists()) {
                System.out.println("   AAAAA " + Ffichero.getPath());
                /* Borra el fichero */
                Ffichero.delete();
                System.gc(); 
            }
        } catch (Exception ex) {
            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }
    }

    public boolean buscarRegistro(File Ffichero, String lineaBuscada) {
        boolean resp = false;
        try {
            /* Si existe el fichero inical */
            if (Ffichero.exists()) {
                /* Abro un flujo de lectura */
                BufferedReader Flee = new BufferedReader(new FileReader(
                        Ffichero));
                String Slinea;
                /* Recorro el fichero de texto linea a linea */
                while ((Slinea = Flee.readLine()) != null) {
                    /*
                     * Si la lia obtenida es igual al la buscada
                     */
                    if (Slinea.toUpperCase().trim()
                            .equals(lineaBuscada.toUpperCase().trim())) {
                        resp = true;
                    }
                }
                /* Cierro el flujo de lectura */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }
        return resp;
    }

    /**
     * *********************************************************
     * Eliminar un registro dentro de un fichero de texto, consiste en leer un
     * archivo y escribir su contenido en uno nuevo llamado X, excepto la linea
     * a eliminar.Luego se borra el fichero inicial y se renombra el nuevo
     * fichero con el nombre del archivo inicial
     * ********************************************************** PARoMETROS:
     * FficheroAntiguo:Objeto File del fichero a eliminar el reg
     * Satigualinea:Linea que se busca para eliminar
     * *********************************************************
     */
    public void eliminarRegistro(File FficheroAntiguo,
            String Santigualinea) {
        /* Obtengo un numero aleatorio */
        Random numaleatorio = new Random(3816L);
        /*
         * Creo un nombre para el nuevo fichero apartir delnumero aleatorio
         */
        String SnombFichNuev = FficheroAntiguo.getParent() + "/auxiliar"
                + String.valueOf(Math.abs(numaleatorio.nextInt())) + ".txt";
        /* Crea un objeto File para el fichero nuevo */
        File FficheroNuevo = new File(SnombFichNuev);
        try {
            /* Si existe el fichero inical */
            if (FficheroAntiguo.exists()) {
                /* Abro un flujo de lectura */
                BufferedReader Flee = new BufferedReader(new FileReader(
                        FficheroAntiguo));
                String Slinea;
                /* Recorro el fichero de texto linea a linea */
                while ((Slinea = Flee.readLine()) != null) {
                    /*
                     * Si la linea obtenida es distinta al la buscadapara
                     * eliminar
                     */
                    if (!Slinea.toUpperCase().trim()
                            .equals(Santigualinea.toUpperCase().trim())) {
                        /* la escribo en el fichero nuevo */
                        escribirFichero(FficheroNuevo, Slinea);
                    } else {
                        /* Si es igual simple mete no hago nada */
                    }
                }
                /* Obtengo el nombre del fichero inicial */
                String SnomAntiguo = FficheroAntiguo.getName();
                /* Borro el fichero inicial */
                borrarFichero(FficheroAntiguo);
                /* renombro el nuevo fichero con el nombre del fichero inicial */
                FficheroNuevo.renameTo(FficheroAntiguo);
                /* Cierro el flujo de lectura */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
