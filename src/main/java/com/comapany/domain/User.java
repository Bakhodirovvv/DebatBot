package com.comapany.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    private String chatId;
    private String fIO;
    private String description;
    private String video;
}
