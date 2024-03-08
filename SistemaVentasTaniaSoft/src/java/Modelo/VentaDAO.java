package Modelo;

import config.Conexion;
import config.GenerarSerie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentaDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    int r;

    public String getConsecutivoFactura() {
        int numeroserie = this.GenerarSerie();
        String nserie = "";

        //System.out.println("EL ENTERO ES:" + numeroserie + "----");
        nserie = new GenerarSerie().NumeroSerie(numeroserie);
        return nserie;

    }

    private int GenerarSerie() {
        int numeroserie = 0;
        String sql = "select max(NumeroSerie) from ventas";
        try {
            con = cn.Conexion();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                numeroserie = rs.getInt(1);
            }
        } catch (Exception v) {
            System.out.println("Error max NumSerie: "+v.getMessage() );
        }
        return numeroserie;
    }

    public String IdVentas() {
        String idventas = "";
        String sql = "select max(IdVentas) from ventas";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                idventas = rs.getString(1);

            }
        } catch (Exception v) {
        }
        return idventas;
    }

    public int guardarVenta(Venta ve) {
        String sql = "insert into ventas(IdCliente, IdEmpleado, NumeroSerie, FechaVentas, Monto, Estado)values(?,?,?,?,?,?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, ve.getIdcliente());
            ps.setInt(2, ve.getIdempleado());
            ps.setString(3, ve.getNumserie());
            ps.setString(4, ve.getFecha());
            ps.setInt(5, ve.getPrecio());
            ps.setString(6, ve.getEstado());
            
            
           r= ps.executeUpdate();
           
        } catch (Exception v) {
            v.printStackTrace();
            System.out.println("ERROR AL REGISTRAR: " + v.getLocalizedMessage());
        }
        return r;
    }

    public int GuardarDetalleVentas(Venta venta) {
        String sql = "insert into detalle_ventas(IdVentas, IdProducto, Cantidad, PrecioVenta)values(?,?,?,?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);

            ps.setInt(1, venta.getId());
            ps.setInt(2, venta.getIdproducto());
            ps.setInt(3, venta.getCantidad());
            ps.setInt(4, venta.getPrecio());

            ps.executeUpdate();

        } catch (Exception v) {
        }
        return r;
    }
}
