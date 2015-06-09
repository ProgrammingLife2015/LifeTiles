package nl.tudelft.lifetiles.sequence.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A sequence entry, representing a row in the sequence table.
 *
 * @author Joren Hammudoglu
 *
 */
public final class SequenceEntry {

    /**
     * The sequence identifier property.
     */
    private final Property<String> identifier = new SimpleStringProperty();
    /**
     * The visible property.
     */
    private final Property<Boolean> visible = new SimpleBooleanProperty();
    /**
     * The reference property.
     */
    private final Property<Boolean> reference = new SimpleBooleanProperty();

    /**
     * Create a new sequence entry.
     *
     * @param identifier
     *            The sequence identifier property.
     * @param visible
     *            The visible property.
     * @param reference
     *            The reference property.
     */
    public SequenceEntry(final String identifier, final boolean visible,
            final boolean reference) {
        this.identifier.setValue(identifier);
        this.visible.setValue(visible);
        this.reference.setValue(reference);
    }
    /**
     * Get the identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier.getValue();
    }

    /**
     * Get the visible boolean.
     *
     * @return true if visible, else false.
     */
    public boolean getVisible() {
        return visible.getValue();
    }

    /**
     * @return the identifier property
     */
    public Property<String> identifierProperty() {
        return identifier;
    }

    /**
     * Set the visible property.
     *
     * @param visible
     *            true if visible, else false.
     */
    public void setVisible(final boolean visible) {
        this.visible.setValue(visible);
    }

    /**
     * @return the visible property
     */
    public Property<Boolean> visibleProperty() {
        return visible;
    }

    /**
     * Get the reference boolean.
     *
     * @return true if reference, else false.
     */
    public boolean getReference() {
        return reference.getValue();
    }

    /**
     * Set the reference property.
     *
     * @param reference
     *            true if reference, else false.
     */
    public void setReference(final boolean reference) {
        this.reference.setValue(reference);
    }

    /**
     * @return the reference property
     */
    public Property<Boolean> referenceProperty() {
        return reference;
    }

    /**
     * Create a visible, non-reference {@link SequenceEntry} from a
     * {@link Sequence}.
     *
     * @param sequence
     *            the sequence
     * @return a new {@link SequenceEntry}
     */
    public static SequenceEntry fromSequence(final Sequence sequence) {
        return fromSequence(sequence, true, false);
    }

    /**
     * Create a {@link SequenceEntry} from a {@link Sequence}.
     *
     * @param sequence
     *            the sequence
     * @param visible
     *            true if visible, else false.
     * @param reference
     *            true if reference, else false.
     * @return a new {@link SequenceEntry}
     */
    public static SequenceEntry fromSequence(final Sequence sequence,
            final boolean visible, final boolean reference) {
        return new SequenceEntry(sequence.getIdentifier(), visible, reference);
    }

}
