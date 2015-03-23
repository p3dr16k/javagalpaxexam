package jemma.osgi.javagal;

import org.energy_home.jemma.zgd.GatewayEventListenerExtended;
import org.energy_home.jemma.zgd.jaxb.Address;
import org.energy_home.jemma.zgd.jaxb.BindingList;
import org.energy_home.jemma.zgd.jaxb.NodeDescriptor;
import org.energy_home.jemma.zgd.jaxb.NodeServices;
import org.energy_home.jemma.zgd.jaxb.ServiceDescriptor;
import org.energy_home.jemma.zgd.jaxb.Status;
import org.energy_home.jemma.zgd.jaxb.WSNNode;
import org.energy_home.jemma.zgd.jaxb.ZCLMessage;
import org.energy_home.jemma.zgd.jaxb.ZDPMessage;


/*=======================================================================

*FILE: GatewayEventListenerForTest.java
*
*DESCRIPTION:  
*REQUIREMENTS: 
*AUTHOR:       patrick
*COMPANY:      CSP s.c. a r.l. "Innovazione nelle ICT"
*VERSION:      1.0
*CREATED:      12-mar-2015
*LICENSE:      LGPL
*========================================================================
* 
*/

public class GatewayEventListenerForTest implements GatewayEventListenerExtended
{
    private String callback;

    public GatewayEventListenerForTest()
    {
        this.callback = "NO callback";
    }

    public String getCallback()
    {
        return callback;
    }   
    
    @Override
    public void gatewayStartResult(Status status)
    {
        callback = "gatewayStartResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void nodeDiscovered(Status status, WSNNode node)
    {
        callback = "nodeDiscovered";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void nodeRemoved(Status status, WSNNode node)
    {
        callback = "nodeRemoved";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void servicesDiscovered(Status status, NodeServices services)
    {
        callback = "serviceDiscovered";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void serviceDescriptorRetrieved(Status status, ServiceDescriptor service)
    {
        callback = "serviceDescriptorRetrieved";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void nodeDescriptorRetrieved(Status status, NodeDescriptor node)
    {
        callback = "nodeDescriptorRetrieved";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }
    
    @Override
    public void dongleResetResult(Status status)
    {
        callback = "dongleResetResult";        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void bindingResult(Status status)
    {
        callback = "bindingResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void unbindingResult(Status status)
    {
        callback = "unbindingResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void nodeBindingsRetrieved(Status status, BindingList bindings)
    {
        callback = "nodeBindingsRetrieved";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void leaveResult(Status status)
    {
        callback = "leaveResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void permitJoinResult(Status status)
    {
        callback = "permitJoinResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void nodeDescriptorRetrievedExtended(Status status, NodeDescriptor node, Address addressOfInterest)
    {
        callback = "nodeDescriptorRetrievedExtended";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void gatewayStopResult(Status status)
    {
        callback = "gatewayStopResult";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void leaveResultExtended(Status status, Address addressOfInteres)
    {
        callback = "leaveResultExtended";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void notifyZDPCommand(ZDPMessage message)
    {
        callback = "notifyZDPCommand";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void notifyZCLCommand(ZCLMessage message)
    {
        callback = "notifyZCLCommand";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }

    @Override
    public void FrequencyAgilityResponse(Status status)
    {
        callback = "FrequencyAgilityResponse";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CALLBACK: " + callback);
    }    
}
