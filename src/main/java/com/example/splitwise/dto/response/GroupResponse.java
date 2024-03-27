package com.example.splitwise.dto.response;

import com.example.splitwise.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupResponse {

    private Long id;
    private String groupName;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<UserResponse> groupUsers;
}
