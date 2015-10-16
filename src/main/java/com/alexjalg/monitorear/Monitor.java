/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alexjalg.monitorear;

import com.alexjalg.clientws.Client;
import com.alexjalg.utilitario.Fichero;
import com.alexjalg.utilitario.Tarea;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ALEJANDRO TU TERROR :D!
 */
public class Monitor {

	private ArrayList<Tarea> listTaskFall = new ArrayList<Tarea>();
	private Client clienteWS = new Client();

	public static void main(String[] args) {
		new Monitor().execute();
	}

	public void execute() {
		for (Tarea tarea : clienteWS.listTaskReview()) {
			System.out.println("revisando tarea " + tarea.getNombreTarea() + " ...");
			if (!checkActiveTask(tarea.getNombreTarea())) {
				this.listTaskFall.add(tarea);
			}
		}
		if (this.listTaskFall.isEmpty()) {
			return;
		}
		Fichero fichero = new Fichero(new File("C:\\Monitor\\taskfail.txt"));
		fichero.setlTaskFall(this.listTaskFall);
		fichero.actualizar();
		for (Tarea t : fichero.getlNewTaskFall()) {
			System.out.println("registrando tarea nueva " + t.getNombreTarea());
			clienteWS.registrarIncidencia(t.getId());
		}
	}

	public boolean checkActiveTask(String task) {
		try {
			Process p = Runtime.getRuntime().exec("schtasks /query /fo LIST /v");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			boolean existeTarea = false;
			boolean lastResult = false;
			boolean status = false;
			boolean esLaPrimeraEjecucion = false; // es verdadero si la tarea
													// nunca se ha ejecutado
			while ((line = in.readLine()) != null) {
				line = line.replace(" ", "");
				if (line.equals("Nombredetarea:" + (char) 92 + task) || line.equals("TaskName:" + (char) 92 + task)) {
					existeTarea = true;
				}
				if (existeTarea) {
					if (line.equals("Status:Ready") || line.equals("Estado:Listo")) {
						status = true;
					}
					if (line.equals("Status:Couldnotstart") || line.equals("Estado:Nosepudoiniciar")) {
						status = true;
					}
					if (line.equals("LastRunTime:N/A") || line.equals("éltimotiempodeejecuci¢n:Nodisponible")) {
						esLaPrimeraEjecucion = true;
					}
					if (line.equals("LastResult:0") || line.equals("éltimoresultado:0")) {
						lastResult = true;
					}
					if (line.equals("LastResult:267014") || line.equals("éltimoresultado:267014")) {
						// Esto indica que la ultima ejecución de la tarea fue
						// terminada por el usuario.
						lastResult = true;
					}
					// if (line.indexOf("Estado:") != -1) {
					// System.out.println(line);
					// }
					// if (line.indexOf("ltimoresultado:") != -1) {
					// System.out.println(line);
					// }
					if (line.equals("")) {
						break;
					}
				}
			}
			in.close();
			if (!esLaPrimeraEjecucion) { // En caso la tarea NO ya se haya
											// ejecutado por primera vez
				if (status && !lastResult) {
					// ready 0x1 esto no debe de pasar
					return false;
				}
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
