package handler;


import dao.AuthDao;
import dao.UserDao;
import request.ParsedRequest;
import response.HttpResponseBuilder;


public class BlockUserHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {

        var res = new HttpResponseBuilder();
        UserDao userDao = UserDao.getInstance();
        AuthDao authDao = AuthDao.getInstance();
        AuthFilter.AuthResult authResult = AuthFilter.doFilter(request);
        if(!authResult.isLoggedIn){
            return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
        }

        String userId = authResult.userName;
        String blockedUserId = request.getBody();

        userDao.blockUser(userId, blockedUserId);

        res.setStatus("200 OK");
        return res;
    }
}

