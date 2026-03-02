package com.aquarius.wizard.common;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Parameters(separators = "=")
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

    //如果类型是list，则这个参数可以出现多次
    @Parameter(names = "-host", description = "host", password = true)
    private List<String> hosts = new ArrayList<>();

    //动态参数，可以重复多次，允许接受没有预先定义的选项，比如-Da=b, -Dc=d
    @DynamicParameter(names = "-D", description = "Dynamic Parameter")
    private Map<String,String> params = new HashMap<>();

}