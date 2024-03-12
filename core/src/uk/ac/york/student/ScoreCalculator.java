package uk.ac.york.student;

public class ScoreCalculator {
    private static void main(String[] arg) {
        // In order to easily see some outputs, make this public and uncomment:
        // System.out.println(calculateScore(50, 100, 50, 100, 3, 3));
    }

    /**
     * Calculate a score for the student/player.
     * <p>
     * This calculates the score using an algorithm which uses two variables.
     * The algorithm has diminishing returns for the amount that the student
     * has done (i.e. the gain from energy going from 10 to 20 is more than the
     * gain from 20 to 30).
     * <p>
     * <p>
     * The algorithm also allows for a choice of difficulties, with a custom
     * The algorithm also allows for a choice of difficulties, with a custom
     * difficulty range too.
     * </p>
     *
     * @param  energy The first variable to affect the score. Represents
     *                student's energy.
     * @param max_energy The maximum value that energy could've been.
     * @param study_time The second variable to affect the score. Represents
     *                   the time spent by the student studying.
     * @param max_study_time The maximum time that the student could've
     *                       studied.
     * @param difficulty The difficulty level set.
     * @param max_difficulty The maximum difficulty the game could've been set
     *                       to.
     * @return An int score out of 100 calculated with the given variable.
     */
    public static int calculateScore(float energy, float max_energy, float study_time,
                                     float max_study_time, int difficulty,
                                     int max_difficulty) {
        float energy_score = Math.min(energy / max_energy, 1.0f) * 100;
        float study_score = Math.min(study_time / max_study_time, 1.0f) * 100;

        float difficulty_score = (float) difficulty / (float) max_difficulty;

        double percent_score_double = Math.min(
                (200.0f * (1.0f
                    - 10.0f * (1.0f / (energy_score + 20.0f)
                        + 1.0f / (study_score + 20.0f))))
                / (1.4 + difficulty_score * (0.26f))
                , 100.0f);

        int percent_score_int = (int) Math.round(percent_score_double);

        return percent_score_int;
    }

    public static int calculateScore(int energy, int max_energy, int study_time,
                                     int max_study_time, int difficulty,
                                     int max_difficulty) {
        return calculateScore(
                (float) energy, (float) max_energy, (float) study_time,
                (float) max_study_time, difficulty, max_difficulty
        );
    }

    /**
     * Output an associated degree class given a score (out of 100).
     */
    public static String calculateGrade(int score) {
        if (score >= 70) {
            return "First-class Honours";
        } else if (score >= 60) {
            return "Upper second-class Honours";
        } else if (score >= 50) {
            return "Lower second-class Honours";
        } else if (score >= 40) {
            return "Third-class Honours";
        } else {
            return "Fail";
        }
    }

}