/*
package com.aquarius.wizard.common.utils;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.ietf.jgss.GSSException;

public class HadoopWithKerberosAuthentication {

    public static void main(String[] args) throws IOException, GSSException {

        // set kerberos host and realm
        System.setProperty("java.security.krb5.realm", "DRB.COM");
        System.setProperty("java.security.krb5.kdc", "192.168.33.10");

        Configuration conf = new Configuration();

        conf.set("hadoop.security.authentication", "kerberos");
        conf.set("hadoop.security.authorization", "true");

        conf.set("fs.defaultFS", "hdfs://192.168.33.10");
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());

        // hack for running locally with fake DNS records
        // set this to true if overriding the host name in /etc/hosts
        conf.set("dfs.client.use.datanode.hostname", "true");

        // server principal
        // the kerberos principle that the namenode is using
        conf.set("dfs.namenode.kerberos.principal.pattern", "hduser/*@DRB.COM");

        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab("dbathgate@DRB.COM", "src/main/resources/dbathgate.keytab");

        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
        while(files.hasNext()) {
            LocatedFileStatus file = files.next();

            System.out.println(IOUtils.toString(fs.open(file.getPath())));
        }
    }
}*/
