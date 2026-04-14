package vista;

import controlador.UsuarioControlador;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuariosVista extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnActualizar, btnEliminar, btnVolver;
    private JFrame ventanaAnterior;
    private UsuarioControlador controlador;

    public UsuariosVista(JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        this.controlador = new UsuarioControlador();
        setTitle("Gestión de Usuarios");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponentes();
        cargarTabla();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(235, 240, 255));

        // Título
        JLabel titulo = new JLabel("Clientes Registrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setBackground(new Color(30, 60, 120));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(new Color(235, 240, 255));

        btnNuevo     = crearBoton("Nuevo",      new Color(30, 60, 120));
        btnActualizar = crearBoton("Actualizar", new Color(30, 120, 60));
        btnEliminar  = crearBoton("Eliminar",   new Color(180, 30, 30));
        btnVolver    = crearBoton("← Volver",   new Color(100, 100, 100));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // ACCIONES
        btnNuevo.addActionListener(e -> abrirFormulario(null));
        btnActualizar.addActionListener(e -> actualizarSeleccionado());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnVolver.addActionListener(e -> volver());
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        return btn;
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Usuario> lista = controlador.listar();
        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getIdUser(),
                u.getNombre(),
                u.getApellido(),
                u.getTelefono(),
                u.getEmail(),
                u.getUserName()
            });
        }
    }

    private void abrirFormulario(Usuario u) {
        new UsuarioFormVista(this, u).setVisible(true);
        setVisible(false);
    }

    private void actualizarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        List<Usuario> lista = controlador.listar();
        Usuario u = lista.stream().filter(x -> x.getIdUser() == id).findFirst().orElse(null);
        abrirFormulario(u);
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un usuario de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar este usuario?",
            "Eliminar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            controlador.eliminar(id);
            cargarTabla();
        }
    }

    private void volver() {
        ventanaAnterior.setVisible(true);
        dispose();
    }
}