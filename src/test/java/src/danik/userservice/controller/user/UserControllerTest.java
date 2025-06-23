package src.danik.userservice.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testThatUserRegisterIsSuccess() throws Exception {
        UserRegistrationDto userRegisterDto = getUserRegisterDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userRegisterDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userRegisterDto.getUsername()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserRegistrationDto userRegisterDto = getUserRegisterDto();
        UserDto userDto = userService.registerUser(userRegisterDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(userDto.getUsername()));
    }
    @Test
    public void testUserGetById() throws Exception {
        UserRegistrationDto userRegisterDto = getUserRegisterDto();
        UserDto userDto = userService.registerUser(userRegisterDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userRegisterDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userRegisterDto.getUsername()));
    }

    @Test
    public void testThatUserCanBeDeletedWhenUserExist() throws Exception {
        UserRegistrationDto userRegistrationDto = getUserRegisterDto();
        UserDto userDto = userService.registerUser(userRegistrationDto);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + userDto.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testThatUsersCanBeGetByIdsWhenUsersExist() throws Exception {
        UserRegistrationDto dto1 = UserRegistrationDto.builder().username("user1").email("user1@email.com").password("pass1").build();
        UserRegistrationDto dto2 = UserRegistrationDto.builder().username("user2").email("user2@email.com").password("pass2").build();
        UserDto userDto1 = userService.registerUser(dto1);
        UserDto userDto2 = userService.registerUser(dto2);

        List<Long> ids = Arrays.asList(userDto1.getId(), userDto2.getId());
        String jsonContent = objectMapper.writeValueAsString(ids);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("user1@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("user2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("user2@email.com"));
    }


    private UserRegistrationDto getUserRegisterDto() {
        return UserRegistrationDto.builder()
                .email("testEmail@email.com")
                .username("username")
                .password("password")
                .build();
    }
}
