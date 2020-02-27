package helper;

import commentary.Commentary;
import gamestrategy.GameStrategy;
import gamestrategy.RandomWeightedGameStrategy;
import models.Match;
import models.Player;
import rules.ChangeStrikeRule;
import rules.PlayerOutRule;
import rules.Rule;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static helper.CricketSimulatorConstants.CONFIG_PATH;

public class MatchHelper {
    Properties properties;

    public MatchHelper() throws IOException {
        loadProperties();
    }

    private void loadProperties() throws IOException {
        InputStream input = new FileInputStream(CONFIG_PATH);
        properties = new Properties();
        properties.load(input);
    }

    public List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        for (int playerIndex = 1; playerIndex <= getNoOfPlayers(); playerIndex++)
            players.add(new Player(properties.getProperty("Player" + playerIndex), getFormattedList("Probability" + playerIndex), 0, 0, false));
        return players;
    }

    private int getNoOfPlayers() {
        return Integer.parseInt(properties.getProperty("NoOfPlayers"));
    }

    private List<Double> getFormattedList(String probability) {
        String[] probabilityItems = properties.getProperty(probability).split(",");
        List<Double> requiredFormatOfProbability = new ArrayList<>();
        for (String item : probabilityItems)
            requiredFormatOfProbability.add(Double.parseDouble(item));
        return requiredFormatOfProbability;
    }

    public Match createMatch() {
        return new Match(properties.getProperty("PlayingTeam"), properties.getProperty("OpposingTeam"), Integer.parseInt(properties.getProperty("Wickets")), Integer.parseInt(properties.getProperty("RunsNeededToWin")), Integer.parseInt(properties.getProperty("Overs")));
    }

    public Rule[] createRules() {
        return new Rule[]{new PlayerOutRule(), new ChangeStrikeRule()};
    }

    public GameStrategy createGameStrategy() {
        return new RandomWeightedGameStrategy();
    }

    public Commentary createCommentary() {
        return new Commentary();
    }
}
