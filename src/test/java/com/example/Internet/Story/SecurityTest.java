package com.example.Internet.Story;

import com.example.Internet.Story.config.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;

    @Test
    @WithMockUser(roles = "USER")
    public void userCannotAccessAdminEndpoint() throws Exception{

        String token = jwtService.generateToken("kolya@mail.ru", "ROLE_USER");

        mockMvc.perform(get("/customer")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCanAccessAdminEndpoint() throws Exception{

        String adminToken = jwtService.generateToken("masha@mail.ru", "ROLE_ADMIN");

        mockMvc.perform(get("/customer")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    public void anonymousUserCannotAccessAdminEndPoint()throws Exception{
        mockMvc.perform(get("/customer"))
                .andExpect(status().isUnauthorized());
    }
}
