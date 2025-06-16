package com.aquarius.wizard.common;

import com.beust.jcommander.JCommander;

public class JcommanderTest {

    public static void main(String[] args) {
        //https://jcommander.org/
        Args paramArgs = new Args();
        String[] argv = {"-log", "2", "-groups", "unit", "-password", "1234"};
        JCommander.newBuilder()
                .addObject(paramArgs)
                .build()
                .parse(argv);

        System.out.println(paramArgs);
    }
}
