package com.example.splitwise.controller;

import com.example.splitwise.domain.Group;
import com.example.splitwise.dto.request.GroupRequest;
import com.example.splitwise.dto.response.GroupResponse;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.service.GroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable(value = "id") long id) throws NotFoundException {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Group createGroup(@RequestBody @Validated GroupRequest groupRequest) {
        return groupService.createGroup(groupRequest);
    }

    @GetMapping("/balance/{groupId}")
    @ApiOperation(
            value = "Display balances",
            notes = "Retrieves and displays the balances of users for a specific group."
    )
    public List<String> getGroupBalance(@PathVariable(value = "groupId") long id) throws NotFoundException {
        return groupService.getGroupBalance(id);
    }
}
