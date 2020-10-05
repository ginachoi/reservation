# ReservationService
The reservation service helps both owner and customer to make, view and cancel reservations.

### Use Cases
#### List table-seat status
Both owner and customer can view status for all seats in all tables to make reservations.

#### Make reservations
The owner can make reservations on behalf of customers and customers also can make their own 
reservation.

#### View reservations
The owner can view all reservation but customers can view only their own reservations.

#### Cancel reservations
The owner can cancel any reservations but customers can cancel only their own reservations.




### REST APIs
#### Get status of seats for all tables in the restaurants
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
#### Owner make reservations for a customer
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
#### Try to make a reservation for more than 10 people will fail
##### Request
```bash
HTTP Method: POST
URL: http:http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Felix",
   "reservation_type":"CUSTOMER",
   "reservations":[
      [
         {
            "table_id":"1",
            "seat_id":"1"
         },
         {
            "table_id":"2",
            "seat_id":"1"
         },
         {
            "table_id":"2",
            "seat_id":"2"
         },         
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
         },
         {
            "table_id":"4",
            "seat_id":"1"
         },
         {
            "table_id":"4",
            "seat_id":"2"
         },
         {
            "table_id":"4",
            "seat_id":"3"
         },
         {
            "table_id":"4",
            "seat_id":"4"
         },
         {
            "table_id":"5",
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
    "errorMsg": "Cannot reserve more than 10 people per single reservation",
    "status": 400,
    "timestamp": "2020-10-04 08:43:10"
}
```
#### Owner query all reservations
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
#### Customers query their own reservations
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
#### Try to cancel someone else's reservations will fail
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
#### Owner cancel reservations on behalf of customers
##### Request
```bash
HTTP Method: DELETE
URL: http://localhost:8088/reservation/v1/reservations
Request body:
{
   "customer_name":"Andrew",
   "reservation_type":"OWNER",
   "ids":[
      4,
      5
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

