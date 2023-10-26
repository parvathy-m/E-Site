package com.project.carro.Utils;

import org.springframework.beans.factory.annotation.Value;

public class CommonConstants {
    public enum Flag{
        ACTIVE(1,"ACTIVE"),
        DELETED(0,"DELETED"),
        BLOCKED(2,"BLOCKED");
        private final int value;
        private final String type;

        Flag(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return value;
        }
        public String getType() {
            return type;
        }
    }

    public enum Status{
        APPROVED(1,"APPROVED"),
        PENDING(2,"PENDING"),
        REJECTED(0,"REJECTED"),
        ON_HOLD(3,"ON_HOLD");

        private int value;
        private String type;

        Status(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }
}
