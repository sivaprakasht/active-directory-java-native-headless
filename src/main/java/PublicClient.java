import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;

public class PublicClient {

    private final static String AUTHORITY = "https://login.microsoftonline.com/common";
    private final static String CLIENT_ID = "c881b25f-e1c2-4321-baf5-81cf9ee40579";

    private final static String SECRET = "9kDNZzKR724tnJ9V8/+Rt/cXQ0w+PuXkXy2vxGhLDoU=";

    public static void main(String args[]) throws Exception {



        String username = "raja@chrischrismc.onmicrosoft.com";
        String password = "ORK33jfk!";
        System.out.print("Fetching Token . . . . . . ");

        AuthenticationResult result = getAccessTokenFromUserCredentials(
                username, password);
        System.out.println("Access Token - " + result.getAccessToken());
        System.out.println("Refresh Token - " + result.getRefreshToken());
        System.out.println("ID Token - " + result.getIdToken());

    }

    private static AuthenticationResult getAccessTokenFromUserCredentials(
            String username, String password) throws Exception {
        AuthenticationContext context = null;
        AuthenticationResult result = null;
        ExecutorService service = null;
        ClientCredential creds = new ClientCredential(CLIENT_ID,SECRET);

        try {
            service = Executors.newFixedThreadPool(1);
            context = new AuthenticationContext(AUTHORITY, false, service);
            Future<AuthenticationResult> future = context.acquireToken("https://graph.windows.net",creds ,null);
            //Future<AuthenticationResult> future = context.acquireToken("https://graph.windows.net",CLIENT_ID,username,password ,null);
            result = future.get();
        } finally {
            // service.shutdown();
        }

        if (result == null) {
            throw new ServiceUnavailableException(
                    "authentication result was null");
        }
        return result;
    }
}
