# Java EE Product Management Application
<img width="1107" height="651" alt="image" src="https://github.com/user-attachments/assets/72382c27-cf16-498c-96e6-a99eea20e7ea" />
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
## French Version
## Couplage faible, interfaces et injection de dépendances avec Spring

### 1. Pourquoi utiliser des interfaces entre les couches ?

Une application peut être organisée en trois couches :

- **Web** : reçoit les requêtes et gère les interactions HTTP.
- **Métier (Service)** : applique les règles métier.
- **DAO / Repository** : accède aux données.

Pour réduire le couplage, une couche dépend d'une abstraction (interface) plutôt que d'une implémentation concrète de la couche suivante.

Par exemple, `ProduitService` dépend de `IProduitDAO`, et non directement de `ProduitDAOImpl`.

```java
public interface IProduitDAO {
    List<Produit> getProducts();
}
```

Plusieurs classes peuvent implémenter ce contrat :

- `ProduitDAOImpl` : première stratégie d'accès aux données.
- `ProduitDAOImplV2` : nouvelle stratégie d'accès aux données.

Tant que les implémentations respectent le contrat de l'interface, le service peut les utiliser sans connaître leurs détails internes.

### 2. Pourquoi l'interface ne suffit-elle pas ?

Sans Spring, on peut écrire :

```java
IProduitDAO dao = new ProduitDAOImpl();
```

Le type de la variable est `IProduitDAO`, mais l'objet concret est un `ProduitDAOImpl`.

Si le service crée lui-même cet objet avec `new`, il reste lié à cette classe au moment de la création. Pour utiliser `ProduitDAOImplV2`, il faut modifier cette création.

Le polymorphisme permet d'utiliser différentes implémentations à travers la même interface, mais il ne décide pas qui doit créer ou sélectionner l'objet.

**Le couplage faible nécessite donc de séparer le contrat de la décision de création et de sélection de l'implémentation.**

### 3. Injection de dépendances et inversion de contrôle

Avec Spring, le service exprime son besoin à travers son constructeur :

```java
@Service
public class ProduitService {

    private final IProduitDAO dao;

    public ProduitService(IProduitDAO dao) {
        this.dao = dao;
    }
}
```

Spring détecte les beans, crée les objets gérés par son conteneur et injecte une implémentation compatible de `IProduitDAO`.

- **IoC (Inversion of Control)** : le contrôle de la création et de l'assemblage des objets est confié au conteneur.
- **DI (Dependency Injection)** : les dépendances nécessaires sont fournies à l'objet, ici par constructeur.

Le service n'a pas besoin d'instancier lui-même `ProduitDAOImpl` ou `ProduitDAOImplV2`.

### 4. Comment sélectionner une implémentation ?

Si une seule implémentation est disponible comme bean, Spring peut généralement l'injecter automatiquement.

Si plusieurs implémentations sont disponibles, il faut lever l'ambiguïté. On peut notamment utiliser :

- `@Primary` pour privilégier une implémentation par défaut.
- `@Qualifier` pour désigner explicitement le bean souhaité.

Par exemple :

```java
@Repository
@Primary
public class ProduitDAOImplV2 implements IProduitDAO {
    // Nouvelle implémentation
}
```

Le service continue de recevoir un `IProduitDAO` ; il n'a pas besoin de connaître le nom de la classe concrète choisie.

### 5. Appliquer le même principe entre Web et Métier

La couche Web peut dépendre de `IProduitService` plutôt que de `ProduitServiceImpl`.

```java
public interface IProduitService {
    List<Produit> getProducts();
    Produit getProductById(Long id);
}
```

Le contrôleur reçoit cette interface par injection de dépendances.

Si l'implémentation métier change tout en respectant le même contrat, le contrôleur n'a généralement pas besoin d'être modifié.

### 6. Limites et bonnes pratiques

- Une interface définit un contrat ; elle ne sélectionne pas l'implémentation.
- Une interface permet le polymorphisme, mais le choix de l'objet concret doit être effectué quelque part.
- Spring facilite ce choix en prenant en charge la création et l'injection des beans.
- Plusieurs implémentations de la même interface peuvent nécessiter une configuration de sélection explicite.
- Si le contrat de l'interface change, ses consommateurs ou ses implémentations peuvent également devoir évoluer.
- Créer une nouvelle implémentation n'est pas obligatoire pour chaque modification : cela dépend du besoin architectural.
<img width="970" height="612" alt="image" src="https://github.com/user-attachments/assets/34973d3a-136c-4ec7-bf26-65c0e061657c" />
<img width="976" height="607" alt="image" src="https://github.com/user-attachments/assets/c885ff02-34b4-4051-a87f-bb0605f39800" />

**À retenir :** les interfaces réduisent la dépendance aux détails d'implémentation, tandis que l'inversion de contrôle et l'injection de dépendances permettent de déléguer à Spring la création et l'assemblage des objets. Ensemble, ces mécanismes facilitent l'évolution, les tests et le remplacement des implémentations avec un impact limité sur les autres couches.
<img width="767" height="290" alt="image" src="https://github.com/user-attachments/assets/fc435fb3-2e08-4b86-9cd4-245622bda4a2" />



## Author

Bakhta Nairouz Ben Hadj Slema
