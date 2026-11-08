## Analisis de SOLID
## SOLID:

* S (Principio de responsabilidad unica):
Se ha implementado una única responsabilidad por clase, ya que, cuando se ha creado el adapter, esta clase solo se encarga de adaptar las operaciones definidas en
CustomersRepository, y, además, hace la conversión entre las clases: CustomersEntity y Customer, implementadas en persistence y domain, respectivamente, lo que permite
la separación de la lógica de la aplicacion con la persistencia.
Las clases que daba el enunciado ya seguian este principio, pues cada una cumple con su función, sin involucrarse directamente en otras.

* O (principio abierto, cerrado):
La arquitectura permite la implementación de CustomersRepository sin modificar, permitiendo mantener la interfaz y la lógica original.
Además, con la implementación del patrón adapter, se ha conseguido implementar una nueva clase al repositorio sin necesidad de editar las existentes.

* L (principio de sustitución de Liskov):
Al no alterar las clases implementadas en domain y application, no estamos alterando el comportamiento esperado por los consumidores, pues los cambios están a bajo nivel, 
y el CustomersRepository sigue pudiendo implementar las operaciones que oferta, aunque ahora las obtenga por el adapter y no por el InMemory. Permitiendo utilizar la 
implementación JPA sin necesitar de conocer sus detalles internos.

* I (Principio de segregación de interfaces):
La interfaz CustomersRepository contiene operaciones específicas relacionadas con la persistencia y consulta de clientes.
Al tener separado ConsumersJPARepository, la aplicación depende unicamente de las operaciones que necesita, sin tener que conocer todos los métodos proporcionados por JPA

* D (Principio de inversión de dependencias):
Siguiendo la arquitectura hexagonal planteada, este principio se está cumpliendo, pues:
Application depende de la abstracción de CustomersRepository
Infraestructure proporciona el patrón del adapter para la comunicación, como ya se mencionó
El adaptador utiliza CustomersJPARepository para comunicarse con la bas de datos.
Lo que permite que la capa de Application no dependa directamente de conocer los detalles de como se almacenan los datos, ni del Spring Data JPA

## Patrones identificados:
## Patrón implementado:
Se optó por implementar un patrón adapter en persistencia, para cubrir los cambios asociados con la solicitud.
Este patrón permite conectar las interfaces de CustomersRepository (establece las operaciones que se realizarán en la aplicación), en application, y CustomersJPARepository (métodos de Spring Data JPA, para la comunicación con la base de datos), en persistance.
Permitiendo la traducción de las operaciones de aplicación con las de JPA.
También realiza una conversión entre Customer y CustomerEntity, permitiendo usar las capacidades de Spring Data JPA sin modificar la interfaz de la aplicación

## Repositorio:
El patrón Repository permite abstraer el acceso a los datos, separando la lógica de almacenamiento del resto de la aplicación.
En el proyecto aparece mediante la interfaz CustomersRepository, que define las operaciones disponibles para trabajar con clientes.
La implementación CustomersRepositoryAdapter utiliza CustomersJPARepository para realizar las operaciones sobre la base de datos.
Spring Data JPA proporciona automáticamente operaciones habituales como guardar, buscar por identificador y eliminar entidades.

## Inyección de dependencias:
El proyecto utiliza inyección de dependencias de Spring para proporcionar a las clases los objetos que necesitan.
En CustomersRepositoryAdapter, la dependencia CustomersJPARepository se recibe mediante el constructor.
Esto evita que el adaptador tenga que crear directamente su propia instancia del repositorio JPA y permite que Spring gestione las dependencias.

## Builder:
Las clases Customer y CustomerEntity utilizan la anotación Lombok @Builder.
Esta anotación permite construir objetos mediante un patrón Builder, asignando sus atributos de forma explícita y legible.
Por ejemplo, en el adaptador se utiliza para convertir un objeto del dominio en una entidad de persistencia:

## Arquitectura del proyecto:
El proyecto sigue una arquitectura hexagonal.

Las responsabilidades se distribuyen en distintas capas:
Domain: contiene los objetos y conceptos propios del dominio, como Customer.
Application: define los casos de uso y los puertos que necesita la aplicación, como CustomersRepository.
Infrastructure: contiene los adaptadores y detalles técnicos, como CustomersRepositoryAdapter, CustomersJPARepository y CustomerEntity.

Esta separación permite mantener la lógica de negocio independiente de los detalles tecnológicos de persistencia.
La incorporación de JPA respeta esta arquitectura porque se realiza mediante un adaptador en Infrastructure, sin modificar las interfaces de Application ni las clases de Domain.


## Conclusion:
La implementación de persistencia mediante Spring Data JPA mantiene la separación de responsabilidades entre las capas del proyecto.
El patrón Adapter permite conectar el puerto de persistencia de Application con la tecnología JPA, mientras que la inyección de dependencias facilita la gestión de los componentes.
Además, la arquitectura hexagonal permite cambiar los detalles de infraestructura sin afectar directamente a la lógica de negocio.
La solución permite almacenar y recuperar clientes mediante una base de datos relacional, manteniendo la interfaz de aplicación existente.