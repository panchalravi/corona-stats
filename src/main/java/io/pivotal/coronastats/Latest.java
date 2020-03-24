package io.pivotal.coronastats;

import lombok.Data;

@Data
public class Latest {
    private Long confirmed;
    private Long deaths;
    private Long recovered;
}
