import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String > words = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                words.add(parameters[1]);
            }
            return "added " + parameters[1];
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            String ret = "[";
            if (parameters[0].equals("s")) {
                for (String s : words) {
                    if (s.contains(parameters[1])) {
                        ret = ret + " " + s;
                    }
                }
            }
            return ret + " ]";
        } else {
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
