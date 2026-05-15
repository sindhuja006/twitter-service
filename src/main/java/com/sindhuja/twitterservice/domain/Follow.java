package com.sindhuja.twitterservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Follow {
    private UserId followId;
    private UserId followeeId;

}
