/*
UNIVERSIDAD ESTATAL A DISTANCIA
Curso: Programación avanzada
Código: 00830
Proyecto #2: Tienda deportiva
Tutor: Juan Ramírez Valladares
Grupo: 09
Estudiante: Francisco Campos Sandi
Cédula: 114750560
III Cuatrimestre 2024
*/

using System;
using System.Collections.Generic;
using System.Configuration;//Libreria para acceder al archivo de configuracion
using System.Data;//Libreria para acceder a las clases de ADO.NET
using System.Data.SqlClient;//
using System.Diagnostics;
using System.Text.RegularExpressions;
using TiendaDeportivaServidor.Entidades;

namespace TiendaDeportivaServidor.Datos
{
    public class Database
    {
        private readonly string _connectionString;// Almacena la cadena de conexión a la base de datos
        //Constructor de la clase Database
        public Database()
        {//Inicializa la cadena de conexión a la base de datos
            _connectionString = ConfigurationManager.ConnectionStrings["DatabaseConnection"].ConnectionString;
        }
        //Método para registrar una categoría a la base de datos
        public bool RegistrarCategoria(Categoria categoria)
        {     // Se establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();//Se abre la conexión
                // Consulta para verificar si ya existe una categoría con el mismo ID o nombre
                string checkQuery = "SELECT COUNT(*) FROM Categoria WHERE id = @id OR nombre = @nombre";
                using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))// Se crea un comando SQL
                {    // Se añaden los parámetros a la consulta SQL
                    checkCommand.Parameters.AddWithValue("@id", categoria.Id);//Se asigna el valor del ID
                    checkCommand.Parameters.AddWithValue("@nombre", categoria.Nombre);//Se asigna el valor del nombre
                    int count = (int)checkCommand.ExecuteScalar();//Se ejecuta el comando y se obtiene el resultado
                    if (count > 0)//Si el resultado es mayor a 0
                    {
                        return false;//Se retorna falso
                    }
                }

                // Consulta para insertar la nueva categoría en la base de datos
                string insertQuery = "INSERT INTO Categoria (id, nombre, descripcion) VALUES (@id, @nombre, @descripcion)";//
                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {   // Se añaden los parámetros a la consulta SQL para insertar la categoría
                    insertCommand.Parameters.AddWithValue("@id", categoria.Id);
                    insertCommand.Parameters.AddWithValue("@nombre", categoria.Nombre);
                    insertCommand.Parameters.AddWithValue("@descripcion", categoria.Descripcion);
                    int rowsAffected = insertCommand.ExecuteNonQuery();//Se ejecuta el comando y se obtiene el número de filas afectadas
                    return rowsAffected > 0;// Si se han insertado filas correctamente, retorna 'true', indicando éxito
                }
            }
        }

        //Método para consultar las categorías registradas en la base de datos
        public List<Categoria> ConsultarCategorias()
        {
            List<Categoria> categorias = new List<Categoria>();//Se crea una lista de categorías
            // Se establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL para seleccionar las categorías: id, nombre y descripción
                string query = "SELECT id, nombre, descripcion FROM Categoria";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Ejecuta la consulta y obtiene un SqlDataReader para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            Categoria categoria = new Categoria//Se crea un objeto de la clase categoría 
                            {    // Se convierte el valor de la columna 'id' a un tipo entero, ya que en la base de datos es de tipo decima
                                // Se asignan los valores de las columnas 'nombre' y 'descripcion'
                                Id = Convert.ToInt32(reader.GetDecimal(0)),  
                                Nombre = reader.GetString(1),
                                Descripcion = reader.GetString(2)
                            };
                            categorias.Add(categoria);//Se añade la categoría a la lista
                        }
                    }
                }
            }
            return categorias;//Se retorna la lista de categorías
        }
        //Método para registrar un artículo en la base de datos
        public bool RegistrarArticulo(Articulo articulo)
        {// Establece una conexión con la base de datos usando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Verifica si el artículo con el mismo ID, descripción y marca ya existe en la base de datos
                string checkQuery = "SELECT COUNT(*) FROM Articulo WHERE id = @id OR (descripcion = @descripcion AND marca = @marca)";
                using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))
                {// Se añaden los parámetros a la consulta SQL 
                    checkCommand.Parameters.AddWithValue("@id", articulo.Id);
                    checkCommand.Parameters.AddWithValue("@descripcion", articulo.Descripcion);
                    checkCommand.Parameters.AddWithValue("@marca", articulo.Marca);
                    int count = (int)checkCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (count > 0)// Si existe algún artículo con los mismos valores, retorna falso
                    {
                        return false; 
                    }
                }
                 // Si no existe, se procede a insertar el nuevo artículo en la base de datos
                string insertQuery = "INSERT INTO Articulo (id, descripcion, id_categoria, marca, activo) " +
                                     "VALUES (@id, @descripcion, @id_categoria, @marca, @activo)";
                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {// Se asignan los parámetros de la consulta de inserción
                    insertCommand.Parameters.AddWithValue("@id", articulo.Id);
                    insertCommand.Parameters.AddWithValue("@descripcion", articulo.Descripcion);
                    insertCommand.Parameters.AddWithValue("@id_categoria", articulo.Categoria.Id);
                    insertCommand.Parameters.AddWithValue("@marca", articulo.Marca);
                    insertCommand.Parameters.AddWithValue("@activo", articulo.Activo);
                    int rowsAffected = insertCommand.ExecuteNonQuery();// Ejecuta la inserción y verifica si se insertaron filas correctamente
                    return rowsAffected > 0;// Si se insertaron filas correctamente, retorna 'true'
                }
            }
        }

        //Método para consultar los artículos registrados en la base de datos
        public List<Articulo> ConsultarArticulos()
        {   // Crea una lista para almacenar los artículos obtenidos
            List<Articulo> articulos = new List<Articulo>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL que obtiene los artículos con su categoría asociada
                string query = "SELECT a.id, a.descripcion, a.marca, a.activo, c.id AS CategoriaId, c.nombre AS CategoriaNombre, c.descripcion AS CategoriaDescripcion " +
                               "FROM Articulo a " +
                               "JOIN Categoria c ON a.id_categoria = c.id";
                using (SqlCommand command = new SqlCommand(query, connection))
                {    // Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())// Lee cada fila del resultado de la consulta
                        {
                            Articulo articulo = new Articulo// Crea un nuevo objeto Articulo y asigna los valores leídos desde la base de dato
                            {
                                Id = Convert.ToInt32(reader["id"]),// Convierte el valor del 'id' a entero
                                Descripcion = reader["descripcion"].ToString(),
                                Marca = reader["marca"].ToString(),
                                Activo = Convert.ToBoolean(reader["activo"]),// Convierte el valor 'activo' a booleano
                                Categoria = new Categoria
                                {
                                    Id = Convert.ToInt32(reader["CategoriaId"]),
                                    Nombre = reader["CategoriaNombre"].ToString(),
                                    Descripcion = reader["CategoriaDescripcion"].ToString()
                                }
                            };
                            articulos.Add(articulo);// Agrega el artículo a la lista
                        }
                    }
                }
            }
            return articulos;// Retorna la lista de artículos
        }
        //Método para registrar un administrador en la base de datos
        public bool RegistrarAdministrador(Administrador administrador)
        {    // Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL para verificar si el administrador ya existe, basado en la identificación o en los apellidos y nombre
                string checkQuery = @"
            SELECT COUNT(*) FROM Administrador 
            WHERE identificacion = @identificacion 
               OR (nombre = @nombre AND apellido1 = @apellido1 AND apellido2 = @apellido2)";

                using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))
                {// Se añaden los parámetros a la consulta SQL
                    checkCommand.Parameters.AddWithValue("@identificacion", administrador.Identificacion);
                    checkCommand.Parameters.AddWithValue("@nombre", administrador.Nombre);
                    checkCommand.Parameters.AddWithValue("@apellido1", administrador.Apellido1);
                    checkCommand.Parameters.AddWithValue("@apellido2", administrador.Apellido2);
                    
                    int count = (int)checkCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (count > 0)
                    {
                        return false; 
                    }
                }
                // Si no existe, se crea una consulta para insertar un nuevo administrador
                string insertQuery = "INSERT INTO Administrador (identificacion, nombre, apellido1, apellido2, fec_nacimiento, fec_ingreso) " +
                                     "VALUES (@identificacion, @nombre, @apellido1, @apellido2, @fec_nacimiento, @fec_ingreso)";

                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {// Se asignan los parámetros para la consulta de inserción
                    insertCommand.Parameters.AddWithValue("@identificacion", administrador.Identificacion);
                    insertCommand.Parameters.AddWithValue("@nombre", administrador.Nombre);
                    insertCommand.Parameters.AddWithValue("@apellido1", administrador.Apellido1);
                    insertCommand.Parameters.AddWithValue("@apellido2", administrador.Apellido2);
                    insertCommand.Parameters.AddWithValue("@fec_nacimiento", administrador.FechaNacimiento);
                    insertCommand.Parameters.AddWithValue("@fec_ingreso", administrador.FechaIngreso);
                    // Ejecuta la consulta de inserción y verifica si se insertaron filas correctamente
                    int rowsAffected = insertCommand.ExecuteNonQuery();
                    return rowsAffected > 0;// Si se insertaron filas correctamente, retorna 'true'
                }
            }
        }


        //Método para consultar los administradores registrados en la base de datos
        public List<Administrador> ConsultarAdministradores()
        { // Crea una lista para almacenar los administradores obtenidos.
            List<Administrador> administradores = new List<Administrador>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL que obtiene los administradores desde la base de datos
                string query = "SELECT identificacion, nombre, apellido1, apellido2, fec_nacimiento, fec_ingreso FROM Administrador";
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read()) // Lee cada fila del resultado de la consulta
                        {// Crea un nuevo objeto Administrador y asigna los valores leídos desde la base de datos
                            Administrador administrador = new Administrador
                            {
                                Identificacion = Convert.ToInt32(reader["identificacion"]),// Convierte el valor de 'identificacion' a entero
                                Nombre = reader["nombre"].ToString(),
                                Apellido1 = reader["apellido1"].ToString(),
                                Apellido2 = reader["apellido2"].ToString(),
                                FechaNacimiento = Convert.ToDateTime(reader["fec_nacimiento"]),// Convierte el valor de 'fec_nacimiento' a fecha
                                FechaIngreso = Convert.ToDateTime(reader["fec_ingreso"])// Convierte el valor de 'fec_ingreso' a fecha
                            };
                            administradores.Add(administrador);// Agrega el administrador a la lista
                        }
                    }
                }
            }
            return administradores;// Retorna la lista de administradores
        }
        //Método para registrar una sucursal en la base de datos
        public bool RegistrarSucursal(Sucursal sucursal)
        {// Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta para verificar si ya existe una sucursal con el mismo ID
                string checkIdQuery = "SELECT COUNT(*) FROM Sucursal WHERE id = @id";
                using (SqlCommand checkIdCommand = new SqlCommand(checkIdQuery, connection))
                {    // Se agrega el parámetro para la consulta de ID
                    checkIdCommand.Parameters.AddWithValue("@id", sucursal.Id);
                    int countId = (int)checkIdCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (countId > 0)
                    {
                        return false;// Si ya existe una sucursal con el mismo ID, retorna falso
                    }
                }
                // Consulta para verificar si ya existe una sucursal con el mismo nombre
                string checkNameQuery = "SELECT COUNT(*) FROM Sucursal WHERE nombre = @nombre";
                using (SqlCommand checkNameCommand = new SqlCommand(checkNameQuery, connection))
                {// Se agrega el parámetro para la consulta de nombre
                    checkNameCommand.Parameters.AddWithValue("@nombre", sucursal.Nombre);
                    int countName = (int)checkNameCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias con ese nombre
                    if (countName > 0)
                    {
                        return false;// Si ya existe una sucursal con el mismo nombre, retorna falso
                    }
                }
                // Si no existe ni una sucursal con el mismo ID ni con el mismo nombre, se procede a insertar la nueva sucursal
                string insertQuery = "INSERT INTO Sucursal (id, nombre, id_administrador, direccion, telefono, activo) " +
                                     "VALUES (@id, @nombre, @id_administrador, @direccion, @telefono, @activo)";
                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {// Se asignan los parámetros para la consulta de inserción
                    insertCommand.Parameters.AddWithValue("@id", sucursal.Id);
                    insertCommand.Parameters.AddWithValue("@nombre", sucursal.Nombre);
                    insertCommand.Parameters.AddWithValue("@id_administrador", sucursal.Administrador.Identificacion);// Se asigna el identificador del administrado
                    insertCommand.Parameters.AddWithValue("@direccion", sucursal.Direccion);
                    insertCommand.Parameters.AddWithValue("@telefono", sucursal.Telefono);
                    insertCommand.Parameters.AddWithValue("@activo", sucursal.Activo);// Indica si la sucursal está activa o no
                    // Ejecuta la consulta de inserción y verifica si se insertaron filas correctamente
                    int rowsAffected = insertCommand.ExecuteNonQuery();
                    return rowsAffected > 0;// Si se insertaron filas correctamente, retorna 'true'
                }
            }
        }


        //Método para consultar las sucursales registradas en la base de datos
        public List<Sucursal> ConsultarSucursales()
        {// Crea una lista para almacenar las sucursales obtenidas
            List<Sucursal> sucursales = new List<Sucursal>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL que obtiene las sucursales junto con los datos de su administrador
                string query = "SELECT s.id, s.nombre, s.direccion, s.telefono, s.activo, " +
                               "a.identificacion AS AdministradorId, a.nombre AS AdministradorNombre, a.apellido1, a.apellido2 " +
                               "FROM Sucursal s " +
                               "JOIN Administrador a ON s.id_administrador = a.identificacion";
                using (SqlCommand command = new SqlCommand(query, connection))
                {   // Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())// Lee cada fila del resultado de la consulta
                        {
                            Sucursal sucursal = new Sucursal// Crea un nuevo objeto Sucursal y asigna los valores leídos desde la base de datos
                            {
                                Id = Convert.ToInt32(reader["id"]),// Convierte el valor de 'id' a entero
                                Nombre = reader["nombre"].ToString(),
                                Direccion = reader["direccion"].ToString(),
                                Telefono = reader["telefono"].ToString(),
                                Activo = Convert.ToBoolean(reader["activo"]),// Convierte el valor de 'activo' a booleano
                                Administrador = new Administrador// Crea un objeto Administrador relacionado con esta sucursal
                                {
                                    Identificacion = Convert.ToInt32(reader["AdministradorId"]),
                                    Nombre = reader["AdministradorNombre"].ToString(),
                                    Apellido1 = reader["apellido1"].ToString(),
                                    Apellido2 = reader["apellido2"].ToString()
                                }
                            };
                            sucursales.Add(sucursal);// Agrega la sucursal a la lista
                        }
                    }
                }
            }
            return sucursales;// Retorna la lista de sucursaless
        }
        //Método para registrar un cliente en la base de datos
        public bool RegistrarCliente(Cliente cliente)
        {// Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta para verificar si ya existe un cliente con la misma identificación
                string checkIdQuery = "SELECT COUNT(*) FROM Cliente WHERE identificacion = @identificacion";
                using (SqlCommand checkIdCommand = new SqlCommand(checkIdQuery, connection))
                {// Se añade el parámetro para la consulta de identificación
                    checkIdCommand.Parameters.AddWithValue("@identificacion", cliente.Identificacion);
                    int countId = (int)checkIdCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (countId > 0)
                    {
                        return false;// Si ya existe un cliente con la misma identificación, retorna falso
                    }
                }
                // Consulta para verificar si ya existe un cliente con el mismo nombre y apellidos
                string checkNameQuery = "SELECT COUNT(*) FROM Cliente WHERE nombre = @nombre AND apellido1 = @apellido1 AND apellido2 = @apellido2";
                // Se crea un comando SQL para ejecutar la consulta de nombre y apellidos
                using (SqlCommand checkNameCommand = new SqlCommand(checkNameQuery, connection))
                { // Se añaden los parámetros para la consulta de nombre y apellidos
                    checkNameCommand.Parameters.AddWithValue("@nombre", cliente.Nombre);
                    checkNameCommand.Parameters.AddWithValue("@apellido1", cliente.Apellido1);
                    checkNameCommand.Parameters.AddWithValue("@apellido2", cliente.Apellido2);
                    int countName = (int)checkNameCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (countName > 0)
                    {
                        return false;// Si ya existe un cliente con el mismo nombre y apellidos, retorna falso
                    }
                }
                // Si no existe un cliente con la misma identificación, nombre y apellidos, se procede a insertar el nuevo cliente
                string insertQuery = "INSERT INTO Cliente (identificacion, nombre, apellido1, apellido2, fec_nacimiento, activo) " +
                                     "VALUES (@identificacion, @nombre, @apellido1, @apellido2, @fec_nacimiento, @activo)";
                // Se crea un comando SQL para ejecutar la consulta de inserción
                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {// Se añaden los parámetros para la consulta de inserción
                    insertCommand.Parameters.AddWithValue("@identificacion", cliente.Identificacion);
                    insertCommand.Parameters.AddWithValue("@nombre", cliente.Nombre);
                    insertCommand.Parameters.AddWithValue("@apellido1", cliente.Apellido1);
                    insertCommand.Parameters.AddWithValue("@apellido2", cliente.Apellido2);
                    insertCommand.Parameters.AddWithValue("@fec_nacimiento", cliente.FechaNacimiento);
                    insertCommand.Parameters.AddWithValue("@activo", cliente.Activo);
                    int rowsAffected = insertCommand.ExecuteNonQuery();// Ejecuta la consulta de inserción y verifica si se insertaron filas correctamente
                    return rowsAffected > 0;
                }
            }
        }


        //Método para consultar los clientes registrados en la base de datos
        public List<Cliente> ConsultarClientes()
        {// Crea una lista para almacenar los clientes obtenidos
            List<Cliente> clientes = new List<Cliente>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL que obtiene los clientes desde la base de datos
                string query = "SELECT identificacion, nombre, apellido1, apellido2, fec_nacimiento, activo FROM Cliente";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {    // Lee cada fila del resultado de la consulta
                        while (reader.Read())
                        {// Crea un nuevo objeto Cliente y asigna los valores leídos desde la base de datos
                            Cliente cliente = new Cliente
                            {
                                Identificacion = Convert.ToInt32(reader["identificacion"]),// Convierte el valor de 'identificacion' a entero
                                Nombre = reader["nombre"].ToString(),
                                Apellido1 = reader["apellido1"].ToString(),
                                Apellido2 = reader["apellido2"].ToString(),
                                FechaNacimiento = Convert.ToDateTime(reader["fec_nacimiento"]),// Convierte el valor de 'fec_nacimiento' a fecha
                                Activo = Convert.ToBoolean(reader["activo"])// Convierte el valor de 'activo' a booleano
                            };
                            clientes.Add(cliente);// Agrega el cliente a la lista
                        }
                    }
                }
            }
            return clientes;// Retorna la lista de clientes
        }
        //Método para registrar un artículo por sucursal en la base de datos
        public bool RegistrarArticulosPorSucursal(ArticuloSucursal articuloSucursal)
        {// Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta para verificar si ya existe un artículo por sucursal con el mismo ID de sucursal y de artículo
                string checkQuery = "SELECT COUNT(*) FROM ArticuloxSucursal WHERE id_sucursal = @id_sucursal AND id_articulo = @id_articulo";
                // Se crea un comando SQL para ejecutar la consulta de verificación
                using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))
                {// Se añaden los parámetros para la consulta de verificación
                    checkCommand.Parameters.AddWithValue("@id_sucursal", articuloSucursal.Sucursal.Id);
                    checkCommand.Parameters.AddWithValue("@id_articulo", articuloSucursal.Articulo.Id);
                    int count = (int)checkCommand.ExecuteScalar();// Ejecuta la consulta y obtiene el número de coincidencias
                    if (count > 0)
                    {
                        return false;// Si ya existe un artículo por sucursal con el mismo ID de sucursal y de artículo, retorna falso
                    }
                }
                      // Si no existe un artículo por sucursal con el mismo ID de sucursal y de artículo, se procede a insertar el nuevo artículo por sucursal
                string insertQuery = "INSERT INTO ArticuloxSucursal (id_sucursal, id_articulo, cantidad) VALUES (@id_sucursal, @id_articulo, @cantidad)";
                     // Se crea un comando SQL para ejecutar la consulta de inserción
                using (SqlCommand insertCommand = new SqlCommand(insertQuery, connection))
                {// Se añaden los parámetros para la consulta de inserción
                    insertCommand.Parameters.AddWithValue("@id_sucursal", articuloSucursal.Sucursal.Id);
                    insertCommand.Parameters.AddWithValue("@id_articulo", articuloSucursal.Articulo.Id);
                    insertCommand.Parameters.AddWithValue("@cantidad", articuloSucursal.Cantidad);
                    int rowsAffected = insertCommand.ExecuteNonQuery();// Ejecuta la consulta de inserción y verifica si se insertaron filas correctamente
                    return rowsAffected > 0;
                }
            }
        }

        //Método para consultar los artículos por sucursal registrados en la base de datos
        public List<ArticuloSucursal> ConsultarArticulosPorSucursal()
        {// Crea una lista para almacenar los artículos por sucursal obtenidos
            List<ArticuloSucursal> articulosPorSucursal = new List<ArticuloSucursal>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL que obtiene los artículos por sucursal desde la base de datos
                string query = "SELECT s.id AS SucursalId, s.nombre AS SucursalNombre, s.direccion, s.telefono, " +
                               "a.id AS ArticuloId, a.descripcion, c.nombre AS CategoriaNombre, a.marca, axs.cantidad " +
                               "FROM ArticuloxSucursal axs " +
                               "JOIN Sucursal s ON axs.id_sucursal = s.id " +
                               "JOIN Articulo a ON axs.id_articulo = a.id " +
                               "JOIN Categoria c ON a.id_categoria = c.id";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())// Lee cada fila del resultado de la consulta
                        {// Crea un nuevo objeto ArticuloSucursal y asigna los valores leídos desde la base de datos
                            ArticuloSucursal articuloSucursal = new ArticuloSucursal
                            {
                                Sucursal = new Sucursal//Crea un objeto Surcusal relacionado con este articulo por surcusal
                                {
                                    Id = Convert.ToInt32(reader["SucursalId"]),// Convierte el valor de 'SucursalId' a entero
                                    Nombre = reader["SucursalNombre"].ToString(),//
                                    Direccion = reader["direccion"].ToString(),
                                    Telefono = reader["telefono"].ToString(),
                                    Activo = true// Indica si la sucursal está activa o no
                                },
                                Articulo = new Articulo//Crea un objeto Articulo relacionado con este articulo por surcusal
                                {
                                    Id = Convert.ToInt32(reader["ArticuloId"]),// Convierte el valor de 'ArticuloId' a entero
                                    Descripcion = reader["descripcion"].ToString(),
                                    Categoria = new Categoria//Crea un objeto Categoria relacionado con este articulo por surcusal
                                    {
                                        Nombre = reader["CategoriaNombre"].ToString()// Obtiene el nombre de la categoría
                                    },
                                    Marca = reader["marca"].ToString(),// Obtiene la marca del artículo
                                    Activo = true// Indica si el artículo está activo o no
                                },
                                Cantidad = Convert.ToInt32(reader["cantidad"])// Convierte el valor de 'cantidad' a entero
                            };
                            articulosPorSucursal.Add(articuloSucursal);// Agrega el artículo por sucursal a la lista
                        }
                    }
                }
            }
            return articulosPorSucursal;// Retorna la lista de artículos por sucursal
        }
        //Método para registrar una reserva en la base de datos
        public string ValidarCliente(string identificacion)
        {// Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta para verificar si el cliente existe y está activo
                string query = "SELECT nombre, apellido1, apellido2 FROM Cliente WHERE identificacion = @identificacion AND activo = 1";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Se añade el parámetro para la consulta
                    command.Parameters.AddWithValue("@identificacion", identificacion);
                    using (SqlDataReader reader = command.ExecuteReader())// Ejecuta la consulta y obtiene un lector para leer los resultados
                    { // Si se encuentra un cliente que coincide con la identificación y está activ
                        if (reader.Read())//
                        {// Combina el nombre, primer apellido y segundo apellido en una cadena
                            string nombreCompleto = $"{reader["nombre"]} {reader["apellido1"]} {reader["apellido2"]}";
                            return nombreCompleto;
                        }
                    }
                }
            }
            return null;// Si no se encuentra un cliente que coincide con la identificación y está activo, retorna nulo
        }
        //Método para registrar una reserva en la base de datos
        public List<Sucursal> ObtenerSucursalesActivas()
        {// Crea una lista para almacenar las sucursales activas
            List<Sucursal> sucursales = new List<Sucursal>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL para obtener todas las sucursales activas (activo = 1)
                string query = "SELECT id, nombre, direccion, telefono FROM Sucursal WHERE activo = 1";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                { // Ejecuta la consulta utilizando un comando SQL
                    using (SqlDataReader reader = command.ExecuteReader())
                    { // Recorre cada fila del resultado y agrega las sucursales activas a la lista
                        while (reader.Read())
                        {
                            sucursales.Add(new Sucursal
                            {
                                Id = Convert.ToInt32(reader["id"]),// Convierte el valor de 'id' a entero
                                Nombre = reader["nombre"].ToString(),//
                                Direccion = reader["direccion"].ToString(),
                                Telefono = reader["telefono"].ToString(),
                                Activo = true// Indica si la sucursal está activa o no
                            });
                        }
                    }
                }
            }
            return sucursales;// Retorna la lista de sucursales activas
        }
        //Método para obtener los artículos disponibles en una sucursal
        public List<ArticuloSucursal> ObtenerArticulosDisponibles(int idSucursal)
        {// Crea una lista para almacenar los artículos disponibles en la sucursal
            List<ArticuloSucursal> articulos = new List<ArticuloSucursal>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL para obtener los artículos disponibles en la sucursal específica
                string query = @"SELECT a.id, a.descripcion, a.marca, axs.cantidad 
                         FROM ArticuloxSucursal axs 
                         JOIN Articulo a ON axs.id_articulo = a.id 
                         WHERE axs.id_sucursal = @idSucursal AND axs.cantidad > 0";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Se añade el parámetro para la consulta
                    command.Parameters.AddWithValue("@idSucursal", idSucursal);
                    // Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())// Recorre cada fila del resultado y agrega los artículos disponibles a la lista
                        {
                            articulos.Add(new ArticuloSucursal
                            {
                                Articulo = new Articulo
                                {
                                    Id = Convert.ToInt32(reader["id"]),// Convierte el valor de 'id' a entero
                                    Descripcion = reader["descripcion"].ToString(),//
                                    Marca = reader["marca"].ToString(),
                                    Activo = true// Indica si el artículo está activo o no
                                },
                                Cantidad = Convert.ToInt32(reader["cantidad"])// Convierte el valor de 'cantidad' a entero
                            });
                        }
                    }
                }
            }
            return articulos;// Retorna la lista de artículos disponibles en la sucursal
        }
        //Método para registrar una reserva en la base de datos
        public bool RegistrarReserva(int idSucursal, int idArticulo, int identificacionCliente, DateTime fechaReserva)
        {// Establece una conexión con la base de datos utilizando la cadena de conexión
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Inicia una transacción para garantizar la integridad de los datos
                using (SqlTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {// Consulta para obtener el siguiente ID disponible para una nueva reserva
                        string getMaxIdQuery = "SELECT ISNULL(MAX(id), 0) + 1 FROM Reserva";
                        int newId;// Se crea una variable para almacenar el nuevo ID de la reserva
                        // Se crea un comando SQL para ejecutar la consulta
                        using (SqlCommand getMaxIdCommand = new SqlCommand(getMaxIdQuery, connection, transaction))
                        {// Ejecuta la consulta y obtiene el siguiente ID disponible
                            newId = Convert.ToInt32(getMaxIdCommand.ExecuteScalar());
                        }
                        // Consulta para verificar si hay suficiente cantidad del artículo en la sucursal
                        string checkQuery = "SELECT cantidad FROM ArticuloxSucursal WHERE id_sucursal = @idSucursal AND id_articulo = @idArticulo";
                        // Se crea un comando SQL para ejecutar la consulta
                        using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection, transaction))
                        {// Se añaden los parámetros para la consulta
                            checkCommand.Parameters.AddWithValue("@idSucursal", idSucursal);
                            checkCommand.Parameters.AddWithValue("@idArticulo", idArticulo);

                            int cantidadDisponible = Convert.ToInt32(checkCommand.ExecuteScalar());// Ejecuta la consulta y obtiene la cantidad disponible
                            if (cantidadDisponible < 1)
                            {
                                return false;// Si no hay suficiente cantidad del artículo en la sucursal, retorna falso
                            }
                        }
                        // Consulta para insertar la nueva reserva en la base de datos
                        string insertReservaQuery = "INSERT INTO Reserva (id, id_sucursal, id_articulo, id_cliente, fec_reserva) VALUES (@id, @idSucursal, @idArticulo, @idCliente, @fechaReserva)";
                        // Se crea un comando SQL para ejecutar la consulta de inserción
                        using (SqlCommand insertCommand = new SqlCommand(insertReservaQuery, connection, transaction))
                        {// Se añaden los parámetros para la consulta de inserción
                            insertCommand.Parameters.Add("@id", SqlDbType.Int).Value = newId;// Se asigna el nuevo ID de la reserva
                            insertCommand.Parameters.Add("@idSucursal", SqlDbType.Int).Value = idSucursal;// Se asigna el ID de la sucursal
                            insertCommand.Parameters.Add("@idArticulo", SqlDbType.Int).Value = idArticulo;// Se asigna el ID del artículo
                            insertCommand.Parameters.Add("@idCliente", SqlDbType.BigInt).Value = identificacionCliente;// Se asigna la identificación del cliente
                            insertCommand.Parameters.Add("@fechaReserva", SqlDbType.DateTime).Value = fechaReserva;// Se asigna la fecha de la reserva
                            insertCommand.ExecuteNonQuery();// Ejecuta la consulta de inserción
                        }
                        // Consulta para actualizar la cantidad del artículo en la sucursal
                        string updateCantidadQuery = "UPDATE ArticuloxSucursal SET cantidad = cantidad - 1 WHERE id_sucursal = @idSucursal AND id_articulo = @idArticulo";
                        // Se crea un comando SQL para ejecutar la consulta de actualización
                        using (SqlCommand updateCommand = new SqlCommand(updateCantidadQuery, connection, transaction))
                        {// Se añaden los parámetros para la consulta de actualización
                            updateCommand.Parameters.AddWithValue("@idSucursal", idSucursal);
                            updateCommand.Parameters.AddWithValue("@idArticulo", idArticulo);
                            updateCommand.ExecuteNonQuery();// Ejecuta la consulta de actualización
                        }

                        transaction.Commit();// Confirma la transacción
                        return true; // Retorna verdadero si la reserva se registró correctamente
                    }
                    catch (Exception )
                    {
                        transaction.Rollback();// Cancela la transacción


                        return false;// Retorna falso si ocurrió un error al registrar la reserva
                        
                        //Probar: throw new InvalidOperationException("Ocurrió un error al registrar la reserva: " + ex.Message);

                    }
                }
            }
        }
        //Método para consultar las reservas de un cliente
        public List<Reserva> ConsultarReservasPorCliente(int identificacionCliente)
        {// Crea una lista para almacenar las reservas del cliente
            List<Reserva> reservas = new List<Reserva>();
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Consulta SQL para obtener las reservas del cliente, uniendo las tablas Reserva, Sucursal y Articulo
                string query = @"
            SELECT r.id, s.nombre AS Sucursal, a.descripcion AS Articulo, r.fec_reserva 
            FROM Reserva r
            JOIN Sucursal s ON r.id_sucursal = s.id
            JOIN Articulo a ON r.id_articulo = a.id
            WHERE r.id_cliente = @idCliente";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Añadir el parámetro para filtrar por el idCliente
                    command.Parameters.Add("@idCliente", SqlDbType.BigInt).Value = identificacionCliente;
                    // Ejecuta la consulta y obtiene un lector para leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {// Agregar una nueva reserva a la lista con los datos obtenidos
                            reservas.Add(new Reserva
                            {
                                Id = Convert.ToInt32(reader["id"]),// Convierte el valor de 'id' a entero
                                Sucursal = reader["Sucursal"].ToString(),
                                Articulo = reader["Articulo"].ToString(),
                                FechaReserva = Convert.ToDateTime(reader["fec_reserva"])// Convierte el valor de 'fec_reserva' a fecha
                            });
                        }
                    }
                }
            }
            return reservas;// Retorna la lista de reservas del cliente
        }
        //Método para consultar una reserva por su ID
        public Reserva ConsultarReservaPorId(int identificacionCliente, int idReserva)
        {// Crea un objeto Reserva para almacenar la reserva obtenida
            Reserva reserva = null;
            // Establece una conexión con la base de datos
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();
                // Añadir los parámetros para filtrar por el idCliente y el idReserva
                string query = @"
            SELECT r.id, s.nombre AS Sucursal, a.descripcion AS Articulo, r.fec_reserva 
            FROM Reserva r
            JOIN Sucursal s ON r.id_sucursal = s.id
            JOIN Articulo a ON r.id_articulo = a.id
            WHERE r.id_cliente = @idCliente AND r.id = @idReserva";
                // Se crea un comando SQL para ejecutar la consulta
                using (SqlCommand command = new SqlCommand(query, connection))
                {// Se añaden los parámetros para la consulta
                    command.Parameters.Add("@idCliente", SqlDbType.BigInt).Value = identificacionCliente;
                    command.Parameters.Add("@idReserva", SqlDbType.Int).Value = idReserva;
                    // Ejecutar la consulta y leer los resultados
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())// Si se encuentra la reserva, asignarla al objeto
                        {
                            reserva = new Reserva
                            {
                                Id = Convert.ToInt32(reader["id"]),// Convierte el valor de 'id' a entero
                                Sucursal = reader["Sucursal"].ToString(),
                                Articulo = reader["Articulo"].ToString(),
                                FechaReserva = Convert.ToDateTime(reader["fec_reserva"])// Convierte el valor de 'fec_reserva' a fecha
                            };
                        }
                    }
                }
            }
            return reserva;// Retorna la reserva obtenida
        }
    }
}
