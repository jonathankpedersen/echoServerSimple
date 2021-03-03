package classdemo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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
        //TO do: break "UPPER#Hello World" into token UPPER and content Hello World
        String[] kurt = valg.split("#");
        while(!valg.equalsIgnoreCase("bye")) {
            switch (kurt[0]) {
                case "GEO": geoHandler();break;
                case "LIT": litHandler();break;
                case "ADD": pw.println(valg + "ADD");break;
                case "UPPER": pw.println(kurt[1].toUpperCase());break;
                case "LOWER": pw.println(kurt[1].toLowerCase());break;
                case "TRANSLATE": translate(kurt[1]);break;
                case "Reverse": reverse(kurt[1]); break;
                case "CLOSE":  ;break;
                default: pw.println("prøv igen");
            }
            pw.println("Well done. Whats next?");
            valg = br.readLine();
        }
    }
    private void translate(String string){
         Map<String,String> translated = new HashMap<String,String>();
         translated.put("hund","dog");
    }
    private void reverse(String string){
        
    }

    /*private void requestReader(){
        Scanner myObj = new Scanner(System.in);
        String besked = myObj.nextLine();
        if(besked.matches("UPPER#")){
            String newMessage = besked.replace("UPPER#", "");
            newMessage.toUpperCase();
        } if (besked.matches("LOWER#")){
            String newMessage = besked.replace("LOWER#", "");
            newMessage.toLowerCase();
        }

    }*/

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