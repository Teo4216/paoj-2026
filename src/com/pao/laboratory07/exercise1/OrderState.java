package com.pao.laboratory07.exercise1;

public enum OrderState {
    PLACED {
        @Override
        public OrderState next() { return PROCESSED; }
        @Override
        public boolean isFinal() { return false; }
    },
    PROCESSED {
        @Override
        public OrderState next() { return SHIPPED; }
        @Override
        public boolean isFinal() { return false; }
    },
    SHIPPED {
        @Override
        public OrderState next() { return DELIVERED; }
        @Override
        public boolean isFinal() { return false; }
    },
    DELIVERED {
        @Override
        public OrderState next() { return this; }
        @Override
        public boolean isFinal() { return true; }
    },
    CANCELED {
        @Override
        public OrderState next() { return this; }
        @Override
        public boolean isFinal() { return true; }
    };
    public abstract OrderState next();
    public abstract boolean isFinal();

    public OrderState cancel() {
        return CANCELED;
    }
}