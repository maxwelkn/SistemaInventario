package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnRegistrarse;

    public LoginVista() {
        setTitle("Sistema de Almacén - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponentes();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(235, 240, 255));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("LOGIN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(30, 60, 120));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(titulo, g);

        // Usuario
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Usuario:"), g);
        txtUsuario = new JTextField(15);
        g.gridx = 1;
        panel.add(txtUsuario, g);

        // Contraseña
        g.gridx = 0; g.gridy = 2;
        panel.add(new JLabel("Contraseña:"), g);
        txtPassword = new JPasswordField(15);
        g.gridx = 1;
        panel.add(txtPassword, g);

        // Botón Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(30, 60, 120));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        panel.add(btnEntrar, g);

        // Link Registrarse
        btnRegistrarse = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setContentAreaFilled(false);
        btnRegistrarse.setForeground(new Color(30, 60, 120));
        g.gridy = 4;
        panel.add(btnRegistrarse, g);

        add(panel);

        // ACCIONES
        btnEntrar.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
    }

    private void iniciarSesion() {
        String usuario  = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Validación de campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Debe ingresar su usuario y contraseña.\nSi no está registrado debe registrarse.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(usuario, password);

        if (u != null) {
            dispose(); // cierra el login
            new PrincipalVista(u).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        new RegistroVista(this).setVisible(true);
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginVista().setVisible(true));
    }
}