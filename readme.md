# Imobilt

![Logo Imobilt](assets/OIG1.jpeg)

## Description

- This project was developed as a way to apply my knowledge in building microservices in Java using Spring boot and as an academic project.
- **Academic Data:**
  - **Institution:** Universidade Federal Rural de Pernambuco - Unidade Academica de Serra Talhada (UFRPE-UAST)
  - **Program:** Information Systems
  - **Course:** DataBase Project
  - **Docente:** Prof. Héldon José Oliveira Albuquerque
* Create a new bank in postgresql with the name imobilt_bd
* Make the appropriate database settings in the project's application.properties

## Database Diagram
![class diagram](assets/img_3.png)

## Project Documentation
* The project documentation was made using Swagger UI.
  * [Link to documentation](http://localhost:8081/swagger-ui/index.html)
    * ***Obs:*** *As the server is local, the api must be running.*
* Below, some images and details from the project documentation:


### Lote
![img lote](assets/img.png)
* **Endpoints:**
  * **PUT** /api/v1/lote/{id_lote}/cancel
  ![Paramenters](assets/img_1.png)
    * **Payload**
      ```shell
      {
        "observation": "string"
      }
      ```
    * **Response**
      ```shell
      {
        "id": 0
      }
      ```
  * **POST** /api/v1/lote/{id_lote}/sale
    ![Paramenters](assets/img_1.png)
    * **Payload**
      ```shell
       {
         "client_id": 0
       }
      ```
    * **Response**
      ```shell
      {
        "id": 0
      }
      ```
   * **POST** /api/v1/lote/{id_lote}/reserve
  ![Paramenters](assets/img_1.png)
      * **Payload**
        ```shell
         {
           "client_id": 0
         }
        ```
      * **Response**
        ```shell
        {
          "id": 0
        }
        ```
  * **GET** /api/v1/lote
    <br>
  ![Paramenters](assets/img_2.png)
    * **Response**
      ```shell
        { 
            "additionalProp1": [
                {
                    "id": 0,
                    "lote": "string",
                    "enterprise": {
                      "id": 0,
                      "description": "string"
                    },
                   "block": "string"
                }
            ],
            "additionalProp2": [
               {
                 "id": 0,
                 "lote": "string",
                 "enterprise": {
                   "id": 0,
                   "description": "string"
                 },
                 "block": "string"
               }
            ]
        }
      ```
### Client
![Documentation](assets/image.png)
* **Endpoints:**
   * **PUT** /api/v1/client/{id}
     <br>
  ![Paramenters](assets/image_2.png)
    * **Payload**
      ```shell
      {
        "name": "string",
        "phone": "string",
        "cpf": "string"
      }
      ```
    * **Response**
      ```shell
      {
        "id": 0
      }
      ``` 
   * **POST** /api/v1/client
     <br>
  ![Paramenters](assets/image_3.png)
    * **Payload**
      ```shell
      {
        "name": "string",
        "phone": "string",
        "cpf": "string"
      }
      ```
    * **Response**
      ```shell
      {
        "id": 0
      }
      ``` 
### Enterprise
![Documentation](assets/image_4.png)
* **Endpoints:**
   * **POST** /api/v1/enterprise
     <br>
  ![Paramenters](assets/image_3.png)
    * **Payload**
      ```shell
      {
        "description": "string"
      }
      ```
    * **Response**
      ```shell
      {
        "id": 0
      }
      ``` 
   * **POST** /api/v1/enterprise/{enterprise_ìd}/blocks
  ![Paramenters](assets/image_5.png)
    * **Payload**
      ```shell
      [
        {
          "description": "string",
            "lotes": [
              {
                "description": "string"
              }
            ]
        }
      ]
      ```
