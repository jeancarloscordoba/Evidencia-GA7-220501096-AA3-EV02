
package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="producto")
public class Producto implements Serializable { //Esta clase producto se utiliza para crear las variables y las acciones del sistema (en la parte de productos) que la base de datos almacenara. (Atributos y metodos)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="IdProducto")
    int id; //Este es la variable para el id de la tabla productos en el BD. 
    @Column(name="Nombres")
    String nom;
    @Column(name="Precio")
    Integer precio; 
    @Column(name="Stock")
    Integer stock;
    @Column(name="Descripcion")
    String descripcion;
    @Column(name="Estado")
    String estado;

    public Producto() {
    }

    public Producto(int id, String nom, Integer precio, Integer stock, String descripcion, String estado) {
        this.id = id;
        this.nom = nom;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.id+"-"+this.nom;
    }



}
