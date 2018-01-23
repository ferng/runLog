package com.thecrunchycorner.lmax.testHelpers

class IdGenerator {
    static def id = 0;

    static getId() {
        return id++;
    }
}
