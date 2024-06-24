package com.behappy.java.feature;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

//测试指标为吞吐量
@BenchmarkMode(Mode.Throughput)
//需要预热，排除 jit 即时编译以及 JVM 采集各种指标带来的影响，由于我们单次循环很多次，所以预热一次就行
@Warmup(iterations = 1)
//单线程即可
@Fork(1)
//测试次数，我们测试10次
@Measurement(iterations = 10)
//定义了一个类实例的生命周期，所有测试线程共享一个实例
@State(value = Scope.Benchmark)
public class VectorTest {
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_256;
    final int size = 1000;
    final float[] a = new float[size];
    final float[] b = new float[size];
    final float[] c = new float[size];

    public VectorTest() {
        for (int i = 0; i < size; i++) {
            a[i] = ThreadLocalRandom.current().nextFloat(0.0001f, 100.0f);
            b[i] = ThreadLocalRandom.current().nextFloat(0.0001f, 100.0f);
        }
    }

    public static void main(String[] args) throws RunnerException {
        var opt = new OptionsBuilder().include(VectorTest.class.getSimpleName()).build();
        new Runner(opt).run();
    }

    @Benchmark
    public void testScalar(Blackhole blackhole) throws Exception {
        for (int i = 0; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
    }

    @Benchmark
    public void testVector(Blackhole blackhole) {
        int i = 0;
        //高于数组长度的 SPECIES 一次处理数据长度的倍数
        int upperBound = SPECIES.loopBound(a.length);
        //每次循环处理 SPECIES.length() 这么多的数据
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc;
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            var vc = va.mul(va)
                    .add(vb.mul(vb))
                    .neg();
            vc.intoArray(c, i);
        }

        for (; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
    }
}
