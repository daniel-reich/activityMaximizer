package model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by surender on 2/22/2017.
 */

@IgnoreExtraProperties
public class SolutionNo {

    public String solution_number,state;

    public SolutionNo(String solution_number, String state) {
        this.solution_number = solution_number;
        this.state=state;
    }

}
