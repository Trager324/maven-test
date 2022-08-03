package com.syd.java17.struct;

import lombok.Data;

@Data
class P {
    int x, y;

    P() {
    }

    P(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
