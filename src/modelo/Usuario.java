package modelo;

// HERENCIA: Usuario extiende Persona
public class Usuario extends Persona {

    private int idUser;
    private String userName;
    private String password;

    public Usuario(int idUser, String userName, String nombre, String apellido,
                   String telefono, String email, String password) {
        // Llama al constructor de la clase padre (Persona)
        super(nombre, apellido, telefono, email);
        this.idUser   = idUser;
        this.userName = userName;
        this.password = password;
    }

    public int    getIdUser()   { return idUser; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }

    public void setIdUser(int idUser)        { this.idUser = idUser; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }

    // POLIMORFISMO: implementación específica de getInfo()
    @Override
    public String getInfo() {
        return "Usuario: " + userName + " | Nombre: " + getNombre() + " " + getApellido();
    }
}