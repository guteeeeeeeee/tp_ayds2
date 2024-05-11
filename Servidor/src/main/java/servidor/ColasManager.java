package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ColasManager {
    public ArrayList<String> boxes=new ArrayList<String>();
    public ArrayList<String> dnis=new ArrayList<String>();
    public ArrayList<String> atendidos=new ArrayList<String>();
    public static int indexBox=0;
    public static int indexDnis=0;
    public ArrayList<DatosConexion> teles=new ArrayList<DatosConexion>();
    
    public ColasManager() {
    }
    public int obtener_index_dnis() {
    	return indexBox;
    }
    public int obtener_index_box() {
    	return indexBox;
    }
    public ArrayList<String> obtener_dnis(){
    	return this.dnis;
    }
    public ArrayList<String> obtener_boxes() {	
    	return this.boxes;
    }
    public ArrayList<String> obtener_atendidos() {
    	return this.atendidos;
    }
    public ArrayList<DatosConexion> obtener_teles(){
    	return this.teles;
    }
    public void agregarBox(String box) {
    	this.boxes.add(box);
    }
    public void agregarIndexBox(int indexBox) {
    	this.indexBox = indexBox;
    }
    public void agregarDnis(ArrayList<String> dnis_nuevo) {
    	//System.out.println("entro colas");
    	this.dnis = dnis_nuevo;
    }
    public void agregarBoxes(ArrayList<String> boxes) {
    	this.boxes = boxes;
    }
    public void agregarAtendidos(ArrayList<String> atendidos) {
    	this.atendidos = atendidos;
    }
    public void agregarTeles(ArrayList<DatosConexion> teles2) {
    	//System.out.println("entro teles");
    	this.teles = teles2;
    }
    
    public void newBox(DatosConexion datos) {
    	PrintWriter out;
		try {
			System.out.println("las cosas bien"+indexBox);
			out = new PrintWriter(datos.getSocket().getOutputStream(), true);
			out.println(String.valueOf(indexBox));
	    	
	    	boxes.add(indexBox,String.valueOf(indexBox));
	    	indexBox++;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void newCliente(String Dni) {
    	dnis.add(indexDnis,Dni);
    	indexDnis++;
    }
    
    public void llamaCliente(String box) {
    	//manda a el tele el box en cuestion y el dni
    	PrintWriter out;
    	if(dnis.size()>=1) {
    		//aca manda con el primer dni al box en cuestion
    		for(int i=0;i<teles.size();i++) {
    			try {
					out = new PrintWriter(teles.get(i).getSocket().getOutputStream(), true);
					out.println(box);
					out.println(dnis.get(0));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}		
    		atendidos.add(dnis.get(0));
    		dnis.remove(0);
    	}
    	else {
    		System.out.println("No hay clientes");
    	}
    	
    	
    }
    
    public void creaTele(DatosConexion datos) {
    	teles.add(datos);
    }
}
