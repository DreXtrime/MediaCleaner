package com.cleaner.mediacleaner.filter


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = ["api.key=testkey"])
class ApiKeyFilterTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `request without api key returns 401`() {
        mockMvc.perform(get("/recommendations"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `request with wrong api key returns 401`() {
        mockMvc.perform(get("/recommendations")
            .header("X-Api-Key", "wrongkey"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `request with valid api key returns 200`() {
        mockMvc.perform(get("/recommendations")
            .header("X-Api-Key", "testkey"))
            .andExpect(status().isOk)
    }
}