package org.behappy.java.nullsafety.lombok;

import org.behappy.java.nullsafety.annotation.NonNull;
import org.behappy.java.nullsafety.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCustomLombok {
    @NonNull
    String fieldNonNull;
    @Nullable
    String fieldNullable;
}
