package controlador;

// PATRÓN DE DISEÑO: MVC (Model-View-Controller)
// Este controlador actúa como intermediario entre la Vista y el DAO.
// La vista no accede directamente a la base de datos, sino a través del controlador.

import dao.UsuarioDAO;
import modelo.Usuario;
import java.util.List;

public class UsuarioControlador {

    private UsuarioDAO dao;

    public UsuarioControlador() {
        this.dao = new UsuarioDAO();
    }

    public Usuario login(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) return null;
        return dao.login(userName, password);
    }

    public boolean registrar(Usuario u) {
        return dao.registrar(u);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }

    public boolean actualizar(Usuario u) {
        return dao.actualizar(u);
    }

    public boolean eliminar(int idUser) {
        return dao.eliminar(idUser);
    }
}