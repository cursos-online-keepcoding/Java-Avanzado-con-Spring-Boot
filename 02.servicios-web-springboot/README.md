# SERVICIOS WEB CON SPRING BOOT
Este es el proyecto que contiene todo el código generado durante la sección Servicios Web con Spring Boot del curso Microservicios con Spring Cloud.

En esta sección se desarrollará un Servicio Web de tipo REST full que contendrá dos recursos principales: Héroes y Poderes. 

Qué aprenderemos:
- Qué es un servicio web RESTful
- Como implementar servicios web RESTful con Spring y Spring Boot
- Cómo diseñar basándonos en recurso y usando las operaciones de HTTP (GET, POST y DELETE)
- Cómo implementar manejo de excepciones personalizado para servicios web
- Versionado
- Authenticación básica
- Autenticación con token JWT
- Filtrado dinámico y estático
- Cómo documentar servicios web RESTful
- Validación de datos

A continuación puedes encontrar información relativa al desarrollo de la sección.


1. [Aproximación al diseño del Servicio (REST)](diseño)
2. [JPA](jpa)
3. [Swagger](swagger) 
4. [Actuator](actuator)
5. [Enlaces](enlaces)


[id]:diseño
## 1. Aproximación al diseño del Servicio (REST)
La clave es el concepto de Recurso

### SHIELD DATABASE
#### Héroes
##### Obtener a todos los héroes
```
GET /hero
HTTResponseStatus: 200, 404, 500...
```
##### Insertar un nuevo héroe
```
POST /hero
Body: Información del héroe
HttpResponseStatus: 201, 404, 500...
```
##### Obtener a un héroe en concreto
```
GET /hero/{id} --> /hero/1
HttpResponseStatus: 200, 404, 500...
```
##### Borrar a un héroe
```
DELETE /hero/{id} --> /hero/1
HttpResponseStatus: 200, 202, 404, 500...
```

#### Poderes
##### Obtener todos los poderes de un héroe
```
GET /hero/{id}/power
```
##### Añadir un poder a un héroe
```
POST /hero/{id}/power
Body: Detalles del poder a añadir
```
##### Obtener un poder en concreto de un héroe
```
GET /hero/{id}/power/{powerId}
```

[id]:jpa
## 2. JPA
##### Refactorización para utilizar interfaces.
```java
public interface HeroService {
    List<Hero> findAll();
    Hero findHeroById(int id);
    Hero addHero(Hero hero);
    boolean deleteHero(int id);
}
```
##### Anotación `@Qualifyier
```java
@Qualifier("dao")
```
##### Convertir los modelos en entidades
```java
@Entity
@Id
@GeneratedValue
```
##### Activar la consola h2
```
spring.jpa.show-sql=true
spring.hs.console.enabled=true
```
##### Crear un archivo `data.sql` para insertar valores en la tabla usuario
```oracle-sql
INSERT INTO HERO (id, name, hero_name, birth_date) values (1, 'Peter Parker', 'Spiderman' , current_date)
INSERT INTO HERO (id, name, hero_name, birth_date) values (2, 'Tony Stark', 'Ironman' , current_date)
INSERT INTO HERO (id, name, hero_name, birth_date) values (3, 'Steve Rogers', 'Capitan America' , current_date)
```
##### Crear un nuevo HeroRepository.
```java
@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {
}
```
##### Sustituir HeroDaoService por HeroService

##### En el caso del POST, tenemos que modificar la secuencia de alguna forma. Un workaround podría ser modificar los ids de los objetos insertados por defecto... pero los hay mejores.
##### Crear la clase Power y anotarla con `@Entity`

```java
import com.keepcoding.springboot.model.Hero;

public class Post {
    @ManyToOne(fetch=FetchType.LAZY)
    private Hero hero;
}

public class User {
    @OneToMany(mappedBy="user")
    private List<Power> powers;
}
```
- Crer datos para insertar en la tabla Power.
- Añadir un `@JsonIgnore` al heroe en cada poder.
- Implementar métodos para recuperar todos los poderes de un heroe.
- Implementar método para recuperar un poder en concreto.
- Crear un power repository
- Implementar método para añadar un nuevo post.

````java
public ReponseEntity<Object> createPower(@PathVariable int id, @RequestBody Power power) {
    // Recuperar el usuario por id
    power.setUser(hero);
    postRepository.save(power);
}
````


[id]:swagger
## 3. SWAGGER
### Añadir dependencia Maven
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
```

### Añadiendo el Bean de configuración de Swagger

```
@Configuration
@EnableSwagger2
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
}
```



Ahora, la documentación se encontraría en la siguiente url:

http://localhost:8080/v2/api-docs

Podemos ver un ejemplo de la ui en 

https://editor.swagger.io/

### Swagger UI

Añadir la dependecia maven
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

Ahora, Swagger UI se encuentra en la url:

http://localhost:8080/swagger-ui.html

### Bean Validations
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-bean-validators</artifactId>
    <version>2.9.2</version>
</dependency>
```
```
@NotNull(message = "First Name cannot be null")
    private String firstName;
     
    @Min(value = 15, message = "Age should not be less than 15")
    @Max(value = 65, message = "Age should not be greater than 65")
    private int age;
```
```
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
    //...
}
```
### Configuración Avanzada
#### Custom Information
```
@Bean
public Docket api() {                
    return new Docket(DocumentationType.SWAGGER_2)          
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
      .paths(PathSelectors.ant("/foos/*"))
      .build()
      .apiInfo(apiInfo());
}
 
private ApiInfo apiInfo() {
    return new ApiInfo(
      "My REST API", 
      "Some custom description of API.", 
      "API TOS", 
      "Terms of service", 
      new Contact("John Doe", "www.example.com", "myeaddress@company.com"), 
      "License of API", "API license URL", Collections.emptyList());
}
```
#### Custom Methods Response Messages
```
.useDefaultResponseMessages(false)                                   
.globalResponseMessage(RequestMethod.GET,                     
  newArrayList(new ResponseMessageBuilder()   
    .code(500)
    .message("500 message")
    .responseModel(new ModelRef("Error"))
    .build(),
    new ResponseMessageBuilder() 
      .code(403)
      .message("Forbidden!")
      .build()));
```

## Basic Authorization
#### Añadir la dependencia
```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
```
#### Añadir propiedades al properties
```
spring.security.user.name=user # Default user name.
spring.security.user.password= # Password for the default user name. A random password is logged on startup by default.
```

[id]:actuator
# 4. Actuator

## Actuator 2.x
Actuator nos permite implementar características como monitorización, métricas, tráfico 
o el estado de nuestra base de datos sin necesidad de implementarlas nosotros mismos.

[Documentación de Actuator](https://docs.spring.io/spring-boot/docs/2.0.x/actuator-api/html/)

Actuator se usa para exponer información sobre la aplicación y nos permite interactuar con ella a través de endpoints HTTP.

Para habilitar actuator, debemos importar la siguiente dependencia

```
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
 </dependency>
```

### Seguridad
Actuator comparte la seguridad con la seguridad de la aplicación, por lo que si tenemos la seguridad activada para nuestra aplicación, también lo estará para los endpoints de Actuator.
Desactivar la seguridad para los endpoints de Actuator

````java
@Bean
public SecurityWebFilterChain securityWebFilterChain(
  ServerHttpSecurity http) {
    return http.authorizeExchange()
      .pathMatchers("/actuator/**").permitAll()
      .anyExchange().authenticated()
      .and().build();
}
````

### Listado de endpoints disponibles por defecto

```
/auditevents – eventos de seguridad y auditoria
/beans – devuelve todos los beans de nuestra BeanFactory
/conditions – crea un informe sobre la auto-configuración de nuestro proyecto
/configprops – recupera todos los beans anotados con @ConfigurationProperties
/env – devuelve las variables de entorno
/flyway – detalles sobre las migraciones de base de datos de flyway
/health – summarises the health status of our application
/heapdump – heap dump de la máquina virtual usado por nuestra aplicación
/info – información general que podemos customizar
/liquibase – igual que /flyway pero para liquibase
/logfile – logs de la aplicación
/loggers – nos permite consultar y modificar el nivel de login de la aplicación
/metrics – metricas detalladas de la aplicación, se pueden customizar
/prometheus – metricas formateadas para ser entendidas por prometheus
/scheduledtasks – información sobre las tareas programadas de nuestra aplicación
/sessions – devuelve las sesiones HTTP en caso de que estemos usando Spring session
/shutdown – para la aplicación con un graceful shutdown
/threaddump – información sobre los threads de la JVM
```

### Health indicators

El endpoint health se usa para comprobar el estado de la aplicación.
http://localhost:8080/actuator/health
```json

{
  "status": "UP"
}
```
Podemos implementar nuestro propio health indicator
```java
@Component
public class HealthCheck implements HealthIndicator {
  
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
              .withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }
     
    public int check() {
        // Our logic to check health
        return 0;
    }
}
```

### Métricas
Las métricas siguen el formato de [Micrometer](https://micrometer.io/), por lo que resulta sencillo utilizar este ultimo para monitorizar nuestra aplicación.

### Creando un endpoint propio
Podemos crear endpoints custom para actuator.

```java
@Component
@Endpoint(id = "features")
public class FeaturesEndpoint {
 
    private Map<String, Feature> features = new ConcurrentHashMap<>();
 
    @ReadOperation
    public Map<String, Feature> features() {
        return features;
    }
 
    @ReadOperation
    public Feature feature(@Selector String name) {
        return features.get(name);
    }
 
    @WriteOperation
    public void configureFeature(@Selector String name, Feature feature) {
        features.put(name, feature);
    }
 
    @DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }
 
    public static class Feature {
        private Boolean enabled;
 
        // [...] getters and setters 
    }
 
}
```

### Extendiendo los endpoints existentes
Y también podemos extender los endpoints existentes
```java
@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoWebEndpointExtension {
 
    private InfoEndpoint delegate;
 
    // standard constructor
 
    @ReadOperation
    public WebEndpointResponse<Map> info() {
        Map<String, Object> info = this.delegate.info();
        Integer status = getStatus(info);
        return new WebEndpointResponse<>(info, status);
    }
 
    private Integer getStatus(Map<String, Object> info) {
        // return 5xx if this is a snapshot
        return 200;
    }
}
```

### Activando/desactivando endpoints
Actuator viene sólo con `/info` y `/healt` activos. 

Para activar todos los endpoints
`management.endpoints.web.exposure.include=*`

Para activar un endpoint específico `/sessions`
`management.endpoint.sessions.enabled=true`

Para activar todos los endpoints menos uno concreto
```
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=sessions
```

[id]:enlaces
## 5. Enlaces a materiales y recursos
### Spring Initializr
Spring Initializr es una web que nos facilita la creación de proyectos Spring boot definiendo una configuración.

https://start.spring.io/

### IntelliJ
Intelli J es un IDE de desarrollo de Jet Brains. Es rápido, ligero y tiene tanto una version gratuita (que será la que utilicemos) como un versión de pago que ofrece funcionalidades adicionales.

https://www.jetbrains.com/idea/

### Swagger
Swagger es una librería que nos permite documentar API Rest de una manera muy sencilla, especialmente si la integramos con Spring Boot.

https://swagger.io/

### PostMan
PostMan es un cliente REST que nos permite realizar peticiones usando el protocolo HTTP.

http://www.getpostman.com