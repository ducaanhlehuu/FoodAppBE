package com.shop.food.controller;

import com.shop.food.dto.GroupDto;
import com.shop.food.entity.response.ResponseBody;
import com.shop.food.entity.user.Group;
import com.shop.food.entity.user.Role;
import com.shop.food.entity.user.User;
import com.shop.food.exception.UnauthorizedException;
import com.shop.food.exception.UserNotFoundException;
import com.shop.food.service.iservice.GroupService;
import com.shop.food.service.iservice.UserService;
import com.shop.food.util.ServerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private void checkGroupOwner(String userEmail, Group group) throws UnauthorizedException {
        if (!group.getOwner().getEmail().equalsIgnoreCase(userEmail)) {
            throw new UnauthorizedException("You are not the owner of this group");
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseBody> getGroupById(@PathVariable Integer groupId) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group group = groupService.getGroup(groupId);
        if (group == null) {
            return new ResponseEntity<>(new ResponseBody("Not found group", ResponseBody.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseBody("Get success", ResponseBody.SUCCESS, group), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseBody> getAllGroupByUserId() throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        List<Group> groups = userDetailsService.loadUserByUsername(userEmail).getAuthorities().contains(Role.ADMIN.name()) ?
                groupService.getAllGroup() :
                groupService.getAllGroupByUserEmail(userEmail);

        return new ResponseEntity<>(ResponseBody.builder().data(groups).resultCode(ResponseBody.SUCCESS).resultMessage("Lấy thành công").build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> createGroup(@RequestParam String name, @RequestParam String description) throws UnauthorizedException {
        String username = ServerUtil.getAuthenticatedUserEmail();
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        try {
            groupService.addGroup(group, username);
            return new ResponseEntity<>(new ResponseBody("Success", ResponseBody.SUCCESS, group), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(new ResponseBody("User not found", ResponseBody.NOT_FOUND, null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseBody> updateGroup(@RequestBody GroupDto groupDto) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group existedGroup = groupService.getGroup(groupDto.getId());
        if (existedGroup == null) {
            return new ResponseEntity<>(new ResponseBody("Not found group", ResponseBody.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }
        checkGroupOwner(userEmail, existedGroup);
        existedGroup.updateGroup(groupDto);
        groupService.saveGroup(existedGroup);
        return new ResponseEntity<>(new ResponseBody("Update group successfully", ResponseBody.SUCCESS, existedGroup), HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ResponseBody> deleteGroup(@PathVariable Integer groupId) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group existedGroup = groupService.getGroup(groupId);
        checkGroupOwner(userEmail, existedGroup);
        if (!groupService.deleteGroup(groupId)) {
            return new ResponseEntity<>(new ResponseBody("Not found group", ResponseBody.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseBody("Delete group successfully", ResponseBody.DELETED, null), HttpStatus.OK);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<ResponseBody> getGroupMembers(@PathVariable Integer groupId) {
        if (groupService.getGroup(groupId) == null) {
            return new ResponseEntity<>(new ResponseBody("Not found group", ResponseBody.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }
        List<User> members = groupService.getGroupMembers(groupId);
        return new ResponseEntity<>(new ResponseBody("Get member success", ResponseBody.SUCCESS, members), HttpStatus.OK);
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ResponseBody> addMemberToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group existedGroup = groupService.getGroup(groupId);
        checkGroupOwner(userEmail, existedGroup);
        Group group = groupService.addMemberToGroup(groupId, userId);
        return new ResponseEntity<>(new ResponseBody("Add new member successfully", ResponseBody.SUCCESS, group), HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ResponseBody> removeMemberFromGroup(@PathVariable Integer groupId, @PathVariable Integer userId) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group existedGroup = groupService.getGroup(groupId);
        checkGroupOwner(userEmail, existedGroup);
        Group group = groupService.removeMemberInGroup(groupId, userId);
        return new ResponseEntity<>(new ResponseBody("Remove member successfully", ResponseBody.DELETED, group), HttpStatus.OK);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<ResponseBody> addMemberToGroup(@PathVariable Integer groupId, @RequestParam("email") String email) throws UnauthorizedException {
        String userEmail = ServerUtil.getAuthenticatedUserEmail();
        Group existedGroup = groupService.getGroup(groupId);
        checkGroupOwner(userEmail, existedGroup);
        User newMember = userService.getUserByEmail(email);
        Group group = groupService.addMemberToGroup(groupId, newMember.getId());
        return new ResponseEntity<>(new ResponseBody("Add new member successfully", ResponseBody.SUCCESS, group), HttpStatus.OK);
    }
}
