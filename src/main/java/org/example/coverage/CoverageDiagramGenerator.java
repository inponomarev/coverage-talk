package org.example.coverage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class CoverageDiagramGenerator {

    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;

    private static final int SVG_WIDTH = 900;
    private static final int SVG_HEIGHT = 900;
    private static final int FONT_SIZE = 36;
    private static final String FONT_NAME = "Arial";

    private static final double YRANGE_MIN = -0.12;
    private static final double YRANGE_MAX = 1.01;

    private static final double LEFT_MARGIN = 0.14;
    private static final double RIGHT_MARGIN = 0.98;
    private static final double BOTTOM_MARGIN = 0.10;
    private static final double TOP_MARGIN = 0.97;

    private static final double PROJECTION_BAR_BOTTOM = -0.06;
    private static final double PROJECTION_BAR_TOP = -0.01;
    private static final double COVERAGE_LABEL_Y = -0.09;

    private static final double VERTICAL_LABEL_SCREEN_X = 0.095;
    private static final double VERTICAL_LABEL_SCREEN_Y = 0.60;

    private static final String FRAME_COLOR = "#777777";
    private static final String PATCH_COLOR = "#4F81BD";
    private static final String PROJECTION_COLOR = "#70AD47";

    private static final DecimalFormat DF = new DecimalFormat(
            "0.######",
            DecimalFormatSymbols.getInstance(Locale.US)
    );

    public static void main(String[] args) throws Exception {
        List<Spec> specs = List.of(
                //1
                new Spec(
                        "square",
                        0.08,
                        List.of()
                ),
                //2
                new Spec(
                        "square",
                        0.08,
                        List.of(entry(0.12, 0.55))
                ),
                //3
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.12, 0.55),
                                entry(0.38, 0.32)
                        )
                ),
                //4
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.12, 0.55),
                                entry(0.38, 0.32),
                                entry(0.43, 0.44),
                                entry(0.74, 0.36)
                        )
                ),
                // 5 make it bigger
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.12, 0.55),
                                entry(0.38, 0.32),
                                entry(0.43, 0.44),
                                entry(0.84, 0.36),
                                entry(0.11, 0.90),
                                entry(0.05, 0.42),
                                entry(0.70, 0.68),
                                entry(0.69, 0.14)

                        )
                ),
                //6
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.12, 0.55),
                                entry(0.38, 0.32),
                                entry(0.43, 0.44),
                                entry(0.84, 0.36),
                                entry(0.11, 0.90),
                                entry(0.05, 0.42),
                                entry(0.70, 0.68),
                                entry(0.69, 0.14),


                                entry(0.92, 0.64),
                                entry(0.71, 0.92),
                                entry(0.41, 0.71),
                                entry(0.47, 0.27),
                                entry(0.24, 0.43),
                                entry(0.73, 0.46),
                                entry(0.29, 0.13),
                                entry(0.41, 0.95)
                        )
                ),
                //7
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.12, 0.55),
                                entry(0.38, 0.32),
                                entry(0.43, 0.44),
                                entry(0.84, 0.36),
                                entry(0.11, 0.90),
                                entry(0.05, 0.42),
                                entry(0.70, 0.68),
                                entry(0.69, 0.14),


                                entry(0.92, 0.64),
                                entry(0.71, 0.92),
                                entry(0.41, 0.71),
                                entry(0.47, 0.27),
                                entry(0.24, 0.43),
                                entry(0.73, 0.46),
                                entry(0.29, 0.13),
                                entry(0.41, 0.95),

                                entry(0.05, 0.79),
                                entry(0.30, 0.95),
                                entry(0.30, 0.23),
                                entry(0.59, 0.27),
                                entry(0.82, 0.51),
                                entry(0.80, 0.19),
                                entry(0.24, 0.60),
                                entry(0.69, 0.56),
                                entry(0.88, 0.27),
                                entry(0.47, 0.56),
                                entry(0.69, 0.82),
                                entry(0.23, 0.71),
                                entry(0.62, 0.43),
                                entry(0.52, 0.46),
                                entry(0.29, 0.32),
                                entry(0.38, 0.13)
                        )
                ),
                //artificial
                new Spec(
                        "square",
                        0.08,
                        List.of(
                                entry(0.04, 0.55),
                                entry(0.12, 0.55),
                                entry(0.20, 0.55),
                                entry(0.28, 0.55),
                                entry(0.36, 0.55),
                                entry(0.44, 0.55),
                                entry(0.52, 0.55),
                                entry(0.60, 0.55),
                                entry(0.68, 0.55),
                                entry(0.76, 0.55),
                                entry(0.84, 0.55),
                                entry(0.92, 0.55),
                                entry(0.96, 0.65)
                        )
                ));

        Path outputDir = Path.of("target/generated-slides/images");
        generateAll(specs, outputDir, true);
    }

    public static void generateAll(List<Spec> specs, Path outputDir, boolean runGnuplot)
            throws IOException, InterruptedException {

        if (specs == null || specs.isEmpty()) {
            throw new IllegalArgumentException("specs must not be null or empty");
        }

        Files.createDirectories(outputDir);

        int index = 1;
        for (Spec spec : specs) {
            validate(spec);

            Result result = compute(spec);

            String baseName = String.format(Locale.US, "coverage-%02d", index);
            Path gpPath = outputDir.resolve(baseName + ".gp");
            Path svgPath = outputDir.resolve(baseName + ".svg");

            String gnuplot = generateGnuplot(spec, result, svgPath);
            Files.writeString(gpPath, gnuplot);

            System.out.printf(
                    Locale.US,
                    "%s: code coverage = %.2f%%, full space coverage = %.2f%%%n",
                    baseName,
                    result.codeCoveragePercent(),
                    result.fullSpaceCoveragePercent()
            );

            for (Interval interval : result.mergedIntervals()) {
                System.out.printf(
                        Locale.US,
                        "  projection [%s, %s]%n",
                        fmt(interval.from()),
                        fmt(interval.to())
                );
            }

            if (runGnuplot) {
                runGnuplot(gpPath);
            }

            index++;
        }
    }

    static Map.Entry<Double, Double> entry(double x, double y) {
        return new AbstractMap.SimpleImmutableEntry<>(x, y);
    }

    static void validate(Spec spec) {
        if (spec == null) {
            throw new IllegalArgumentException("spec must not be null");
        }
        if (spec.shape() == null
                || !(spec.shape().equalsIgnoreCase("square")
                || spec.shape().equalsIgnoreCase("circle"))) {
            throw new IllegalArgumentException("shape must be 'square' or 'circle'");
        }
        if (spec.size() <= 0.0) {
            throw new IllegalArgumentException("size must be > 0");
        }
        if (spec.patches() == null) {
            throw new IllegalArgumentException("patches must not be null");
        }

        for (Map.Entry<Double, Double> patch : spec.patches()) {
            if (patch == null || patch.getKey() == null || patch.getValue() == null) {
                throw new IllegalArgumentException("each patch must contain x and y");
            }
        }
    }

    static Result compute(Spec spec) {
        double halfSpan = spec.size() / 2.0;

        List<Interval> raw = new ArrayList<>();
        for (Map.Entry<Double, Double> patch : spec.patches()) {
            double x = patch.getKey();

            double from = clamp(x - halfSpan, XMIN, XMAX);
            double to = clamp(x + halfSpan, XMIN, XMAX);

            if (to > from) {
                raw.add(new Interval(from, to));
            }
        }

        List<Interval> merged = merge(raw);

        double coveredLength = merged.stream().mapToDouble(Interval::length).sum();
        double codeCoveragePercent = 100.0 * coveredLength / (XMAX - XMIN);

        double singlePatchArea;
        if (spec.shape().equalsIgnoreCase("square")) {
            singlePatchArea = spec.size() * spec.size();
        } else if (spec.shape().equalsIgnoreCase("circle")) {
            double radius = spec.size() / 2.0;
            singlePatchArea = Math.PI * radius * radius;
        } else {
            throw new IllegalArgumentException("shape must be 'square' or 'circle'");
        }

        double fullSpaceCoverageFraction = singlePatchArea * spec.patches().size();
        double fullSpaceCoveragePercent = 100.0 * Math.min(1.0, fullSpaceCoverageFraction);

        return new Result(
                merged,
                coveredLength,
                codeCoveragePercent,
                fullSpaceCoveragePercent
        );
    }

    static List<Interval> merge(List<Interval> raw) {
        if (raw.isEmpty()) {
            return List.of();
        }

        List<Interval> sorted = new ArrayList<>(raw);
        sorted.sort(Comparator.comparingDouble(Interval::from));

        List<Interval> merged = new ArrayList<>();
        Interval current = sorted.get(0);

        for (int i = 1; i < sorted.size(); i++) {
            Interval next = sorted.get(i);
            if (next.from() <= current.to()) {
                current = new Interval(current.from(), Math.max(current.to(), next.to()));
            } else {
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);
        return merged;
    }

    static String generateGnuplot(Spec spec, Result result, Path svgPath) {
        String shape = spec.shape().toLowerCase(Locale.ROOT);
        double size = spec.size();
        double radius = size / 2.0;

        StringBuilder gp = new StringBuilder();

        gp.append("set terminal svg size ")
                .append(SVG_WIDTH).append(",").append(SVG_HEIGHT)
                .append(" dynamic enhanced font \"")
                .append(FONT_NAME).append(",").append(FONT_SIZE)
                .append("\"\n");
        gp.append("set output \"").append(normalizePath(svgPath)).append("\"\n");
        gp.append("\n");

        gp.append("unset key\n");
        gp.append("unset border\n");
        gp.append("unset xtics\n");
        gp.append("unset ytics\n");
        gp.append("\n");

        gp.append("set xrange [0:1]\n");
        gp.append("set yrange [").append(fmt(YRANGE_MIN)).append(":").append(fmt(YRANGE_MAX)).append("]\n");
        gp.append("set size ratio -1\n");
        gp.append("\n");

        gp.append("set lmargin at screen ").append(fmt(LEFT_MARGIN)).append("\n");
        gp.append("set rmargin at screen ").append(fmt(RIGHT_MARGIN)).append("\n");
        gp.append("set bmargin at screen ").append(fmt(BOTTOM_MARGIN)).append("\n");
        gp.append("set tmargin at screen ").append(fmt(TOP_MARGIN)).append("\n");
        gp.append("\n");

        gp.append("set style fill solid 0.35 noborder\n");
        gp.append("\n");

        gp.append("# Main square frame\n");
        gp.append("set object 1 rect from 0,0 to 1,1 fs empty border rgb \"")
                .append(FRAME_COLOR)
                .append("\" lw 2\n");
        gp.append("\n");

        gp.append("# Patches\n");
        int objectId = 2;
        for (Map.Entry<Double, Double> patch : spec.patches()) {
            double x = patch.getKey();
            double y = patch.getValue();

            if ("square".equals(shape)) {
                gp.append("set object ").append(objectId++)
                        .append(" rect at ")
                        .append(fmt(x)).append(",").append(fmt(y))
                        .append(" size ").append(fmt(size)).append(",").append(fmt(size))
                        .append(" fc rgb \"").append(PATCH_COLOR)
                        .append("\" fs solid 0.35 border rgb \"").append(PATCH_COLOR)
                        .append("\" lw 2\n");
            } else {
                gp.append("set object ").append(objectId++)
                        .append(" circle at ")
                        .append(fmt(x)).append(",").append(fmt(y))
                        .append(" size ").append(fmt(radius))
                        .append(" fc rgb \"").append(PATCH_COLOR)
                        .append("\" fs solid 0.35 border rgb \"").append(PATCH_COLOR)
                        .append("\" lw 2\n");
            }
        }
        gp.append("\n");

        gp.append("# Axes of the main space\n");
        gp.append("set arrow 1 from 0,0 to 1,0 nohead lw 2 lc rgb \"").append(FRAME_COLOR).append("\"\n");
        gp.append("set arrow 2 from 0,0 to 0,1 nohead lw 2 lc rgb \"").append(FRAME_COLOR).append("\"\n");
        gp.append("\n");

        gp.append("# Projection bar directly under X axis\n");

        gp.append("set object ").append(objectId++).append(" rect from 0,")
                .append(fmt(PROJECTION_BAR_BOTTOM))
                .append(" to 1,")
                .append(fmt(PROJECTION_BAR_TOP))
                .append(" fc rgb \"#F2B8B5\"")
                .append(" fs solid 1.0 border rgb \"")
                .append(FRAME_COLOR)
                .append("\" lw 2\n");
        gp.append("\n");

        gp.append("# Green projected intervals\n");
        for (Interval interval : result.mergedIntervals()) {
            gp.append("set object ").append(objectId++)
                    .append(" rect from ")
                    .append(fmt(interval.from())).append(",").append(fmt(PROJECTION_BAR_BOTTOM))
                    .append(" to ")
                    .append(fmt(interval.to())).append(",").append(fmt(PROJECTION_BAR_TOP))
                    .append(" fc rgb \"").append(PROJECTION_COLOR)
                    .append("\" fs solid 0.85 border rgb \"").append(PROJECTION_COLOR)
                    .append("\" lw 2\n");
        }
        gp.append("\n");

        gp.append("# Labels\n");
        gp.append("set label 1 \"Full space of program execution paths\" at screen ")
                .append(fmt(VERTICAL_LABEL_SCREEN_X))
                .append(", screen ")
                .append(fmt(VERTICAL_LABEL_SCREEN_Y))
                .append(" rotate by 90 center font \",")
                .append(FONT_SIZE)
                .append("\"\n");

        gp.append("set label 2 \"code cov. = ")
                .append(String.format(Locale.US, "%.2f%%", result.codeCoveragePercent()))
                .append(", full space cov. = ")
                .append(String.format(Locale.US, "%.2f%%", result.fullSpaceCoveragePercent()))
                .append("\" at 0.5,")
                .append(fmt(COVERAGE_LABEL_Y))
                .append(" center font \",")
                .append(FONT_SIZE)
                .append("\"\n");
        gp.append("\n");

        gp.append("plot 1/0 notitle\n");

        return gp.toString();
    }

    static void runGnuplot(Path gpPath) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("gnuplot", gpPath.toAbsolutePath().toString())
                .inheritIO()
                .start();

        int exit = process.waitFor();
        if (exit != 0) {
            throw new IllegalStateException("gnuplot exited with code " + exit);
        }
    }

    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    static String fmt(double d) {
        return DF.format(d);
    }

    static String normalizePath(Path path) {
        return path.toAbsolutePath().toString().replace("\\", "/").replace("\"", "\\\"");
    }

    public record Spec(
            String shape,
            double size,
            List<Map.Entry<Double, Double>> patches
    ) {
    }

    public record Interval(double from, double to) {
        double length() {
            return to - from;
        }
    }

    public record Result(
            List<Interval> mergedIntervals,
            double coveredLength,
            double codeCoveragePercent,
            double fullSpaceCoveragePercent
    ) {
    }
}