package dto;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserDto extends BaseDto{

  private String userName;
  private String password;
  private List<String> friends;
  private List<String> blockedUsers = new ArrayList<>();

  public UserDto() {
    super();
  }

  public UserDto(String uniqueId) { super(uniqueId); }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public List<String> getFriends() {
    return friends;
  }

  public List<String> getBlockedUsers() { return blockedUsers; }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFriends(List<String> friends) {
    this.friends = friends;
  }

  public void setBlockedUsers(List<String> blockedUsers) {
    this.blockedUsers = blockedUsers;
  }

  public Document toDocument(){
    return new Document()
            .append("userName", userName)
            .append("password", password)
            .append("friends", friends)
            .append("blockedUsers", blockedUsers);
  }

  public static UserDto fromDocument(Document match) {
    var userDto = new UserDto();
    userDto.setUserName(match.getString("userName"));
    userDto.setPassword(match.getString("password"));
    List<String> friends = (List<String>) match.get("friends");
    userDto.setFriends(friends != null ? friends : new ArrayList<>());
    List<String> blockedUsers = (List<String>) match.get("blockedUsers"); // new field
    userDto.setBlockedUsers(blockedUsers != null ? blockedUsers : new ArrayList<>());
    return  userDto;
  }
}
