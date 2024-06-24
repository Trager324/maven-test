package com.behappy.java.nullsafety.lombok;

import com.behappy.java.nullsafety.annotation.NonNull;
import com.behappy.java.nullsafety.annotation.Nullable;
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
