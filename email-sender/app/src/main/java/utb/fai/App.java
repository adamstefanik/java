package utb.fai;

public class App {

    public static void main(String[] args) {
        if (args.length < 6) {
            System.err.println("Not enough arguments. Usage: java -jar app.jar <host> <port> <sender> <recipient> <subject> <body>");
            return;
        }

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String sender = args[2];
            String recipient = args[3];
            String subject = args[4];
            String body = args[5];

            EmailSender senderObj = new EmailSender(host, port);
            senderObj.send(sender, recipient, subject, body);
            senderObj.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}