package com.google.maps.clients;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a mechanism for running multiple HttpRequestInitializers in sequence.
 *
 * @author macd@google.com (Mark McDonald)
 */
public class HttpRequestInitializerPipeline implements HttpRequestInitializer {

  protected final List<HttpRequestInitializer> initializers;

  /**
   * Creates a new pipeline from the provided list of initializers.
   * @param existing The existing initializers are cloned, exactly like a new List<>(someList)
   */
  public HttpRequestInitializerPipeline(List<HttpRequestInitializer> existing) {
    initializers = new ArrayList<HttpRequestInitializer>(existing);
  }

  /**
   * Creates a new pipeline from the provided list of initializers.
   * @param initializers  The existing initializers to be used in the order specified.
   */
  public HttpRequestInitializerPipeline(HttpRequestInitializer... initializers) {
    this.initializers = Arrays.asList(initializers);
  }

  @Override
  public void initialize(HttpRequest httpRequest) throws IOException {
    for (HttpRequestInitializer initializer : initializers) {
      initializer.initialize(httpRequest);
    }
  }

  /**
   * Return the HttpRequestInitializer at the position specified.
   * @param index  The index, ordering consistent with use in the constructor.
   * @return The HttpRequestInitializer at position specified.
   */
  public HttpRequestInitializer getInitializer(int index) {
    return initializers.get(index);
  }
}
