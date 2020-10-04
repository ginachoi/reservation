# ReservationService
The reservation service helps both owner and customer to make reservations.
* A restaurant has 4 tables of capacity 1,2,3 and 4 seats
* Two reservations can be made at the same time of multiple seating capacities. Cannot make more than two reservations.
* No single reservation can be greater than 10 people
* No waitlist - successful reservation or failed reservation are the only 2 states possible
* Customer and Owner are the only 2 people who will use this API
* Both customer and owner can make/cancel a reservation.

- Create 3 REST APIs
- As a User (customer or owner), I want to make a reservation
- As a User, I want to cancel a reservation
- As a User, at any given point, I want to check which tables are free and of what



### Use Case
* Either customers and owner make a query to get a list of table status
* Both customer and owner can make reservations
* Customer can view reservations that he/she made. Owner can view the entire reservations
* Both customer and owner can cancel reservations. Customers only cancel own reservations
* Owner can cancel any reservations
* Both owner and user can cancel one reservation at a time


### REST APIs
#### Get status of seats in the restaurants
##### Request
```bash
HTTP Method: GET
URL: http://localhost:8088/reservation/v1/tables
```
#### Response
```bash
Respone code: 200
Response body:
[
    {
        "table_id": "1",
        "seats": [
            {
                "seat_id": "1",
                "availale": true
            }
        ]
    },
    {
        "table_id": "2",
        "seats": [
            {
                "seat_id": "1",
                "availale": true
            },
            {
                "seat_id": "2",
                "availale": true
            }
        ]
    },
    {
        "table_id": "3",
        "seats": [
            {
                "seat_id": "1",
                "availale": true
            },
            {
                "seat_id": "2",
                "availale": true
            },
            {
                "seat_id": "3",
                "availale": true
            }
        ]
    },
    {
        "table_id": "4",
        "seats": [
            {
                "seat_id": "1",
                "availale": true
            },
            {
                "seat_id": "2",
                "availale": true
            },
            {
                "seat_id": "3",
                "availale": true
            },
            {
                "seat_id": "4",
                "availale": true
            }
        ]
    }
]
```
#### Owner make reservations
##### Request
```bash
HTTP Method: POST
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Suhas",
   "reservation_type":"OWNER",
   "reservations":[
      [
         {
            "table_id":"2",
            "seat_id":"1"
         }
      ]
   ]
}
```
#### Response
```bash
Respone code: 201
Response body:
[
    {
        "id": 1,
        "customer_name": "Suhas",
        "reservation_type": "OWNER",
        "seats": [
            {
                "table_id": "2",
                "seat_id": "1"
            }
        ]
    }
]
```
#### Customer make reservations
##### Request
```bash
HTTP Method: POST
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Andrew",
   "reservation_type":"CUSTOMER",
   "reservations":[
      [
         {
            "table_id":"1",
            "seat_id":"1"
         }
      ],
      [
         {
            "table_id":"3",
            "seat_id":"1"
         },
         {
            "table_id":"3",
            "seat_id":"2"
         },
         {
            "table_id":"3",
            "seat_id":"3"
         }
      ]
   ]
}
```
#### Response
```bash
Respone code: 201
Response body:
[
    {
        "id": 2,
        "customer_name": "Andrew",
        "reservation_type": "CUSTOMER",
        "seats": [
            {
                "table_id": "1",
                "seat_id": "1"
            }
        ]
    },
    {
        "id": 3,
        "customer_name": "Andrew",
        "reservation_type": "CUSTOMER",
        "seats": [
            {
                "table_id": "3",
                "seat_id": "1"
            },
            {
                "table_id": "3",
                "seat_id": "2"
            },
            {
                "table_id": "3",
                "seat_id": "3"
            }
        ]
    }
]
```
#### Try to make three reservations will fail
##### Request
```bash
HTTP Method: POST
URL: http:http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Suhas",
   "reservation_type":"OWNER",
   "reservations":[
      [
         {
            "table_id":"1",
            "seat_id":"1"
         }
      ],
      [
         {
            "table_id":"2",
            "seat_id":"1"
         }
      ],
      [
         {
            "table_id":"3",
            "seat_id":"1"
         }
      ]      
   ]
}
```
#### Response
```bash
Respone code: 400
Response body:
{
    "errorCode": "BAD_REQUEST",
    "errorMsg": "Cannot make more than two reservations",
    "status": 400,
    "timestamp": "2020-10-03 03:14:05"
}
```
#### Owner get list of all reservations
   ##### Request
   ```bash
   HTTP Method: GET
   URL: http://localhost:8088/reservation/v1/reservations?type=OWNER
   ```
   #### Response
   ```bash
   Respone code: 200
   Response body:
   [
       {
           "id": 1,
           "customer_name": "Suhas",
           "reservation_type": "OWNER",
           "seats": [
               {
                   "table_id": "2",
                   "seat_id": "1"
               }
           ]
       },
       {
           "id": 2,
           "customer_name": "Andrew",
           "reservation_type": "CUSTOMER",
           "seats": [
               {
                   "table_id": "1",
                   "seat_id": "1"
               }
           ]
       },
       {
           "id": 3,
           "customer_name": "Andrew",
           "reservation_type": "CUSTOMER",
           "seats": [
               {
                   "table_id": "3",
                   "seat_id": "1"
               },
               {
                   "table_id": "3",
                   "seat_id": "2"
               },
               {
                   "table_id": "3",
                   "seat_id": "3"
               }
           ]
       }
   ]
```
#### Customer get list of own reservations
##### Request
```bash
HTTP Method: GET
URL: http://localhost:8088/reservation/v1/reservations?type=CUSTOMER&name=Andrew
```
#### Response
```bash
Respone code: 200
Response body:
[
    {
        "id": 1,
        "customer_name": "Andrew",
        "reservation_type": "CUSTOMER",
        "seats": [
            {
                "table_id": "1",
                "seat_id": "1"
            }
        ]
    },
    {
        "id": 2,
        "customer_name": "Andrew",
        "reservation_type": "CUSTOMER",
        "seats": [
            {
                "table_id": "3",
                "seat_id": "1"
            },
            {
                "table_id": "3",
                "seat_id": "2"
            },
            {
                "table_id": "3",
                "seat_id": "3"
            }
        ]
    }
]
```
#### Try to cancel a reservation doesn't exist will fail
##### Request
```bash
HTTP Method: DELETE
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Suhas",
   "reservation_type":"CUSTOMER",
   "ids":[
      6
   ]
}
```
#### Response
```bash
Respone code: 400
Response body:
{
    "errorCode": "BAD_REQUEST",
    "errorMsg": "Reservation with ID '6' does not exist",
    "status": 400,
    "timestamp": "2020-10-04 03:09:18"
}
```
#### Try to cancel someone else's reservations
##### Request
```bash
HTTP Method: DELETE
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Gina",
   "reservation_type":"CUSTOMER",
   "ids":[
      1
   ]
}
```
#### Response
```bash
Respone code: 401
Response body:
{
    "errorCode": "UNAUTHORIZED",
    "errorMsg": "Not authorized",
    "status": 401,
    "timestamp": "2020-10-04 03:06:57"
}
```
#### Customer cancel their own reservations
##### Request
```bash
HTTP Method: DELETE
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Suhas",
   "reservation_type":"CUSTOMER",
   "ids":[
      1
   ]
}
```
#### Response
```bash
Respone code: 204
```
### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

