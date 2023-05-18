package handler;

import request.ParsedRequest;

public class HandlerFactory {
  // routes based on the path. Add your custom handlers here
  public static BaseHandler getHandler(ParsedRequest request) {
    switch (request.getPath()) {
      case "/createUser":
        return new CreateUserHandler();
      case "/login":
        return new LoginHandler();
      case "/getConversations":
        return new GetConversationsHandler();
      case "/getConversation":
        return new GetConversationHandler();
      case "/createMessage":
        return new CreateMessageHandler();
      case "/blockUser":
        return new BlockUserHandler();
      case "/unregUser":
        return new UnregUserHandler();
      case "/logout":
        return new LogoutHandler();
      case "/registration":
        return new RegistrationHandler();
      case "/addFriend":
        return new AddFriendHandler();
      default:
        return new FallbackHandler();
    }
  }

}
