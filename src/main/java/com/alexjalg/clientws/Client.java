/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alexjalg.clientws;
 
import java.util.ArrayList;

import com.alexjalg.utilitario.Consumir;
import com.alexjalg.utilitario.Tarea;

/**
 *
 * @author ALEJANDRO TU TERROR :D!
 */
public class Client {

	public static void main(String[] args) {
		Client client = new Client();
		for (Tarea tarea : client.obtenerTarea("Servidor1")) {
			System.out.println(tarea.getFechaRegistro());
			System.out.println(tarea.getId());
			System.out.println(tarea.getNombreTarea());
			System.out.println(tarea.getNombreServidor());
			System.out.println();
		}
		client.registrarIncidencia("1");
	}

	public ArrayList<Tarea> listTaskReview() {
		return this.obtenerTarea("Servidor1");
	}

	public void sendTasksFalls(ArrayList<Tarea> listTaskFall) {
		// Aqui se consume el webService que enviara las tareas caidas a Google
		for (Tarea tarea : listTaskFall) {
			this.registrarIncidencia(tarea.getId());
		}
	}

	public boolean registrarIncidencia(String idTarea){
			boolean result = false;
			Consumir util = new Consumir();
			String targetEndpointAddress = "http://52.88.24.228/ServicioTareas/ServiciciosSOAP.svc";
			String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
					+ "<soapenv:Header/>"
					+ "<soapenv:Body>"
					+ "<tem:RegistrarIncidencia>"
					+ "<tem:IdTarea>"+idTarea+"</tem:IdTarea>"
					+ "<tem:Estado>Pendiente</tem:Estado>"
					+ "</tem:RegistrarIncidencia>"
					+ "</soapenv:Body>" 
					+ "</soapenv:Envelope>";
			String soapAction = "http://tempuri.org/IServiciciosSOAP/RegistrarIncidencia";
			String operation = "RegistrarIncidencia";
			String xmlResponse = util.InvocarWS(targetEndpointAddress, operation,
					xml, soapAction);
			try {
				String response = util.getNodeValue(xmlResponse,
						"RegistrarIncidenciaResult");
				if (response == null)
					result = false;
				String success = util.getNodeValue(response, "a:Success");
				if(success.equals("true")){
					result = true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		return result;
	}
	
	public ArrayList<Tarea> obtenerTarea(String servidor) {
		ArrayList<Tarea> listTarea = new ArrayList<Tarea>();
		Consumir util = new Consumir();
		String targetEndpointAddress = "http://52.88.24.228/ServicioTareas/ServiciciosSOAP.svc";
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<tem:ObtenerTareas>"
				+ "<tem:nombreServidor>"
				+ servidor
				+ "</tem:nombreServidor>"
				+ "</tem:ObtenerTareas>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";
		String soapAction = "http://tempuri.org/IServiciciosSOAP/ObtenerTareas";
		String operation = "ObtenerTareas";
		String xmlResponse = util.InvocarWS(targetEndpointAddress, operation,
				xml, soapAction);
		try {
			String response = util.getNodeValue(xmlResponse,
					"ObtenerTareasResult");
			if (response == null)
				return listTarea;
			String item;
			int s = 0;
			Tarea tarea;
			do {
				item = util.getNodeValue(response, "a:TareaBE", s);
				s = util.getLastFound();
				if (item != null) {
					tarea = new Tarea(
							util.getNodeValue(item, "a:FechaRegistro"),
							util.getNodeValue(item, "a:Id"), 
							util.getNodeValue(item, "a:NombreServidor"),
							util.getNodeValue(item, "a:NombreTarea"));
					listTarea.add(tarea);
				}
			} while (item != null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listTarea;
	}

}
