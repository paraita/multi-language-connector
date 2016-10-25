package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.occi.infrastructure.mixin.Contextualization;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class EntityTest {

    @Test
    public void constructorTest() {
        Kind k = Kinds.ENTITY;
        List<Mixin> mixins = new ArrayList<>();
        Contextualization contextualization = new Contextualization("userDataTest", null);
        mixins.add(contextualization);
        EntityImplemented implemented = new EntityImplemented("url", k, "titleTest2", mixins);

        assertThat(implemented.getId().toString()).startsWith("url");
        assertThat(implemented.getKind()).isEqualTo(k);
        assertThat(implemented.getTitle().get()).isEqualTo("titleTest2");
        assertThat(implemented.getMixins()).containsExactly(contextualization);
    }

    private class EntityImplemented extends Entity {

        EntityImplemented(String url, Kind k, String title, List<Mixin> mixins) {
            super(Optional.of(url), k, Optional.of(title), mixins);
        }

        @Override
        public EntityRendering getRendering() {
            return null;
        }
    }
}
