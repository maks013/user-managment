package com.usermanagment.user.infrastructure;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginAndRegisterControllerTest extends UserBaseIntagration {

    @Test
    @Order(1)
    void should_register_user() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/register").content("""
                        {
                        "username": "user",
                        "email": "adrestestowy@adres.pl",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        //then
        register.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void should_response_bad_request_when_user_try_to_register_with_invalid_email_format() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/register").content("""
                        {
                        "username": "user2",
                        "email": "email.com",
                        "password": "password2"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        //then
        register.andExpect(status().isBadRequest());
    }


    @Test
    @Order(3)
    void should_response_bad_request_when_user_try_to_register_with_already_taken_email() throws Exception {
        //given
        ResultActions registerUserWithTheSameEmail = mockMvc.perform(post("/auth/register").content("""
                        {
                        "username": "user2",
                        "email": "adrestestowy@adres.pl",
                        "password": "password2"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        //then
        registerUserWithTheSameEmail.andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void should_response_bad_request_when_user_try_to_register_with_already_taken_username() throws Exception {
        //given
        ResultActions registerUserWithTheSameEmail = mockMvc.perform(post("/auth/register").content("""
                        {
                        "username": "user",
                        "email": "spokotest@test.pl",
                        "password": "password2"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        //then
        registerUserWithTheSameEmail.andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void should_login_user_succesfully() throws Exception {
        //given
        ResultActions register = mockMvc.perform(post("/auth/login").content("""
                        {
                        "username": "user",
                        "password": "password"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        //when
        //then
        register.andExpect(status().isOk());
    }

}
