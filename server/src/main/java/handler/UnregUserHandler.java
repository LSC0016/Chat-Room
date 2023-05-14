package handler;

import dao.UserDao;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

public class UnregUserHandler implements BaseHandler{

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        // Get the username from the auth header
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        String userName = authResult.userName;

        // Delete the user from the database
        UserDao userDao = UserDao.getInstance();
        var query = new Document("userName", userName);
        userDao.delete(query);

        // Create and return the response
        RestApiAppResponse res = new RestApiAppResponse<>(true, null, "User unregistered");
        return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
    }
}
