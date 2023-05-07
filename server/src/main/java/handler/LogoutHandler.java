package handler;

import dao.AuthDao;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class LogoutHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        AuthDao authDao = AuthDao.getInstance();
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        if(!authResult.isLoggedIn){
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        }
        // Get the hash value from the "auth" header of the request
        String hash = request.getHeaderValue("auth");
        // Delete the hash value from the AuthDao collection
        authDao.deleteUser(hash);
        System.out.println("You have been logged out successfully.");
        System.out.println("Hash value deleted: " + hash + " at " + java.time.LocalDateTime.now());

        var res = new RestApiAppResponse<>(true, null, "You have been logged out successfully.");
        return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
    }
}