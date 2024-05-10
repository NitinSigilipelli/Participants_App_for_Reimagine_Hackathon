package com.example.reimagine;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        // Create some sample data
        ArrayList<TimeLineEvent> events = new ArrayList<>();
        // Creating timestamps for start and end times
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp event1Start = Timestamp.valueOf("2024-04-29 07:00:00");
        Timestamp event1End = Timestamp.valueOf("2024-04-29 08:00:00");
        Timestamp event2Start = Timestamp.valueOf("2024-04-29 8:00:00");
        Timestamp event2End = Timestamp.valueOf("2024-04-29 10:30:00");
        Timestamp event3Start = Timestamp.valueOf("2024-04-29 10:30:00");
        Timestamp event3End = Timestamp.valueOf("2024-04-29 11:00:00");
        Timestamp event4Start = Timestamp.valueOf("2024-04-29 11:00:00");
        Timestamp event4End = Timestamp.valueOf("2024-04-29 13:00:00");
        Timestamp event5Start = Timestamp.valueOf("2024-04-29 13:00:00");
        Timestamp event5End = Timestamp.valueOf("2024-04-29 14:00:00");
        Timestamp event6Start = Timestamp.valueOf("2024-04-29 14:00:00");
        Timestamp event6End = Timestamp.valueOf("2024-04-29 16:00:00");
        Timestamp event7Start = Timestamp.valueOf("2024-04-29 16:00:00");
        Timestamp event7End = Timestamp.valueOf("2024-04-29 16:10:00");
        Timestamp event8Start = Timestamp.valueOf("2024-04-29 16:10:00");
        Timestamp event8End = Timestamp.valueOf("2024-04-29 19:30:00");
        Timestamp event9Start = Timestamp.valueOf("2024-04-29 19:30:00");
        Timestamp event9End = Timestamp.valueOf("2024-04-29 21:30:00");
        Timestamp event10Start = Timestamp.valueOf("2024-04-29 21:30:00");
        Timestamp event10End = Timestamp.valueOf("2024-04-29 23:30:00");
        Timestamp event11Start = Timestamp.valueOf("2024-04-29 23:30:00");
        Timestamp event11End = Timestamp.valueOf("2024-04-29 23:40:00");
        Timestamp event12Start = Timestamp.valueOf("2024-04-29 23:40:00");
        Timestamp event12End = Timestamp.valueOf("2024-04-30 01:00:00");
        //day2
        Timestamp event13Start = Timestamp.valueOf("2024-04-30 01:00:00");
        Timestamp event13End = Timestamp.valueOf("2024-04-30 02:00:00");
        Timestamp event14Start = Timestamp.valueOf("2024-04-30 02:00:00");
        Timestamp event14End = Timestamp.valueOf("2024-04-30 03:00:00");
        Timestamp event15Start = Timestamp.valueOf("2024-04-30 03:00:00");
        Timestamp event15End = Timestamp.valueOf("2024-04-30 04:30:00");
        Timestamp event16Start = Timestamp.valueOf("2024-04-30 04:30:00");
        Timestamp event16End = Timestamp.valueOf("2024-04-30 04:40:00");
        Timestamp event17Start = Timestamp.valueOf("2024-04-30 04:40:00");
        Timestamp event17End = Timestamp.valueOf("2024-04-30 07:00:00");
        Timestamp event18Start = Timestamp.valueOf("2024-04-30 07:00:00");
        Timestamp event18End = Timestamp.valueOf("2024-04-30 09:00:00");
        Timestamp event19Start = Timestamp.valueOf("2024-04-30 09:00:00");
        Timestamp event19End = Timestamp.valueOf("2024-04-30 11:00:00");
        Timestamp event20Start = Timestamp.valueOf("2024-04-30 11:00:00");
        Timestamp event20End = Timestamp.valueOf("2024-04-30 12:30:00");
        Timestamp event21Start = Timestamp.valueOf("2024-04-30 12:30:00");
        Timestamp event21End = Timestamp.valueOf("2024-04-30 13:30:00");
        Timestamp event22Start = Timestamp.valueOf("2024-04-30 13:30:00");
        Timestamp event22End = Timestamp.valueOf("2024-04-30 16:30:00");
        Timestamp event23Start = Timestamp.valueOf("2024-04-30 16:30:00");
        Timestamp event23End = Timestamp.valueOf("2024-04-30 16:40:00");
        Timestamp event24Start = Timestamp.valueOf("2024-04-30 16:40:00");
        Timestamp event24End = Timestamp.valueOf("2024-04-30 19:30:00");
        Timestamp event25Start = Timestamp.valueOf("2024-04-30 19:30:00");
        Timestamp event25End = Timestamp.valueOf("2024-04-30 21:30:00");
        Timestamp event26Start = Timestamp.valueOf("2024-04-30 21:30:00");
        Timestamp event26End = Timestamp.valueOf("2024-04-30 23:30:00");
        Timestamp event27Start = Timestamp.valueOf("2024-04-30 23:30:00");
        Timestamp event27End = Timestamp.valueOf("2024-04-30 23:40:00");
        Timestamp event28Start = Timestamp.valueOf("2024-04-30 23:40:00");
        Timestamp event28End = Timestamp.valueOf("2024-05-01 01:00:00");
        //day3
        Timestamp event29Start = Timestamp.valueOf("2024-05-01 01:00:00");
        Timestamp event29End = Timestamp.valueOf("2024-05-01 02:00:00");
        Timestamp event30Start = Timestamp.valueOf("2024-05-01 02:00:00");
        Timestamp event30End = Timestamp.valueOf("2024-05-01 03:30:00");
        Timestamp event31Start = Timestamp.valueOf("2024-05-01 03:30:00");
        Timestamp event31End = Timestamp.valueOf("2024-05-01 04:30:00");
        Timestamp event32Start = Timestamp.valueOf("2024-05-01 04:30:00");
        Timestamp event32End = Timestamp.valueOf("2024-05-01 04:40:00");
        Timestamp event33Start = Timestamp.valueOf("2024-05-01 04:40:00");
        Timestamp event33End = Timestamp.valueOf("2024-05-01 07:00:00");
        Timestamp event34Start = Timestamp.valueOf("2024-05-01 07:00:00");
        Timestamp event34End = Timestamp.valueOf("2024-05-01 09:00:00");
        Timestamp event35Start = Timestamp.valueOf("2024-05-01 09:00:00");
        Timestamp event35End = Timestamp.valueOf("2024-05-01 11:00:00");
        Timestamp event36Start = Timestamp.valueOf("2024-05-01 11:00:00");
        Timestamp event36End = Timestamp.valueOf("2024-05-01 12:30:00");
        Timestamp event37Start = Timestamp.valueOf("2024-05-01 12:30:00");
        Timestamp event37End = Timestamp.valueOf("2024-05-01 13:30:00");
        Timestamp event38Start = Timestamp.valueOf("2024-05-01 13:30:00");
        Timestamp event38End = Timestamp.valueOf("2024-05-01 15:00:00");
        Timestamp event39Start = Timestamp.valueOf("2024-05-01 15:00:00");
        Timestamp event39End = Timestamp.valueOf("2024-05-01 17:00:00");
        Timestamp event40Start = Timestamp.valueOf("2024-05-01 17:00:00");
        Timestamp event40End = Timestamp.valueOf("2024-05-01 18:30:00");
        Timestamp event41Start = Timestamp.valueOf("2024-05-01 18:30:00");
        Timestamp event41End = Timestamp.valueOf("2024-05-01 20:30:00");
        Timestamp event42Start = Timestamp.valueOf("2024-05-01 20:30:00");
        Timestamp event42End = Timestamp.valueOf("2024-05-01 21:30:00");

        events.add(new TimeLineEvent(event1Start, event1End, "Breakfast"));
        events.add(new TimeLineEvent(event2Start, event2End, "Opening Ceremony"));
        events.add(new TimeLineEvent(event3Start, event3End, "Teams Reporting"));
        events.add(new TimeLineEvent(event4Start, event4End, "Working on ideation"));
        events.add(new TimeLineEvent(event5Start, event5End, "Lunch break"));
        events.add(new TimeLineEvent(event6Start, event6End, "Working on project"));
        events.add(new TimeLineEvent(event7Start, event7End, "Snacks break"));
        events.add(new TimeLineEvent(event8Start, event8End, "Working on project"));
        events.add(new TimeLineEvent(event9Start, event9End, "Dinner with fun activities"));
        events.add(new TimeLineEvent(event10Start, event10End, "Working on project"));
        events.add(new TimeLineEvent(event11Start, event11End, "Snacks break"));
        events.add(new TimeLineEvent(event12Start, event12End, "First round evaluation"));
        //day2
        events.add(new TimeLineEvent(event13Start, event13End, "Refinement process"));
        events.add(new TimeLineEvent(event14Start, event14End, "Camp fire"));
        events.add(new TimeLineEvent(event15Start, event15End, "Working on project"));
        events.add(new TimeLineEvent(event16Start, event16End, "Snacks break"));
        events.add(new TimeLineEvent(event17Start, event17End, "Working on project"));
        events.add(new TimeLineEvent(event18Start, event18End, "Long break"));
        events.add(new TimeLineEvent(event19Start, event19End, "Working on project"));
        events.add(new TimeLineEvent(event20Start, event20End, "Tech experts session"));
        events.add(new TimeLineEvent(event21Start, event21End, "Lunch break"));
        events.add(new TimeLineEvent(event22Start, event22End, "Working on project"));
        events.add(new TimeLineEvent(event23Start, event23End, "Snacks break"));
        events.add(new TimeLineEvent(event24Start, event24End, "Doubts clarification session"));
        events.add(new TimeLineEvent(event25Start, event25End, "Dinner with fun activities"));
        events.add(new TimeLineEvent(event26Start, event26End, "Working on project"));
        events.add(new TimeLineEvent(event27Start, event27End, "Snacks break"));
        events.add(new TimeLineEvent(event28Start, event28End, "Second round evaluation"));

        //Day3
        events.add(new TimeLineEvent(event29Start, event29End, "Assest Refinement process"));
        events.add(new TimeLineEvent(event30Start, event30End, "Dj Night"));
        events.add(new TimeLineEvent(event31Start, event31End, "Working on project"));
        events.add(new TimeLineEvent(event32Start, event32End, "Snacks break"));
        events.add(new TimeLineEvent(event33Start, event33End, "Working on project"));
        events.add(new TimeLineEvent(event34Start, event34End, "Long break"));
        events.add(new TimeLineEvent(event35Start, event35End, "Working on project"));
        events.add(new TimeLineEvent(event36Start, event36End, "Meet and Greet session"));
        events.add(new TimeLineEvent(event37Start, event37End, "Lunch break"));
        events.add(new TimeLineEvent(event38Start, event38End, "Working on Optimization and Bug fixes"));
        events.add(new TimeLineEvent(event39Start, event39End, "Final Round of Evaluation"));
        events.add(new TimeLineEvent(event40Start, event40End, "Break"));
        events.add(new TimeLineEvent(event41Start, event41End, "Valedictory"));
        events.add(new TimeLineEvent(event42Start, event42End, "Dinner"));



        // Get reference to the table layout
        TableLayout tableLayout = view.findViewById(R.id.tableLayout);

        // Populate the table
        for (TimeLineEvent event : events) {
            TableRow row = new TableRow(getContext());

            TextView timelineText = new TextView(getContext());
            timelineText.setText(event.getStartTime().toString());
            timelineText.setPadding(10, 10, 10, 10);
            row.addView(timelineText);

            TextView eventText = new TextView(getContext());
            eventText.setText(event.getEventName());
            eventText.setPadding(10, 10, 10, 10);
            row.addView(eventText);

            // Check if the event is currently running or finished
            if (currentTime.after(event.getStartTime()) && currentTime.before(event.getEndTime())) {
                // Event is currently running
                timelineText.setTextColor(Color.WHITE);
                eventText.setTextColor(Color.WHITE);
                row.setBackgroundColor(Color.BLUE);

            } else if (currentTime.after(event.getEndTime())) {
                // Event has finished
                row.setBackgroundColor(Color.RED);
            }

            tableLayout.addView(row);
        }
        return view;
    }
}