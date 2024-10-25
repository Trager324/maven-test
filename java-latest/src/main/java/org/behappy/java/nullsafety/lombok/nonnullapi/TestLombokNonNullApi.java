package org.behappy.java.nullsafety.lombok.nonnullapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class TestLombokNonNullApi {
    String fieldNonNull;
    @Nullable
    String fieldNullable;
}
