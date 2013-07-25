package org.glassfish.flow;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;


@Named("flow1")
public class Flow1 implements Serializable {
private static final long serialVersionUID = -7623501087369765218L;

    @FlowDefinition
    public Flow defineFlow(FacesContext context, FlowBuilder flowBuilder) {
        String flowId = "flow1";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        return flowBuilder.getFlow();
    }
}
