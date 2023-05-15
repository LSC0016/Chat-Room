package handler;

<<<<<<< HEAD
import request.ParsedRequest;
import response.HttpResponseBuilder;


=======
import dao.AuthDao;
import dao.UserDao;
import request.ParsedRequest;
import response.HttpResponseBuilder;

>>>>>>> 7e7c0745d169ebaa5c26851842606ea2d3c5c2e3
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
