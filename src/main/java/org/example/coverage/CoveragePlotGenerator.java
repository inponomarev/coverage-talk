package org.example.coverage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class CoveragePlotGenerator {

    private static final int SVG_WIDTH = 900;
    private static final int SVG_HEIGHT = 900;

    private static final String BASE_FONT_NAME = "Arial";
    private static final int BASE_FONT_SIZE = 24;
    private static final int AXIS_LABEL_FONT_SIZE = 36;

    private static final String X_LABEL = "full coverage, %";
    private static final String Y_LABEL = "code coverage, %";

    private static final String LINE_COLOR = "red";
    private static final int LINE_WIDTH = 6;
    private static final int POINT_TYPE = 7;
    private static final double POINT_SIZE = 2.0;

    private static final DecimalFormat DF = new DecimalFormat(
            "0.######",
            DecimalFormatSymbols.getInstance(Locale.US)
    );

    public static void main(String[] args) throws Exception {
        List<Map<Double, Double>> plots = List.of(
                Map.of(0.0, 0.0),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0
                ),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0,
                        1.28, 16.0
                ),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0,
                        1.28, 16.0,
                        2.56, 29.0
                ),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0,
                        1.28, 16.0,
                        2.56, 29.0,
                        5.12, 45.0
                ),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0,
                        1.28, 16.0,
                        2.56, 29.0,
                        5.12, 45.0,
                        10.24, 73.0
                ),
                Map.of(
                        0.0, 0.0,
                        0.64, 8.0,
                        1.28, 16.0,
                        2.56, 29.0,
                        5.12, 45.0,
                        10.24, 73.0,
                        20.48, 92.0
                )
        );

        Path outputDir = Path.of("target/generated-slides/images");
        generateAll(plots, outputDir, true);
    }

    public static void generateAll(List<Map<Double, Double>> plots, Path outputDir, boolean runGnuplot)
            throws IOException, InterruptedException {

        if (plots == null || plots.isEmpty()) {
            throw new IllegalArgumentException("plots must not be null or empty");
        }

        for (Map<Double, Double> plot : plots) {
            validate(plot);
        }

        Files.createDirectories(outputDir);

        int index = 1;
        for (Map<Double, Double> plot : plots) {
            String baseName = String.format(Locale.US, "coverage-plot-%02d", index);
            Path gpPath = outputDir.resolve(baseName + ".gp");
            Path svgPath = outputDir.resolve(baseName + ".svg");

            AxisRanges axisRanges = computeAxisRanges(plot);

            String gnuplot = generateGnuplot(plot, axisRanges, svgPath);
            Files.writeString(gpPath, gnuplot);

            System.out.printf(
                    Locale.US,
                    "%s: %d points, xrange=[0,%s], yrange=[0,%s]%n",
                    baseName,
                    plot.size(),
                    fmt(axisRanges.xMax()),
                    fmt(axisRanges.yMax())
            );

            if (runGnuplot) {
                runGnuplot(gpPath);
            }

            index++;
        }
    }

    static void validate(Map<Double, Double> plot) {
        if (plot == null || plot.isEmpty()) {
            throw new IllegalArgumentException("each plot must not be null or empty");
        }

        for (Map.Entry<Double, Double> entry : plot.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("plot entries must not contain nulls");
            }
            if (entry.getKey() < 0.0) {
                throw new IllegalArgumentException("x values must be >= 0");
            }
            if (entry.getValue() < 0.0) {
                throw new IllegalArgumentException("y values must be >= 0");
            }
        }
    }

    static AxisRanges computeAxisRanges(Map<Double, Double> plot) {
        double xMax = 0.0;
        double yMax = 0.0;

        for (Map.Entry<Double, Double> entry : plot.entrySet()) {
            xMax = Math.max(xMax, entry.getKey());
            yMax = Math.max(yMax, entry.getValue());
        }

        // Small safety fallback so that the very first frame with a single (0,0) point still renders.
        if (xMax == 0.0) {
            xMax = 1.0;
        }
        if (yMax == 0.0) {
            yMax = 1.0;
        }

        return new AxisRanges(xMax, yMax);
    }

    static String generateGnuplot(Map<Double, Double> plot, AxisRanges axisRanges, Path svgPath) {
        StringBuilder gp = new StringBuilder();

        gp.append("set terminal svg size ")
                .append(SVG_WIDTH).append(",").append(SVG_HEIGHT)
                .append(" dynamic enhanced font \"")
                .append(BASE_FONT_NAME).append(",").append(BASE_FONT_SIZE)
                .append("\"\n");

        gp.append("set output \"").append(normalizePath(svgPath)).append("\"\n");
        gp.append("\n");

        gp.append("set xlabel \"").append(X_LABEL).append("\" font \",")
                .append(AXIS_LABEL_FONT_SIZE).append("\"\n");
        gp.append("set ylabel \"").append(Y_LABEL).append("\" font \",")
                .append(AXIS_LABEL_FONT_SIZE).append("\"\n");
        gp.append("\n");

        gp.append("set xrange [0:").append(fmt(axisRanges.xMax())).append("]\n");
        gp.append("set yrange [0:").append(fmt(axisRanges.yMax())).append("]\n");
        gp.append("\n");

        gp.append("set grid\n");
        gp.append("set key off\n");
        gp.append("\n");

        gp.append("$Data << EOD\n");
        sortedEntries(plot).forEach(entry ->
                gp.append(fmt(entry.getKey())).append(" ").append(fmt(entry.getValue())).append("\n")
        );
        gp.append("EOD\n");
        gp.append("\n");

        gp.append("plot $Data using 1:2 with linespoints \\\n");
        gp.append("    linecolor rgb \"").append(LINE_COLOR).append("\" ")
                .append("linewidth ").append(LINE_WIDTH).append(" \\\n");
        gp.append("    pointtype ").append(POINT_TYPE).append(" ")
                .append("pointsize ").append(fmt(POINT_SIZE)).append("\n");

        return gp.toString();
    }

    static List<Map.Entry<Double, Double>> sortedEntries(Map<Double, Double> plot) {
        return plot.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getKey))
                .toList();
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

    static String fmt(double d) {
        return DF.format(d);
    }

    static String normalizePath(Path path) {
        return path.toAbsolutePath().toString()
                .replace("\\", "/")
                .replace("\"", "\\\"");
    }

    public record AxisRanges(double xMax, double yMax) {
    }

    public static Map<Double, Double> orderedPlot(Object... xyPairs) {
        if (xyPairs.length % 2 != 0) {
            throw new IllegalArgumentException("xyPairs length must be even");
        }

        Map<Double, Double> result = new LinkedHashMap<>();
        for (int i = 0; i < xyPairs.length; i += 2) {
            double x = ((Number) xyPairs[i]).doubleValue();
            double y = ((Number) xyPairs[i + 1]).doubleValue();
            result.put(x, y);
        }
        return result;
    }
}