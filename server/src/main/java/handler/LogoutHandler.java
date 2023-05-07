package handler;

import dao.AuthDao;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class LogoutHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        AuthDao authDao = AuthDao.getInstance();

        // Get the hash value from the "auth" header of the request
        String hash = request.getHeaderValue("auth");
        // Delete the hash value from the AuthDao collection
        authDao.deleteUser(hash);


        var res = new RestApiAppResponse<>(true, null, "You have been logged out successfully.");
        return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
    }
}