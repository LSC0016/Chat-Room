package dao;

import com.mongodb.client.MongoCollection;
import dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UserDao extends BaseDao<UserDto> {

  private static UserDao instance;

  private UserDao(MongoCollection<Document> collection){
    super(collection);
  }

  public static UserDao getInstance(){
    if(instance != null){
      return instance;
    }
    instance = new UserDao(MongoConnection.getCollection("UserDao"));
    return instance;
  }

  public static UserDao getInstance(MongoCollection<Document> collection){
    instance = new UserDao(collection);
    return instance;
  }

  public UserDto getUserById(String userId) {
    Document filter = new Document().append("_id", new ObjectId(userId));
    Document userDocument = collection.find(filter).first();
    return userDocument != null ? UserDto.fromDocument(userDocument) : null;
  }

  public void update(UserDto userDto) {
    Document filter = new Document().append("userName", userDto.getUserName());
    Document update = new Document().append("$set", userDto.toDocument());
    collection.updateOne(filter, update);
  }

  public void blockUser(String userId, String blockedUserId) {
    UserDto user = getUserById(userId);
    if (user != null) {
      user.getBlockedUsers().add(blockedUserId);
      update(user);
    }
  }



  public void delete(Document filter) {
    List<UserDto> users = query(filter);
    if (users.size() > 0) {
      UserDto user = users.get(0);
      super.delete(user.getUniqueId());
    }
  }
  @Override
  public void put(UserDto messageDto) {
    collection.insertOne(messageDto.toDocument());
  }

  public List<UserDto> query(Document filter){
    return collection.find(filter)
            .into(new ArrayList<>())
            .stream()
            .map(UserDto::fromDocument)
            .collect(Collectors.toList());
  }

}
