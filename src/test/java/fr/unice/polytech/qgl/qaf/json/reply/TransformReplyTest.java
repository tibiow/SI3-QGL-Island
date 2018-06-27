package fr.unice.polytech.qgl.qaf.json.reply;

import fr.unice.polytech.qgl.qaf.util.resource.ResourceType;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformReplyTest {
    private TransformReply transformReply;
    private TransformReply badTransformReply;

    @Before
    public void defineContext() {

        JSONObject transformReplyJSON = new JSONObject();
        transformReplyJSON.put("cost", 5);

        JSONObject extrasJSON = new JSONObject();
        extrasJSON.put("production", 1);
        extrasJSON.put("kind", "GLASS");
        transformReplyJSON.put("extras", extrasJSON.toString());

        transformReply = new TransformReply(transformReplyJSON.toString());

        JSONObject badtransformReplyJSON = new JSONObject();
        badtransformReplyJSON.put("costs", 5);

        JSONObject badextrasJSON = new JSONObject();
        badextrasJSON.put("productions", 1);
        badextrasJSON.put("kindof", "GLASS");
        badtransformReplyJSON.put("extras", badextrasJSON.toString());

        badTransformReply = new TransformReply(badtransformReplyJSON.toString());
    }

    @Test
    public void testParseObject() {
        assertEquals(transformReply.getCost(), 5);
        assertEquals(transformReply.getProduction(), 1);
        assertEquals(transformReply.getResourceProduced(), ResourceType.GLASS);

        assertEquals(badTransformReply.getCost(), 0);
        assertEquals(badTransformReply.getProduction(), 0);
        assertEquals(badTransformReply.getResourceProduced(), null);
    }
}
