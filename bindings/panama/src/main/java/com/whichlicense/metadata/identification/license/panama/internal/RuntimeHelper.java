// Generated by jextract

package com.whichlicense.metadata.identification.license.panama.internal;

import com.whichlicense.foreign.ForeignRuntimeHelper;
import com.whichlicense.metadata.identification.license.LicenseIdentificationPipelineStepTrace;
import com.whichlicense.metadata.identification.license.LicenseMatch;
import com.whichlicense.metadata.identification.license.LicenseNormalization;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep.Batch;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep.Remove;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep.Replace;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStepArgument;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStepArgument.Regex;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStepArgument.Text;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.foreign.SymbolLookup.loaderLookup;
import static java.util.Map.entry;
import static java.util.stream.Collectors.*;

public final class RuntimeHelper {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final ClassLoader LOADER = RuntimeHelper.class.getClassLoader();
    private static final MethodHandles.Lookup MH_LOOKUP = MethodHandles.lookup();
    private static final SymbolLookup SYMBOL_LOOKUP;

    static {
        ForeignRuntimeHelper.loadLibrary(LOADER, "identification");
        SYMBOL_LOOKUP = name -> loaderLookup().find(name)
                .or(() -> LINKER.defaultLookup().find(name));
    }

    // Suppresses default constructor, ensuring non-instantiability.
    private RuntimeHelper() {
    }

    static MethodHandle downcallHandle(String name, FunctionDescriptor fdesc) {
        return SYMBOL_LOOKUP.find(name).
                map(addr -> LINKER.downcallHandle(addr, fdesc)).
                orElse(null);
    }

    static MethodHandle upcallHandle(Class<?> fi, String name, FunctionDescriptor fdesc) {
        try {
            return MH_LOOKUP.findVirtual(fi, name, fdesc.toMethodType());
        } catch (Throwable ex) {
            throw new AssertionError(ex);
        }
    }

    static <Z> MemorySegment upcallStub(MethodHandle fiHandle, Z z, FunctionDescriptor fdesc, SegmentScope scope) {
        try {
            fiHandle = fiHandle.bindTo(z);
            return LINKER.upcallStub(fiHandle, fdesc, scope);
        } catch (Throwable ex) {
            throw new AssertionError(ex);
        }
    }


    public static CNormalizationFn wrapNormalizationFunction(LicenseNormalization normalization, SegmentAllocator allocator) {
        return input -> allocator.allocateUtf8String(normalization.apply(input.getUtf8String(0)));
    }

    private static String describeOperation(PipelineStep step) {
        return switch (step) {
            case Remove ignored -> "remove";
            case Replace ignored -> "replace";
            case Batch ignored -> "batch";
            case PipelineStep.Custom ignored -> "custom-function";
        };
    }

    private static Map<String, Object> captureOperationParams(PipelineStep step) {
        return Stream.of(step).<Entry<String, Object>>mapMulti((s, consumer) -> {
            switch (s) {
                case Remove(var argument) -> {
                    switch (argument) {
                        case Regex(var pattern) -> consumer.accept(entry("pattern", pattern.pattern()));
                        case Text(var text) -> consumer.accept(entry("text", text));
                    }
                }
                case Replace(var argument, var replacement) -> {
                    switch (argument) {
                        case Regex(var pattern) -> consumer.accept(entry("pattern", pattern.pattern()));
                        case Text(var text) -> consumer.accept(entry("text", text));
                    }
                    consumer.accept(entry("replacement", replacement));
                }
                case Batch(var steps) -> {
                    record PipelineStepImpl(String operation, Map<String, Object> parameters) {}
                    consumer.accept(entry("steps", steps.stream()
                            .map(nested -> new PipelineStepImpl(describeOperation(nested),
                                    captureOperationParams(nested)))));
                }
                default -> {}
            }
        }).collect(toMap(Entry::getKey, Entry::getValue, (f, s) -> s));
    }

    public static List<LicenseIdentificationPipelineStepTrace> licenseIdentificationPipelineStepTraceSetOfAddress(MemorySegment addr, String algorithm, Map<String, Object> parameters, List<PipelineStep> steps, SegmentScope scope) {
        record LicenseIdentificationPipelineStepTraceImpl(long step, String operation, Map<String, Object> parameters, Map<String, Float> matches, boolean terminated) implements LicenseIdentificationPipelineStepTrace {
        }

        var rawMatches = PipelineLicenseMatches.ofAddress(addr, scope);
        var rawLength = PipelineLicenseMatches.length$get(rawMatches);

        var matches = LicenseMatches.arrayOfAddress(PipelineLicenseMatches.step_matches$get(rawMatches), rawLength, scope)
                .elements(LicenseMatches.$LAYOUT())
                .map(e -> licenseMatchSetOfAddress(e, algorithm, parameters, scope))
                .toList();

        var last = Math.min(steps.size(), matches.size());
        return IntStream.range(0, last)
                .mapToObj(i -> new LicenseIdentificationPipelineStepTraceImpl(
                        i + 1,
                        describeOperation(steps.get(i)),
                        captureOperationParams(steps.get(i)),
                        matches.get(i).stream()
                                .map(m -> entry(m.license(), m.confidence()))
                                .collect(toMap(Entry::getKey, Entry::getValue, (f, s) -> s, TreeMap::new)),
                        i == last - 1
                ))
                .collect(toUnmodifiableList());
    }

    public static Set<LicenseMatch> licenseMatchSetOfAddress(MemorySegment addr, String algorithm, Map<String, Object> parameters, SegmentScope scope) {
        record LicenseMatchImpl(String license, float confidence, String algorithm,
                                Map<String, Object> parameters) implements LicenseMatch, Comparable<LicenseMatch> {
            @Override
            public int compareTo(LicenseMatch o) {
                return Float.compare(o.confidence(), this.confidence);
            }
        }

        var matches = LicenseMatches.ofAddress(addr, scope);
        var length = LicenseMatches.length$get(matches);

        return LicenseMatchEntry
                .arrayOfAddress(LicenseMatches.matches$get(matches), length, scope)
                .elements(LicenseMatchEntry.$LAYOUT())
                .map(element -> new LicenseMatchImpl(
                        LicenseMatchEntry.name$get(element).getUtf8String(0)
                                .replace(".LICENSE", ""),
                        LicenseMatchEntry.confidence$get(element),
                        algorithm, parameters
                )).collect(toCollection(TreeSet::new));
    }
}
