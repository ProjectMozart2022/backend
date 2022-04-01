package api.security;

import spark.Request;

public class SecurityService {
    public static String getBearerToken(Request request){
        String authorization = request.headers("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
