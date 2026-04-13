package modelo;

// ABSTRACCIÓN: Clase abstracta que define atributos comunes
// HERENCIA: Usuario hereda de esta clase
public abstract class Persona {

    // ENCAPSULAMIENTO: atributos privados con getters y setters
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    public Persona(String nombre, String apellido, String telefono, String email) {
        this.nombre   = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email    = email;
    }

    // Getters y Setters
    public String getNombre()   { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getEmail()    { return email; }

    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email)       { this.email = email; }

    // ABSTRACCIÓN: método abstracto que cada subclase debe implementar
    public abstract String getInfo();
}