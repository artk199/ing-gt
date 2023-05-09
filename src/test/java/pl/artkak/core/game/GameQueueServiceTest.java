package pl.artkak.core.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GameQueueServiceTest {

    private GameQueueService gameQueueService;

    @BeforeEach
    void setUp() {
        gameQueueService = new GameQueueService();
    }

    @Test
    void testWithOnlyOneClan() {
        // setup
        Clan clan = createClan(4, 50);

        // when
        List<List<Clan>> login = gameQueueService.group(6, List.of(clan));

        // then
        assertThat(login).containsExactly(List.of(clan));
    }

    @Test
    void exampleTest() {
        // setup
        Clan clan1 = createClan(4, 50);
        Clan clan2 = createClan(2, 70);
        Clan clan3 = createClan(6, 60);
        Clan clan4 = createClan(1, 15);
        Clan clan5 = createClan(5, 40);
        Clan clan6 = createClan(3, 45);
        Clan clan7 = createClan(1, 12);
        Clan clan8 = createClan(4, 40);

        List<Clan> clans = List.of(clan1, clan2, clan3, clan4, clan5, clan6, clan7, clan8);

        // when
        List<List<Clan>> login = gameQueueService.group(6, clans);

        // then
        assertThat(login).containsExactly(
                List.of(clan2, clan1),
                List.of(clan3),
                List.of(clan6, clan4, clan7),
                List.of(clan8),
                List.of(clan5)
        );
    }

    private Clan createClan(int numberOfPlayers, int points) {
        Clan clan = new Clan();
        clan.setNumberOfPlayers(numberOfPlayers);
        clan.setPoints(points);
        return clan;
    }

}
