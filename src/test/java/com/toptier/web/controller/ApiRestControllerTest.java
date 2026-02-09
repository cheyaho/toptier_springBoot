package com.toptier.web.controller;

import com.toptier.web.dto.BoardRequest;
import com.toptier.web.entity.BoardType;
import com.toptier.web.repository.BoardTypeRepository;
import com.toptier.web.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("카테고리에 포함된 메뉴 목록 가져오기")
    @Test
    void getMenusInCategory() throws Exception {
        // given
        String categoryId = "3";
        String url = "/api/menu/category";

        // when
        ResultActions result = mockMvc.perform(get(url)
                        .param("categoryId", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("여러 카테고리에 포함된 메뉴 목록 가져오기")
    @Test
    void getMenusInCategories() throws Exception {
        // given
        String[] categoryIds = {"1", "2"};
        String url = "/api/menu/categories";

        // when
        ResultActions result = mockMvc.perform(get(url)
                        .param("categoryIds", categoryIds)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        result.andExpect(status().isOk());
    }

    @DisplayName("시그니쳐 메뉴 가져오기")
    @Test
    void getSignitureMenu() throws Exception {
        // given
        String url = "/api/menu/signature";

        // when
        ResultActions result = mockMvc.perform(get(url))
                .andExpect(status().isOk());

        // then
        result.andExpect(status().isOk());
    }
}