
package Modelo;

import Controlador.PersistanceController;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    Conexion cn = new Conexion();
    Connection con; 
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public Producto buscar(int id){
        Producto pro = new Producto();
        String sql = "select * from producto where IdProducto="+id; //Posible error por el idproducto (es IdProducto).
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                pro.setId(rs.getInt(1));
                pro.setNom(rs.getString(2));
                pro.setPrecio(rs.getInt(3));
                pro.setStock(rs.getInt(4));
                pro.setDescripcion(rs.getString(5));
                pro.setEstado(rs.getString(6));
            }
        } catch (Exception p) {
        }
        return pro; 
    }
    
    public int actualizarstock(int id, int stock) {
        String sql = "update producto set Stock=? where IdProducto=?";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, stock);
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch (Exception p) {
        }
        return r;
    }
    
    //************ Operaciones CRUD ***************//
    public List listar(){
        String sql="select * from producto";
        List<Producto> lista=new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Producto pr = new Producto();
                pr.setId(rs.getInt(1));
                pr.setNom(rs.getString(2));
                pr.setPrecio(rs.getInt(3));
                pr.setStock(rs.getInt(4));
                pr.setDescripcion(rs.getString(5));
                pr.setEstado(rs.getString(6));
                lista.add(pr);
            }
        } catch (Exception p) {
        }
        return lista; 
    }
    
    public int agregar(Producto pr){
        String sql="insert into producto(Nombres, Precio, Stock, Descripcion, Estado)values(?,?,?,?,?)"; 
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNom());
            ps.setInt(2, pr.getPrecio());
            ps.setInt(3, pr.getStock());
            ps.setString(4, pr.getDescripcion());
            ps.setString(5, pr.getEstado());
            ps.executeUpdate();
        } catch (Exception p) {
        }
        return r;
    }
    
    public Producto listarId(int id){
        Producto pro = new Producto();
        String sql="select * from producto where IdProducto="+id;
        try {
            con = cn.Conexion(); 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {  
                pro.setId(rs.getInt(1));
                pro.setNom(rs.getString(2));
                pro.setPrecio(rs.getInt(3));
                pro.setStock(rs.getInt(4));
                pro.setDescripcion(rs.getString(5));
                pro.setEstado(rs.getString(6));
            }
        } catch (Exception p) {
        }
        return pro; 
    }
    
    public int actualizar(Producto pr){
        String sql="update producto set Nombres=?, Precio=?, Stock=?, Descripcion=?, Estado=? where IdProducto=?"; 
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNom());
            ps.setInt(2, pr.getPrecio());
            ps.setInt(3, pr.getStock());
            ps.setString(4, pr.getDescripcion());
            ps.setString(5, pr.getEstado());
            ps.setInt(6, pr.getId());
            ps.executeUpdate(); 
        } catch (Exception p) {
        }
        return r;
    }
    
    public void delete(int id) {
        String sql="delete from producto where IdProducto="+id;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception p) {
        } 
    }
    
    public boolean guardarProducto(Producto producto){
        return PersistanceController.guardar(producto);
    }
    
    public List<Producto> getListaProductos(){
        return PersistanceController.buscarPorClase(Producto.class);
    }
    
    public Producto getProductoPorId(int idproducto){
        return (Producto)PersistanceController.buscarPorId(Producto.class, idproducto);
    }
}
