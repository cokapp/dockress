package com.github.dockerjava.api.model;

import java.util.Map;

import javax.annotation.CheckForNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.core.RemoteApiVersion;

/**
 * Used for Listing containers.
 *
 * @author Konstantin Pelykh (kpelykh@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Container {

    @JsonProperty("Command")
    private String command;

    @JsonProperty("Created")
    private Long created;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Image")
    private String image;

    /**
     * @since since {@link RemoteApiVersion#VERSION_1_21}
     */
    @JsonProperty("ImageID")
    private String imageId;

    @JsonProperty("Names")
    private String[] names;

    @JsonProperty("Ports")
    public ContainerPort[] ports;

    @JsonProperty("Labels")
    public Map<String, String> labels;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("State")
    private String state;    
    
    /**
     * @since ~{@link RemoteApiVersion#VERSION_1_19}
     */
    @JsonProperty("SizeRw")
    private Long sizeRw;

    /**
     * Returns only when {@link ListContainersCmd#withShowSize(java.lang.Boolean)} set
     *
     * @since ~{@link RemoteApiVersion#VERSION_1_19}
     */
    @JsonProperty("SizeRootFs")
    private Long sizeRootFs;

    /**
     * @since ~{@link RemoteApiVersion#VERSION_1_20}
     */
    @JsonProperty("HostConfig")
    private ContainerHostConfig hostConfig;

    /**
     * Docker API docs says "list of networks", but json names `networkSettings`.
     * So, reusing existed NetworkSettings model object.
     *
     * @since ~{@link RemoteApiVersion#VERSION_1_22}
     */
    @JsonProperty("NetworkSettings")
    private ContainerNetworkSettings networkSettings;

    public String getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getImage() {
        return image;
    }

    @CheckForNull
    public String getImageId() {
        return imageId;
    }

    public Long getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public ContainerPort[] getPorts() {
        return ports;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public String[] getNames() {
        return names;
    }

    /**
     * @see #sizeRw
     */
    @CheckForNull
    public Long getSizeRw() {
        return sizeRw;
    }

    /**
     * @see #sizeRootFs
     */
    @CheckForNull
    public Long getSizeRootFs() {
        return sizeRootFs;
    }

    /**
     * @see #networkSettings
     */
    @CheckForNull
    public ContainerNetworkSettings getNetworkSettings() {
        return networkSettings;
    }

    /**
     * @see #hostConfig
     */
    @CheckForNull
    public ContainerHostConfig getHostConfig() {
        return hostConfig;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
