package util;

/**
 * A class for handling duration convertion.
 */
public class DurationConverter {

  public static int hoursAndMinutesToMinutes(int hours, int minutes) {
    return hours * 60 + minutes;
  }

  /**
   * Converts minutes to hours and minutes.
   * 
   * @param minutes minutes to be converter.
   * @return Tupple of hour and minutes
   */
  public static int[] minutesToHoursAndMinutes(int minutes) {
    int[] timeTuppel = new int[2];
    timeTuppel[0] = Math.floorDiv(minutes, 60);
    timeTuppel[1] = minutes - timeTuppel[0] * 60;
    return timeTuppel;
  }

  /**
   * Duration display text.
   * 
   * @param totalMinutes minutes to be converted
   * @return Stringified to hours and minutes.
   */
  public static String getDurationDisplayText(int totalMinutes) {
    int[] timetuppel = minutesToHoursAndMinutes(totalMinutes);
    int hours = timetuppel[0];
    int minutes = timetuppel[1];
    String durationString = String.valueOf(hours) + " : " + String.valueOf(minutes);
    return hours < 10 ? "0" + durationString : durationString;
  }
}
