package com.bridgelabz.questionbank.Model;

import java.util.List;

public class Questions {

    private String question;
    private questionType questionType;

    private questionLevels questionLevels;
    List<String> options;

    enum questionType{
        Singlechoice,
        Multiplechoice,
        FillinTheBlank
    }

    enum questionLevels{
        Easy,
        Medium,
        Hard
    }
    enum questionOption{
        Option1,
        Option2,
        Option3,
        Option4
    }
}
