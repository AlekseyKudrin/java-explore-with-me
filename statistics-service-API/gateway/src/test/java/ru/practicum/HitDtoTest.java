package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class HitDtoTest {

    @Autowired
    private JacksonTester<HitDto> json;

    HitDto hitDto = new HitDto(
            "test-service",
            "/event/1",
            "192.168.0.1",
            "2022-09-06 11:00:23"
    );

    @Test
    public void createHitDto() throws Exception {

        JsonContent<HitDto> allFieldPresents = json.write(editorField(null,null));
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.app").isEqualTo("test-service");
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.uri").isEqualTo("/event/1");
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.ip").isEqualTo("192.168.0.1");
        assertThat(allFieldPresents).extractingJsonPathStringValue("timestamp").isEqualTo("2022-09-06 11:00:23");
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
        for (String key: fields.keySet()){
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