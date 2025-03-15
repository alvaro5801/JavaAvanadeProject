classDiagram
    class Product {
        +int id
        +String name
        +String category
        +int quantity
        +double price
        +Supplier supplier
    }

    class Supplier {
        +int id
        +String name
        +String contact
    }

    class StockOperations {
        +CreateOperation create
        +ReadOperation read
        +UpdateOperation update
        +DeleteOperation delete
    }

    class CreateOperation {
        +String description
        +Product example
    }

    class ReadOperation {
        +String description
        +String example
    }

    class UpdateOperation {
        +String description
        +Product example
    }

    class DeleteOperation {
        +String description
        +String example
    }

    class StockManager {
        +int id
        +String name
        +String role
    }

    class InventorySystem {
        +List~Product~ products
        +StockOperations operations
        +String lastUpdate
        +StockManager manager
    }

    class User {
        +int id
        +String username
        +String password
        +List~Role~ roles
    }

    class Role {
        +int id
        +String name
    }

    class SecurityService {
        +boolean authenticate(String username, String password)
        +boolean authorize(User user, String permission)
    }

    class JwtTokenProvider {
        +String generateToken(User user)
        +boolean validateToken(String token)
    }

    Product --> Supplier : has
    InventorySystem --> Product : contains
    InventorySystem --> StockOperations : manages
    StockOperations --> CreateOperation : uses
    StockOperations --> ReadOperation : uses
    StockOperations --> UpdateOperation : uses
    StockOperations --> DeleteOperation : uses
    InventorySystem --> StockManager : managed by

    User --> Role : has
    SecurityService --> User : authenticates
    SecurityService --> Role : verifies
    JwtTokenProvider --> User : issues token
    JwtTokenProvider --> SecurityService : validates token
