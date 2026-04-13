package dao;

import conexion.ConexionDB;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection con;

    public ProductoDAO() {
        con = ConexionDB.getInstancia().getConexion();
    }

    // REGISTRAR producto nuevo
    public boolean registrar(Producto p) {
        try {
            String sql = "INSERT INTO productos (NombreProducto, MarcaProducto, CategoriaProducto, PrecioProducto, StockProducto) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setInt(4,    p.getPrecio());
            ps.setInt(5,    p.getStock());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    // LISTAR todos los productos
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM productos";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("NombreProducto"),
                    rs.getString("MarcaProducto"),
                    rs.getString("CategoriaProducto"),
                    rs.getInt("PrecioProducto"),
                    rs.getInt("StockProducto")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR producto
    public boolean actualizar(Producto p) {
        try {
            String sql = "UPDATE productos SET NombreProducto=?, MarcaProducto=?, CategoriaProducto=?, PrecioProducto=?, StockProducto=? WHERE idProducto=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setInt(4,    p.getPrecio());
            ps.setInt(5,    p.getStock());
            ps.setInt(6,    p.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR producto
    public boolean eliminar(int idProducto) {
        try {
            String sql = "DELETE FROM productos WHERE idProducto=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}