package src.danik.userservice.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import src.danik.userservice.controller.UserController;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.service.user.UserService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void testThatUserRegisterIsSuccess() throws Exception {
        UserRegistrationDto userRegisterDto = getUserRegisterDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userRegisterDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userRegisterDto.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userRegisterDto.getPassword()))
                .andExpect(status().isOk());

    }
    @Test
    public void testThatUserCanBeDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/7")).andExpect(status().isNoContent());
    }

    private UserRegistrationDto getUserRegisterDto() {
        return UserRegistrationDto.builder()
                .email("testEmail@email.com")
                .username("username")
                .password("password")
                .build();
    }
}
