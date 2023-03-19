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
public class Book {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("author")
    private String author;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("current-stock")
    private Integer currentStock;
    @JsonProperty("available")
    private Boolean available;
}