package com.czy.log;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chenzy
 * @date 2020-08-07
 */
public record LogSetting(@JsonProperty("logName") String logName,@JsonProperty("dataPattern") String dataPattern
        ,@JsonProperty("filePath") String filePath,@JsonProperty("level") LogLevel level,@JsonProperty("consolePrint") boolean consolePrint ) {
}
