# SIMPLE CRUD PROJECT - Donation to Charities
## Introduction

This documentation provides an overview of a RESTful API project for managing donations to charities using Spring Boot.
## Technology Stack

* Java 11
* Apache Maven
* Spring Boot
* Spring Data JPA
* Spring Web (WebMVC)
* PostgreSQL (as the database)
* Springdoc OpenAPI (for API documentation/Swagger UI)
* Lombok (for reducing boilerplate code)

## Project Structure

The project follows a structured approach with the following main components:

- **Controllers:** These classes define the REST API endpoints for managing donations, charities, donors, and images. They handle incoming requests and route them to the appropriate service methods.
- **Services:** The service classes contain the business logic for managing donations, charities, donors, and images. They interact with the database and perform CRUD operations.
- **Models:** Model classes define the data structure for `Donation`, `Charity`, `Donor`, `Image`, and `Address`. They represent the entities stored in the database.
- **DTOs (Data Transfer Objects):** The project includes DTOs to facilitate data exchange between the client and the server. Notable DTOs include `DonationRequest` and `DonationResponse`, which are used for creating and responding to donation-related requests.
- **Utils:** The `utils` package contains the `CharityStatus` enum.
- **Database:** PostgreSQL is used as the database to store information about donations, charities, and donors.
- **Springdoc OpenAPI:** The project includes Springdoc OpenAPI for API documentation. You can access the Swagger UI to explore and test the API endpoints.

## API Endpoints

Access the Swagger UI documentation at `http://localhost:8080/swagger-ui.html` to explore and test the API endpoints.

### Charity Controller
- **Add Charity** : `POST /api/charities/add`
- **Get All Charities** : `GET /api/charities/get-all-charities`
- **Get Charity by ID** : `GET /api/charities/get-charity`
- **Get Charitable Funds** : `GET /api/charities/get-charitable-funds`
- **Update Charity** : `PUT /api/charities/update`
- **Delete Charity** : `DELETE /api/charities/delete`

#### Sample JSON Request (Create a New Charity):
````
{
    "name": "Sample Charity",
    "description": "A sample charity for demonstration",
    "currency": "USD",  
    "address": {
        "street": "123 Main Street",
        "city": "Sampleville",
        "state": "Sample State",
        "country": "Sample Country",
        "zipCode": "12345"
    }
}
````
### Donation Controller
- **Send Donation** : `POST /api/donate/send-donation`
- **Get All Donations** : `GET /api/donate/get-all-donations`
- **View Donation** : `GET /api/donate/view-donation`
- **Update Donation** : `PUT /api/donate/update`
- **Cancel Donation** : `DELETE /api/donate/cancel`

#### Sample JSON Request (Create a New Donation):
````
{
  "amount": 73.23,
  "donor": {
        "uuid": "b9f4c99a-66cf-4d8c-a202-8df429e67188",
        "firstName": "Johnny",
        "lastName": "Dos",
        "email": "john.doe@example.com",
        "dateOfBirth": "1995-01-15",
        "balance": 2200.00,
        "image": null
    },
  "charity": {
        "uuid": "9016eb75-d708-4d41-a39c-157ace65b39c",
        "name": "AWS Charity",
        "description": "As sample charity for demonstration",
        "currency": "PHP",
        "status": "ACTIVE",
        "dateCreated": "2023-11-08T17:16:09.03672",
        "address": {
            "uuid": "112f8d0e-64f6-4806-960e-68b9497fef7e",
            "street": "1234 Main Street",
            "city": "Sampsleville",
            "state": "Sampale State",
            "country": "Samfple Country",
            "zipCode": "123415"
        },
        "image": null
    }
}

````

### Donor Controller
- **Get All Donors** : `GET /api/donors/get-all-donors`
- **Get Donor by ID** : `GET /api/donors/get-donor`
- **Add Donor** : `POST /api/donors/add`
- **Update Donor** : `PUT /api/donors/update`
- **Fund Balance** : `PUT /api/donors/fund-balance`
- **Delete Donor** : `DELETE /api/donors/delete`
- **Check Balance** : `GET /api/donors/check-balance`

#### Sample JSON Request (Create a New Donor):
````
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "johney.doe@example.com",
    "dateOfBirth": "1990-01-15",
    "balance": 0
}
````

### Image Controller
- **Upload Image** : `POST /api/media/upload`
- **View Image** : `GET /api/media/view`
- **Download Image** : `GET /api/media/download`
- **Update Image** : `PUT /api/media/update`
- **Delete Image** : `DELETE /api/media/delete`

