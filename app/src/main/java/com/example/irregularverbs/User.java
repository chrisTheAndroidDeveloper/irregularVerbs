package com.example.irregularverbs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class User {
    public int goal;
    public boolean exactAlarmsAllowed;
    public int completedToday;
    public boolean[] daysOfPractice;
    public int totalGoals;
    public int completedGoals;
    public int bestScore;
    public int totalScore;
    public int testsCompleted;
    public int numQuestions;
    public int questionType;
    public boolean dailyGoalsOn;
    public boolean remindersOn;
    public int reminderMinute;
    public int reminderHour;
    public int testRangeStart;
    public int testRangeEnd;
    public boolean askAboutRangeEachTime;
    public int day = -1;
    public int year;

    public boolean hasCompletedTests(){
        return testsCompleted != 0;
    }

    public void setDayOfPractice(int i, boolean isAPracticeDay) {
        daysOfPractice[i] = isAPracticeDay;
    }

    public String getAverageScoreString() {
        if(testsCompleted > 0) {
            return (totalScore / testsCompleted)+"";
        }
        return "-";
    }

    public String getGoalsCompletionString() {
        if(totalGoals > 0) {
            return ((int) ((double) completedGoals / totalGoals * 100)) + "%";
        }
        return "-";
    }

    public int getTodaysGoalCompletion() {
        return (int) ((double) completedToday / goal * 100);
    }

    public User(){
        goal = 5;
        completedToday = 0;
        daysOfPractice = new boolean[]{true, true, true, true, true, true, true};
        totalGoals = 1;
        completedGoals = 0;
        bestScore = 0;
        totalScore = 0;
        testsCompleted = 0;
        reminderMinute = 0;
        reminderHour = 15;
        numQuestions = 5;
        questionType = 2;
        dailyGoalsOn = true;
        remindersOn = true;
        testRangeStart = 1;
        testRangeEnd = TestActivity1.verbs.length;
        askAboutRangeEachTime = true;
        exactAlarmsAllowed = false;
    }

    public static User copy(User other){
        User res = new User();
        res.goal = other.goal;
        res.completedToday = other.completedToday;
        res.daysOfPractice = new boolean[other.daysOfPractice.length];
        System.arraycopy(other.daysOfPractice, 0, res.daysOfPractice, 0, other.daysOfPractice.length);
        res.totalGoals = other.totalGoals;
        res.completedGoals = other.completedGoals;
        res.bestScore = other.bestScore;
        res.totalScore = other.totalScore;
        res.testsCompleted = other.testsCompleted;
        res.reminderMinute = other.reminderMinute;
        res.reminderHour = other.reminderHour;
        res.numQuestions = other.numQuestions;
        res.questionType = other.questionType;
        res.dailyGoalsOn = other.dailyGoalsOn;
        res.remindersOn = other.remindersOn;
        res.testRangeStart = other.testRangeStart;
        res.testRangeEnd = other.testRangeEnd;
        res.askAboutRangeEachTime = other.askAboutRangeEachTime;
        res.day = other.day;
        res.year = other.year;
        res.exactAlarmsAllowed = other.exactAlarmsAllowed;
        return res;
    }
}
