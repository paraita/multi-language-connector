package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;


/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Compute instance state for Inactive
 */
public final class StopCompute extends ComputeAction {

    private static StopCompute STOP_COMPUTE = new StopCompute();

    private StopCompute() {
        super(InfrastructureIdentifiers.COMPUTE_ACTION_SCHEME,
              InfrastructureIdentifiers.STOP,
              InfrastructureIdentifiers.STOP,
              new HashSet<Attribute>());
    }

    public static StopCompute getInstance() {
        return STOP_COMPUTE;
    }
}
