package com.github.dgwatts.pidigits.core;

import static com.github.dgwatts.pidigits.core.TestUtils.assertRangeEquals;
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

		final PiDigitResponse response = cg.getRoot();

		assertEquals("default returns 30 elements", 30, response.getDigits().length);
	}

	@Test
	public void testControllerOneDigit() throws Exception {
		mockMvc.perform(get("/pidigits/9"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigitResponse response = cg.getRoot();

		assertEquals("returns 1 element", 1, response.getDigits().length);
		assertEquals("10th digit", TestUtils.FIRST_TEN[9], response.getDigits()[0].getValue());
	}

	@Test
	public void testControllerRange() throws Exception {
		mockMvc.perform(get("/pidigits/0-10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigitResponse response = cg.getRoot();

		assertEquals("returns 10 elements", 10, response.getDigits().length);
		assertRangeEquals(response.getDigits(), TestUtils.FIRST_TEN);
	}

	@Test
	public void testControllerRangeTruncated() throws Exception {
		mockMvc.perform(get("/pidigits/0-600"))
				.andDo(print())
				.andExpect(status().isPartialContent())
				.andDo(cg);

		final PiDigitResponse response = cg.getRoot();

		assertEquals("returns 500 elements", 500, response.getDigits().length);
	}

	@Test
	public void testControllerList() throws Exception {
		mockMvc.perform(get("/pidigits/0,2,4,6,8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(cg);

		final PiDigitResponse response= cg.getRoot();

		assertEquals("returns 5 elements", 5, response.getDigits().length);
		for (int i = 0; i < response.getDigits().length; i++) {
			assertEquals("index " + (i*2), i*2, response.getDigits()[i].getIndex());
			assertEquals("digit " + i*2, TestUtils.FIRST_TEN[i*2], response.getDigits()[i].getValue());
		}
	}

	@Test
	public void testControllerBadArgs() throws Exception {
		mockMvc.perform(get("/pidigits/bad"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testControllerPastEndOfRange() throws Exception {
		mockMvc.perform(get("/pidigits/" + (1000000 + 500)))
				.andDo(print())
				.andExpect(status().isPartialContent());
	}

	@Test
	public void testControllerPastTruncatedAndPastEndOfRange() throws Exception {
		mockMvc.perform(get("/pidigits/" + (1000000 - 400) + "-" + (1000000+400)))
				.andDo(print())
				.andExpect(status().isPartialContent());
	}

	class ContentGetter implements ResultHandler {

		private Gson gson = new Gson();

		private String content;
		private PiDigitResponse root;

		@Override
		public void handle(MvcResult mvcResult) throws Exception {
			content = mvcResult.getResponse().getContentAsString();
			root = gson.fromJson(content, PiDigitResponse.class);
		}

		public String getContent() {
			return content;
		}

		public PiDigitResponse getRoot() {
			return root;
		}
	}
}
