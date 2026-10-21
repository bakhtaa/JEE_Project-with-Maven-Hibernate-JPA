# Java EE Product Management Application

## Overview

This project is a Java EE web application developed to manage products and their categories using JPA, Hibernate, and Maven.

Beyond implementing basic data persistence and web request handling, the project helped me understand the principles behind layered architecture, loose coupling, dependency injection, and Object-Relational Mapping (ORM).

The main objective was to understand how the different layers of a Java web application collaborate, from receiving an HTTP request to interacting with a relational database and displaying the result to the user.

## Technologies Used

- **Java** — application logic and object-oriented programming.
- **Java EE** — web application components and request handling.
- **Servlets** — handling HTTP requests and dispatching them to the appropriate application logic.
- **JPA (Java Persistence API)** — persistence interfaces and object-relational mapping annotations.
- **Hibernate** — ORM framework and JPA implementation.
- **Maven** — dependency management and project build.
- **Apache Tomcat** — servlet container and web application runtime.
- **Relational Database** — persistent storage for products and categories.

## Application Architecture

The application follows a layered architecture that separates web request handling, business logic, and data persistence.

### 1. Web Layer

The web layer receives HTTP requests through Servlets and maps incoming requests to the corresponding application operations.

It coordinates the request processing, retrieves the necessary data from the lower layers, stores the results in request attributes, and forwards the request to the appropriate JSP view.

### 2. Service Layer

The service layer is responsible for business logic and use cases. It provides an abstraction between the web layer and the data access layer.

This separation allows business rules to evolve without placing all application logic inside Servlets or database access classes.

*Note: This layer is applicable if a service layer is implemented in the project.*

### 3. DAO / Repository Layer

The data access layer is responsible for interacting with the database.

Using JPA's `EntityManager`, the application performs persistence operations such as creating, retrieving, updating, and deleting entities. Hibernate implements the JPA persistence interfaces and translates entity operations into SQL statements.

This keeps database access separate from request handling and presentation logic.

### Architecture Flow

```text
HTTP Request
     |
     v
Servlet (Web Layer)
     |
     v
Service Layer
     |
     v
DAO / Repository Layer
     |
     v
EntityManager (JPA)
     |
     v
Hibernate ORM
     |
     v
Relational Database
     |
     v
Result returned to the Web Layer
     |
     v
Request Attributes + Forward
     |
     v
JSP View
     |
     v
HTML Response
```


## Data Model and Object-Relational Mapping

The application models two main entities: `Produit` and `Categorie`.

### Entity Relationships

A category can contain multiple products, while each product belongs to one category.

The relationship is represented using JPA annotations.

**In the `Produit` entity:**

```java
@ManyToOne
@JoinColumn(name = "categorie_id")
private Categorie categorie;
```

- `@ManyToOne` indicates that several products can reference the same category.
- `@JoinColumn(name = "categorie_id")` specifies the foreign key column used to associate a product with its category.

**In the `Categorie` entity:**

```java
@OneToMany(
    mappedBy = "categorie",
    cascade = CascadeType.PERSIST,
    fetch = FetchType.LAZY
)
private List<Produit> produits;
```

- `@OneToMany` represents the collection of products belonging to a category.
- `mappedBy = "categorie"` indicates that the `categorie` field in `Produit` owns the relationship. The foreign key is therefore managed on the product side.
- `cascade = CascadeType.PERSIST` propagates a persist operation from a category to its associated products.
- `fetch = FetchType.LAZY` delays loading the products until the collection is accessed, subject to the persistence context and provider behavior.

This relationship helped me understand how object references are mapped to relational database foreign keys.

## Key Concepts Learned

### 1. Inversion of Control (IoC) and Dependency Injection

A key learning outcome was understanding how application components can delegate object creation and dependency management to a framework or container.

With Inversion of Control, a component does not necessarily control every aspect of its own lifecycle and dependencies. The framework or container takes responsibility for some of that control.

Dependency Injection is a technique through which a component receives the dependencies it needs instead of creating them directly.

For example, constructor injection allows a class to receive an implementation through an interface:

```java
public ProductService(IProductRepository repository) {
    this.repository = repository;
}
```

The service depends on the repository abstraction rather than a specific implementation.

**Important distinction:** Spring Boot and the Spring container can automatically manage dependency injection when configured accordingly. In a traditional Java EE application, dependencies may instead be created and wired manually or managed through Java EE container facilities. The exact mechanism depends on the implementation.

### 2. Loose Coupling and Polymorphism

The project helped me understand why programming against interfaces improves modularity.

When one layer depends on an interface instead of a concrete implementation, the implementation can be replaced with another one without necessarily changing the calling layer.

This relies on polymorphism: different implementations can be used through the same interface.

Combined with separation of concerns, this reduces direct dependencies between layers and makes the application easier to maintain, test, and extend.

### 3. JPA, Hibernate, and ORM

I learned the difference between JPA and Hibernate:

- **JPA** defines the persistence API, annotations, and contracts used to manage entities.
- **Hibernate** provides an implementation of JPA and handles the mapping between Java objects and relational database structures.
- **ORM (Object-Relational Mapping)** connects the object-oriented model of the application to the relational model of the database.

Through JPA's `EntityManager`, the application can persist and retrieve entities without manually writing every SQL statement.

Hibernate translates persistence operations into database operations and maps retrieved rows back to Java objects.

This abstraction reduces direct dependence on SQL and on a particular database implementation, although database-specific differences may still require adjustments.

### 4. Request Handling and View Rendering

I also learned how a traditional Java web application processes an HTTP request.

A Servlet receives the request and invokes the relevant application logic. The result can be stored as a request attribute and passed to a JSP using `RequestDispatcher.forward()`.

The JSP then uses the available data to generate the HTML response.

This helped me understand the separation between request handling, data retrieval, and presentation.

In Spring MVC, a similar role is played by the `Model`, which carries data from a controller to a view. However, a Servlet request attribute and Spring MVC's `Model` are distinct mechanisms.

## Running the Project

### Prerequisites

- A compatible JDK.
- Apache Maven.
- Apache Tomcat compatible with the application's Servlet API version.
- A configured relational database.
- An IDE such as Eclipse, or another Java development environment.

### Setup

1. Clone the repository:

   ```bash
   git clone <repository-url>
   ```

2. Import the project into Eclipse as an existing Maven project.

3. Configure the database connection and persistence settings.

4. Ensure the required database and tables are available, or configure the project to create them if supported.

5. Build the project and resolve Maven dependencies.

6. Deploy the application to the configured Tomcat server.

7. Open the application using its deployed context path.

*The exact database configuration and deployment steps depend on the project settings.*

## Future Improvements

Possible improvements include:

- Adding automated tests for the persistence and business layers.
- Improving validation and exception handling.
- Externalizing database credentials and environment-specific configuration.
- Adding documentation for the available application features and endpoints.

## Author

Bakhta Nairouz Ben Hadj Slema
