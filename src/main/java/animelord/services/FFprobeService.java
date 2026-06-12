package animelord.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FFprobeService {

    private static final String FFPROBE_PATH =
            "ffprobe";

    /*
        RETURNS DURATION IN SECONDS
    */
    public int getDurationSeconds(
            String videoPath)
            throws Exception {

        String result =
                executeFFprobe(
                        videoPath,
                        "format=duration"
                );

        if(result == null
                || result.isBlank()){

            return 0;

        }

        return (int) Math.round(
                Double.parseDouble(
                        result
                )
        );
    }

    /*
        RETURNS VIDEO WIDTH
    */
    public int getVideoWidth(
            String videoPath)
            throws Exception {

        String result =
                executeFFprobe(
                        videoPath,
                        "stream=width"
                );

        if(result == null
                || result.isBlank()){

            return 0;

        }

        return Integer.parseInt(
                result.trim()
        );
    }

    /*
        RETURNS VIDEO HEIGHT
    */
    public int getVideoHeight(
            String videoPath)
            throws Exception {

        String result =
                executeFFprobe(
                        videoPath,
                        "stream=height"
                );

        if(result == null
                || result.isBlank()){

            return 0;

        }

        return Integer.parseInt(
                result.trim()
        );
    }

    /*
        RETURNS RESOLUTION STRING

        Example:
        1920x1080
    */
    public String getResolution(
            String videoPath)
            throws Exception {

        int width =
                getVideoWidth(
                        videoPath
                );

        int height =
                getVideoHeight(
                        videoPath
                );

        return width
                + "x"
                + height;
    }

    /*
        EXECUTE FFPROBE
    */
    private String executeFFprobe(
            String videoPath,
            String showEntry)
            throws Exception {

        ProcessBuilder pb =
                new ProcessBuilder(
                        FFPROBE_PATH,
                        "-v",
                        "error",
                        "-select_streams",
                        "v:0",
                        "-show_entries",
                        showEntry,
                        "-of",
                        "default=noprint_wrappers=1:nokey=1",
                        videoPath
                );

        Process process =
                pb.start();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );

        String result =
                reader.readLine();

        process.waitFor();

        return result;
    }

}