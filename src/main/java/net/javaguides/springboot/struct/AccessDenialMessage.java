package net.javaguides.springboot.struct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccessDenialMessage {

    @JsonProperty("RqUID")
    String rqUID;

    @JsonProperty("requestNumber")
    String requestNumber;

    @JsonProperty("contractNumber")
    String contractNumber;

    @JsonProperty("visitorId")
    Long visitorId;

    @JsonProperty("denyDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate denyDate;

    @JsonProperty("cause")
    String cause;


}
