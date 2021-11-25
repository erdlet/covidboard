package de.erdlet.covidboard.dataloader;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The feature object of the {@link APIResponse}. Only necessary as a container for the
 * {@link APIResponseFeatureAttributes}.
 *
 * @author erdlet
 *
 */
public class APIResponseFeature {

    @JsonbProperty("attributes")
    public APIResponseFeatureAttributes attributes;

    @Override
    public String toString() {
        return "APIResponseFeature [attributes=" + attributes + "]";
    }
}
