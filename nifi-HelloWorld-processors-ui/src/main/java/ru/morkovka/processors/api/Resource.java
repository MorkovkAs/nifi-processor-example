package ru.morkovka.processors.api;

import org.apache.nifi.web.HttpServletConfigurationRequestContext;
import org.apache.nifi.web.HttpServletRequestContext;
import org.apache.nifi.web.NiFiWebConfigurationContext;
import org.apache.nifi.web.NiFiWebConfigurationRequestContext;
import org.apache.nifi.web.NiFiWebRequestContext;
import org.apache.nifi.web.Revision;
import org.apache.nifi.web.UiExtensionType;
import org.apache.nifi.web.ComponentDetails;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/criteria")
public class Resource  {

    @Context
    private ServletContext servletContext;

    @Context
    private HttpServletRequest request;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/set-context")
    public Response updateEvaluationContext(
            @QueryParam("name") final String name,
            @QueryParam("processorId") final String processorId,
            @QueryParam("clientId") final String clientId,
            @QueryParam("revision") final Long revision) {

        final NiFiWebConfigurationContext configurationContext = (NiFiWebConfigurationContext) servletContext.getAttribute("nifi-web-configuration-context");

        final NiFiWebConfigurationRequestContext requestContext = getConfigurationRequestContext(
                processorId, revision, clientId, true);

        Map<String, String> properties = new HashMap<>();
        properties.put("name", name);
        configurationContext.updateComponent(requestContext, null, properties);
        // load the criteria
        // generate the response
        final Response.ResponseBuilder response = Response.ok(new Object());
        return response.build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/get-context")
    public Response getEvaluationContext(
            @QueryParam("processorId") final String processorId,
            @QueryParam("clientId") final String clientId,
            @QueryParam("revision") final Long revision) {

        final NiFiWebConfigurationContext configurationContext = (NiFiWebConfigurationContext) servletContext.getAttribute("nifi-web-configuration-context");

        final NiFiWebConfigurationRequestContext requestContext = getConfigurationRequestContext(
                processorId, revision, clientId, true);

        ComponentDetails details = configurationContext.getComponentDetails(requestContext);

        final Response.ResponseBuilder response = Response.ok(details.getProperties());
        return response.build();
    }

    private NiFiWebRequestContext getRequestContext(final String processorId) {
        return new HttpServletRequestContext(UiExtensionType.ProcessorConfiguration, request) {
            @Override
            public String getId() {
                return processorId;
            }
        };
    }

    private NiFiWebConfigurationRequestContext getConfigurationRequestContext(final String processorId, final Long revision, final String clientId, final Boolean isDisconnectionAcknowledged) {
        return new HttpServletConfigurationRequestContext(UiExtensionType.ProcessorConfiguration, request) {
            @Override
            public String getId() {
                return processorId;
            }

            @Override
            public Revision getRevision() {
                return new Revision(revision, clientId, processorId);
            }

            @Override
            public boolean isDisconnectionAcknowledged() {
                return Boolean.TRUE.equals(isDisconnectionAcknowledged);
            }
        };
    }
}