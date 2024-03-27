package com.example.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest implements Serializable {
    private String groupName;
    private String description;
    @NonNull
    private List<Long> userIds;
}
