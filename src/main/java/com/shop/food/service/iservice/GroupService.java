package com.shop.food.service.iservice;


import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.User;
import com.shop.food.exception.UserNotFoundException;

import java.util.List;

public interface GroupService {
    public List<Group> getAllGroupByUserEmail(String email);
    public List<Group> getAllGroup();
    public Group getGroup(Integer groupId);
    public Group saveGroup(Group group);
    public Group addGroup(Group group,String ownerName) throws UserNotFoundException;
    public boolean deleteGroup(Integer groupId);

    public List<User> getGroupMembers(Integer groupID);
    public Group addMemberToGroup(Integer groupId, Integer userId);
    public Group removeMemberInGroup(Integer groupId, Integer userId);
}