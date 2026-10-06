package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

        // Read initial welcome message
        expect(220);

        // Send EHLO
        command("EHLO localhost", 250);
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        command("MAIL FROM:<" + from + ">", 250);
        command("RCPT TO:<" + to + ">", 250, 251);
        command("DATA", 354);

        StringBuilder msg = new StringBuilder();
        msg.append("From: ").append(from).append("\r\n");
        msg.append("To: ").append(to).append("\r\n");
        msg.append("Subject: ").append(subject).append("\r\n");
        msg.append("MIME-Version: 1.0\r\n");
        msg.append("Content-Type: text/plain; charset=UTF-8\r\n");
        msg.append("Content-Transfer-Encoding: 8bit\r\n");
        msg.append("\r\n");

        for (String line : text.split("\r?\n", -1)) {
            if (line.startsWith(".")) {
                line = "." + line;
            }
            msg.append(line).append("\r\n");
        }
        msg.append(".\r\n");

        out.write(msg.toString());
        out.flush();
        expect(250);
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                command("QUIT", 221);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void command(String cmd, int... okCodes) throws IOException {
        out.write(cmd + "\r\n");
        out.flush();
        expect(okCodes);
    }

    private String readResponse() throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            // Check if it's the last line of a multi-line response (4th char is space or line length is 3)
            if (line.length() < 4 || line.charAt(3) == ' ') {
                return line;
            }
        }
        throw new IOException("Server closed connection.");
    }

    private void expect(int... okCodes) throws IOException {
        String response = readResponse();
        int code = Integer.parseInt(response.substring(0, 3));
        for (int ok : okCodes) {
            if (code == ok) return;
        }
        throw new IOException("Unexpected server response: " + response);
    }
}
