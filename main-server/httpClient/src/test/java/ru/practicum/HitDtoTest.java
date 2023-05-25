package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.allDto.HitDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class HitDtoTest {

    @Autowired
    private JacksonTester<ru.practicum.allDto.HitDto> json;

    ru.practicum.allDto.HitDto hitDto = new ru.practicum.allDto.HitDto(
            "test-service",
            "/event/1",
            "192.168.0.1",
            "2022-09-06 11:00:23"
    );

    @Test
    public void createHitDto() throws Exception {

        JsonContent<HitDto> allFieldPresents = json.write(hitDto);
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.app").isEqualTo("test-service");
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.uri").isEqualTo("/event/1");
        assertThat(allFieldPresents).extractingJsonPathStringValue("$.ip").isEqualTo("192.168.0.1");
        assertThat(allFieldPresents).extractingJsonPathStringValue("timestamp").isEqualTo("2022-09-06 11:00:23");
    }
}