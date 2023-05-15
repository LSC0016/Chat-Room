package dto;

import org.bson.Document;

import java.util.List;

public class UserDto extends BaseDto{

  private String userName;
  private String password;
  private List<String> friends;

  private boolean blocked;


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

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }
  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public Document toDocument(){
    return new Document()

        .append("userName", userName)
        .append("password", password)
            .append("friends", friends)
            .append("blocked", blocked);
  }

  public static UserDto fromDocument(Document match) {
    var userDto = new UserDto();
    userDto.setUserName(match.getString("userName"));
    userDto.setPassword(match.getString("password"));
    userDto.setBlocked(match.getBoolean("blocked"));
    return  userDto;
  }
}
