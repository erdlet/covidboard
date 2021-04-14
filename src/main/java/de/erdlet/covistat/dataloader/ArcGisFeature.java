package de.erdlet.covistat.dataloader;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The feature object of the {@link ArcGisResponse}.
 *
 * @author erdlet
 *
 */
public class ArcGisFeature {

    @JsonbProperty("attributes")
    public ArcGisFeatureAttributes attributes;

    @Override
    public String toString() {
        return "ArcGisFeature [attributes=" + attributes + "]";
    }
}
