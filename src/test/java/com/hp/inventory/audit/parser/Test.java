package com.hp.inventory.audit.parser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: description
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File f = new File("data/productPage16.html");
        String content = FileUtils.readFileToString(f);
        Pattern pat = Pattern.compile("<span class=\"prodNum\">(.*?)</span>");
        Matcher matcher = pat.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(1).trim());
        }

    }
}
