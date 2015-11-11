namespace java.swift kr.iolo.springboard.thrift
namespace java kr.iolo.springboard.thrift



struct User {
  1:  i32 id;
  2:  string username;
  3:  string password;
}

struct Forum {
  1:  i32 id;
  2:  string title;
}

struct Post {
  1:  i32 id;
  2:  i32 userId;
  3:  i32 forumId;
  4:  string title;
  5:  string content;
  6:  string createdAt;
}

struct Comment {
  1:  i32 id;
  2:  i32 userId;
  3:  i32 postId;
  4:  string content;
  5:  string createdAt;
}

service Springboard {
  i32 createComment(1:  Comment arg0);
  i32 createForum(1:  Forum arg0);
  i32 createPost(1:  Post arg0);
  i32 createUser(1:  User arg0);
  bool deleteComment(1:  i32 arg0);
  bool deleteForum(1:  i32 arg0);
  void deletePost(1:  i32 arg0);
  bool deleteUser(1:  i32 arg0);
  Comment getComment(1:  i32 arg0);
  list<Comment> getComments(1:  i32 arg0);
  Forum getForum(1:  i32 arg0);
  list<Forum> getForums();
  Post getPost(1:  i32 arg0);
  list<Post> getPosts(1:  i32 arg0);
  User getUser(1:  i32 arg0);
  User getUserByUsernameAndPassword(1:  string arg0, 2:  string arg1);
  list<User> getUsers();
  bool updateComment(1:  Comment arg0);
  bool updateForum(1:  Forum arg0);
  bool updatePost(1:  Post arg0);
  bool updateUser(1:  User arg0);
}
