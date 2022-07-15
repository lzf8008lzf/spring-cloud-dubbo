package com.enjoy.common.log;

import java.util.regex.Pattern;

public class RegexReplacement {
    /**
     * 脱敏匹配正则
     */
    private Pattern regex;
    /**
     * 替换正则
     */
    private String replacement;

    public RegexReplacement(){

    }

    public RegexReplacement(String regex,String replacement){
        Pattern pattern = Pattern.compile(regex);
        this.regex = pattern;
        this.replacement = replacement;
    }

    /**
     * Perform the replacement.
     *
     * @param msg The String to match against.
     * @return the replacement String.
     */
    public String format(final String msg) {
        return regex.matcher(msg).replaceAll(replacement);
    }

    public Pattern getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = Pattern.compile(regex);
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}

