package vista;

import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class ProductoFormVista extends JFrame {

    private JTextField txtNombre, txtMarca, txtCategoria, txtPrecio, txtStock;
    private JButton btnGuardar, btnEliminar, btnVolver;
    private ProductosVista ventanaAnterior;
    private Producto productoEditar;
    private ProductoDAO dao;

    public ProductoFormVista(ProductosVista ventanaAnterior, Producto productoEditar) {
        this.ventanaAnterior = ventanaAnterior;
        this.productoEditar  = productoEditar;
        this.dao             = new ProductoDAO();

        setTitle(productoEditar == null ? "Nuevo Producto" : "Editar Producto");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();

        // Si es edición llenar los campos
        if (productoEditar != null) {
            txtNombre.setText(productoEditar.getNombre());
            txtMarca.setText(productoEditar.getMarca());
            txtCategoria.setText(productoEditar.getCategoria());
            txtPrecio.setText(String.valueOf(productoEditar.getPrecio()));
            txtStock.setText(String.valueOf(productoEditar.getStock()));
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
            productoEditar == null ? "NUEVO PRODUCTO" : "EDITAR PRODUCTO",
            SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 60, 120));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(titulo, g);

        g.gridwidth = 1;
        txtNombre   = agregarCampo(panel, g, "Nombre:",             1);
        txtMarca    = agregarCampo(panel, g, "Marca:",              2);
        txtCategoria= agregarCampo(panel, g, "Categoría:",          3);
        txtPrecio   = agregarCampo(panel, g, "Precio:",             4);
        txtStock    = agregarCampo(panel, g, "Cantidad Disponible:", 5);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(new Color(235, 240, 255));

        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(30, 120, 60));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));

        btnEliminar = new JButton("✕ Eliminar");
        btnEliminar.setBackground(new Color(180, 30, 30));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 13));
        // Solo mostrar eliminar en modo edición
        btnEliminar.setVisible(productoEditar != null);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        g.gridx = 0; g.gridy = 6; g.gridwidth = 2;
        panel.add(panelBotones, g);

        // Botón Volver
        btnVolver = new JButton("← Volver");
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setForeground(new Color(30, 60, 120));
        g.gridy = 7;
        panel.add(btnVolver, g);

        add(panel);

        // ACCIONES
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
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
        String nombre    = txtNombre.getText().trim();
        String marca     = txtMarca.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr  = txtStock.getText().trim();

        // Validar campos vacíos
        if (nombre.isEmpty())    { mostrarError("El campo Nombre es obligatorio.");             return; }
        if (marca.isEmpty())     { mostrarError("El campo Marca es obligatorio.");              return; }
        if (categoria.isEmpty()) { mostrarError("El campo Categoría es obligatorio.");          return; }
        if (precioStr.isEmpty()) { mostrarError("El campo Precio es obligatorio.");             return; }
        if (stockStr.isEmpty())  { mostrarError("El campo Cantidad Disponible es obligatorio.");return; }

        // Validar que precio y stock sean números
        int precio, stock;
        try {
            precio = Integer.parseInt(precioStr);
            stock  = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            mostrarError("Precio y Cantidad Disponible deben ser números enteros.");
            return;
        }

        if (productoEditar == null) {
            // MODO NUEVO
            Producto p = new Producto(0, nombre, marca, categoria, precio, stock);
            if (dao.registrar(p)) {
                JOptionPane.showMessageDialog(this, "Producto registrado exitosamente.");
                volver();
            } else {
                mostrarError("Error al registrar el producto.");
            }
        } else {
            // MODO EDICIÓN
            productoEditar.setNombre(nombre);
            productoEditar.setMarca(marca);
            productoEditar.setCategoria(categoria);
            productoEditar.setPrecio(precio);
            productoEditar.setStock(stock);

            if (dao.actualizar(productoEditar)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
                volver();
            } else {
                mostrarError("Error al actualizar el producto.");
            }
        }
    }

    private void eliminar() {
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar este producto?",
            "Eliminar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            dao.eliminar(productoEditar.getIdProducto());
            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
            volver();
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