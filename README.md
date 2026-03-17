Documentación Técnica: Gym API (Modular Monolith)
1. Introducción

Este proyecto implementa un sistema de gestión de gimnasios utilizando una arquitectura de Monolito Modular basada en Spring Modulith. El objetivo principal es mantener un bajo acoplamiento entre los dominios de negocio, garantizando la integridad de las transacciones y la escalabilidad del sistema mediante la gestión de eventos asíncronos.

2. Requisitos del Entorno

Para la correcta ejecución y despliegue del sistema, se requiere:

    Java Development Kit (JDK): Versión 21.
    Motor de Contenedores: Docker Desktop o Docker Engine.
    Gestor de Dependencias: Apache Maven 3.9+

3. Infraestructura y Despliegue

La persistencia de datos y la mensajería se gestionan a través de contenedores. El archivo compose.yaml automatiza la configuración de los siguientes servicios:
Ejecución de infraestructura:
Bash

`docker compose up -d`

Detalles de Conectividad:

    PostgreSQL:

        Host: localhost: 5432
        Base de datos: gym_api

    RabbitMQ:

        Puerto de mensajería (AMQP): 5672
        Panel de administración (Management): http://localhost:15672
        Credenciales por defecto: rabbit / rabbit

4. Arquitectura de Módulos y Dominios

El sistema se organiza en módulos de contexto delimitado, donde cada uno es responsable de su propia lógica y persistencia:

    Memberships (Membresías): Gestiona el ciclo de vida de los socios. Es el origen del flujo de negocio al publicar el evento MemberRegisteredEvent.
    Billing (Facturación): Responsable de la emisión de comprobantes y gestión de estados de pago (PENDING, PAID, FAILED).
    Notifications (Notificaciones): Actúa como adaptador de salida hacia sistemas externos, transformando eventos internos de la aplicación en mensajes de RabbitMQ.

5. Modelo de Comunicación Asíncrona
   Garantía de Entrega (Transactional Outbox)

El proyecto utiliza el Event Publication Registry de Spring Modulith. Este mecanismo asegura que los eventos se registren en la base de datos dentro de la misma transacción de negocio, garantizando que ninguna notificación se pierda en caso de fallos en el broker de mensajería.
Configuración de Colas en RabbitMQ:

    member.registration.queue: Recibe los datos de nuevos registros para procesos de bienvenida.

    invoice.paid.queue: Recibe las confirmaciones de pago para la generación de comprobantes.

6. Notas de Implementación

   Seguridad: Se incluye una configuración de SecurityFilterChain que permite el acceso a los endpoints en entorno de desarrollo.

   Serialización: Se utiliza Jackson2JsonMessageConverter para asegurar la compatibilidad de los Java Records con el formato JSON en las colas de RabbitMQ.