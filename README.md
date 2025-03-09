# JavaAvanadeProject

Diagrama de classes 

``` mermaid
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

    Product --> Supplier : has
    InventorySystem --> Product : contains
    InventorySystem --> StockOperations : manages
    StockOperations --> CreateOperation : uses
    StockOperations --> ReadOperation : uses
    StockOperations --> UpdateOperation : uses
    StockOperations --> DeleteOperation : uses
    InventorySystem --> StockManager : managed by
```
