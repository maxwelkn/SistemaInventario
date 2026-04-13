package dao;

import conexion.ConexionDB;
import modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO() {
        con = ConexionDB.getInstancia().getConexion();
    }

    // REGISTRAR usuario nuevo con contraseña hasheada
    public boolean registrar(Usuario u) {
        try {
            // SEGURIDAD: nunca guardamos la contraseña en texto plano
            String hashPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            String sql = "INSERT INTO usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, hashPassword);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    // LOGIN: verifica usuario y contraseña con BCrypt
    public Usuario login(String userName, String password) {
        try {
            String sql = "SELECT * FROM usuarios WHERE UserName = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashGuardado = rs.getString("Password");
                // SEGURIDAD: BCrypt compara el hash, nunca texto plano
                if (BCrypt.checkpw(password, hashGuardado)) {
                    return new Usuario(
                        rs.getInt("idUser"),
                        rs.getString("UserName"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getString("Telefono"),
                        rs.getString("Email"),
                        hashGuardado
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null; // null = credenciales incorrectas
    }

    // LISTAR todos los usuarios
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usuarios";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("idUser"),
                    rs.getString("UserName"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Telefono"),
                    rs.getString("Email"),
                    rs.getString("Password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR usuario
    public boolean actualizar(Usuario u) {
        try {
            String sql = "UPDATE usuarios SET Nombre=?, Apellido=?, Telefono=?, Email=? WHERE idUser=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getIdUser());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR usuario
    public boolean eliminar(int idUser) {
        try {
            String sql = "DELETE FROM usuarios WHERE idUser=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUser);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}