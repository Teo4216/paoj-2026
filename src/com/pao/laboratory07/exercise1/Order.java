package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.Stack;

public class Order {
    private OrderState currentState;
    private Stack<OrderState> istoric;

    public Order(OrderState initialState) {
        this.currentState = initialState;
        this.istoric = new Stack<>();
    }

    public OrderState getCurrentState() {
        return currentState;
    }

    public void nextState() throws OrderIsAlreadyFinalException {
        if (currentState.isFinal()) {
            throw new OrderIsAlreadyFinalException();
        }
        istoric.push(currentState);
        currentState = currentState.next();
    }

    public void cancel() throws CannotCancelFinalOrderException {
        if (currentState.isFinal()) {
            throw new CannotCancelFinalOrderException();
        }
        istoric.push(currentState);
        currentState = currentState.cancel();
    }

    public void undoState() throws CannotRevertInitialOrderStateException {
        if (istoric.isEmpty()) {
            throw new CannotRevertInitialOrderStateException();
        }
        currentState = istoric.pop();
    }
}