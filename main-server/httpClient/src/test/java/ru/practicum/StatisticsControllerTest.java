package ru.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.client.ServerClient;
import ru.practicum.controller.StatisticsController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ServerClient serverClient;

    @Test
    void createHit() throws Exception {

        when(serverClient.create(any(HitDto.class)))
                .thenReturn(ResponseEntity.ok().body(editorField(null, null)));

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField(null, null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(editorField(null, null))));


        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("app", null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("app", "")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("uri", null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("uri", "")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("ip", null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("ip", "")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("timestamp", null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(editorField("timestamp", "")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStats() throws Exception {
        when(serverClient.getCompilations(anyString(), anyString(), anyList(), anyBoolean()))
                .thenReturn(ResponseEntity.ok().body(List.of()));
    }

    private <T> HitDto editorField(T field, String value) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("app", "test-service");
        fields.put("uri", "/event/1");
        fields.put("ip", "192.168.0.1");
        fields.put("timestamp", "2022-09-06 11:00:23");
        if (field != null) {
            fields.put((String) field, value);
        }
        String[] strings = new String[fields.size()];
        int i = 0;
        for (String key : fields.keySet()) {
            strings[i] = fields.get(key);
            i++;
        }
        return new @Valid HitDto(
                strings[0],
                strings[1],
                strings[2],
                strings[3]
        );
    }
}