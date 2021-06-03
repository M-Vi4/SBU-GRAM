package Server;

import Common.Command;
import Common.Comment;
import Common.Post;
import Common.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class API {
    static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static Map<String , Object> isUsernameExist(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        User user = Server.users.get(username);
        Boolean exists = (user != null);
        answer.put("answer" , exists);
        answer.put("command" , Command.USERNAME_UNIQUE);
        return answer;
    }

    public static Map<String , Object> login(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String password = (String) receive.get("password");
        User user = Server.users.get(username);
        String passwordS = Server.users.get(username).getPassword();
        if (Server.users.get(username) == null) {
            answer.put("answer", false);
        }
        else {
            answer.put("answer" , true);
            Date date = new Date();
            System.out.println("action: connect, login\n" + username + "login\n" + "time:" + format.format(date));
        }
        answer.put("command", Command.LOGIN);

        return answer;
    }

    public static Map<String , Object> signup(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        if (Server.users.get(username) != null){
            answer.put("answer" , false);
        }
        else {
            String name = (String) receive.get("name");
            String lastname = (String) receive.get("lastname");
            String password = (String) receive.get("password");
            User user = new User(name , lastname , username , password);
            Server.users.put(username , user);
            answer.put("answer" , true);
            Date date = new Date();
            System.out.println(username + " register " + user.getProfile().getUrl() + "\n" + format.format(date));
        }
        answer.put("command" , Command.SIGNUP);
        return answer;
    }

    public static Map<String , Object> publishPost(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        Post post = (Post) receive.get("post");
        Server.users.get(username).addPost(post);
        answer.put("answer" , true);
        answer.put("command" , Command.PUBLISH_POST);
        Date date = new Date();
        System.out.println(username + " publish\n" + "message: " + post.getTitle() + " " + post.getPublisher() + "\n" + format.format(date));
        return answer;
    }

    public static Map<String , Object> follow(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String target = (String) receive.get("target");
        Server.users.get(username).addFollowings(Server.users.get(target));
        Server.users.get(target).addFollowers(Server.users.get(username));
        answer.put("answer" , true);
        answer.put("command" , Command.FOLLOW);
        Date date = new Date();
        System.out.println("action: follow\n" + username + " action\n" + "message: " + target + "\ntime: " + format.format(date));
        return answer;
    }

    public static Map<String , Object> unfollow(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String target = (String) receive.get("target");
        Server.users.get(username).removeFollowings(Server.users.get(target));
        Server.users.get(target).removeFollowers(Server.users.get(username));
        answer.put("answer" , true);
        answer.put("command" , Command.UNFOLLOW);
        Date date = new Date();
        System.out.println("action: unfollow\n" + username + " action\n" + "message: " + target + "\ntime: " + format.format(date));
        return answer;
    }

    public static Map<String , Object> like(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String targetUsername = (String) receive.get("targetUsername");
        Post likedPost = (Post) receive.get("post");
        User target = Server.users.get(targetUsername);
        for (Post post:target.getPosts()){
            if (post.equals(likedPost))
                post.addLike(Server.users.get(username));
        }
        answer.put("answer" , true);
        answer.put("command" , Command.LIKE);
        Date date = new Date();
        System.out.println("action: like\n" + username + "like\n" + "message: " + targetUsername + " " + likedPost.getTitle() + "\ntime: " + format.format(date));
        return answer;
    }

    public static Map<String , Object> dislike(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String targetUsername = (String) receive.get("targetUsername");
        Post dislikedPost = (Post) receive.get("post");
        User target = Server.users.get(targetUsername);
        for (Post post:target.getPosts()){
            if (post.equals(dislikedPost))
                post.removeLike(Server.users.get(username));
        }
        answer.put("answer" , true);
        answer.put("command" , Command.DISLIKE);
        Date date = new Date();
        System.out.println("action: dislike\n" + username + "dislike\n" + "message: " + targetUsername + " " + dislikedPost.getTitle() + "\ntime: " + format.format(date));
        return answer;
    }

    public static Map<String , Object> addComment(Map<String , Object> receive){
        Map<String , Object> answer = new HashMap<>();
        String username = (String) receive.get("username");
        String targetUsername = (String) receive.get("targetUsername");
        Post post = (Post) receive.get("post");
        Comment comment = (Comment) receive.get("comment");
        User target = Server.users.get(targetUsername);
        for (Post post1 : target.getPosts()){
            if (post1.equals(post))
                post1.addComment(comment);
        }
        answer.put("answer" , true);
        answer.put("command" , Command.ADD_COMMENT);
        Date date = new Date();
        System.out.println(username + " comment\n" + "message: " + post.getTitle() + "\n" + "time: " + format.format(date));
        return answer;
    }


}