package com.github.dgwatts.pidigits;

import static com.github.dgwatts.pidigits.TestUtils.assertRangeEquals;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
public class PiDigitsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ContentGetter cg;

	@BeforeEach
	public void setup() {
		cg = new ContentGetter();
	}

	@Test
	public void testControllerNoArgs() throws Exception {
		mockMvc.perform(get("/pidigits"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigit[] digits = cg.getRoot();

		assertEquals("default returns 30 elements", 30, digits.length);
	}

	@Test
	public void testControllerOneDigit() throws Exception {
		mockMvc.perform(get("/pidigits/9"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigit[] digits = cg.getRoot();

		assertEquals("returns 1 element", 1, digits.length);
		assertEquals("10th digit", TestUtils.FIRST_TEN[9], digits[0].getValue());
	}

	@Test
	public void testControllerRange() throws Exception {
		mockMvc.perform(get("/pidigits/0-10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigit[] digits = cg.getRoot();

		assertEquals("returns 10 elements", 10, digits.length);
		assertRangeEquals(digits, TestUtils.FIRST_TEN);
	}

	@Test
	public void testControllerList() throws Exception {
		mockMvc.perform(get("/pidigits/0,2,4,6,8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigit[] digits = cg.getRoot();

		assertEquals("returns 5 elements", 5, digits.length);
		for (int i = 0; i < digits.length; i++) {
			assertEquals("index " + (i*2), i*2, digits[i].getIndex());
			assertEquals("digit " + i*2, TestUtils.FIRST_TEN[i*2], digits[i].getValue());
		}
	}

	class ContentGetter implements ResultHandler {

		private Gson gson = new Gson();

		private String content;
		private PiDigit[] root;

		@Override
		public void handle(MvcResult mvcResult) throws Exception {
			content = mvcResult.getResponse().getContentAsString();
			root = gson.fromJson(content, PiDigit[].class);
		}

		public String getContent() {
			return content;
		}

		public PiDigit[] getRoot() {
			return root;
		}
	}
}
