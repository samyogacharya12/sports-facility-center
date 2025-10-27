package org.sports.facility.center.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sports.facility.center.enumconstant.ResponseStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {

    private String message;
    private String status;
    private Object detail;
    private ResponseStatus responseStatus;
}
