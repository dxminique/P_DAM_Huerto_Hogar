# Proyecto HuertoHogar
HuertoHogar – Aplicación Móvil + Microservicios
Integrantes

Dominique Cofré

Luis Inostroza

Descripción general

HuertoHogar es una aplicación móvil desarrollada en Android (Jetpack Compose) que permite a los usuarios visualizar productos del huerto, agregarlos al carrito, realizar compras, registrar usuarios, iniciar sesión, capturar fotos y obtener su ubicación.
La app se conecta a una arquitectura basada en microservicios mediante un API Gateway.

Funcionalidades principales
App móvil

Registro de usuario

Inicio de sesión

Visualización de productos

Carrito de compras

Confirmación de pedidos

Actualización de stock en tiempo real

Acceso a cámara del dispositivo

Obtención de ubicación GPS

Navegación mediante barra inferior (Home, Productos, Carrito, Perfil)

Microservicios

ms-auth: Registro, login y emisión de JWT

ms-productos: CRUD y consulta de productos

ms-pedidos: Creación de pedidos y actualización de stock

API Gateway: Redirección de rutas a los microservicios internos

Arquitectura

Android App (Jetpack Compose + Kotlin)

API Gateway (Spring Cloud Gateway)

Microservicios en Spring Boot:

Auth

Productos

Pedidos

Base de datos: MySQL

Exposición pública mediante ngrok

Endpoints utilizados
Auth (ms-auth)
Método	Endpoint	Descripción
POST	/auth/login	Iniciar sesión
POST	/auth/registro	Crear nuevo usuario
Productos (ms-productos)
Método	Endpoint	Descripción
GET	/productos	Lista todos los productos
GET	/productos/{id}	Obtiene un producto por ID
Pedidos (ms-pedidos)
Método	Endpoint	Descripción
POST	/pedidos/crear	Crear un pedido
GET	/pedidos/cliente/{email}	Ver pedidos por usuario (si aplica)
Externos
Servicio	Uso
Google Play Services – FusedLocationProvider	Obtención de ubicación GPS
Cámara nativa de Android	Captura de fotos
Instrucciones para ejecutar el proyecto
1. Clonar repositorios
   git clone https://github.com/tuusuario/P_DAM_Huerto_Hogar.git
   git clone https://github.com/tuusuario/Microservicio_Auth_PruebaFS2.git
   git clone https://github.com/tuusuario/Microservicio_Producto_PruebaFS2.git
   git clone https://github.com/tuusuario/Microservicio_Pedido_PruebaFS2.git
   git clone https://github.com/tuusuario/Microservicio_Gateway_PruebaFS2.git

2. Configurar MySQL

Crear base de datos:

CREATE DATABASE huertohogar;


Cada microservicio tiene su propio application.properties apuntando a esa BD.

3. Levantar microservicios

Dentro de cada proyecto:

./mvnw spring-boot:run

4. Levantar API Gateway
   ./mvnw spring-boot:run

5. Exponer el Gateway a internet

(para uso desde el celular real)

ngrok http 8085


Copiar la URL generada en la app móvil:

private const val BASE_URL = "https://XXXXX.ngrok-free.app/"

6. Ejecutar Android App

Desde Android Studio:

Abrir proyecto

Conectar celular o usar emulador

Ejecutar “Run App”

APK firmado y archivo .jks

Ubicación del archivo .apk firmado:

/app/release/app-release.apk


Ubicación del archivo .jks:

/app/keystore/huertohogar-release.jks


Configuración utilizada en build.gradle:

signingConfigs {
release {
storeFile file("keystore/huertohogar-release.jks")
storePassword "password_aqui"
keyAlias "huertohogar"
keyPassword "password_aqui"
}
}


(Modificar si tus valores son distintos.)

Código fuente

Los repositorios deben estar públicos:

App móvil Android https://github.com/dxminique/P_DAM_Huerto_Hogar.git

Microservicio Usuarios https://github.com/dxminique/Microservicio_Usuario_PruebaFS2.git

Microservicio Auth https://github.com/dxminique/Microservicio_Auth_PruebaFS2.git

Microservicio Productos https://github.com/dxminique/Microservicio_Producto_PruebaFS2.git

Microservicio Pedidos https://github.com/dxminique/Microservicio_Pedido_PruebaFS2.git

API Gateway https://github.com/dxminique/Microservicio_Getaway_PruebaFS2.git

