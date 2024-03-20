package uk.ac.york.student.player;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The PlayerScore interface provides methods to calculate and convert a player's score.
 * The score is calculated based on the player's energy, study time, and the game's difficulty level.
 * The score is then converted to a string representation of a degree class.
 */
public interface PlayerScore {
    /**
     * Calculate a score for the player based on their energy, study time, and the game's difficulty level.
     * The score is calculated using a specific algorithm that has diminishing returns for the amount of work the player has done.
     * The algorithm also allows for a choice of difficulties, with a custom difficulty range.
     *
     * @param energy The player's energy level.
     * @param maxEnergy The maximum possible energy level.
     * @param studyTime The time the player has spent studying.
     * @param maxStudyTime The maximum possible study time.
     * @param difficulty The game's difficulty level.
     * @param maxDifficulty The maximum possible difficulty level.
     * @return The player's score, calculated based on the provided parameters.
     */
    default int calculateScore(float energy, float maxEnergy, float studyTime,
                               float maxStudyTime, int difficulty,
                               int maxDifficulty) {
        // Calculate the energy score, study score, and difficulty score.
        float energyScore = Math.min(energy / maxEnergy, 1.0f) * 100;
        float studyScore = Math.min(studyTime / maxStudyTime, 1.0f) * 100;
        float difficultyScore = (float) difficulty / (float) maxDifficulty;

        // Calculate the final score using the algorithm.
        double percentScoreDouble = Math.min(
            (200.0f * (1.0f
                - 10.0f * (1.0f / (energyScore + 20.0f)
                + 1.0f / (studyScore + 20.0f))))
                / (1.4 + difficultyScore * (0.26f))
            , 100.0f);

        // Return the final score as an integer.
        return (int) Math.round(percentScoreDouble);
    }

    /**
     * Overload of the calculateScore method that accepts integer parameters.
     * This method simply converts the integer parameters to floats and calls the other calculateScore method.
     *
     * @param energy The player's energy level.
     * @param maxEnergy The maximum possible energy level.
     * @param studyTime The time the player has spent studying.
     * @param maxStudyTime The maximum possible study time.
     * @param difficulty The game's difficulty level.
     * @param maxDifficulty The maximum possible difficulty level.
     * @return The player's score, calculated based on the provided parameters.
     */
    default int calculateScore(int energy, int maxEnergy, int studyTime,
                               int maxStudyTime, int difficulty,
                               int maxDifficulty) {
        return calculateScore(
            (float) energy, (float) maxEnergy, (float) studyTime,
            (float) maxStudyTime, difficulty, maxDifficulty
        );
    }

    /**
     * Convert a score to a string representation of a degree class.
     * The degree class is determined based on the score's value.
     *
     * @param score The score to convert.
     * @return A string representation of a degree class.
     */
    @Contract(pure = true)
    default @NotNull String convertScoreToString(int score) {
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