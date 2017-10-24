/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 *
 * This program is made available under the terms of the MIT License.
 * See the LICENSE file in the project root for more information.
 */
package com.microsoft.dhalion.metrics;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * An {@link InstanceMetric} holds metric information of a specific metric for instance of a component.
 */
public class InstanceMetric {
  // id of the component
  private final String componentName;

  // id of the instance
  private final String instanceName;

  // id of the component
  private final String metricName;

  //metric values at different times
  private Map<Instant, Double> metrics = new HashMap<>();

  public InstanceMetric(String componentName, String instanceName, String metricName) {
    this.componentName = componentName;
    this.instanceName = instanceName;
    this.metricName = metricName;
  }

  public void addValues(Map<Instant, Double> values) {
    metrics.putAll(values);
  }

  /**
   * Adds a metric and its value for the instance. This is a shorthand method for
   * {@link InstanceMetric#addValues} method. The assumption is that the metric will have only one
   * value.
   *
   * @param value metric value
   */
  public void addValue(double value) {
    metrics.put(Instant.now(), value);
  }

  public Map<Instant, Double> getValues() {
    return metrics;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public String getComponentName() {
    return componentName;
  }

  public String getMetricName() {
    return metricName;
  }

  public Double getValueSum() {
    return metrics.values().stream().mapToDouble(x -> x.doubleValue()).sum();
  }

  public boolean hasValueAboveLimit(double limit) {
    return metrics.values().stream().anyMatch(x -> x > limit);
  }

  @Override
  public String toString() {
    return "InstanceMetric{" +
        "component='" + componentName + '\'' +
        ", instance='" + instanceName + '\'' +
        ", metric='" + metricName + '\'' +
        ", metrics=" + metrics +
        '}';
  }
}
