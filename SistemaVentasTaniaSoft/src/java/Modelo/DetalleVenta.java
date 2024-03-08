
package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue
    private Long id;
    private Producto producto;
    private Venta idventa;
    private int cantidad;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}