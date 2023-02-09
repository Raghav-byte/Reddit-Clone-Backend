package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.Subreddit;
import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Model.UserLoginDetails;
import com.example.RedditClone.Reddit.Repository.SubredditRepo;
import com.example.RedditClone.Reddit.Repository.UserLoginDetailsRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Requests.UserFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private SubredditRepo subRepo;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserLoginDetailsRepo userLoginDetailsRepo;
    @Autowired
    private MiscellaneousService miscellaneousService;

    //CREATE USER
    public User createUser(User user) {
        userRepo.save(user);
        sendEmail(user.getUserId());
        return userRepo.save(user);
    }

    //LIST OF USER
    public List<User> getAllUser() {
        return userRepo.findAll(Sort.by("name").ascending());
    }

    //GET USER BY ID
    public Optional<User> getUserById(UUID userId) {
        return userRepo.findById(userId);
    }

    //GET USER BY STATUS (ACTIVE/INACTIVE)
    public List<User> getUserByStatus(boolean status) {
        return userRepo.findByStatus(status);
    }

    //UPDATE USER
    public User updateUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));

        Update update = new Update();
        if (!user.getName().isEmpty()) {
            update.set("name", user.getName());
        }
        if (user.getDateOfBirth() != null) {
            update.set("dateOfBirth", user.getDateOfBirth());
        }
        if (user.getAddress() != null) {
            update.set("address", user.getAddress());
        }
        if (user.getContactInformation() != null) {
            update.set("contactInformation", user.getContactInformation());
        }
        update.set("updatedTimeStamp", new Date());

        return mongoTemplate.findAndModify(query, update, User.class);
    }

    //DELETE USER
    public String deleteUser(UUID userId) {
        userRepo.deleteById(userId);
        return "User Deleted Successfully";
    }

    //CHANGING STATUS OF USER
    public String changeStatus(boolean status, UUID userId) {
        Optional<User> user = userRepo.findById(userId);
        List<Subreddit> subreddits = subRepo.findAll();
        List<Subreddit> temp = new ArrayList<>();

        //FINDING ALL THE SUBS WHERE USER IS PRESENT AND STORING THAT SUBS IN TEMP
        subreddits.forEach(subs -> subs.getUsers().stream().filter(u -> u.getUserId().equals(userId)).map(u -> subs).forEach(temp::add));

        if (user.isPresent()) {
            user.get().setActive(status);
            userRepo.save(user.get());
            //SAVING THE CHANGED STATUS IN SUBS ALSO
            temp.forEach(s -> s.getUsers().forEach(u -> u.setActive(status)));
            subRepo.saveAll(temp);
        } else {
            throw new ResourceAccessException("User Not Found");
        }
        return "Status changed Successfully";
    }

    //SEARCH USER BY NAME AND USERNAME
    public List<User> searchUser(String searchRequest) {
        return userRepo.searchUser(searchRequest);
    }

    //FILTER USER
    public List<User> filterUser(UserFilterRequest filterRequest) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filterRequest.getActive() != null) {
            criteriaList.add(Criteria.where("isActive").is(filterRequest.getActive()));
        }
        if (filterRequest.getStartDate() != null && filterRequest.getEndDate() != null) {
            criteriaList.add(Criteria.where("createdTimeStamp").gte(filterRequest.getStartDate()));
            criteriaList.add(Criteria.where("createdTimeStamp").lt(filterRequest.getEndDate()));
        }
        if (filterRequest.getDateOfBirth() != null) {
            criteriaList.add(Criteria.where("dateOfBirth").gte(filterRequest.getDateOfBirth()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getCountry().isEmpty()) {
            criteriaList.add(Criteria.where("country").is(filterRequest.getCountry()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getState().isEmpty()) {
            criteriaList.add(Criteria.where("state").is(filterRequest.getState()));
        }
        if (filterRequest.getCountry() != null && !filterRequest.getCity().isEmpty()) {
            criteriaList.add(Criteria.where("city").is(filterRequest.getCountry()));
        }
        if (filterRequest.getGender() != null) {
            criteriaList.add(Criteria.where("gender").is(filterRequest.getGender()));
        }

        if (!CollectionUtils.isEmpty(criteriaList)) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
        }

        return mongoTemplate.find(query, User.class);
    }

    //SENDING USERNAME AND PASSWORD TO USER VIA EMAIL
    public String sendEmail(UUID userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<User> user = userRepo.findById(userId);

        //GENERATING RANDOM USERNAME
        Random random = new Random();
        String[] name = new String(user.get().getName()).split(" ");
        String userName = name[0].toLowerCase() + random.nextInt(1500);
        String password = miscellaneousService.generateUsernameAndPassword();

        if (user.isPresent()) {
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            userLoginDetails.setName(user.get().getName());
            userLoginDetails.setUserId(userId);
            userLoginDetails.setUserName(userName);
            userLoginDetails.setEmailId(user.get().getEmailAddress());
            userLoginDetails.setPassword(password);
            userLoginDetails.setCreatedTimeStamp(new Date());
            userLoginDetailsRepo.save(userLoginDetails);

            message.setTo(user.get().getEmailAddress());
            message.setSubject("Hi User ! Here are your login credentials");
            message.setText("Username : " + userName + "Password : " + password);

            mailSender.send(message);
            return "Login credentials sent successfully to the user";
        } else {
            throw new ResourceAccessException("User not found ");
        }

    }

}
