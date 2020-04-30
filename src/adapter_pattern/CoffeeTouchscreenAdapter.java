package adapter_pattern;

public class CoffeeTouchscreenAdapter implements CoffeeMachineInterface {

    private OldCoffeeMachine oldMachine;

    public CoffeeTouchscreenAdapter(OldCoffeeMachine newMachine) {
        oldMachine = newMachine;
    }

    @Override
    public void chooseFirstSelection() {
        oldMachine.selectA();
    }

    @Override
    public void chooseSecondSelection() {
        oldMachine.selectB();
    }
}
