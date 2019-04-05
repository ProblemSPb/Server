// this is the server
// to connect to this local server:
// 1. open the browser
// 2. in the address put local IP address + :8080
// http://10.77.107.159:8080
// the server should be running

// https://start.spring.io/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class OldSchoolHttpServer {
    static final int port = 8080;
    static final String newLine = "\r\n";

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(port);

        while (true) {
            Socket connection = socket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            PrintStream pout = new PrintStream(out);

            // read first line of request
            String request = in.readLine();
            if (request == null) continue;

            // we ignore the rest
            while (true) {
                String ignore = in.readLine();
                if (ignore == null || ignore.length() == 0) break;
            }

            if (!request.startsWith("GET ") || !(request.endsWith(" HTTP/1.0") || request.endsWith(" HTTP/1.1"))) {
                // bad request
                pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
            } else {
                String response = "Hello, World!";

                pout.print(
                        "HTTP/1.0 200 OK" + newLine +
                                "Content-Type: text/plain" + newLine +
                                "Date: " + new Date() + newLine +
                                "Content-length: " + response.length() + newLine + newLine +
                                response
                );
            }

            pout.close();
        }

    }
}