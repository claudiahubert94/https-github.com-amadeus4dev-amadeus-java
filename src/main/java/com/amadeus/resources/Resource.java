package com.amadeus.resources;

import com.amadeus.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.Getter;

/**
 * A generic resource as returned by all namespaced APIs.
 */
public class Resource {
  private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

  /**
   * The original response that this object is populated from.
   */
  private transient @Getter Response response;
  /**
   * The class used for deserialization.
   * @hide as only used internally
   */
  private transient @Getter Class deSerializationClass;

  protected Resource() {}

  /**
   * Turns a response into a Gson deserialized array of resources,
   * attaching the response to each resource.
   * @hide as only used internally
   */
  public static Resource[] fromArray(Response response, Class klass) {
    JsonElement responseData = response.getData();
    if (responseData == null) {
      responseData = new JsonArray(0);
    }
    Resource[] resources = (Resource[]) GSON.fromJson(responseData, klass);
    for (Resource resource : resources) {
      resource.response = response;
      resource.deSerializationClass = klass;
    }
    return resources;
  }

  /**
   * Turns a response into a Gson deserialized resource,
   * attaching the response to the resource.
   * @hide as only used internally
   */
  public static Resource fromObject(Response response, Class klass) {
    Resource resource = (Resource) GSON.fromJson(response.getData(), klass);
    resource.response = response;
    resource.deSerializationClass = klass;
    return resource;
  }
}
