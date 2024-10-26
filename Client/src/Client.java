import java.util.*;
import java.io.*;
import java.net.*;


public class Client {

    // Global varibales
    static InputStream is;
    static OutputStream os;
    static Socket clientSocket;
    public static void main(String[] args) throws Exception {


        

        
        clientSocket = new Socket("localhost", 3535);
        is = clientSocket.getInputStream();
        os = clientSocket.getOutputStream();



        Scanner k = new Scanner(System.in);

        System.out.print("\n1- Download\n2- Upload\n3- Delete\nWhich operation you want: ");
        int choice = k.nextInt();
        k.nextLine();

        if (choice > 0 && choice < 4){

            System.out.print("Enter file location: ");
            String filePath = k.nextLine();
            k.close();

            switch(choice){
                case 1:{
                    download(filePath);
                    break;
                }
                case 2: {
                    upload(filePath);
                }

                case 3:{
                    delete(filePath);
                }
            }



        }

   


    }






    public static void download(String filePath){

        System.out.println("Hello");
        

        Scanner in = new Scanner(is);
        PrintWriter pw = new PrintWriter(os);

        // Sending READ command to the server
        pw.println("R " + filePath);
        pw.flush();

        // Receiveing the fileSize
        System.out.println("--------------------------------");
        String strSize = in.nextLine();
        System.out.println("Filesize: " + strSize);
        int fileSize = Integer.parseInt(strSize);
        

        if (fileSize == -1){
            System.out.println("File " + filePath + ": Not found");
        }
        else if (fileSize == 0){
            System.out.println("File " + filePath + ": is empty");
        }
        else{
                String fileName[] = filePath.split("\\\\");
                // Recieve the file
                try{
                    
                FileOutputStream fos = new FileOutputStream("E:\\UG-3\\Semester 2\\Special Topics in Software Engineering\\Assignments submission\\TCPSocket\\Client\\src\\" + fileName[fileName.length - 1]);
                
                byte b[] = new byte[4096];

                int sum = 0;

                DataInputStream dis = new DataInputStream(is);

                while (true){
                    int n = dis.read(b, 0, 4096);
                    fos.write(b, 0, n);
                    sum += n;
                    

                    if (sum == fileSize){
                        System.out.println(sum + " bytes downloaded");
                        break;
                    }
                    

                }
                System.out.println("File downloaded successfully");
                fos.close();
                dis.close();

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            in.close();
        }

    
    public static void upload(String filePath){



        try {
            
            PrintWriter pw = new PrintWriter(os);

            // Sending SAVE command to the server
            pw.println("U " + filePath);
            pw.flush();

            // Create a file input stream to read the file to send
            FileInputStream fileIn = new FileInputStream(filePath);

            // Create a buffer to hold the file contents
            byte[] buffer = new byte[4096];

            // Read the file contents into the buffer and send them to the server
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            // Close the file input stream and output stream
            fileIn.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        

       
        

    

    }


    public static void delete(String filePath){
        PrintWriter pw = new PrintWriter(os);

        // Sending SAVE command to the server
        pw.println("D " + filePath);
        pw.flush();


    }



}
