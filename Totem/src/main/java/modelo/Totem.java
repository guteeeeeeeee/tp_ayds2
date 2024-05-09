/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import controlador.ControladorTotem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;

public class Totem implements Serializable {
    
    private static final long serialVersionUID = 4209360273818925922L;
    
    private transient BufferedReader entrada;
    private transient BufferedWriter salida;
    //private transient String dni;
    private transient NuevoTotem nuevo;
    //private BufferedReader entrada;
    //private BufferedWriter salida;
    private String dni;
    //private NuevoTotem nuevo;
    
    public Totem() {
    }

    public Totem(BufferedWriter salida, String dni, NuevoTotem nuevo) {
        this.salida = salida;
        this.dni = dni;
        this.nuevo = nuevo;
    }

    public void iniciar() {
        ControladorTotem controladorTotem = new ControladorTotem(this);
        controladorTotem.ejecutar();
        nuevo = new NuevoTotem();
    }

    public void ingresa(String dni) {
        try {
            setDni(dni);
            nuevo.envioCliente(this, "cliente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
	public String toString() {
		return "Totem [dni=" + dni + "]";
	}

	public String getDni() {
        return this.dni;
    }

	public void setDni(String dni) {
		this.dni = dni;
	}

	public BufferedReader getEntrada() {
		return entrada;
	}

	public void setEntrada(BufferedReader entrada) {
		this.entrada = entrada;
	}

	public BufferedWriter getSalida() {
		return salida;
	}

	public void setSalida(BufferedWriter salida) {
		this.salida = salida;
	}

	public NuevoTotem getNuevo() {
		return nuevo;
	}

	public void setNuevo(NuevoTotem nuevo) {
		this.nuevo = nuevo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
