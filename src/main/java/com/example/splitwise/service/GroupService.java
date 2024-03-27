package com.example.splitwise.service;

import com.example.splitwise.domain.Group;
import com.example.splitwise.dto.request.GroupRequest;
import com.example.splitwise.exception.NotFoundException;

import java.util.List;

public interface GroupService {

    Group getGroupById(long id) throws NotFoundException;

    Group createGroup(GroupRequest groupRequest);

    List<String> getGroupBalance(long id) throws NotFoundException;

}
