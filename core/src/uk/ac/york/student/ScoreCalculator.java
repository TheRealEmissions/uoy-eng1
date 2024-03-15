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
     * @param energy The first variable to affect the score. Represents
     *               student's energy.
     * @param maxEnergy The maximum value that energy could've been.
     * @param studyTime The second variable to affect the score. Represents
     *                   the time spent by the student studying.
     * @param maxStudyTime The maximum time that the student could've
     *                       studied.
     * @param difficulty The difficulty level set.
     * @param maxDifficulty The maximum difficulty the game could've been set
     *                       to.
     * @return An int score out of 100 calculated with the given variable.
     */
    public static int calculateScore(float energy, float maxEnergy, float studyTime,
                                     float maxStudyTime, int difficulty,
                                     int maxDifficulty) {
        float energyScore = Math.min(energy / maxEnergy, 1.0f) * 100;
        float studyScore = Math.min(studyTime / maxStudyTime, 1.0f) * 100;

        float difficultyScore = (float) difficulty / (float) maxDifficulty;

        double percentScoreDouble = Math.min(
                (200.0f * (1.0f
                    - 10.0f * (1.0f / (energyScore + 20.0f)
                        + 1.0f / (studyScore + 20.0f))))
                / (1.4 + difficultyScore * (0.26f))
                , 100.0f);

        int percentScoreInt = (int) Math.round(percentScoreDouble);

        return percentScoreInt;
    }

    public static int calculateScore(int energy, int maxEnergy, int studyTime,
                                     int maxStudyTime, int difficulty,
                                     int maxDifficulty) {
        return calculateScore(
                (float) energy, (float) maxEnergy, (float) studyTime,
                (float) maxStudyTime, difficulty, maxDifficulty
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