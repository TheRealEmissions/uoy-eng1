package uk.ac.york.student;

public class ScoreCalculator {
    private static void main(String[] arg) {
        // In order to easily see some outputs, make this public and uncomment:
        // System.out.println(calculateScore(50, 100, 50, 100, 3, 3));
    }

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