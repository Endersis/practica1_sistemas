#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 5001

int main() {
    int server_fd, new_socket;
    struct sockaddr_in address;
    int addrlen = sizeof(address);
    int received_number, incremented_number;
    
    // Crear socket
    server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd == -1) {
        perror("Error al crear el socket");
        exit(EXIT_FAILURE);
    }
    
    // Configurar dirección del servidor
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT);
    
    // Vincular socket
    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
        perror("Error en bind");
        exit(EXIT_FAILURE);
    }
    
    // Escuchar conexiones
    if (listen(server_fd, 3) < 0) {
        perror("Error en listen");
        exit(EXIT_FAILURE);
    }
    
    printf("Servidor esperando conexiones...\n");
    
    while(1) {
        // Aceptar conexión
        new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen);
        if (new_socket < 0) {
            perror("Error en accept");
            exit(EXIT_FAILURE);
        }
        
        printf("Cliente conectado\n");
        
        while(1) {
            // Recibir número
            if (recv(new_socket, &received_number, sizeof(int), 0) <= 0) {
                break;
            }
            printf("Número recibido: %d\n", received_number);
            
            if(received_number == 0) {
                printf("Cliente envió 0. Cerrando conexión.\n");
                break;
            }
            
            // Incrementar y enviar de vuelta
            incremented_number = received_number + 1;
            send(new_socket, &incremented_number, sizeof(int), 0);
            printf("Número enviado: %d\n", incremented_number);
        }
        
        close(new_socket);
    }
    
    return 0;
}
