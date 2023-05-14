package handler;

import request.ParsedRequest;
import response.HttpResponseBuilder;


public class BlockUserHandler implements BaseHandler {

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        //TODO
        return new HttpResponseBuilder().setStatus("404 Not Found");
    }
}