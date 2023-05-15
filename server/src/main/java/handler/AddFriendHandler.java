package handler;

import com.google.gson.Gson;
import dao.UserDao;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;

class AddFriendDto {
    String userName;
    String friendUserName;
}

public class AddFriendHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        var res = new HttpResponseBuilder();
        AddFriendDto addFriendDto = new Gson().fromJson(request.getBody(), AddFriendDto.class);
        UserDao userDao = UserDao.getInstance();

        var userQuery = new Document().append("userName", addFriendDto.userName);
        var friendQuery = new Document().append("userName", addFriendDto.friendUserName);

        var userResult = userDao.query(userQuery);
        var friendResult = userDao.query(friendQuery);

        if(userResult.size() == 0 || friendResult.size() == 0){
            res.setStatus("400 Bad Request");
            res.setBody("Either the user or the friend to be added does not exist.");
        }else{
            UserDto userDto = userResult.get(0);
            userDto.getFriends().add(addFriendDto.friendUserName);
            userDao.update(userDto);
            res.setStatus("200 OK");
            res.setBody("Friend added successfully.");
        }

        return res;
    }
}
