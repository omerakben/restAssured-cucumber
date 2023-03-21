package utils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private Map<String, Object> contextData;

    public ScenarioContext() {
        contextData = new HashMap<>();
    }

    public void setContextData(String key, Object value) {
        contextData.put(key, value);
    }

    public Object getContextData(String value) {
        return contextData.get(value);
    }



}
