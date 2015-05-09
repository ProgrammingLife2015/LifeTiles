package nl.tudelft.lifetiles.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import nl.tudelft.lifetiles.controller.ViewController;
import nl.tudelft.lifetiles.graph.sequence.Sequence;
import nl.tudelft.lifetiles.graph.sequence.SequenceImplementation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class ViewControllerTest {
    ViewController controller;
    Observer view;
    Map<String, Sequence> sequences;
    Sequence s1, s2, s3, s4;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        s1 = new SequenceImplementation("s1");
        s2 = new SequenceImplementation("s2");
        s3 = new SequenceImplementation("s3");
        s4 = new SequenceImplementation("s4");
        sequences = new HashMap<>();
        sequences.put("s1", s1);
        sequences.put("s2", s2);
        sequences.put("s3", s3);
        controller = new ViewController(sequences);
    }

    @Test
    public void testSetWrongVisible() {
        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s4);
        thrown.expect(IllegalArgumentException.class);
        controller.setVisible(newSequences);
    }

    @Test
    public void testObserve() {
        view = Mockito.mock(Observer.class);
        controller.addObserver(view);
        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s2);
        controller.setVisible(newSequences);
        Mockito.verify(view, Mockito.atLeastOnce()).update(
                Mockito.<Observable> any(), Mockito.<Object> any());
    }

}
