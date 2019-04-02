# Exhausted-Server-Problem

The Foo Bar and Restaurant is unfortunately under-staffed. In fact, they can only
afford to have one server and he is forced to work double-shifts in order to keep the
place open. The restaurant has only a single counter with 15 seats.

Business at the restaurant is somewhat variable. There are times when there is a
line out the door waiting to get one of the limited available seats. At other times of
the day, there are no customers and the server takes the opportunity to try to get a
little bit of sleep.

If a customer arrives at the empty restaurant and finds the server sleeping then he
wakes him up and the service begins. If the customer arrives and the server is
active, then the customer will take an available seat (if one exists) and wait her turn
for service. Finally, if the customer arrives and there are no available seats then she
must wait outside the door for someone to exit and thus free up a counter seat.
When the order is delivered it is quickly devoured and the customer leaves their
payment and exit the establishment.

The server provides service (takes an order and serves up the request) in the order
that people are seated. If the restaurant is empty, then the server simply sits down
and sleeps until awakened by an arriving customer. Being a strange little
establishment, the server only waits on one customer at a time. This is because he is
also the cook. So, if the server/cook is busy then only one customer is actively being
serviced and the others (in chairs or possibly outside) are waiting.

=> This is a semaphore-based solution program using Java. However, this program hasn't been thoroughly tested. It is just an example and hopefully someone can learn or at least has a some idea where to start. Keep learning and practicing!!!
