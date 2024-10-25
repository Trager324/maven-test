package org.behappy.java.nullsafety.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
public class TestLombok {
    @NonNull
    String fieldNonNull;
    @Nullable
    String fieldNullable;
}
