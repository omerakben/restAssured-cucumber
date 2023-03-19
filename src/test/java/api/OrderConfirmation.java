package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderConfirmation {
    @JsonProperty("created")
    private Boolean created;
    @JsonProperty("orderId")
    private String orderId;
}