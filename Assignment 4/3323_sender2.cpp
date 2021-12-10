//  sender for Selective repeat
//Write a program to simulate Go back N and Selective Repeat Modes of Sliding Window Protocol in Peer-to-Peer mode. 

#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>
using namespace std;

const int MAXPENDING=5 ;

int main(){
    int sendSock = socket(PF_INET, SOCK_STREAM, 0) ;
    if(sendSock < 0){ cout << "Error with server socket";  return 0;}
    struct sockaddr_in serverAddr ;
    socklen_t serverLen ;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1") ; // as it is peer to peer it will recieve from only one address 
    serverAddr.sin_port = htons(5105);
    serverLen = sizeof(serverAddr);
    if(bind(sendSock,(struct sockaddr*)& serverAddr, sizeof(serverAddr) )<0 ){
        cout << "Error : not able to BIND "<<endl  ; return 0 ;
    }
    cout <<"Waiting for connection"<<endl ;
    if(listen(sendSock, MAXPENDING)<0){
        cout << "Error : not able to LISTEN "<<endl  ; return 0 ;
    }
    int recvSock = accept(sendSock,(struct sockaddr*)& serverAddr, (socklen_t *)&serverLen ) ;
    if(recvSock<0){
        cout << "Error : not able to ACCEPT "<<endl  ; return 0 ;
    }
    cout <<" ---- Connection Successfull --- \n" ;
    string pack ="abcde12345" ; // packets 
    int zx1=5, x1i=0 ;
    while (zx1--){ 
        for(int x1=x1i;x1<pack.size();x1++){
            char inp[2]={ pack[x1] ,'\0'} ;
            send(recvSock, inp, 2,0) ;             // sending message to reciever
            cout << "sent "<<x1<<endl ;
            char recvbuf[2] ;
            int inLen;
            if( (inLen = recv(recvSock, recvbuf, 2, 0)) <0){       // accepting response from reciever
            cout <<"Error Recieving !"<< endl ; return 0;   
            }
            if(recvbuf[0]!='2'){                                        // displaying message correspnding the response from reciever
                cout << "*** error ***\n" ;
                shutdown(sendSock,2) ;  
                break ;
            }
        }
        char recvbuf[2] ;
        int inLen;
        if( (inLen = recv(recvSock, recvbuf, 2, 0)) <0){       // accepting response from reciever
        cout <<"Error Recieving !"<< endl ; return 0;   
        }
        if(recvbuf[0]!='-'){                                        // displaying message correspnding the response from reciever
            cout << "*** Resending packets ***\n" ;
            char inp[2]={ pack[recvbuf[0]-'0'] ,'\0'} ;
            send(recvSock, inp, 2,0) ;             // sending message to reciever
            cout << "sent"<<recvbuf[0]<<"\n" ;
        } 
        cout << "-------Packets recieved successfully !! -------" ; break ; 
        cout << endl<<endl<<endl;
    }   
        shutdown(sendSock,2) ;      // closing the socket connection
    return 0 ;

}
