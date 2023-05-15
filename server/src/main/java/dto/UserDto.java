package dto;

import org.bson.Document;

import java.util.List;

public class UserDto extends BaseDto{

  private String userName;
  private String password;
<<<<<<< HEAD
  private boolean blocked;
=======
  private List<String> friends;
>>>>>>> 7e7c0745d169ebaa5c26851842606ea2d3c5c2e3

  public UserDto() {
    super();
  }

  public UserDto(String uniqueId) {
    super(uniqueId);
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public List<String> getFriends() {
    return friends;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

<<<<<<< HEAD
  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
=======
  public void setFriends(List<String> friends) {
    this.friends = friends;
>>>>>>> 7e7c0745d169ebaa5c26851842606ea2d3c5c2e3
  }

  public Document toDocument(){
    return new Document()
<<<<<<< HEAD
            .append("userName", userName)
            .append("password", password)
            .append("blocked", blocked);
=======
            .append("userName", userName)
            .append("password", password)
            .append("friends", friends);
>>>>>>> 7e7c0745d169ebaa5c26851842606ea2d3c5c2e3
  }

  public static UserDto fromDocument(Document match) {
    var userDto = new UserDto();
    userDto.setUserName(match.getString("userName"));
    userDto.setPassword(match.getString("password"));
    userDto.setBlocked(match.getBoolean("blocked"));
    return  userDto;
  }
}
