package org.behappy.java;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class Main {


    static void f1(Map<String, ? extends Map<String, Integer>> map) {

    }

    public static boolean isUnsignedLongPowerOf2(long n) {
        return n != 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        var yaml = new Yaml();
//        var config = yaml.loadAs(Files.newInputStream(Path.of("bucket4j-config.yml")), Bucket4jConfig.class);
        System.out.println(Files.exists(Path.of("file:/Applications")));
//        RRateLimiter rrl = new RedissonRateLimiter(null, "");
//        RateLimiter rl;
//        WatchService ws;
//        FileSystemWatcher fsw = new FileSystemWatcher();
//        fsw.addListener(cfss -> {
//            System.out.println("File change");
//            for (var cfs : cfss) {
//                for (var cf : cfs.getFiles()) {
//                    System.out.println(cf.getFile());
//                }
//            }
//        });
//        fsw.addSourceDirectory(new File("."));
//        fsw.start();
//        System.in.read();
    }
}
