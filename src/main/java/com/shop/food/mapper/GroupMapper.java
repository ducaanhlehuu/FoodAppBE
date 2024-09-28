package com.shop.food.mapper;

import com.shop.food.dto.GroupDto;
import com.shop.food.entity.user.Group;

public class GroupMapper {
    public static Group getGroupFromGroupDto(GroupDto groupDto) {
        var group =  Group.builder()
                .name(groupDto.getGroupName())
                .description(groupDto.getDescription())
                .enable(groupDto.isEnable())
                .build();
        group.setId(group.getId());
        return  group;
    }

    public static GroupDto getGroupDtoFromGroup(Group group) {
        return  GroupDto.builder()
                .groupName(group.getName())
                .description(group.getDescription())
                .enable(group.isEnable())
                .build();
    }
}
