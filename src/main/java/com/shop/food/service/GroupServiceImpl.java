package com.shop.food.service;

import com.shop.food.dto.NotificationMessage;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.repository.GroupRecipeRepository;
import com.shop.food.repository.GroupRepository;
import com.shop.food.repository.UserRepository;
import com.shop.food.service.external.EmailService;
import com.shop.food.service.external.FirebaseMessagingService;
import com.shop.food.service.iservice.GroupService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupReposistory;
    private final GroupRecipeRepository groupRecipeRepository;
    private final UserRepository userReposistory;
    private final EmailService emailService;
    private final FirebaseMessagingService firebaseMessagingService;

    @Override
    public List<Group> getAllGroupByUserEmail(String email) {
        List<Group> groups = groupReposistory.getGroupsByUserEmail(email);
        for (Group group: groups) {
            group.setGroupRecipes(groupRecipeRepository.findByGroupId(group.getId()));
        }
        return groups;
    }

    @Override
    public List<Group> getAllGroup() {
        return groupReposistory.findAll(Sort.unsorted().ascending());
    }

    @Override
    public Group getGroup(Integer groupId) {
        Group group = groupReposistory.findById(groupId).orElse(null);
        if (group != null) {
            group.setGroupRecipes(groupRecipeRepository.findByGroupId(groupId));
        }
        return group;
    }

    @Override
    public Group saveGroup(Group group) {
        return  groupReposistory.save(group);
    }

    @Override
    public Group addGroup(Group group, String ownerEmail) throws UserNotFoundException {
        User owner = userReposistory.findByEmail(ownerEmail).orElse(null);
        if (owner == null)
            throw new UserNotFoundException("Người tạo nhóm không tồn tại");
        group.setOwner(owner);
        group.setMembers(List.of(owner));
        return saveGroup(group);
    }

    @Override
    public boolean deleteGroup(Integer groupId) {
        Group existingGroup = this.getGroup(groupId);
        if (existingGroup!=null) {
            groupReposistory.deleteById(groupId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getGroupMembers(Integer groupID) {
        return userReposistory.getMembersByGroupId(groupID);
    }

    @Override
    public Group addMemberToGroup(Integer groupId, Integer userId) {
        Group group = this.getGroup(groupId);
        User user = userReposistory.findById(userId).orElse(null);
        if (group == null) {
            throw new IllegalArgumentException("Group not found");
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (group.getMembers().contains(user)) {
            throw new IllegalStateException("User is already a member of the group");
        }
        group.getMembers().add(user);

        if (user.getEmail().isEmpty()) {
            emailService.sendEmail(user.getEmail(), "Thông báo tới bạn" ,"Bạn đã được thêm vào nhóm " + group.getName() + " bởi người dùng: " +  group.getOwner().getFullName() +" (" + group.getOwner().getEmail()+")");
        }
        try {
            if (user.getNotificationToken()!=null && user.getNotificationToken().isEmpty()) {
                NotificationMessage notificationMessage = NotificationMessage.builder().recipientToken(user.getNotificationToken())
                        .title("Foodie App").body("Bạn đã được thêm vào nhóm " + group.getName() + " bởi người dùng: " +  group.getOwner().getFullName() +" (" + group.getOwner().getEmail()+")").build();
                firebaseMessagingService.sendNotification(notificationMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.saveGroup(group);
    }

    @Override
    public Group removeMemberInGroup(Integer groupId, Integer userId) {
        Group group = this.getGroup(groupId);
        User user = userReposistory.findById(userId).orElse(null);
        if (group == null) {
            throw new IllegalArgumentException("Group not found");
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!group.getMembers().contains(user)) {
            throw new IllegalStateException("User is not a member of the group");
        }

        group.getMembers().remove(user);
        return  this.saveGroup(group);
    }
}
