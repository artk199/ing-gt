package pl.artkak.core.atm;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


class AtmServiceTest {

    private AtmService atmService;

    @BeforeEach
    void setUp() {
        atmService = new AtmService();
    }

    @Test
    void whenAtmWithTheSameIdAndRegionIsAdded_thenReturnedShouldByOnlyOneElement() {
        // setup
        atmService.addToRoute(1, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(1, 1, AtmRequestType.PRIORITY);

        // when
        Collection<IndexedAtmInfo> result = atmService.getSortedResult();

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void whenCreatingRouteForSingleRegion_thenShouldOrderAsTheyArrived() {
        // setup
        atmService.addToRoute(1, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(1, 2, AtmRequestType.STANDARD);

        // when
        Collection<IndexedAtmInfo> result = atmService.getSortedResult();

        // then
        assertThat(result).extracting("region", "atmId")
                .containsExactly(
                        Tuple.tuple(1, 1),
                        Tuple.tuple(1, 2));
    }

    @Test
    void testExample1() {
        // setup
        atmService.addToRoute(4, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(1, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(2, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(3, 2, AtmRequestType.PRIORITY);
        atmService.addToRoute(3, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(2, 1, AtmRequestType.SIGNAL_LOW);
        atmService.addToRoute(5, 2, AtmRequestType.STANDARD);
        atmService.addToRoute(5, 1, AtmRequestType.FAILURE_RESTART);

        // when
        Collection<IndexedAtmInfo> sortedResult = atmService.getSortedResult();

        // then
        assertThat(sortedResult).extracting("region", "atmId")
                .containsExactly(
                        Tuple.tuple(1, 1),
                        Tuple.tuple(2, 1),
                        Tuple.tuple(3, 2),
                        Tuple.tuple(3, 1),
                        Tuple.tuple(4, 1),
                        Tuple.tuple(5, 1),
                        Tuple.tuple(5, 2));
    }

    @Test
    void testExample2() {
        // setup
        atmService.addToRoute(1, 2, AtmRequestType.STANDARD);
        atmService.addToRoute(1, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(2, 3, AtmRequestType.PRIORITY);
        atmService.addToRoute(3, 4, AtmRequestType.STANDARD);
        atmService.addToRoute(4, 5, AtmRequestType.STANDARD);
        atmService.addToRoute(5, 2, AtmRequestType.PRIORITY);
        atmService.addToRoute(5, 1, AtmRequestType.STANDARD);
        atmService.addToRoute(3, 2, AtmRequestType.SIGNAL_LOW);
        atmService.addToRoute(2, 1, AtmRequestType.SIGNAL_LOW);
        atmService.addToRoute(3, 1, AtmRequestType.FAILURE_RESTART);

        // when
        Collection<IndexedAtmInfo> sortedResult = atmService.getSortedResult();

        // then
        assertThat(sortedResult).extracting("region", "atmId")
                .containsExactly(
                        Tuple.tuple(1, 2),
                        Tuple.tuple(1, 1),
                        Tuple.tuple(2, 3),
                        Tuple.tuple(2, 1),
                        Tuple.tuple(3, 1),
                        Tuple.tuple(3, 2),
                        Tuple.tuple(3, 4),
                        Tuple.tuple(4, 5),
                        Tuple.tuple(5, 2),
                        Tuple.tuple(5, 1));
    }

}
