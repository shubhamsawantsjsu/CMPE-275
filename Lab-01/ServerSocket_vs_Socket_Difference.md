### THE DIFFERENCES OF HANDLING SOCKET AND SERVERSOCKET

* A  socket is  “an abstraction   of an   IP  Port” (Swand,  nd.). They first appeared  in early  UNIX systems in 1970’s.

* There are two kinds of sockets: the connection oriented sockets based on TCP and connectionless sockets based on user Datagram Protocol (UDP). While the TCP type  sockets guarantee data arrives in the correct order, the UDP-type do not.One of the   differences between serversocket  and   socket is that  while   serversocket is implemented at the server side so that it can listen to the client’s request and respond to them,

* socket   is   implemented   at   the   client   side   to send request   to the  port of the machine where serversocket is listening.Put differently, the   socket class is   placed on  the  client’s   side which  sends  request to serverside socket  and waits for response from  the server.

* While the  serversocket  is placed  in serverside, which sends requests to clients side socket  and waits for response from client.

* In essence, we need to make an object of socket class for networking application, while for   serverside   networking   application   serversocket   class   is   used.   

* A   method   named serversocket_object.accepts[] is used by server to listen to clients at specific port addresses.In summary we can infer that the socket is completely based on the client’s side while the server socket is based on the server side.
