package classdemo1;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ClientHandler extends Thread{
    Socket client;
    BufferedReader br;
    PrintWriter pw;
    String  name;

    public ClientHandler(Socket client) {
        this.client = client;
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.pw = new PrintWriter(client.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void greeting() throws IOException {
        pw.println("Hej Hvad hedder du?");
        name = br.readLine();
        pw.printf("Hej %s \n",name);
        //int ans = ir.read();
        //pw.close();
        //br.close();
        //client.close();
    }

    public void protocol() throws IOException {
        pw.printf("Hej %s, Vi vil gerne quizze",name);
        pw.printf("Indtast val\n");
        String valg = br.readLine();
        while(!valg.equalsIgnoreCase("bye")) {
            switch (valg) {
                case "GEO": geoHandler();break;
                case "LIT": litHandler();break;
                case "ADD": pw.println(valg + "ADD");break;
                default: pw.println("prøv igen");
            }
            pw.println("Well done. Whats next?");
            valg = br.readLine();
        }
    }

    private void litHandler() {
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        questions.add("Hvem har skrevet Harry Potter?");
        answers.add("J.K.Rowling");
        questions.add("Hvem har skrevet den lille havfrue");
        answers.add("H.C.Andersen");
        questions.add("Hvem har skrevet ringenes herre?");
        answers.add("J.R.R.Tolkien");
        for(int i = 0; i< questions.toArray().length; i++) {

            pw.println(questions.get(i));
            try {
                String ans = br.readLine();
                if (ans.equals(answers.get(i))) {
                    pw.println("Korrekt, videre");
                } else{
                    pw.println("Forkert, videre");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void geoHandler() {
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        questions.add("Hvor bor Kurt?");
        answers.add("Oslo");
        questions.add("Hvor bor Anton?");
        answers.add("Berlin");
        questions.add("Hvor bor Viggo?");
        answers.add("Tokyo");
        pw.println("Kan du svare på flg:?");

        for (int i =0; i<questions.toArray().length;i++){
            try {
                pw.println(questions.get(i));

                String ans = br.readLine();
                if (ans.equals(answers.get(i))) {
                    pw.println("Fint. Hvad nu?");
                } else {
                    pw.println("Dumkopff ..");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

public class EchoServer {
    public static final int DEFAULT_PORT = 2345;

    public static void main(String[] args) {
        int port = 8188;
        if (args.length==1) {
            port=Integer.parseInt(args[0]);
        }
        try {
            ServerSocket ss = new ServerSocket(8188);
            Socket client = ss.accept();
            //DataInputStream ds = new DataInputStream(client.getInputStream());
            //InputStreamReader ir = new InputStreamReader(client.getInputStream());
            //Scanner sc = new Scanner(ir);
            ClientHandler cl = new ClientHandler(client);
            cl.greeting();
            cl.protocol();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}