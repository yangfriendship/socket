package com.byultudy.socketserver.lineup.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LineupRequest {

    private Long lineId;

    private Long userId;

}