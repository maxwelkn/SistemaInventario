package conexion;

// PATRÓN DE DISEÑO: SINGLETON
// Garantiza que solo exista UNA conexión a la base de datos en toda la aplicación.
// Esto evita abrir múltiples conexiones innecesarias al servidor MySQL.

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionDB {

    // Única instancia de la clase (Singleton)
    private static ConexionDB instancia;
    private Connection conexion;

    // Constructor privado — nadie puede hacer "new ConexionDB()" desde afuera
    private ConexionDB() {
        try {
        	// Lee las credenciales desde config.properties (no incluido en el repositorio por seguridad)
        	// Soporta conexión local y remota (aivencloud) solo cambiando el archivo config.properties
            Properties props = new Properties();
            InputStream input = getClass().getClassLoader()
                                .getResourceAsStream("config.properties");
            props.load(input);

            String url      = props.getProperty("db.url");
            String usuario  = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    // Método estático para obtener la única instancia
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    // Retorna el objeto Connection para usarlo en los DAOs
    public Connection getConexion() {
        return conexion;
    }
}