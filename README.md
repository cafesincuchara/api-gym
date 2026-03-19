Documentación Técnica: Gym API (Modular Monolith)


1. Introducción

Este proyecto implementa un sistema de gestión de gimnasios utilizando una arquitectura de Monolito Modular basada en Spring Modulith. El sistema garantiza un bajo acoplamiento entre dominios mediante el uso de Eventos de Aplicación y asegura la integridad del negocio mediante un registro de publicación de eventos persistente.



2. Requisitos del Entorno

    Java Development Kit (JDK): Versión 21.

    Motor de Contenedores: Docker Desktop o Docker Engine.

    Gestor de Dependencias: Apache Maven 3.9+.

3. Infraestructura y Despliegue

La persistencia y mensajería se automatizan mediante Docker Compose:
Bash

    docker compose up -d

    PostgreSQL: localhost:5432 | DB: gym_api

    RabbitMQ: localhost:5672 | Admin: http://localhost:15672 (rabbit/rabbit)

4. Arquitectura de Módulos y Dominios

El sistema se organiza en contextos delimitados protegidos por reglas de encapsulamiento de Modulith:

    Auth (Seguridad): Gestiona el control de acceso mediante JWT (JSON Web Tokens). Responsable de la autenticación y el registro de credenciales cifradas con BCrypt.

    Memberships (Membresías): Gestiona el ciclo de vida de los socios. Origen del flujo de negocio al publicar MemberRegisteredEvent.

    Billing (Facturación): Responsable de la emisión de facturas y gestión de estados de pago (PENDING, PAID, FAILED). Escucha eventos de membresía para generar cobros automáticos.

    Notifications (Notificaciones): Adaptador de salida que transforma eventos internos en mensajes de RabbitMQ para sistemas externos.

5. Seguridad y Autenticación (Nuevo)

El sistema implementa un modelo de seguridad Stateless basado en estándares modernos:

    JWT: Emisión de tokens firmados con algoritmos HMAC-SHA.

    Filtros Personalizados: JwtAuthenticationFilter intercepta cada petición para validar la autenticidad del token en el Header Authorization: Bearer <token>.

    Endpoints Públicos: Únicamente /api/v1/auth/** y la documentación de Swagger están abiertos sin autenticación.

6. Modelo de Comunicación y Resiliencia

Se utiliza el Event Publication Registry de Spring Modulith. Esto asegura que los eventos se registren en la base de datos dentro de la misma transacción de negocio, evitando la pérdida de mensajes si el Broker (RabbitMQ) no está disponible temporalmente.
Configuración de Colas:

    member.registration.queue: Datos de nuevos registros para procesos de bienvenida.

    invoice.paid.queue: Confirmaciones de pago para generación de comprobantes.

7. Notas de Implementación

    Validación: Uso de Jakarta Validation para asegurar la integridad de DTOs (Emails, longitud de contraseñas, campos obligatorios).

    API Documentation: Disponible en http://localhost:8080/swagger-ui.html.

    Serialización: Jackson2JsonMessageConverter para compatibilidad total de Java Records con JSON en el Broker de mensajería.
