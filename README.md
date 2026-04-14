# 🏪 Sistema de Gestión de Almacén

Sistema de escritorio desarrollado en Java con Swing para la gestión de usuarios y productos de un almacén.

---

## 🛠️ Tecnologías utilizadas

- **Java** con programación orientada a objetos
- **Swing / AWT** para la interfaz gráfica
- **MySQL** como base de datos
- **JDBC** para la conexión a la base de datos
- **BCrypt** para el cifrado seguro de contraseñas
- **Eclipse IDE**

---

## 🏗️ Arquitectura del proyecto

El proyecto sigue el patrón de diseño **MVC (Model-View-Controller)**:

SistemaAlmacen/
├── conexion/         # Patrón Singleton - conexión a la BD
├── modelo/           # Clases Persona, Usuario, Producto
├── dao/              # Acceso a datos (CRUD)
├── controlador/      # Lógica de negocio (MVC)
└── vista/            # Interfaces gráficas con Swing

---

## 🧱 Pilares de POO aplicados

| Pilar | Implementación |
|---|---|
| **Abstracción** | Clase abstracta `Persona` con método `getInfo()` |
| **Encapsulamiento** | Atributos privados con getters y setters en todos los modelos |
| **Herencia** | `Usuario` extiende `Persona` |
| **Polimorfismo** | `@Override` de `getInfo()` en `Usuario` |

---

## 🎨 Patrones de diseño

- **Singleton** — `ConexionDB.java` garantiza una única instancia de conexión a la BD
- **MVC** — Separación de responsabilidades entre modelo, vista y controlador

---

## ✨ Funcionalidades

### 👤 Gestión de Usuarios
- Login con validación de campos
- Registro de nuevos usuarios con todos los campos obligatorios
- Contraseñas cifradas con **BCrypt**
- Listado de usuarios registrados
- Actualizar y eliminar usuarios
- Cerrar sesión

### 📦 Gestión de Productos
- Listado completo de productos del almacén
- Agregar nuevos productos
- Editar productos existentes
- Eliminar productos
- Cambios reflejados automáticamente en la tabla

---

## 🔒 Seguridad

- Las contraseñas se almacenan con hash **BCrypt**, nunca en texto plano
- Las credenciales de la base de datos se manejan en `config.properties` que está excluido del repositorio mediante `.gitignore`

---

## ⚙️ Configuración

1. Clona el repositorio
2. Crea el archivo `src/config.properties` con tus credenciales:

```properties
db.url=jdbc:mysql://localhost:3306/almacen_db
db.user=root
db.password=TU_PASSWORD
```

3. Agrega las dependencias al Build Path:
   - `mysql-connector-j.jar`
   - `jbcrypt-0.4.jar`

4. Ejecuta `LoginVista.java`

---

## 👨‍💻 Autor

**Maxwel** — [@maxwelkn](https://github.com/maxwelkn)
