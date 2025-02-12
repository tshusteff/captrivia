package com.pulley.captrivia;

import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.logging.common.filter.FilterFactory;

@JsonTypeName("leaderboard-filter-factory")
public class LeaderboardFilterFactory implements FilterFactory<IAccessEvent> {
    @Override
    public Filter<IAccessEvent> build() {
        return new Filter<IAccessEvent>() {
            @Override
            public FilterReply decide(IAccessEvent event) {
                if (event.getRequestURI().equals("/leaderboard")) {
                    return FilterReply.DENY;
                } else {
                    return FilterReply.NEUTRAL;
                }
            }
        };
    }
}