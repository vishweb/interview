package com.ubs.opsit.interviews;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BerlinClockConverterTest {
	
    private static final Logger LOG = LoggerFactory.getLogger(BerlinClockConverterTest.class);
    private BerlinClockConverter berlinClock;
    @Before
    public void setup(){
    	berlinClock  = new BerlinClockConverter();
    }

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfInputTimeIsNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Input Time is empty");
		berlinClock.convertTime(null);
	}
	
	@Test
	public void inputTimeNotValid() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Input Time not Valid,Required Format : HH:mm:ss");
		berlinClock.convertTime("45:34");
	}
	
	@Test
	public void inputTimeNotValidOnlyHoursMinutes() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Input Time not Valid,Required Format : HH:mm:ss");
		berlinClock.convertTime("45:34");
	}
	
	@Test
	public void inputTimeValid() {		
		String convertTime = berlinClock.convertTime("12:04:03");
		//LOG.info(convertTime);
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("O", split[0]);
		assertEquals("RROO", split[1]);
		assertEquals("RROO", split[2]);
		assertEquals("OOOOOOOOOOO", split[3]);
		assertEquals("YYYY", split[4]);
	}
	
	@Test
	public void validInputTimeWithTopYellowLampOn() {		
		String convertTime = berlinClock.convertTime("14:05:02");
		String[] split = convertTime.split("[\\r\\n]+");
		//LOG.info(split[0]);
		assertEquals("Y", split[0]);
	}
	
	@Test
	public void validInputTimeWithTopYellowLampOff() {		
		String convertTime = berlinClock.convertTime("14:05:03");
		String[] split = convertTime.split("[\\r\\n]+");
		//LOG.info(split[0]);
		assertEquals("O", split[0]);
	}
	
	@Test
	public void validInputTimeWithFirstRowAllOn() {		
		String convertTime = berlinClock.convertTime("20:00:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("RRRR", split[1]);
	}
	
	@Test
	public void validInputTimeWithFirstRowAllOff() {		
		String convertTime = berlinClock.convertTime("00:00:00");
		String[] split = convertTime.split("[\\r\\n]+");
		LOG.info(convertTime);
		assertEquals("OOOO", split[1]);
	}
	
	@Test
	public void validInputTimeWithSecondRowAllOn() {		
		String convertTime = berlinClock.convertTime("14:00:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("RRRR", split[2]);
	}
	
	@Test
	public void validInputTimeWithSecondRowAllOff() {		
		String convertTime = berlinClock.convertTime("05:00:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("OOOO", split[2]);
	}
	
	@Test
	public void validInputTimeWithThirdRowAllOn() {		
		String convertTime = berlinClock.convertTime("05:55:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("YYRYYRYYRYY", split[3]);
	}
	
	@Test
	public void validInputTimeWithThirdRowAllOff() {		
		String convertTime = berlinClock.convertTime("05:00:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("OOOOOOOOOOO", split[3]);
	}
	
	@Test
	public void validInputTimeWithThirdRowRedLampsOn() {		
	
		String convertTime = berlinClock.convertTime("05:15:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("YYROOOOOOOO", split[3]);
		
		convertTime = berlinClock.convertTime("05:30:00");		
		split = convertTime.split("[\\r\\n]+");
		assertEquals("YYRYYROOOOO", split[3]);
		
		convertTime = berlinClock.convertTime("05:45:00");		
		split = convertTime.split("[\\r\\n]+");
		assertEquals("YYRYYRYYROO", split[3]);
	}
	
	@Test
	public void validInputTimeWithForthRowAllOn() {		
		String convertTime = berlinClock.convertTime("05:59:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("YYYY", split[4]);
	}
	
	@Test
	public void validInputTimeWithForthRowAllOff() {		
		String convertTime = berlinClock.convertTime("05:55:00");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("OOOO", split[4]);
	}
	
	@Test
	public void validInputTimeWithJustBeforeMidnight() {		
		String convertTime = berlinClock.convertTime("23:59:59");
		String[] split = convertTime.split("[\\r\\n]+");
		assertEquals("O", split[0]);
		assertEquals("RRRR", split[1]);
		assertEquals("RRRO", split[2]);
		assertEquals("YYRYYRYYRYY", split[3]);
		assertEquals("YYYY", split[4]);
	}

}
