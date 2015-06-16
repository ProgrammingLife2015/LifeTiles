package nl.tudelft.lifetiles.sequence.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
     * The meta data.
     */
    private final Map<String, Property<String>> metaData = new HashMap<>();

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
        identifierProperty().setValue(identifier);
        visibleProperty().setValue(visible);
        referenceProperty().setValue(reference);
    }

    /**
     * Get the identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifierProperty().getValue();
    }

    /**
     * Get the visible boolean.
     *
     * @return true if visible, else false.
     */
    public boolean isVisible() {
        return visibleProperty().getValue();
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
        visibleProperty().setValue(visible);
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
    public boolean isReference() {
        return referenceProperty().getValue();
    }

    /**
     * Set the reference property.
     *
     * @param reference
     *            true if reference, else false.
     */
    public void setReference(final boolean reference) {
        this.referenceProperty().setValue(reference);
    }

    /**
     * @return the reference property
     */
    public Property<Boolean> referenceProperty() {
        return reference;
    }

    /**
     * Set the meta data.
     *
     * @param metaData
     *            the meta data
     */
    public void setMetaData(final Map<String, String> metaData) {
        for (Entry<String, String> metaValue : metaData.entrySet()) {
            // This method requires instantiation of a bunch of Properties.
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Property<String> value = new SimpleStringProperty(
                    metaValue.getValue());
            this.metaData.put(metaValue.getKey(), value);
        }
    }

    /**
     * Get the meta data for a key.
     *
     * @param key
     *            the key
     * @return the value
     */
    public String getMeta(final String key) {
        assert metaData.containsKey(key);
        return metaProperty(key).getValue();
    }

    /**
     * Get the meta data property.
     *
     * @param key
     *            the key
     * @return the property
     */
    public Property<String> metaProperty(final String key) {
        assert metaData.containsKey(key);
        return metaData.get(key);
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
