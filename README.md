# Pokemon API
API que consume la pokeApi para listar los pokemons y mostrar detalle de cada uno.
# Despliegue en render
- Render: https://render.com/
- Servicio: Web Service con Dockerfile personalizado
- Al crear un nuevo servicio Render detecta el Dockerfile automáticamente y hace el build

# URL del servicio
https://desafio-14st.onrender.com

# Endpoints
### Obtener listado de pokemons
```http
GET /api/pokemon?page=1&size=10
```
### curl para probar en Postman
```
curl --location 'https://desafio-14st.onrender.com/api/pokemon?page=1&size=10'
```

### Obtener detalle de un Pokémon
```http
GET /api/pokemon/{name}
```
### curl para probar en Postman
```
curl --location 'https://desafio-14st.onrender.com/api/pokemon/metapod'
```
