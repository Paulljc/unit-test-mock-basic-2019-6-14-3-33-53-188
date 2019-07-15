package cashregister;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class CashRegisterTest {


    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        Printer printer = new Printer();
        Item[] items = {new Item("coco", 1.0)};
        CashRegister cashRegister = new CashRegister(printer);
        Purchase purchase = new Purchase(items);

        cashRegister.process(purchase);

        assertThat(systemOut()).isEqualTo("coco\t1.0\n");
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Purchase purchase = mock(Purchase.class);
        when(purchase.asString()).thenReturn("mock purchase");

        cashRegister.process(purchase);

        assertThat(systemOut()).isEqualTo("mock purchase");
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        //given
        Printer printer = mock(Printer.class);
        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(printer);
        //when
        cashRegister.process(purchase);
        //then
        verify(printer).print(purchase.asString());
    }

}
