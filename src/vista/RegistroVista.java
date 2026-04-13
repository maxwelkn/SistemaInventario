package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class RegistroVista extends JFrame {

    private JTextField txtNombre, txtApellido, txtUsuario, txtTelefono, txtEmail;
    private JPasswordField txtPassword, txtConfirmar;
    private JButton btnRegistrar, btnVolver;
    private JFrame ventanaAnterior;

    public RegistroVista(JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        setTitle("Registro de Usuario");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(235, 240, 255));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("REGISTRO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 120));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(titulo, g);

        // Campos
        g.gridwidth = 1;
        txtNombre   = agregarCampo(panel, g, "Nombre:",           1);
        txtApellido = agregarCampo(panel, g, "Apellido:",         2);
        txtUsuario  = agregarCampo(panel, g, "Nombre de Usuario:",3);
        txtTelefono = agregarCampo(panel, g, "Teléfono:",         4);
        txtEmail    = agregarCampo(panel, g, "Correo electrónico:",5);

        // Contraseña
        g.gridx = 0; g.gridy = 6;
        panel.add(new JLabel("Contraseña:"), g);
        txtPassword = new JPasswordField(15);
        g.gridx = 1;
        panel.add(txtPassword, g);

        // Confirmar contraseña
        g.gridx = 0; g.gridy = 7;
        panel.add(new JLabel("Confirmar contraseña:"), g);
        txtConfirmar = new JPasswordField(15);
        g.gridx = 1;
        panel.add(txtConfirmar, g);

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(30, 60, 120));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        panel.add(btnRegistrar, g);

        // Botón Volver
        btnVolver = new JButton("← Volver");
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setForeground(new Color(30, 60, 120));
        g.gridy = 9;
        panel.add(btnVolver, g);

        add(panel);

        // ACCIONES
        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> volver());
    }

    // Método auxiliar para no repetir código al crear campos
    private JTextField agregarCampo(JPanel panel, GridBagConstraints g, String label, int fila) {
        g.gridx = 0; g.gridy = fila;
        panel.add(new JLabel(label), g);
        JTextField campo = new JTextField(15);
        g.gridx = 1;
        panel.add(campo, g);
        return campo;
    }

    private void registrar() {
        String nombre    = txtNombre.getText().trim();
        String apellido  = txtApellido.getText().trim();
        String usuario   = txtUsuario.getText().trim();
        String telefono  = txtTelefono.getText().trim();
        String email     = txtEmail.getText().trim();
        String password  = new String(txtPassword.getPassword()).trim();
        String confirmar = new String(txtConfirmar.getPassword()).trim();

        // Validar campos vacíos
        if (nombre.isEmpty())    { mostrarError("El campo Nombre es obligatorio.");            return; }
        if (apellido.isEmpty())  { mostrarError("El campo Apellido es obligatorio.");          return; }
        if (usuario.isEmpty())   { mostrarError("El campo Nombre de Usuario es obligatorio."); return; }
        if (telefono.isEmpty())  { mostrarError("El campo Teléfono es obligatorio.");          return; }
        if (email.isEmpty())     { mostrarError("El campo Correo electrónico es obligatorio.");return; }
        if (password.isEmpty())  { mostrarError("El campo Contraseña es obligatorio.");        return; }
        if (confirmar.isEmpty()) { mostrarError("Debe confirmar la contraseña.");              return; }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmar)) {
            mostrarError("Las contraseñas no coinciden.");
            return;
        }

        Usuario u = new Usuario(0, usuario, nombre, apellido, telefono, email, password);
        UsuarioDAO dao = new UsuarioDAO();

        if (dao.registrar(u)) {
            JOptionPane.showMessageDialog(this,
                "Usuario registrado exitosamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        } else {
            mostrarError("Error al registrar. El nombre de usuario ya existe.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void volver() {
        ventanaAnterior.setVisible(true);
        dispose();
    }
}