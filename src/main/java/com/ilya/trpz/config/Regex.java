package com.ilya.trpz.config;

public interface Regex {
    String regexFirstName = "\\b[А-Яа-яІЇЄЖЭЁИЪA-Za-z]{4,15}\\b";
    String regexLastName = "\\b[А-Яа-яІЇЄЖЭЁИЪA-Za-z]{4,15}\\b";
    String regexEmail = "\\b[A-Za-z0-9._]+@[A-Za-z0-9.]+\\.[A-Za-z]{2,5}\\b";
    String regexUsername = "\\b[A-Za-z0-9._]{5,15}\\b";
    String regexPassword = "\\b[А-Яа-яІЇЄЭЁИA-Za-z0-9._!@#&$]{5,25}\\b";

    String regexTitle = "\\b[А-Яа-яІЇЄЖЭЁИЪA-Za-z]{4,15}\\b";
    String regexWeight = "\\b[0-9]+\\b";
    String regexPrice = "\\d+";
    String regexPhone = "\\b[0-9]{10}\\b";
}
