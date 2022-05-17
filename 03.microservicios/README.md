# MICROSERVICIOS CON SPRING CLOUD
Este es el proyecto que contiene todo el código generado durante la sección Microservicios con Spring Cloud.

En esta sección se desarrollará un diversos microservicios que servirán para ilustrar la estructura de una aplicación compuesta de microservicios. La aplicación en sí proporcionará un servicio de cambio de divisas haciendo uso de distintos microservicios. 

Qué aprenderemos
- Estructura básica de una aplicación con microservicios
- Establecer comunicación entre microservicios
- Crear un servicio de configuración centralizada
- Usar el cliente Feign
- Implementar un balanceador de carga (Ribbon)
- Implementar escalado dinámico usando un servidor de nombres (Eureka) y el balanceador de carga
- Implementar un API Gateway (Zuul)
- Hacer uso del trazado distribuido usando Sleuth y Zipkin
- Implementar tolerancia a fallos con Zipkin
- Usar Spring Cloud Bus para propagar mensajes entre todas los microservicios

A continuación puedes encontrar información relativa al desarrollo de la sección.


1. [Puertos](ports)
2. [URLs](urls)
3. [Instalación de Zipkin](zipkin) 
4. [Utilidades](utilidades)

[id]:ports
## Puertos

|     Aplicacion       |     Puerto          |
| ------------- | ------------- |
| Servicio de límites | 8080, 8081, ... |
| Spring Cloud Config Server | 8888 |
|  |  |
| Servicio de intercambio de moneda | 8000, 8001, 8002, ..  |
| Servicio de conversión de moneda | 8100, 8101, 8102, ... |
| Netflix Eureka Naming Server | 8761 |
| Netflix Zuul API Gateway Server | 8765 |
| Zipkin Distributed Tracing Server | 9411 |

[id]:urls
## URLs

|     Application       |     URL          |
| ------------- | ------------- |
| Limits Service | http://localhost:8080/limits POST -> http://localhost:8080/actuator/refresh|
|Spring Cloud Config Server| http://localhost:8888/limits-service/default http://localhost:8888/limits-service/dev |
|  Servicio de conversion de moneda| http://localhost:8100/currency-converter/from/USD/to/EUR/quantity/10|
| Servicio de intercambio de moneda | http://localhost:8000/currency-exchange/from/EUR/to/INR http://localhost:8001/currency-exchange/from/USD/to/INR|
| Eureka | http://localhost:8761/|
| Zuul - Intercambio y Conversión | http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/USD http://localhost:8765/currency-conversion-service/currency-converter-feign/from/USD/to/EUR/10|
| Zipkin | http://localhost:9411/zipkin/ |
| Spring Cloud Bus Refresh | http://localhost:8080/bus/refresh |

[id]:zipkin
## Instalacion de Zipkin

Quick Start Page
- https://zipkin.io/pages/quickstart

Descarga Jar
- https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec

Comando a ejecutar
```
RABBIT_URI=amqp://localhost java -jar zipkin-server-2.5.2-exec.jar
```

[id]:utilidades
## Utilidades
### VM Argument

-Dserver.port=8001

### Comandos de git

```
mkdir git-configuration-repo
cd git-configuration-repo/
git init
git add -A
git commit -m "first commit"
```

### Spring Cloud Configuration para el repositorio de configuración

```
spring.cloud.config.failFast=true

```
