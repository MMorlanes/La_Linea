const express = require("express");
const cors = require("cors");
const app = express(); 
const PORT = 3000;


const { Pool } = require("pg"); 

const myPool = new Pool({
  user: "postgres",
  host: "postgres.ckp1nbi0hk9f.us-east-1.rds.amazonaws.com",
  database: "postgres",
  password: "Oscar1234.", 
  port: 5432,
  ssl: {
  rejectUnauthorized: false, // Cambia a false si tienes problemas de certificados pero trata de evitarlo por seguridad
  },
});

app.use(cors());



// Iniciar el servidor
app.use(express.json());

app.listen(PORT, () => {
  console.log("Servidor funcionandoooo!!!!");
  console.log(`El servidor está escuchando en http://localhost:${PORT}`);
});

app.post('/login', async (req, res) => {
  const { username, password } = req.body;
  console.log(req.body)

  try {
      const user = await myPool.query('SELECT id_usuario, username FROM Usuarios WHERE username = $1 AND password = $2', [username, password]);

      if (user.rows.length === 1) {
        console.log("sesion iniciada");
          // Usuario autenticado correctamente
          res.status(200).json({ message: 'Inicio de sesión exitoso', user: user.rows[0] });
      } else {
          // Credenciales incorrectas
          res.status(401).json({ error: 'Credenciales incorrectas' });
      }

  } catch (error) {
      console.error('Error al iniciar sesión:', error);
      res.status(500).json({ error: 'Error interno del servidor' });
  }
});

// Ruta para registrar un nuevo usuario
app.post('/register', async (req, res) => {
  const { username, password } = req.body;

  try {
      // Verificar si el usuario ya existe
      const existingUser = await myPool.query('SELECT * FROM Usuarios WHERE username = $1', [username]);

      if (existingUser.rows.length > 0) {
          // Usuario ya existe
          res.status(400).json({ error: 'El usuario ya está registrado' });
      } else {
          // Registrar el nuevo usuario
          await myPool.query('INSERT INTO Usuarios (username, password) VALUES ($1, $2)', [username, password]);
          console.log("usuario registrado");
          res.status(201).json({ message: 'Usuario registrado exitosamente' });
      }

  } catch (error) {
      console.error('Error al registrar usuario:', error);
      res.status(500).json({ error: 'Error interno del servidor' });
}
});


app.get('/peliculas', async (req, res) => {
    try {
      // Realizar la consulta para obtener todas las películas
      const peliculas = await myPool.query('SELECT titulo, descripcion FROM Peliculas');
      
      // Verificar si existen películas en la base de datos
      if (peliculas.rows.length === 0) {
        return res.status(404).json({ message: 'No se encontraron películas' });
      }
      
      // Enviar la respuesta con las películas
      res.status(200).json(peliculas.rows);
    } catch (error) {
      console.error('Error al obtener las películas:', error);
      res.status(500).json({ error: 'Error interno del servidor' });
    }
  });
  

  // Ruta para actualizar el nombre de una película
app.put('/peliculas/:id', async (req, res) => {
  const { id } = req.params;
  const { nuevoTitulo } = req.body;

  try {
    // Verificar si la película existe
    const peliculaExistente = await myPool.query('SELECT * FROM Peliculas WHERE id_pelicula = $1', [id]);

    if (peliculaExistente.rows.length === 0) {
      return res.status(404).json({ message: 'La película no fue encontrada' });
    }

    // Actualizar el título de la película
    await myPool.query('UPDATE Peliculas SET titulo = $1 WHERE id_pelicula = $2', [nuevoTitulo, id]);
    
    console.log(`Película con id ${id} actualizada con éxito`);
    res.status(200).json({ message: 'Película actualizada exitosamente' });
  } catch (error) {
    console.error('Error al actualizar la película:', error);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});




