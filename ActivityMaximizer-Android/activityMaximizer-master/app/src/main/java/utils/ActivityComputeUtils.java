package utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Event;
import u.activitymanager.StringUtils;

/**
 * Created by LucianVictor on 4/1/2017.
 */
public class ActivityComputeUtils {

    public static LinkedHashMap<String, Integer> computeActivityCount(Collection<Event> events, Date date) {

        int appointmentsSetCount = 0;
        int ktDoneCount = 0;
        int interviewCount = 0;
        int closedLifeCount = 0;
        int closedIBACount = 0;
        int inviteCount = 0;
        int newShowCount = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -7);
        Date start = c.getTime();

        for (Event event : events) {
            if (event.type == null) continue;
            Date eventDate = event.getDate();
            if (eventDate.before(start) || eventDate.after(date)) continue;

            switch (event.type) {
                case "Set Appointment":
                    appointmentsSetCount++;
                    break;
                case "Conducted Interview":
                    interviewCount++;
                    break;
                case "Went on KT":
                    ktDoneCount++;
                    break;
                case "Closed Life":
                    closedLifeCount++;
                    break;
                case "Closed IBA":
                    closedIBACount++;
                    break;
                case "Invited To Opportunity Meeting":
                    inviteCount++;
                    break;
                case "Went To Opportunity Meeting":
                    newShowCount++;
                    break;
            }
        }

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        result.put("Appointments Set", appointmentsSetCount);
        result.put("Kitchen Tables Done", ktDoneCount);
        result.put("Interviews", interviewCount);
        result.put("Apps Closed", closedLifeCount);
        result.put("Recruits", closedIBACount);
        result.put("Confirmed Invites", inviteCount);
        result.put("New Shows", newShowCount);
        result.put("Upcoming Appointments", computeUpcomingAppointments(events));
        result.put("Monthly Premium", computeMonthlySalesTotal(events));
        result.put("Contacts Added", 0);
        return result;
    }

    public static int computeUpcomingAppointments(Collection<Event> events) {
        int upcomingAppointmentsCount = 0;

        Date now = new Date();
        for (Event event : events) {
            if (!"Set Appointment".equals(event.type)) continue;
            if (event.getDate().after(now)) upcomingAppointmentsCount++;
        }
        return upcomingAppointmentsCount;
    }

    public static int computeMonthlySalesTotal(Collection<Event> events) {
        if (events == null || events.size() == 0) return 0;

        int total = 0;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date start = c.getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = c.getTime();

        for (Event event : events) {
            if (StringUtils.isEmpty(event.type)) continue;
            if ("Closed Life".equalsIgnoreCase(event.type)) {
                long millis = event.getDate().getTime();
                if (millis >= start.getTime() && millis <= end.getTime()) {
                    total += event.amount;
                }
            }
        }

        return total;
    }

    public static int computeWeeklyTotal(Map<String, Integer> activityCount) {
        int total = 0;

        for (Map.Entry<String, Integer> entry : activityCount.entrySet()) {
            String type = entry.getKey();
            Integer value = entry.getValue();
            if (type == null || value == null) continue;
            switch (type) {
                case "Appointments Set":
                    total += (value * 2);
                    break;
                case "Kitchen Tables Done":
                    total += (value * 3);
                    break;
                case "Interviews":
                    total += (value * 2);
                    break;
                case "Apps Closed":
                    total += (value * 5);
                    break;
                case "Recruits":
                    total += (value * 5);
                    break;
                case "Confirmed Invites":
                    total += value;
                    break;
                case "New Shows":
                    total += (value * 3);
                    break;
            }
        }

        Integer newContactsTotal = activityCount.get("Contacts Added");
        total += Math.min(newContactsTotal != null ? newContactsTotal : 0, 20);

        return Math.min(total, 100);
    }

}
