package com.aquarius.wizard.common;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Args {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
    private Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    private Integer setterParameter;

    @Parameter(names = "-setterParameter", description = "A parameter annotation on a setter method")
    public void setParameter(Integer value) {
        this.setterParameter = value;
    }

    @Parameter(names = "-password", description = "Connection password", password = true)
    private String password;
}