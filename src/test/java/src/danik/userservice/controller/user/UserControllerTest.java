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
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + userDto.getId())).andExpect(status().isNoContent());
    }


    private UserRegistrationDto getUserRegisterDto() {
        return UserRegistrationDto.builder()
                .email("testEmail@email.com")
                .username("username")
                .password("password")
                .build();
    }
}
