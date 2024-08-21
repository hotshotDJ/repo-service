package com.services.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.repo.model.RepoDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class RepoServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    /*
     * Step 1: Saving a repo
     * Step 2: Retrieving the repo and tallying the response
     */
    @Test
    public void testInsertAndRetrieve() throws Exception {

        ResultActions resultActions;

        RepoDetails repoDetails = RepoDetails.builder().fullName("abc-lib").cloneUrl("github.com/abc-lib.git").description("testing repo").stars(4).build();

        String requestPayloadString = mapper.writeValueAsString(repoDetails);

        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/repositories/{owner}", "coder").contentType(MediaType.APPLICATION_JSON).content(requestPayloadString));


        resultActions.andExpect(status().isOk());

        String responsePayloadString = resultActions.andReturn().getResponse().getContentAsString();

        log.info("Returned response for POST API : {}", responsePayloadString);

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repository-name}", "coder", "abc-lib"));

        log.info("Returned response for get API : {}", resultActions.andReturn().getResponse().getContentAsString());

        resultActions.andExpect(status().isOk()).andExpect(content().json(responsePayloadString));
    }

    /*
     * Retrieving a non existent repo
     */
    @Test
    public void testRetrieveWithInvalidRepoName() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repository-name}", "coder", "zzz"));

        log.info("Returned response for get API : {}", resultActions.andReturn().getResponse().getContentAsString());

        resultActions.andExpect(status().isBadRequest());
    }
}
