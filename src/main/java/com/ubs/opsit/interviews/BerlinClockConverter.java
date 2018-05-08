package com.ubs.opsit.interviews;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BerlinClockConverter implements TimeConverter {

	private static final String YELLOW_LAMP_ON = "Y";
	private static final String RED_LAMP_ON = "R";
	private static final String LAMP_OFF = "O";

	private static final Logger LOG = LoggerFactory.getLogger(BerlinClockConverter.class);

	@Override
	public String convertTime(String timeString) {
		if (StringUtils.isEmpty(timeString)) {
			throw new IllegalArgumentException("Input Time is empty");
		}
		try {
			/* NOTE : Time Parsing should have been done as per below commented code. but there are 2 contradictory Midnight test cases in story ,
			 * displaying different outputs at same time based on input format (00:00:00 / 24:00:00).
			
			LocalTime parsedDate = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
			int hour = parsedDate.get(ChronoField.HOUR_OF_DAY);
			int minute = parsedDate.get(ChronoField.MINUTE_OF_HOUR);
			int second = parsedDate.get(ChronoField.SECOND_OF_MINUTE);*/
			
			String[] times = timeString.split(":");
			if(times.length != 3){
				throw new IllegalArgumentException("Input Time not Valid,Required Format : HH:mm:ss");
			}
			
			int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);
            int second = Integer.parseInt(times[2]);
            
            validateTimeComponent(hour, 0, 24);
            validateTimeComponent(minute, 0, 59);
            validateTimeComponent(second, 0, 59);
			LOG.info(String.format("Hours : %d,Minutes : %d,Seconds : %d", hour, minute, second));

			String formattedClockDisplayStr = formatBerlinClockDisplay(hour, minute, second);
			return formattedClockDisplayStr;

		} catch(NumberFormatException ex){
			throw new IllegalArgumentException("Input Time not Valid,Required Format : HH:mm:ss");
		}
		/*catch (DateTimeParseException ex) {
			throw new IllegalArgumentException("Input Time not Valid,Required Format : HH:mm:ss");
		}*/

	}
	
	private void validateTimeComponent(int component,int min,int max){
		if(component < min || component > max)
			throw new IllegalArgumentException("Input Time not Valid,Required Format : HH:mm:ss");
	}

	private String formatRow(int rowOnLamps, int rowOffLamps, String lampSymbol) {
		return StringUtils.repeat(lampSymbol, rowOnLamps) + StringUtils.repeat(LAMP_OFF, rowOffLamps);
	}

	private String formatBerlinClockDisplay(int hours, int minutes, int seconds) {
		StringBuffer buffer = new StringBuffer();
		String topLampBlinksPer2Seconds = (seconds % 2 == 0) ? YELLOW_LAMP_ON : LAMP_OFF;
		buffer.append(topLampBlinksPer2Seconds);
		buffer.append(System.getProperty("line.separator"));
		int rowOnLamps = hours / 5;
		int rowOffLamps = 4 - rowOnLamps;
		buffer.append(formatRow(rowOnLamps, rowOffLamps, RED_LAMP_ON));
		buffer.append(System.getProperty("line.separator"));
		rowOnLamps = hours % 5;
		rowOffLamps = 4 - rowOnLamps;
		buffer.append(formatRow(rowOnLamps, rowOffLamps, RED_LAMP_ON));
		buffer.append(System.getProperty("line.separator"));
		rowOnLamps = minutes / 5;
		rowOffLamps = 11 - rowOnLamps;
		buffer.append(formatRow(rowOnLamps, rowOffLamps, YELLOW_LAMP_ON).replaceAll("YYY", "YYR"));
		buffer.append(System.getProperty("line.separator"));
		rowOnLamps = minutes % 5;
		rowOffLamps = 4 - rowOnLamps;
		buffer.append(formatRow(rowOnLamps, rowOffLamps, YELLOW_LAMP_ON));
		return buffer.toString();
	}
	

}
