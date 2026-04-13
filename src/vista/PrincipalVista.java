package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class PrincipalVista extends JFrame {

    private Usuario usuarioActual;
    private JButton btnUsuarios, btnProductos, btnCerrarSesion;

    public PrincipalVista(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Sistema de Almacén - Principal");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(235, 240, 255));

        // Bienvenida
        JLabel lblBienvenida = new JLabel(
            "Bienvenido, " + usuarioActual.getNombre() + "!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        lblBienvenida.setForeground(new Color(30, 60, 120));
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(lblBienvenida, BorderLayout.NORTH);

        // Panel central con los dos botones grandes
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBotones.setBackground(new Color(235, 240, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Botón Usuarios
        btnUsuarios = crearBotonMenu("👤 Usuarios", new Color(30, 60, 120));
        panelBotones.add(btnUsuarios);

        // Botón Productos
        btnProductos = crearBotonMenu("📦 Productos", new Color(30, 60, 120));
        panelBotones.add(btnProductos);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Botón cerrar sesión arriba a la derecha
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(180, 30, 30));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTop.setBackground(new Color(235, 240, 255));
        panelTop.add(btnCerrarSesion);
        panel.add(panelTop, BorderLayout.SOUTH);

        add(panel);

        // ACCIONES
        btnUsuarios.addActionListener(e -> abrirUsuarios());
        btnProductos.addActionListener(e -> abrirProductos());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JButton crearBotonMenu(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void abrirUsuarios() {
        new UsuariosVista(this).setVisible(true);
        setVisible(false);
    }

    private void abrirProductos() {
        new ProductosVista(this).setVisible(true);
        setVisible(false);
    }

    private void cerrarSesion() {
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            dispose();
            new LoginVista().setVisible(true);
        }
    }
}