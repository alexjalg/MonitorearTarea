package com.alexjalg.utilitario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fichero {
	
	private File file;
	private List<String> lNuevosErrores = new ArrayList<>();
	private List<String> lErrores = new ArrayList<>();
	private ArrayList<Tarea> lTaskFall = new ArrayList<Tarea>();
	private ArrayList<Tarea> lNewTaskFall = new ArrayList<Tarea>();
	
	public Fichero(File file) {
		this.file = file;
	}

	public Fichero() {
	}
	
	public void actualizar(){
		for (Tarea t : this.lTaskFall) {
			this.lErrores.add(t.getNombreTarea());
		}
		if (!this.file.exists()) {
			try {
				if (this.file.createNewFile()) {
					System.out.println("archivo monitorin creado");
					this.lNewTaskFall = this.lTaskFall;
					this.escribirArchivo(this.lErrores);
					return;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		boolean estaRegistado = false;
		for (Tarea t : this.lTaskFall) {
			for (String s : this.leer()) {			
				estaRegistado = false;
				if(s.equals(t.getNombreTarea())){
					estaRegistado = true;
					break;
				}
			}
			if(!estaRegistado){
				this.lNewTaskFall.add(t);
			}
		}
		this.escribirArchivo(this.lErrores);
	}
	
	public void crear(){
		if (!this.file.exists()) {
			try {
				if (this.file.createNewFile()) {
					System.out.println("archivo monitorin creado");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void escribirArchivo(List<String> lString) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(this.file);
			for (String t : lString) {
				printWriter.println(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> leer() {
		List<String> list = new ArrayList<String>();
		Scanner scan = null;
		try{
			scan = new Scanner(this.file);
			while(scan.hasNextLine()){
				list.add(scan.nextLine());				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(scan != null){
					scan.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return list;
	}

	public static void leerWithScanner(File file) {
		Scanner scanner = null;
		Scanner scanner2 = null;
		String linea;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				linea = scanner.nextLine();
				System.out.println(linea);
				scanner2 = new Scanner(linea);
				scanner2.useDelimiter("\\s*,\\s*");
				System.out.println(scanner2.next());
				System.out.println(scanner2.next());
				System.out.println(scanner2.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void leerfichero(String pathname) {
		File file = null;
		FileReader fReader = null;
		BufferedReader bufferedReader = null;
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			file = new File(pathname);
			fReader = new FileReader(file);
			bufferedReader = new BufferedReader(fReader);
			String linea;
			while ((linea = bufferedReader.readLine()) != null) {
				System.out.println(linea + " :)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<String> getlNuevosErrores() {
		return lNuevosErrores;
	}

	public void setlNuevosErrores(List<String> lNuevosErrores) {
		this.lNuevosErrores = lNuevosErrores;
	}

	public List<String> getlErrores() {
		return lErrores;
	}

	public void setlErrores(List<String> lErrores) {
		this.lErrores = lErrores;
	}

	public ArrayList<Tarea> getlTaskFall() {
		return lTaskFall;
	}

	public void setlTaskFall(ArrayList<Tarea> lTaskFall) {
		this.lTaskFall = lTaskFall;
	}

	public ArrayList<Tarea> getlNewTaskFall() {
		return lNewTaskFall;
	}

	public void setlNewTaskFall(ArrayList<Tarea> lNewTaskFall) {
		this.lNewTaskFall = lNewTaskFall;
	}
	
}
