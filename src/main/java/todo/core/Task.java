package todo.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Task {

    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;

}
