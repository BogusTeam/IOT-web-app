package ru.app.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class WSMessage {
    private Integer deviceId;
    private String value;

    @JsonCreator
    public WSMessage(@JsonProperty("deviceId") Integer deviceId,
                     @JsonProperty("value") String value) {
        super();
        this.deviceId = deviceId;
        this.value = value;
    }
}
