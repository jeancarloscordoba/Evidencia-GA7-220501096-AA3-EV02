
package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ventas")
public class Venta implements Serializable { //Esta clase venta, se utilizan para crear las variables y las acciones del sistema (en la parte de Ventas) que la base de datos almacenara. (Atributos y metodos)
    @Id
    @GeneratedValue
    @Column(name = "IdVentas")
    int id; 
    int item; //Pertenece a la Tabla detalleventa
    @Column(name = "IdCliente")
    int idcliente; 
    @Column(name = "IdEmpleado")
    int idempleado;
    int idproducto; //Pertenece a la Tabla detalleventa
    @Column(name = "NumeroSerie")
    String Numserie;
    String DescripcionP; //Pertenece a la Tabla detalleventa
    @Column(name = "FechaVentas")
    String fecha; 
    int precio; //Pertenece a la Tabla detalleventa
    int cantidad; //Pertenece a la Tabla detalleventa
    int subtotal; //Pertenece a la Tabla detalleventa
    @Column(name = "Monto")
    int monto; 
    @Column(name = "Estado")
    String estado; 

    public Venta() {
    }

    public Venta(int id, int item, int idcliente, int idempleado, int idproducto, String Numserie, String DescripcionP, String fecha, int precio, int cantidad, int subtotal, int monto, String estado) {
        this.id = id;
        this.item = item;
        this.idcliente = idcliente;
        this.idempleado = idempleado;
        this.idproducto = idproducto;
        this.Numserie = Numserie;
        this.DescripcionP = DescripcionP;
        this.fecha = fecha;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.monto = monto;
        this.estado = estado;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNumserie() {
        return Numserie;
    }

    public void setNumserie(String Numserie) {
        this.Numserie = Numserie;
    }

    public String getDescripcionP() {
        return DescripcionP;
    }

    public void setDescripcionP(String DescripcionP) {
        this.DescripcionP = DescripcionP;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
