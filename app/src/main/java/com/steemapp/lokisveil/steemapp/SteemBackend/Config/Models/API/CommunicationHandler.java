/*
package com.steemapp.lokisveil.steemapp.SteemBackend.Config.Models.API;

import android.support.v4.util.Pair;

import com.steemapp.lokisveil.steemapp.SteemBackend.Config.SteemJConfig;

import junit.runner.Version;

import java.io.IOException;
import java.net.URI;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

*/
/**
 * Created by boot on 2/28/2018.
 *//*


public class CommunicationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunicationHandler.class);

    */
/**
     * A preconfigured mapper instance used for de-/serialization of Json
     * objects.
     *//*

    private static ObjectMapper mapper = getObjectMapper();
    */
/** A counter for failed connection tries. *//*

    private int numberOfConnectionTries = 0;
    */
/** The client used to send requests. *//*

    private AbstractClient client;

    */
/**
     * Initialize the Connection Handler.
     *
     * @throws SteemCommunicationException
     *             If no connection to the Steem Node could be established.
     *//*

    public CommunicationHandler() throws SteemCommunicationException {
        // Create a new connection
        initializeNewClient();
    }

    */
/**
     * Initialize a new <code>client</code> by selecting one of the configured
     * endpoints.
     *
     * @throws SteemCommunicationException
     *             If no {@link AbstractClient} implementation for the given
     *             schema is available.
     *//*

    public void initializeNewClient() throws SteemCommunicationException {
        if (client != null) {
            try {
                client.closeConnection();
            } catch (IOException e) {
                throw new SteemCommunicationException("Could not close the current client connection.", e);
            }
        }
        // Get a new endpoint URI based on the number of retries.
        Pair<URI, Boolean> endpoint = SteemJConfig.getInstance().getNextEndpointURI(numberOfConnectionTries);

        if (endpoint.getLeft().getScheme().toLowerCase().matches("(http){1}[s]?")) {
            client = new HttpClient();
        } else if (endpoint.getLeft().getScheme().toLowerCase().matches("(ws){1}[s]?")) {
            client = new WebsocketClient();
        } else {
            throw new InvalidParameterException("No client implementation for the following protocol available: "
                    + endpoint.getLeft().getScheme().toLowerCase());
        }
    }

    */
/**
     * Perform a request to the web socket API whose response will automatically
     * get transformed into the given object.
     *
     * @param requestObject
     *            A request object that contains all needed parameters.
     * @param targetClass
     *            The type the response should be transformed to.
     * @param <T>
     *            The type that should be returned.
     * @return The server response transformed into a list of given objects.
     * @throws SteemTimeoutException
     *             If the server was not able to answer the request in the given
     *             time (@see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout()})
     * @throws SteemCommunicationException
     *             If there is a connection problem.
     * @throws SteemTransformationException
     *             If the SteemJ is unable to transform the JSON response into a
     *             Java object.
     * @throws SteemResponseException
     *             If the Server returned an error object.
     *//*

    public <T> List<T> performRequest(JsonRPCRequest requestObject, Class<T> targetClass)
            throws SteemCommunicationException, SteemResponseException {
        try {
            Pair<URI, Boolean> endpoint = SteemJConfig.getInstance().getNextEndpointURI(numberOfConnectionTries++);
            JsonRPCResponse rawJsonResponse = client.invokeAndReadResponse(requestObject, endpoint.getLeft(),
                    endpoint.getRight());
            LOGGER.debug("Received {} ", rawJsonResponse);

            if (rawJsonResponse.isError()) {
                throw rawJsonResponse.handleError(requestObject.getId());
            } else {
                // HANDLE NORMAL RESPONSE
                JavaType expectedResultType = mapper.getTypeFactory().constructCollectionType(List.class, targetClass);
                return rawJsonResponse.handleResult(expectedResultType, requestObject.getId());
            }
        } catch (SteemCommunicationException e) {
            LOGGER.warn("The connection has been closed. Switching the endpoint and reconnecting.");
            LOGGER.debug("For the following reason: ", e);

            return performRequest(requestObject, targetClass);
        }
    }

    */
/**
     * Get a preconfigured Jackson Object Mapper instance.
     *
     * @return The object mapper.
     *//*

    public static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));

            mapper.setDateFormat(simpleDateFormat);
            mapper.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            SimpleModule simpleModule = new SimpleModule("BooleanAsString", new Version(1, 0, 0, null, null, null));
            simpleModule.addSerializer(Boolean.class, new BooleanSerializer());
            simpleModule.addSerializer(boolean.class, new BooleanSerializer());

            mapper.registerModule(simpleModule);
        }

        return mapper;
    }
}
*/
