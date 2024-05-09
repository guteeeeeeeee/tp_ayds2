package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ColasManager {

	
    private ArrayList<String> boxes=new ArrayList<String>();
    public ArrayList<String> dnis=new ArrayList<String>();
    private ArrayList<String> atendidos=new ArrayList<String>();
    private static int indexBox=0;
    private static int indexDnis=0;
    private ArrayList<DatosConexion> teles=new ArrayList<DatosConexion>();
    
    public ColasManager() {
    }
    
    public ArrayList<String> obtener_boxes() {
    	return this.boxes;
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
