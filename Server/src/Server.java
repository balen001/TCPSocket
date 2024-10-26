import java.net.*;
import java.util.Scanner;
import java.io.*;



public class Server {

    // Global variables
    static InputStream is;
    static OutputStream os;
    static Scanner sc;
    static PrintWriter pw;
    static Socket client;

    static ServerSocket serverSocket;
    public static void main(String[] args) throws Exception {

        //intialize serverSocket
        serverSocket = new ServerSocket(3535);
        while(true){
            //Accept client connection
            client = serverSocket.accept();

            // Create a new thread to handle the client
            Thread clientThread = new Thread(new ClientHandler(client));

            // Start the thread to handle the client
            clientThread.start();



            //--------------------------
            // is = client.getInputStream();
            // os = client.getOutputStream();

            // sc = new Scanner(is);
            // pw = new PrintWriter(os);

            //Get the command from client
            // String command = sc.nextLine();

            // //Get the filename
            // String filePath = command.substring(2);
            

            
            // switch(command.substring(0, 1)){
            //     case "R":{
            //         Read(filePath);
            //         break;
            //     }
            //     case "U":{
            //         String words[] = filePath.split("\\\\");
            //         Save(words[words.length - 1]);
            //         break;
            //     }

            //     case "D":{
            //         delete(filePath);
                    
            //     }

            //     sc.close();
            // }

        
        }

    }
    //main



    static class ClientHandler implements Runnable {
        Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {


            is = client.getInputStream();
            os = client.getOutputStream();

            sc = new Scanner(is);
            pw = new PrintWriter(os);
                // Create an input stream to receive data from the client
                String command = sc.nextLine();

            //Get the filename
            String filePath = command.substring(2);
            

            
            switch(command.substring(0, 1)){
                case "R":{
                    Read(filePath);
                    break;
                }
                case "U":{
                    String words[] = filePath.split("\\\\");
                    Save(words[words.length - 1]);
                    break;
                }

                case "D":{
                    delete(filePath);
                    
                }

                sc.close();
            }

                

                // Close the client socket
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }











    
    public static void Read(String filePath){
        


        try{
            
            //check if exists and then send it
            File f = new File(filePath);
            

            if(f.exists() && f.isFile()){
                

                int size = (int) f.length();
                pw.println(size);
                pw.flush();

                if (size > 0){
                    FileInputStream fis = new FileInputStream(f);
                    DataInputStream dis = new DataInputStream(fis);
                    byte b[] = new byte[size];
                    dis.readFully(b);
                    System.out.println("Read file successfuly");
                    fis.close();
                    // sending file to the client 
                    DataOutputStream dos = new DataOutputStream(os);
                    dos.write(b);
                    // file sent successfully

                }
                else{
                    System.out.println("else");
                    pw.println(-1);
                    pw.flush();
                }

            }
        }
        catch(Exception e){e.printStackTrace();}

    }

    public static void Save(String fileName){

        try {
           

            // Create an input stream to receive data from the client
            InputStream in = client.getInputStream();

            // Create a file output stream to write the received file
            FileOutputStream fileOut = new FileOutputStream("E:\\UG-3\\Semester 2\\Special Topics in Software Engineering\\Assignments submission\\TCPSocket\\Server\\src\\"+ fileName);

            // Create a buffer to hold the received file contents
            byte[] buffer = new byte[4096];

            // Read the received file contents into the buffer and write them to the file
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            

            // Close the file output stream and input stream
            fileOut.close();
            in.close();

            // Close the client socket
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void delete(String filePath){
        File file = new File(filePath);

        if(file.exists() && file.isFile()){
            file.delete();
            System.out.println("File deleted successfully");
        }
        else{
            System.out.println("Failed to delete the file");
        }
    }
}
//class
