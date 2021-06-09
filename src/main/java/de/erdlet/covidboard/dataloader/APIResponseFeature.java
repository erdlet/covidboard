package de.erdlet.covidboard.dataloader;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The feature object of the {@link APIResponse}.
 *
 * @author erdlet
 *
 */
public class APIResponseFeature {

    @JsonbProperty("attributes")
    public APIResponseFeatureAttributes attributes;

    @Override
    public String toString() {
        return "ArcGisFeature [attributes=" + attributes + "]";
    }
}
