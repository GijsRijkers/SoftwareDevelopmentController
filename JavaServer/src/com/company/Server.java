package com.company;


import netscape.javascript.JSObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    List<String> sequenceList = new ArrayList<>();

    String sequence1;
    String sequence2;
    String sequence3;
    String sequence4;
    String sequence5;
    String flushsequence;


    public void getJson(){
        
        try{
        sequenceList.add(sequence1 = Files.readString(Paths.get("src/com/company/jsonFiles/Path1.json"), StandardCharsets.US_ASCII));
        sequenceList.add(sequence2 = Files.readString(Paths.get("src/com/company/jsonFiles/Path2.json"), StandardCharsets.US_ASCII));
        sequenceList.add(sequence3 = Files.readString(Paths.get("src/com/company/jsonFiles/Path3.json"), StandardCharsets.US_ASCII));
        sequenceList.add(sequence4 = Files.readString(Paths.get("src/com/company/jsonFiles/Path4.json"), StandardCharsets.US_ASCII));
        sequenceList.add(sequence5 = Files.readString(Paths.get("src/com/company/jsonFiles/Path5.json"), StandardCharsets.US_ASCII));
        flushsequence = Files.readString(Paths.get("src/com/company/jsonFiles/Flushout.json"), StandardCharsets.US_ASCII);
        
        

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public void start(int port) {


        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Wachten op connecite......");
            clientSocket = serverSocket.accept();

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client verbonden");
            StringBuilder sb = new StringBuilder();

            while (true){
                for (String sequence: sequenceList) {
                    out.print(sequence.length() + ":" + sequence);
                    out.flush();
                    System.out.println(sequence + "JSON verzonden");
                    TimeUnit.SECONDS.sleep(15);
                    out.print(flushsequence.length() + ":" + flushsequence);
                    out.flush();
                    TimeUnit.SECONDS.sleep(2);


                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
