package dao;

import com.mongodb.client.MongoCollection;
import dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

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

  public void update(UserDto userDto) {
    Document filter = new Document().append("userName", userDto.getUserName());
    Document update = new Document().append("$set", userDto.toDocument());
    collection.updateOne(filter, update);
  }

  @Override
  public void put(UserDto messageDto) {
    collection.insertOne(messageDto.toDocument());
  }

  public List<UserDto> query(Document filter){
    filter.append("blocked", false); // only retrieve users that are not blocked
    return collection.find(filter)
        .into(new ArrayList<>())
        .stream()
        .map(UserDto::fromDocument)
        .collect(Collectors.toList());
  }
  public void delete(Document filter) {
    List<UserDto> users = query(filter);
    if (users.size() > 0) {
      UserDto user = users.get(0);
      super.delete(user.getUniqueId());
    }
  }

  public void blockUser(String userName) {
    Document filter = new Document("userName", userName);
    Document update = new Document("$set", new Document("blocked", true));
    collection.updateOne(filter, update);
  }
}
