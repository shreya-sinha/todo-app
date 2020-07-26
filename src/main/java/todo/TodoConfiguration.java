package todo;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import xyz.randomcode.dropwizard_morphia.MongoConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TodoConfiguration extends Configuration {

    @Valid
    @NotNull
    @Getter
    private MongoConfiguration mongo;

    @Valid
    @NotNull
    @JsonProperty("swagger")
    @Getter
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

}
