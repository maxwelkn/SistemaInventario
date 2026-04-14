package vista;

import controlador.ProductoControlador;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosVista extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnVolver;
    private JFrame ventanaAnterior;
    private ProductoControlador controlador;

    public ProductosVista(JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        this.controlador = new ProductoControlador();
        setTitle("Gestión de Productos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponentes();
        cargarTabla();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(235, 240, 255));

        // Título
        JLabel titulo = new JLabel("Productos de Almacén", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Marca", "Categoría", "Precio", "Cantidad Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setBackground(new Color(30, 60, 120));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);

        // Al hacer clic en una fila abrir formulario de edición
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 1) {
                    abrirFormularioEdicion();
                }
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(new Color(235, 240, 255));

        btnNuevo  = crearBoton("Nuevo",    new Color(30, 60, 120));
        btnVolver = crearBoton("← Volver", new Color(100, 100, 100));

        panelBotones.add(btnNuevo);
        panelBotones.add(btnVolver);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // ACCIONES
        btnNuevo.addActionListener(e -> abrirFormularioNuevo());
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
        List<Producto> lista = controlador.listar();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getMarca(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }

    private void abrirFormularioNuevo() {
        new ProductoFormVista(this, null).setVisible(true);
        setVisible(false);
    }

    private void abrirFormularioEdicion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int id = (int) modeloTabla.getValueAt(fila, 0);
        List<Producto> lista = controlador.listar();
        Producto p = lista.stream()
                         .filter(x -> x.getIdProducto() == id)
                         .findFirst().orElse(null);

        new ProductoFormVista(this, p).setVisible(true);
        setVisible(false);
    }

    private void volver() {
        ventanaAnterior.setVisible(true);
        dispose();
    }
}