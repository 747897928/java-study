package com.aquarius.wizard.common;

import com.beust.jcommander.JCommander;

public class JcommanderTest {

    public static void main(String[] args) {
        //https://jcommander.org/
        Args paramArgs = new Args();
        String[] argv = {"-log=2", "-debug", "-groups=unit", "-host=127.0.0.1", "-password", "1234", "-host=192.168.1.2",
                "-setterParameter=123", "-DenableAssertions=true", "-Djava.awt.headless=true", "-DenableAssertions=true"};
        JCommander.newBuilder()
                .addObject(paramArgs)
                .build()
                .parse(argv);

        System.out.println(paramArgs);
    }
}
