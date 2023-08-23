package com.byultudy.socketserver.lineup.service;

import com.byultudy.socketserver.lineup.dto.LineupRequest;
import com.byultudy.socketserver.lineup.dto.LineupResponse;

public interface LineupService {

    LineupResponse lineup(final LineupRequest request);

}