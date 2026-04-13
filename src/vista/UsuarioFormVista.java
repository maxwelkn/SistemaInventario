package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class UsuarioFormVista extends JFrame {

    private JTextField txtNombre, txtApellido, txtUsuario, txtTelefono, txtEmail;
    private JPasswordField txtPassword, txtConfirmar;
    private JButton btnGuardar, btnVolver;
    private UsuariosVista ventanaAnterior;
    private Usuario usuarioEditar;
    private UsuarioDAO dao;

    public UsuarioFormVista(UsuariosVista ventanaAnterior, Usuario usuarioEditar) {
        this.ventanaAnterior = ventanaAnterior;
        this.usuarioEditar   = usuarioEditar;
        this.dao             = new UsuarioDAO();

        setTitle(usuarioEditar == null ? "Nuevo Usuario" : "Editar Usuario");
        setSize(420, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();

        // Si es edición, llenar los campos con los datos actuales
        if (usuarioEditar != null) {
            txtNombre.setText(usuarioEditar.getNombre());
            txtApellido.setText(usuarioEditar.getApellido());
            txtUsuario.setText(usuarioEditar.getUserName());
            txtUsuario.setEditable(false); // no se puede cambiar el username
            txtTelefono.setText(usuarioEditar.getTelefono());
            txtEmail.setText(usuarioEditar.getEmail());
            // Ocultar campos de contraseña en modo edición
            txtPassword.setVisible(false);
            txtConfirmar.setVisible(false);
        }
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(235, 240, 255));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel(
            usuarioEditar == null ? "NUEVO USUARIO" : "EDITAR USUARIO",
            SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 60, 120));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(titulo, g);

        g.gridwidth = 1;
        txtNombre   = agregarCampo(panel, g, "Nombre:",            1);
        txtApellido = agregarCampo(panel, g, "Apellido:",          2);
        txtUsuario  = agregarCampo(panel, g, "Usuario:",           3);
        txtTelefono = agregarCampo(panel, g, "Teléfono:",          4);
        txtEmail    = agregarCampo(panel, g, "Correo electrónico:",5);

        // Contraseña (solo en modo nuevo)
        g.gridx = 0; g.gridy = 6;
        panel.add(new JLabel("Contraseña:"), g);
        txtPassword = new JPasswordField(15);
        g.gridx = 1; panel.add(txtPassword, g);

        g.gridx = 0; g.gridy = 7;
        panel.add(new JLabel("Confirmar contraseña:"), g);
        txtConfirmar = new JPasswordField(15);
        g.gridx = 1; panel.add(txtConfirmar, g);

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(30, 120, 60));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        panel.add(btnGuardar, g);

        // Botón Volver
        btnVolver = new JButton("← Volver");
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setForeground(new Color(30, 60, 120));
        g.gridy = 9;
        panel.add(btnVolver, g);

        add(panel);

        // ACCIONES
        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> volver());
    }

    private JTextField agregarCampo(JPanel panel, GridBagConstraints g,
                                     String label, int fila) {
        g.gridx = 0; g.gridy = fila;
        panel.add(new JLabel(label), g);
        JTextField campo = new JTextField(15);
        g.gridx = 1;
        panel.add(campo, g);
        return campo;
    }

    private void guardar() {
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String usuario  = txtUsuario.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email    = txtEmail.getText().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty())   { mostrarError("El campo Nombre es obligatorio.");   return; }
        if (apellido.isEmpty()) { mostrarError("El campo Apellido es obligatorio."); return; }
        if (usuario.isEmpty())  { mostrarError("El campo Usuario es obligatorio.");  return; }
        if (telefono.isEmpty()) { mostrarError("El campo Teléfono es obligatorio."); return; }
        if (email.isEmpty())    { mostrarError("El campo Correo es obligatorio.");   return; }

        if (usuarioEditar == null) {
            // MODO NUEVO — validar contraseñas
            String password  = new String(txtPassword.getPassword()).trim();
            String confirmar = new String(txtConfirmar.getPassword()).trim();

            if (password.isEmpty())  { mostrarError("El campo Contraseña es obligatorio."); return; }
            if (confirmar.isEmpty()) { mostrarError("Debe confirmar la contraseña.");        return; }
            if (!password.equals(confirmar)) {
                mostrarError("Las contraseñas no coinciden."); return;
            }

            Usuario u = new Usuario(0, usuario, nombre, apellido, telefono, email, password);
            if (dao.registrar(u)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                volver();
            } else {
                mostrarError("Error al registrar. El usuario ya existe.");
            }

        } else {
            // MODO EDICIÓN
            usuarioEditar.setNombre(nombre);
            usuarioEditar.setApellido(apellido);
            usuarioEditar.setTelefono(telefono);
            usuarioEditar.setEmail(email);

            if (dao.actualizar(usuarioEditar)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                volver();
            } else {
                mostrarError("Error al actualizar el usuario.");
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void volver() {
        ventanaAnterior.cargarTabla();
        ventanaAnterior.setVisible(true);
        dispose();
    }
}