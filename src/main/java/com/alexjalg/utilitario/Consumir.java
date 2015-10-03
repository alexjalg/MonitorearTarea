package com.alexjalg.utilitario;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.soap.SOAPMessage;
import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class Consumir {

	private int LastFound = 0;
	private boolean ShowMessages = false;
	private int timeOut = 90000;

	public Consumir() {
	}

	public static void main(String[] args) { 	
		Consumir util = new Consumir();
		String targetEndpointAddress = "http://52.88.24.228/ServicioTareas/ServiciciosSOAP.svc";
		String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<tem:ObtenerTareas>"
				+ "<tem:nombreServidor>Servidor1</tem:nombreServidor>"
				+ "</tem:ObtenerTareas>"
				+ "</soapenv:Body>"
				+ "</soapenv:Envelope>";
		String soapAction = "http://tempuri.org/IServiciciosSOAP/ObtenerTareas";
		String operation = "ObtenerTareas";
		String response = util.InvocarWS(targetEndpointAddress, operation, xml, soapAction);
		System.out.println(response);
	}

	public String InvocarWS(String targetEndpointAddress, String operacion, String xml, String soapAction) {
		SOAPMessage soapMessage = null;
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(targetEndpointAddress);
			call.setOperationName(operacion);
			call.setSOAPActionURI(soapAction);
			call.setTimeout(Integer.valueOf(this.getTimeOut()));
			try {
				call.invoke(new Message(xml));
			} catch (Exception e) {
				sout(e.getLocalizedMessage());
			}
			soapMessage = call.getResponseMessage();
		} catch (Exception e) {
			sout(e.getLocalizedMessage());
		}

		ByteArrayOutputStream wout = new ByteArrayOutputStream();
		try {
			soapMessage.writeTo(wout);
		} catch (Exception e) {
			sout(e.getLocalizedMessage());
		}
		return new String(wout.toByteArray());
	}

	// public Response InvocarWSbk(String ip, String uri, String operacion,
	// String xml) {
	// Response response = new Response();
	// response.setResp(false);
	// SOAPMessage soapMessage = null;
	// try {
	// Service service = new Service();
	// Call call = (Call) service.createCall();
	// call.setTargetEndpointAddress("http://" + ip + uri);
	// call.setOperationName(operacion);
	// call.setTimeout(Integer.valueOf(this.getTimeOut()));
	// try {
	// call.invoke(new Message(xml));
	// } catch (Exception e) {
	// response.setMensaje(e.getMessage());
	// }
	// soapMessage = call.getResponseMessage();
	// } catch (Exception e) {
	// response.setMensaje(e.getMessage());
	// }
	// ByteArrayOutputStream wout = new ByteArrayOutputStream();
	// try {
	// soapMessage.writeTo(wout);
	// response.setResp(true);
	// response.setMensaje(new String(wout.toByteArray()));
	// } catch (Exception e) {
	// response.setMensaje(e.getMessage());
	// }
	// return response;
	// }

	public String getNodeValue(String xml, String nodo, int posicion) {
		String nodeValue = "";
		try {
			//int iniPos = xml.indexOf("<" + nodo + ">", posicion);
			int iniPos = xml.indexOf("<" + nodo , posicion);
			int endPos;
			if (iniPos >= 0) {
				endPos = xml.indexOf("</" + nodo + ">", posicion);
				nodeValue = xml.substring(iniPos + nodo.length() + 2, iniPos
						+ nodo.length() + 2 + endPos
						- (iniPos + nodo.length() + 2));
				this.LastFound = (endPos + nodo.length());
			} else {
				int _endPos = xml.indexOf("<" + nodo + "/>", posicion);
				if (_endPos >= 0) {
					this.LastFound = (_endPos + nodo.length() + 2);
					return "";
				}
				sout(" > No se encontro el Nodo : '" + nodo + "'");
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return nodeValue;
	}

	public String getNodeValue(String xml, String nodo) {
		return getNodeValue(xml, nodo, 0);
	}

	public void sout(String msg) {
		if (this.ShowMessages) {
			System.out.println(msg);
		}
	}

	public int getLastFound() {
		return this.LastFound;
	}

	public void setLastFound(int value) {
		this.LastFound = value;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public void escribir(String nombreArchivo, String texto) {
		File f;
		f = new File(nombreArchivo);
		if (f.exists()) {
			Date date = new Date();
			DateFormat hora = new SimpleDateFormat("HH.mm.ss");
			String Strhora = hora.format(date);
			nombreArchivo = nombreArchivo.trim() + "(" + Strhora + ")";
		}
		f = new File(nombreArchivo);
		try {
			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.write(texto);
			wr.close();
			bw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
