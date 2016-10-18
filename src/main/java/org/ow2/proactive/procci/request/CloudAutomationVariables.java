package org.ow2.proactive.procci.request;

import java.io.IOException;

import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mael on 06/10/16.
 */

/**
 * Send crud request on cloud-automation-service/variables
 */
@Service
public class CloudAutomationVariables {

    private static final Logger logger = LogManager.getRootLogger();

    @Autowired
    private RequestUtils requestUtils;

    public String get(String key) throws CloudAutomationException {
        try {
            Response response = Request.Get(getResourceUrl(key))
                    .version(HttpVersion.HTTP_1_1)
                    .execute();

            return requestUtils.readHttpResponse(response.returnResponse());

        } catch (IOException ex) {
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to get on " + getResourceUrl(key) + ", exception : " + ex);
        }
    }


    public void post(String key, String value) throws CloudAutomationException {
        System.out.println("key : "+key+", value : "+value);
        try {
            HttpResponse response = Request.Post(getQueryUrl(key))
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1)
                    .bodyString(value, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to post on " + getQueryUrl(key) + ", exception : " + ex);
        }

    }

    public void update(String key, String value) throws CloudAutomationException {
        try {
            HttpResponse response = Request.Put(getResourceUrl(key))
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1)
                    .bodyString(value, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to put on " + getResourceUrl(key) + " ,exception : " + ex);
        }
    }

    public void delete(String key) throws CloudAutomationException {
        try {
            HttpResponse response = Request.Delete(getResourceUrl(key))
                    .version(HttpVersion.HTTP_1_1)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to delete on " + getQueryUrl(key) + ", exception : " + ex);
        }
    }

    private String getVariablesUrl() {
        return requestUtils.getProperty("cloud-automation-service.variables.endpoint");
    }

    private String getResourceUrl(String key) {
        return getVariablesUrl() + "/" + key;
    }

    private String getQueryUrl(String key) {
        return getVariablesUrl() + "?key=" + key;
    }

    private void checkStatus(HttpResponse response) throws CloudAutomationException {
        if (response.getStatusLine().getStatusCode() >= 300) {
            throw new CloudAutomationException(response.getStatusLine().getReasonPhrase());
        }
    }

}
