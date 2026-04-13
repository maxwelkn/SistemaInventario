package modelo;

// ENCAPSULAMIENTO: todos los atributos privados con getters y setters
public class Producto {

    private int    idProducto;
    private String nombre;
    private String marca;
    private String categoria;
    private int    precio;
    private int    stock;

    public Producto(int idProducto, String nombre, String marca,
                    String categoria, int precio, int stock) {
        this.idProducto = idProducto;
        this.nombre     = nombre;
        this.marca      = marca;
        this.categoria  = categoria;
        this.precio     = precio;
        this.stock      = stock;
    }

    public int    getIdProducto() { return idProducto; }
    public String getNombre()     { return nombre; }
    public String getMarca()      { return marca; }
    public String getCategoria()  { return categoria; }
    public int    getPrecio()     { return precio; }
    public int    getStock()      { return stock; }

    public void setIdProducto(int idProducto)    { this.idProducto = idProducto; }
    public void setNombre(String nombre)         { this.nombre = nombre; }
    public void setMarca(String marca)           { this.marca = marca; }
    public void setCategoria(String categoria)   { this.categoria = categoria; }
    public void setPrecio(int precio)            { this.precio = precio; }
    public void setStock(int stock)              { this.stock = stock; }
}