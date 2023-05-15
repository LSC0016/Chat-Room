package handler;

import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;

import java.util.List;
import java.util.Map;

import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.HttpResponseBuilder;

class RegistrationDto {
    String userName;
    String password;
}

public class RegistrationHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        var res = new HttpResponseBuilder();
        RegistrationDto registrationDto = GsonTool.gson.fromJson(request.getBody(), RegistrationDto.class);
        UserDao userDao = UserDao.getInstance();

        var existingUserQuery = new Document().append("userName", registrationDto.userName);
        var existingUserResult = userDao.query(existingUserQuery);

        if (existingUserResult.size() > 0) {
            res.setStatus("400 Bad Request");
            res.setBody("Username already exists");
        } else {
            UserDto newUser = new UserDto();
            newUser.setUserName(registrationDto.userName);
            newUser.setPassword(DigestUtils.sha256Hex(registrationDto.password));

            userDao.put(newUser);
            res.setStatus("201 Created");
            res.setBody("User created successfully");
        }
        return res;
    }
}

