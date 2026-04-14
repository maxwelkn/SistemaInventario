package controlador;

// PATRÓN DE DISEÑO: MVC (Model-View-Controller)
// Este controlador actúa como intermediario entre la Vista y el DAO.
// La vista no accede directamente a la base de datos, sino a través del controlador.

import dao.ProductoDAO;
import modelo.Producto;
import java.util.List;

public class ProductoControlador {

    private ProductoDAO dao;

    public ProductoControlador() {
        this.dao = new ProductoDAO();
    }

    public boolean registrar(Producto p) {
        return dao.registrar(p);
    }

    public List<Producto> listar() {
        return dao.listar();
    }

    public boolean actualizar(Producto p) {
        return dao.actualizar(p);
    }

    public boolean eliminar(int idProducto) {
        return dao.eliminar(idProducto);
    }
}