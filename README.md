# Demo Integracion BFF con Kafka, HTTP, Mocks

La siguiente tiene 3 aplicaciones:
- jiren-bff: Simula el BFF, y que se comunica contra Kafka, HTTP, Mocks de forma configurable.
- jiren-serv01: Simula un servicio que se consume y que expone como HTTP, y escucha eventos de Kafka.
- jiren-serv02: Simula otro servicio pero que usaria otra forma de conexion.

Esta demo trabajaría solo con jiren-bff y jiren-serv01.

Puede importar los archivos de Collections y Environments para poder realizar las pruebas. Estos se ubican en: `postman-files`

## Breve ejecucuión

Para poder ejecutar los escenarios primero debe realizar primero la preparación de Kafka, haber importado los archivos de postman para llamar al servicio.

Mantener en lo posible Kafka ya iniciado y las colas ya creadas

### Usando solo el servicio Mock:

1. En este caso puede modificar el archivo:
`jiren-bff\src\main\resources\application.yml`

y deberia quedar del siguiente modo:
```
services:
  customer: MockCustomerService
#  customer: KafkaCustomerService
#  customer: RestCustomerService
```
2. Ejecute (capaz con su IDE), jiren-bff
3. Ejecute el servicio erService `BFF::GET CustomererService` de postman. Fíjese que la llamada al servicio: `{{base_url}}/v1/customer/:id`, usa un id. Puede usar 001 o 002 como parametro de id.

Puede ver en log que el servicio que reponde es `MockCustomerService`

### Usando solo el servicio Kafka:

1. En este caso puede modificar el archivo:
`jiren-bff\src\main\resources\application.yml`
y deberia quedar del siguiente modo:

```
services:
#  customer: MockCustomerService
  customer: KafkaCustomerService
#  customer: RestCustomerService
```
2. Ejecute (capaz con su IDE), `jiren-bff`
3. Ejecute (capaz con su IDE), `jiren-serv01`
3. Ejecute el servicio erService `BFF::GET CustomererService` de postman. Fíjese que la llamada al servicio: `{{base_url}}/v1/customer/:id`, usa un id. Puede usar 001 o 002 como parametro de id.

Puede ver en log que el servicio que reponde es `KafkaCustomerService`, y que ahora se realiza la llamada a la otra aplicación `jiren-serv01`

### Usando solo el servicio HTTP:

1. En este caso puede modificar el archivo:
`jiren-bff\src\main\resources\application.yml`

y deberia quedar del siguiente modo:
```
services:
#  customer: MockCustomerService
#  customer: KafkaCustomerService
  customer: RestCustomerService
```
2. Ejecute (capaz con su IDE), `jiren-bff`
3. Ejecute (capaz con su IDE), `jiren-serv01`
3. Ejecute el servicio erService `BFF::GET CustomererService` de postman. Fíjese que la llamada al servicio: `{{base_url}}/v1/customer/:id`, usa un id. Puede usar 001 o 002 como parametro de id.

Puede ver en log que el servicio que reponde es `RestCustomerService`, y que ahora se realiza la llamada a la otra aplicación `jiren-serv01`



## Preparacion de Kafka

La version a usar es kafka_2.12-3.1, y se puede descargar desde acá:
[Download kafka_2.12-3.1](https://kafka.apache.org/downloads)

Antes de levantar Kakfka, hay que reparar algunos problemas con los archivos de log cuando necesita renombrarlos. Esto lo hacemos para una  Kafka en Windows

Asumiendo que la ruta de instalacion de Kafka ha sido:
KAFKA_HOME=kafka_2.12-3.1

Realizamos:
1. Duplicamos el archivo `%KAFKA_HOME%\config\log4j.properties` con el nombre `zookeeper.log4j.properties`
2. Abrimos dicho archivo y a todo el contenido con:

   `File=${kafka.logs.dir}`  
   lo modificamos por:  
   `File=${kafka.logs.dir}/zookeeper`  
   Esto es para que se utilice la carpeta zookeeper como nueva ubicacion de los logs de zookeeper. Una linea de ejemplo nos quedaria asi:  
   `log4j.appender.kafkaAppender.File=${kafka.logs.dir}/zookeeper/server.log`  
   Guardamos el archivo y lo cerramos

3. Abrimos el archivo:`%KAFKA_HOME%\bin\windows\zookeeper-server-start.bat`, y modificamos la linea 24 que dice:
   `/config/log4j.properties`  
   por la linea:  
   `/config/zookeeper.log4j.properties`  
   Le estamos diciendo que use la configuracion que acabamos de crear. Nos quedaría algo asi:  
   `set KAFKA_LOG4J_OPTS=-Dlog4j.configuration=file:%~dp0../../config/zookeeper.log4j.properties`  
   Guardamos el archivo y lo cerramos

Listo, con eso basta.

## Comandos Kafka

Los siguientes comandos asumen que estamos ubicados en `%KAFKA_HOME%`. Además puede consultar otras operaciones básicas en:

[Apache Kafka Quickstart](https://kafka.apache.org/quickstart)

### Iniciar Kafka
Para poder ejecutar Kafka, se necesita iniciar dos programas. Ejecútelos en el siguiente orden:
```
bin\windows\zookeeper-server-start.bat config/zookeeper.properties

bin\windows\kafka-server-start.bat config/server.properties
```

### Operaciones con los tópicos

#### Crear Topicos
Para esta demo vamos a crear dos tópicos:
```
fMyTopicResp
fMyTopicRequ
```
Ejecutamos:
```
bin\windows\kafka-topics.bat  --create --topic fMyTopicResp --bootstrap-server localhost:9092

bin\windows\kafka-topics.bat  --create --topic fMyTopicRequ --bootstrap-server localhost:9092
```

#### Listar tópicos
```
bin\windows\kafka-topics.bat --bootstrap-server=localhost:9092 --list
```

#### Describir un tópico
Si el tópico se llama `fMyTopicRequ`:
```
bin\windows\kafka-topics.bat --describe --topic fMyTopicRequ --bootstrap-server localhost:9092
```

#### Borrar un tópico
Si el tópico se llama `fMyTopicRequ`:
```
bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --topic fMyTopicRequ --delete
```

Este comando a veces da problemas porque no puede borrar algunas carpetas. En caso suceda eso, Detienen Kafka y Zookeeper, y luego borran la carpeta: `logs` y `tmp`.


#### Crear mensaje
Este comando, le permite escribir lo que guste en consola, y al dar [Enter] ingresa dicho texto. Esto puede usarlo para probar que su cola ha sido bien creada. Una vez hecho esto, es preferible borrar dicho mensaje.

Si el tópico se llama `fMyTopicRequ`:
```
bin\windows\kafka-console-consumer.bat --topic fMyTopicRequ --from-beginning --bootstrap-server localhost:9092
```

#### Consumir  mensaje
Este comando le listará todos los mensajes de un tópico dado:

Si el tópico se llama `fMyTopicRequ`:
```
bin\windows\kafka-console-consumer.bat --topic fMyTopicResp --from-beginning --bootstrap-server localhost:9092
```


#### Borrar mensajes
Este comando es preferible a borrar el tópico si es que necesita limpiar el tópico. En este caso, se utiliza los archivos json ubicados en `kafka-json-files` en este repositorio.

Se requiere colocar el nombre del tópico en el json. Por ejemplo para borrar los mensajes en el tópico `fMyTopicRequ`:
```json
{
    "partitions": [
        {
            "topic": "fMyTopicRequ",
            "partition": 0,
            "offset": -1
        }
    ],
    "version": 1
}
```

Los archivos están listo con los nombres de los tópicos creados. Entonces para borrar mensajes de cada tópico, los archivos serían:
- Para `fMyTopicRequ`, usar: `delete-records-requ.json`
- Para `fMyTopicResp`, usar: `delete-records-requ.json`

Puede copiar estos archivos en `%KAFKA_HOME%`, para que lo tenga mas a la mano, y el comando asume esta ubicación del archivo:

Borrar mensajes de `fMyTopicRequ`;
```
bin\windows\kafka-delete-records.bat --bootstrap-server localhost:9092 --offset-json-file delete-records-requ.json
```
Borrar mensajes de `fMyTopicResp`
```
bin\windows\kafka-delete-records.bat --bootstrap-server localhost:9092 --offset-json-file delete-records-resp.json
```



