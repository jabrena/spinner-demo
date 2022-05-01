package info.jab.demos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpinnerDemo2Test {

    @BeforeEach
    void init() {
        System.out.println();
        System.out.println("==========");
        System.out.println("==========");
        System.out.println();
    }

    @Test
    public void given_example_when_execute_then_ok() throws Exception {
        SpinnerDemo2 spinnerDemo2 = new SpinnerDemo2();
        spinnerDemo2.behaviour(2000, false);
    }

    @Test
    public void given_example_when_execute_with_exception_then_ok() throws Exception {
        SpinnerDemo2 spinnerDemo2 = new SpinnerDemo2();
        spinnerDemo2.behaviour(6000, true);
    }

    @Test
    public void given_example_when_execute_with_timeout_then_ok() throws Exception {
        SpinnerDemo2 spinnerDemo2 = new SpinnerDemo2();
        spinnerDemo2.behaviour(6000, false);
    }

    @Test
    public void given_example_when_execute_with_timeout_and_exception_then_ok() throws Exception {
        SpinnerDemo2 spinnerDemo2 = new SpinnerDemo2();
        spinnerDemo2.behaviour(6000, true);
    }
}
