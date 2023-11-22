import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClientTest {
	static Client c;

	@BeforeAll
	static void setup() {
		c = new Client(data -> {
			System.out.println(data.toString());
		}, 5555, "Thanksgiving");
		c.start();
	}

	@Test
	public void testSetGuess() {
		c.setUserG("turkey");
		assertEquals(c.userG, "turkey");
	}

	@Test
	public void testSetCategory() {
		c.setUserC("Thanksgiving");
		assertEquals(c.userC, "Thanksgiving");
	}

}
