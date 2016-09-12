# Seat reservation service
This is a restful service which provides endpoints to list/hold/reserve seats for the show. If there is only one instance of this service running, concurrent requests would not double book the same seat for multiple reservation. I have added a seat locking mechanism in memory which would facilitate this functionality. All the seat holds / reservations are also stored in memory. 

# Installation
get the latest from master branch  
cd seat-reservation  
mvn clean install  
java -jar target/seat-reservation-1.0-SNAPSHOT.jar server configs/seatreservation.yml  
# Assumptions
  - The actual problem was about booking seats in the venue. I have slightly modified this requirement to support booking seats for various shows in the venue.
  - There are total of 720 seats in the venue. 80 seats in 9 rows (rows range : 'a' through 'i')
# Installation
    Add mvn commands

## List Seats
This is the endpoint to list the seats based on the requested status  
URI : http://localhost:9015/shows/{showId}/Seats?status={status}   
values for showId : any integer  
values for status: AVAILABLE/HELD/RESERVED  
Eg:  
GET /shows/1/Seats?status=AVAILABLE HTTP/1.1  
Host: localhost:9015  
Cache-Control: no-cache  
Postman-Token: ddcb613f-5625-cbf9-735d-399b07e6eff5
## Hold Seats
This is the end point to hold the seats on the behalf of the customer. Response header will have the unique seat Hold Identification number (UUID)  
URI : http://localhost:9015/shows/{showId}/holdSeats    
values for showId : any integer  
The posted content (application/json) should have customer's email and number of seats to hold.  
Eg:  
POST /shows/2/holdSeats HTTP/1.1  
Host: localhost:9015  
Content-Type: application/json  
Cache-Control: no-cache  
Postman-Token: ed015d02-07da-e5e4-83b2-6c1486bdd5f5  
{  
   "emailId":"p2333dp@gmail.com",  
   "numberOfSeatsToHold":"34"  
}  
## Reserve Seats  
This is the endpoint to reserve the seats which were previously held by the Hold Seats service  
URI: http://localhost:9015/shows/{showId}/reserveSeats/{seatHoldId}
values for showId : any integer  
value of seatHoldId : seatHoldId from hold Seats Service Request  
Eg:  
PUT /shows/2/reserveSeats/858fd0aa-1d04-4ccc-bf32-511452d5f60e HTTP/1.1  
Host: localhost:9015  
Cache-Control: no-cache  
Postman-Token: 21197b36-da8c-6fd5-a335-0e8a4b04cc91  
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

## Postman Collection link
https://www.getpostman.com/collections/5d1f72c4a934628433cd

### Todos
 - Add logging support with log4j
 - Add More Validations


