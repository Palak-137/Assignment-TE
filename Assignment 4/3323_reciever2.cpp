//  reciever for Selective repeat
//Write a program to simulate Go back N and Selective Repeat Modes of Sliding Window Protocol in Peer-to-Peer mode. 

#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>
using namespace std;

const int MAXPENDING=5 ;

int main(){
    int recvSock, sendSock, recvMsgSize;
    recvSock = socket(PF_INET, SOCK_STREAM, 0) ;
    if(recvSock < 0){ cout << "Error with client socket";  return 0;}
    struct sockaddr_in serverAddr ;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
    serverAddr.sin_port = htons(5105);
    if(connect(recvSock,(struct sockaddr*)&serverAddr,sizeof(serverAddr) )<0){
        cout << "Error : not able to connect"<<endl  ; return 0 ;
    }    
    cout <<" ---- Connection Successfull --- \n" ;
    string pack ="abcde12345" ; // packets 
    int zx1=5;
    while (zx1--){
        string str ="" ;
        for(int x1=0;x1<pack.size();x1++){
            char recvbuf[2] ;
            int inLen;
            cout <<"recieved\n" ;
            if( (inLen = recv(recvSock, recvbuf, 2, 0)) <0){       // accepting response from reciever
            cout <<"Error Recieving !"<< endl ; return 0;   
            }
            str += recvbuf[0] ;
            char inp[2]={ '2' ,'\0'} ;
            send(recvSock, inp, 2,0) ;             // sending message to reciever
        }
        cout << "Packets recieved : "<<str << endl;
        // cout <<"To generate error enter 1, To exit press 0 ";
        char ack='1';// cin >> ack ;
        if(ack=='1'){
            int erp = rand()%(pack.size()) ;
            cout << "Error in "<<erp<<"th packet\n" ;
            char inp[2]={ erp+'0' ,'\0'} ;
            send(recvSock, inp, 2,0) ;             // sending message to reciever
            char recvbuf[2] ;
            int inLen;
            cout <<"recieved\n" ;
            if( (inLen = recv(recvSock, recvbuf, 2, 0)) <0){       // accepting response from reciever
            cout <<"Error Recieving !"<< endl ; return 0;   
            }
            cout << "Packets recieved : "<<recvbuf[0] << endl;
        }
        else{
            char inp[2]={'-' ,'\0'} ;
            send(recvSock, inp, 2,0) ;             // sending message to reciever
        }
        
        cout << endl<<endl<<endl;
        break ;
    }
        shutdown(recvSock,2) ;      // closing the socket connection
    return 0 ;

}
