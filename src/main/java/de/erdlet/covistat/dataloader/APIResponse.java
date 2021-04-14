package de.erdlet.covistat.dataloader;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The parent class of the REST API. Contains metadata this application isn't interested in. This class exists only for
 * easier mapping of the result JSON.
 *
 * @author erdlet
 *
 */
public class APIResponse {

    @JsonbProperty("features")
    public List<APIResponseFeature> features;

    @Override
    public String toString() {
        return "ArcGisResponse [features=" + features + "]";
    }
}
