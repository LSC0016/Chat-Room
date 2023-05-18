package handler;
import dao.UserDao;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder; 
import handler.AuthFilter.AuthResult;
import response.RestApiAppResponse;


public class SearchUserHandler implements BaseHandler {
     @Override
        public HttpResponseBuilder handleRequest(ParsedRequest request) {
    
            // gets username 
            UserDao userDao = UserDao.getInstance();
            AuthResult authResult = AuthFilter.doFilter(request);
            if(!authResult.isLoggedIn){
              return new HttpResponseBuilder().setStatus(StatusCodes.UNAUTHORIZED);
            }
        
            var filter = new Document("userName", authResult.userName);
        
            var res = new RestApiAppResponse<>(true, userDao.query(filter), null);
            return new HttpResponseBuilder().setStatus("200 OK").setBody(res);
          }
        
    }
        
