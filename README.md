# Currency Exchange API

A Spring Boot application that provides currency exchange rates by fetching data from the Central Bank of Azerbaijan (CBAR).

## Features
- Get all currency rates for a specific date
- Get currency rate by specific currency code and date
- XML data parsing from CBAR's official API
- Error handling and response formatting

## API Endpoints & Examples
```http
# Get all currencies for specific date
GET /v1/api/currencies/{date}
GET http://localhost:8080/v1/api/currencies/12.06.2023

# Get specific currency for date
GET /v1/api/currencies/{date}/{code}
GET http://localhost:8080/v1/api/currencies/12.06.2023/USD
```
- `date`: Format `dd.MM.yyyy` (e.g., `12.06.2023`)
- `code`: Currency code (e.g., `USD`, `EUR`)

## Stack
- Java 17, Spring Boot, Spring MVC, Jakarta EE, Lombok, JAXB

## Response Examples
```json
// All currencies response
{
    "date": "12.06.2023",
    "name": "Foreign Currency Rates",
    "description": "Daily rates",
    "currencies": [
        {
            "code": "USD",
            "name": "US Dollar",
            "nominal": "1",
            "value": "1.7000",
            "type": "Bank"
        }
    ]
}

// Single currency response
{
    "code": "USD",
    "name": "US Dollar",
    "nominal": "1",
    "value": "1.7000",
    "type": "Bank"
}
```
