/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alexjalg.clientws;

import java.util.ArrayList;

/**
 *
 * @author ALEJANDRO TU TERROR :D!
 */
public class Client {

    public ArrayList<String> listTaskReview() {
        ArrayList<String> listTaskReview = new ArrayList<String>();
        //Aqui se consume el webService y se carga el arreglo listTaskReview con las tareas a revisar
        //Por mientras cargaremos datos en duro
        listTaskReview.add("tarea1");
        listTaskReview.add("tarea2");
        return listTaskReview;
    }

    public void sendTasksFalls(ArrayList<String> listTaskFall) {
        //Aqui se consume el webService que enviara las tareas caidas a Google Cloud Messaging
        //Por mientras solo se pintara la tarea en la consola
        for (String task : listTaskFall) {
            System.out.println(task.toUpperCase() + " esta caida");
        }
    }

}
