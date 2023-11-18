package com.usermanagment.user.infrastructure;

import com.usermanagment.user.dto.login.LoginResponseDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends UserBaseIntegration {

    @Test
    @Order(1)
    void should_be_able_for_logged_user_to_findAll_users() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = register.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto loginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        //when
        ResultActions userMadeGetUsers = mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + loginResponseDto.getToken())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        userMadeGetUsers.andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void should_response_403_for_GET_users_without_token() throws Exception {
        //given
        //when
        ResultActions userMadeGetUsers = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        userMadeGetUsers.andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void should_response_403_when_unabled_user_trying_to_update() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = register.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto loginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        //when
        ResultActions postUpdate = mockMvc.perform(put("/users/1")
                .header("Authorization", "Bearer " + loginResponseDto.getToken())
                .content("""
                        {
                        "username": "test",
                        "email": "test@test.pl"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        postUpdate.andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    void should_response_406_when_user_try_to_update_with_invalid_id() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = register.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto loginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        //when
        ResultActions postUpdate = mockMvc.perform(put("/users/2")
                .header("Authorization", "Bearer " + loginResponseDto.getToken())
                .content("""
                        {
                        "username": "test",
                        "email": "test@test.pl"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        postUpdate.andExpect(status().isNotAcceptable());
    }

    @Test
    @Order(5)
    void should_response_403_when_unabled_user_try_to_update_his_data() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        MvcResult mvcResult = register.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto loginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        //when
        ResultActions postUpdate = mockMvc.perform(put("/users/1")
                .header("Authorization", "Bearer " + loginResponseDto.getToken())
                .content("""
                        {
                        "username": "test",
                        "email": "test@test.pl"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON));
        //then
        postUpdate.andExpect(status().isForbidden());
    }
}
