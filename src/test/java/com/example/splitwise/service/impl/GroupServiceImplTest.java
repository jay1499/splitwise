package com.example.splitwise.service.impl;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.User;
import com.example.splitwise.domain.UserBalance;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.UserBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBalanceService userBalanceService;

    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When group exists")
    public void testGetGroupById_GroupExists() throws NotFoundException {
        long id = 1;
        Group group = new Group();
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));

        Group result = groupService.getGroupById(id);

        assertNotNull(result);
        assertEquals(group, result);
    }

    @Test
    @DisplayName("When group does not exists")
    public void testGetGroupById_GroupDoesNotExist() {
        long id = 1;
        when(groupRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> groupService.getGroupById(id));
    }

    @Test
    @DisplayName("When getting balances of users from valid group")
    public void testGetGroupBalance_GroupExists() throws NotFoundException {
        int groupId = 1;
        Group group = new Group();
        group.setId(groupId);

        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("User1");
        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("User2");
        User user3 = new User();
        user3.setId(3);
        user3.setFirstName("User3");

        List<UserBalance> userBalances = new ArrayList<>();
        userBalances.add(new UserBalance(user1, user2, group, 10.0));
        userBalances.add(new UserBalance(user2, user1, group, 5.0));
        userBalances.add(new UserBalance(user1, user3, group, 20.0));

        when(groupRepository.findById((long) groupId)).thenReturn(Optional.of(group));
        when(userBalanceService.getAllUserBalancesByGroup(group)).thenReturn(userBalances);
        when(userRepository.findById((long) user1.getId())).thenReturn(Optional.of(user1));
        when(userRepository.findById((long) user2.getId())).thenReturn(Optional.of(user2));
        when(userRepository.findById((long) user3.getId())).thenReturn(Optional.of(user3));

        List<String> result = groupService.getGroupBalance(groupId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertArrayEquals(new String[]{"User3 owes User1 ₹20.00", "User2 owes User1 ₹5.00"}, result.toArray());
    }

    @Test
    @DisplayName("When getting balances of users from invalid group")
    public void testGetGroupBalance_GroupDoesNotExist() {
        long groupId = 1;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> groupService.getGroupBalance(groupId));
        assertEquals("Group id: 1 not found", exception.getMessage());
    }
}
